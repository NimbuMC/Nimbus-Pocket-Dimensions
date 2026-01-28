package net.nimbu.thaumaturgy;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.StickyKeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.block.ModBlocks;
import net.nimbu.thaumaturgy.block.entity.ModBlockEntityTypes;
import net.nimbu.thaumaturgy.block.entity.renderer.RevisualisingTableBlockEntityRenderer;
import net.nimbu.thaumaturgy.component.ModDataComponentTypes;
import net.nimbu.thaumaturgy.effect.ModEffects;
import net.nimbu.thaumaturgy.entity.ModEntities;
import net.nimbu.thaumaturgy.entity.client.PixieEntityModel;
import net.nimbu.thaumaturgy.entity.client.PixieEntityRenderer;
import net.nimbu.thaumaturgy.entity.client.SpellEntityRenderer;
import net.nimbu.thaumaturgy.item.ModItems;
import net.nimbu.thaumaturgy.network.PocketDimClientNetworking;
import net.nimbu.thaumaturgy.particle.MagicParticle;
import net.nimbu.thaumaturgy.particle.ModParticles;
import net.nimbu.thaumaturgy.renderer.PocketDimensionBorderRenderer;
import net.nimbu.thaumaturgy.screen.ModScreenHanders;
import net.nimbu.thaumaturgy.screen.custom.RevisualisingTableScreen;
import net.nimbu.thaumaturgy.screen.custom.SpellScreen;
import net.nimbu.thaumaturgy.screen.custom.SpellScreenHandler;
import net.nimbu.thaumaturgy.util.ModModelPredicates;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class ThaumaturgyClient implements ClientModInitializer {
    public static KeyBinding openSpellWheel;

    @Override
    public void onInitializeClient() {
        PocketDimClientNetworking.register();

        PocketDimensionBorderRenderer.registerWorldRenderer();
        ModModelPredicates.registerModelPredicates();


        EntityModelLayerRegistry.registerModelLayer(PixieEntityModel.PIXIE, PixieEntityModel::getTexturedModelData);
        //EntityModelLayerRegistry.registerModelLayer(PixieEntityModel.PIXIE_TRANSPARENT, PixieEntityModel::getTexturedModelDataTransparent);
        EntityRendererRegistry.register(ModEntities.PIXIE, PixieEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.SNOWBALL_COPY, FlyingItemEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.SPELL_PORTAL, SpellEntityRenderer::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.MAGIC_PARTICLE, MagicParticle.Factory::new);

        BlockEntityRendererFactories.register(ModBlockEntityTypes.REVISUALISING_TABLE_BLOCK_ENTITY, RevisualisingTableBlockEntityRenderer::new);
        HandledScreens.register(ModScreenHanders.REVISUALISING_TABLE_SCREEN_HANDLER, RevisualisingTableScreen::new);

        HandledScreens.register(ModScreenHanders.SPELL_SCREEN_HANDLER, SpellScreen::new);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLE_MAGIC_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MAGENTA_MAGIC_MUSHROOM, RenderLayer.getCutout());

        openSpellWheel = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.thaumaturgy.open_spell_wheel", /* translation key */
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G, /* default key */
                "category.thaumaturgy.controls" /* category */
        ));

        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            if (stack.getOrDefault(ModDataComponentTypes.REVISUALISED,false)) {
                lines.add(Text.literal(""));
                lines.add(
                        Text.literal("Revisualised:")
                                .formatted(Formatting.DARK_PURPLE)
                );
                lines.add(
                        Text.translatable(
                                "tooltip.modid.revisualised_item",
                                Text.translatable(stack.getItem().getTranslationKey()),
                                Text.translatable("tooltip.thaumaturgy.arrow"),
                                Text.translatable("item." + stack.get(ModDataComponentTypes.REPLACE_MODEL_NAMESPACE) + "." + stack.get(ModDataComponentTypes.REPLACE_MODEL_PATH))
                        ).formatted(Formatting.GRAY)
                );
            }
        });

        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            if (stack.isOf(ModItems.MAGIC_MUSHROOM_STEW)) {
                lines.add(
                        Text.literal("EPILEPSY WARNING: ")
                                .formatted(Formatting.RED)
                );
                lines.add(Text.literal("WARNING: This content contains flashing lights").formatted(Formatting.GRAY));
                lines.add(Text.literal("and high-contrast patterns that may trigger").formatted(Formatting.GRAY));
                lines.add(Text.literal("seizures in people with photosensitive epilepsy.").formatted(Formatting.GRAY));
                lines.add(Text.literal("Viewer discretion is advised.").formatted(Formatting.GRAY));
            }
        });

        HudRenderCallback.EVENT.register((DrawContext context, RenderTickCounter renderTickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();

            if(client.player==null) return;

            if(client.player.hasStatusEffect(ModEffects.MAGICAL)) {

                float time = renderTickCounter.getTickDelta(true);
                float hue = (MinecraftClient.getInstance().world.getTime() + time) * 0.03f % 1.0f;

                int rgb = Color.HSBtoRGB(hue, 1.0f, 1.0f) | 0xFF000000;

                int alpha = 50;
                int argb = (alpha << 24) | (rgb & 0x00FFFFFF);

                context.fill(
                        0, 0,
                        context.getScaledWindowWidth(),
                        context.getScaledWindowHeight(),
                        argb);
            }
        });



        ModelLoadingPlugin.register(pluginContext -> {
            pluginContext.addModels(
                    Identifier.of(Thaumaturgy.MOD_ID, "spell_glint/staff_glint")
            );
        });


    }

}
