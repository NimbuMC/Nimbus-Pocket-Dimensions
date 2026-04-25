package net.nimbu.pocketdimensions;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.nimbu.pocketdimensions.block.ModBlocks;
import net.nimbu.pocketdimensions.block.entity.ModBlockEntityTypes;
import net.nimbu.pocketdimensions.block.entity.renderer.PocketDimensionCustomizerBlockEntityRenderer;
import net.nimbu.pocketdimensions.entity.ModEntities;
import net.nimbu.pocketdimensions.entity.client.*;
import net.nimbu.pocketdimensions.network.PocketDimClientNetworking;
import net.nimbu.pocketdimensions.network.ReloadRendererPayload;
import net.nimbu.pocketdimensions.particle.GatewayProjectileParticle;
import net.nimbu.pocketdimensions.particle.ModParticleTypes;
import net.nimbu.pocketdimensions.renderer.PocketDimensionBorderRenderer;
import net.nimbu.pocketdimensions.screen.ModScreenHandlers;
import net.nimbu.pocketdimensions.screen.custom.DimensionCustomizerScreen;

public class PocketDimensionsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PocketDimClientNetworking.register();

        PocketDimensionBorderRenderer.registerWorldRenderer();


        EntityModelLayerRegistry.registerModelLayer(PocketDimensionOrbModel.ORB, PocketDimensionOrbModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.SPELL_PORTAL, GatewayProjectileRenderer::new);

        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.GATEWAY_PROJECTILE_PARTICLE, GatewayProjectileParticle.Factory::new);

        BlockEntityRendererFactories.register(ModBlockEntityTypes.POCKET_DIMENSION_CUSTOMIZER_BLOCK_ENTITY, PocketDimensionCustomizerBlockEntityRenderer::new);

        HandledScreens.register(ModScreenHandlers.POCKET_DIM_BIOME_SCREEN_HANDLER, DimensionCustomizerScreen::new);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DIMENSION_CUSTOMIZER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OAK_GATEWAY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.JUNGLE_GATEWAY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ACACIA_GATEWAY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHERRY_GATEWAY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BAMBOO_GATEWAY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GUI_WATER, RenderLayer.getTranslucent());



        //For reloading the renderer after server things are done
        //needed as gateways need more time before reload and are buggy
        PayloadTypeRegistry.playS2C().register(
                ReloadRendererPayload.ID,
                ReloadRendererPayload.CODEC
        );
        ClientPlayNetworking.registerGlobalReceiver(
                ReloadRendererPayload.ID,
                (payload, context) -> {

                    context.client().execute(() -> {
                        MinecraftClient client = MinecraftClient.getInstance();

                        if (client.world != null) {
                            client.worldRenderer.reload();
                        }
                    });
                }
        );
    }


}
