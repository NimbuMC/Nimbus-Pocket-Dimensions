package net.nimbu.thaumaturgy;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.nimbu.thaumaturgy.block.entity.ModBlockEntityTypes;
import net.nimbu.thaumaturgy.block.entity.renderer.RevisualisingTableBlockEntityRenderer;
import net.nimbu.thaumaturgy.component.ModDataComponentTypes;
import net.nimbu.thaumaturgy.entity.ModEntities;
import net.nimbu.thaumaturgy.entity.client.PixieEntityModel;
import net.nimbu.thaumaturgy.entity.client.PixieEntityRenderer;
import net.nimbu.thaumaturgy.entity.client.SpellEntityRenderer;
import net.nimbu.thaumaturgy.particle.MagicParticle;
import net.nimbu.thaumaturgy.particle.ModParticles;
import net.nimbu.thaumaturgy.renderer.PocketDimensionBorderRenderer;
import net.nimbu.thaumaturgy.screen.ModScreenHanders;
import net.nimbu.thaumaturgy.screen.custom.RevisualisingTableScreen;
import net.nimbu.thaumaturgy.util.ModModelPredicates;

public class ThaumaturgyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelPredicates.registerModelPredicates();
        BlockEntityRendererFactories.register(
                ModBlockEntityTypes.POCKET_DIMENSION_BORDER_BLOCK,
                PocketDimensionBorderRenderer::new
        );
        PocketDimensionBorderRenderer.register();

        EntityModelLayerRegistry.registerModelLayer(PixieEntityModel.PIXIE, PixieEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.PIXIE, PixieEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.SNOWBALL_COPY, FlyingItemEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.SPELL_PORTAL, SpellEntityRenderer::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.MAGIC_PARTICLE, MagicParticle.Factory::new);

        BlockEntityRendererFactories.register(ModBlockEntityTypes.REVISUALISING_TABLE_BLOCK_ENTITY, RevisualisingTableBlockEntityRenderer::new);
        HandledScreens.register(ModScreenHanders.REVISUALISING_TABLE_SCREEN_HANDLER, RevisualisingTableScreen::new);


        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            if (stack.getOrDefault(ModDataComponentTypes.REVISUALISED,false)) {
                lines.add(Text.literal(""));
                lines.add(
                        Text.literal("Revisualised: ")
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
    }

}
