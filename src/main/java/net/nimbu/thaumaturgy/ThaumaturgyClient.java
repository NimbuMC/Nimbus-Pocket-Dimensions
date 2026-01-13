package net.nimbu.thaumaturgy;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.nimbu.thaumaturgy.entity.ModEntities;
import net.nimbu.thaumaturgy.entity.client.PixieModel;
import net.nimbu.thaumaturgy.entity.client.PixieRenderer;
import net.nimbu.thaumaturgy.util.ModModelPredicates;

public class ThaumaturgyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelPredicates.registerModelPredicates();

        EntityModelLayerRegistry.registerModelLayer(PixieModel.PIXIE, PixieModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.PIXIE, PixieRenderer::new);
    }
}
