package net.nimbu.thaumaturgy;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.nimbu.thaumaturgy.block.entity.ModBlockEntityTypes;
import net.nimbu.thaumaturgy.block.entity.renderer.RevisualisingTableBlockEntityRenderer;
import net.nimbu.thaumaturgy.entity.ModEntities;
import net.nimbu.thaumaturgy.entity.client.PixieEntityModel;
import net.nimbu.thaumaturgy.entity.client.PixieEntityRenderer;
import net.nimbu.thaumaturgy.entity.client.SpellEntityRenderer;
import net.nimbu.thaumaturgy.network.ClientPocketRooms;
import net.nimbu.thaumaturgy.network.PocketDimClientNetworking;
import net.nimbu.thaumaturgy.network.RoomSyncPayload;
import net.nimbu.thaumaturgy.network.SingularRoomPayload;
import net.nimbu.thaumaturgy.particle.MagicParticle;
import net.nimbu.thaumaturgy.particle.ModParticles;
import net.nimbu.thaumaturgy.renderer.PocketDimensionBorderRenderer;
import net.nimbu.thaumaturgy.screen.ModScreenHanders;
import net.nimbu.thaumaturgy.screen.custom.RevisualisingTableScreen;
import net.nimbu.thaumaturgy.util.ModModelPredicates;

public class ThaumaturgyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PocketDimClientNetworking.register();

        PocketDimensionBorderRenderer.registerWorldRenderer();
        ModModelPredicates.registerModelPredicates();


        EntityModelLayerRegistry.registerModelLayer(PixieEntityModel.PIXIE, PixieEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.PIXIE, PixieEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.SNOWBALL_COPY, FlyingItemEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.SPELL_PORTAL, SpellEntityRenderer::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.MAGIC_PARTICLE, MagicParticle.Factory::new);

        BlockEntityRendererFactories.register(ModBlockEntityTypes.REVISUALISING_TABLE_BLOCK_ENTITY, RevisualisingTableBlockEntityRenderer::new);
        HandledScreens.register(ModScreenHanders.REVISUALISING_TABLE_SCREEN_HANDLER, RevisualisingTableScreen::new);

    }

}
