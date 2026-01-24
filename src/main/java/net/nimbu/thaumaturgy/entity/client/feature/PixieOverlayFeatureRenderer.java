package net.nimbu.thaumaturgy.entity.client.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.entity.client.PixieEntityModel;
import net.nimbu.thaumaturgy.entity.custom.PixieEntity;

@Environment(EnvType.CLIENT)
public class PixieOverlayFeatureRenderer<T extends LivingEntity> extends FeatureRenderer<PixieEntity, PixieEntityModel> {
    private final PixieEntityModel model;

    public PixieOverlayFeatureRenderer(FeatureRendererContext<PixieEntity, PixieEntityModel> context, EntityModelLoader loader) {
        super(context);
        this.model = new PixieEntityModel(loader.getModelPart(new EntityModelLayer(Identifier.of(Thaumaturgy.MOD_ID, "pixie"),"outer")));

    }

    public void render(
            MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, PixieEntity livingEntity, float f, float g, float h, float j, float k, float l
    ) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        boolean bl = minecraftClient.hasOutline(livingEntity) && livingEntity.isInvisible();
        if (!livingEntity.isInvisible() || bl) {
            VertexConsumer vertexConsumer;
            if (bl) {
                vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getOutline(this.getTexture(livingEntity)));
            } else {
                vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(livingEntity)));
            }

            this.getContextModel().copyStateTo(this.model);
            //this.model.animateModel(livingEntity, f, g, h);
            //this.model.setAngles(livingEntity, f, g, j, k, l);
            this.model.render(matrixStack, vertexConsumer, 0xF000F0, LivingEntityRenderer.getOverlay(livingEntity, 0.0F));
        }
    }


}