package net.nimbu.thaumaturgy;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.nimbu.thaumaturgy.entity.ModEntities;
import net.nimbu.thaumaturgy.entity.client.PixieEntityModel;
import net.nimbu.thaumaturgy.entity.client.PixieEntityRenderer;
import net.nimbu.thaumaturgy.entity.client.SpellEntityRenderer;
import net.nimbu.thaumaturgy.util.ModModelPredicates;

public class ThaumaturgyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelPredicates.registerModelPredicates();

        EntityModelLayerRegistry.registerModelLayer(PixieEntityModel.PIXIE, PixieEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.PIXIE, PixieEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.SNOWBALL_COPY, FlyingItemEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.SPELL_PORTAL, SpellEntityRenderer::new);
    }
}
