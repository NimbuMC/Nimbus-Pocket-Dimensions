package net.nimbu.thaumaturgy.entity.client.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.entity.client.PixieEntityModel;
import net.nimbu.thaumaturgy.entity.custom.PixieEntity;

@Environment(EnvType.CLIENT)
public class PixieEyesFeatureRenderer<T extends LivingEntity> extends EyesFeatureRenderer<PixieEntity, PixieEntityModel> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(Identifier.of(Thaumaturgy.MOD_ID,"textures/entity/pixie/pixie_inside.png"));

    public PixieEyesFeatureRenderer(FeatureRendererContext<PixieEntity, PixieEntityModel> context, EntityModelLoader loader) {
        super(context); }

    @Override
    public RenderLayer getEyesTexture() { return SKIN; }
}
