package net.nimbu.thaumaturgy.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.entity.custom.PixieEntity;

public class PixieEntityRenderer extends MobEntityRenderer<PixieEntity, PixieEntityModel<PixieEntity>> {

    public PixieEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PixieEntityModel<>(context.getPart(PixieEntityModel.PIXIE)), 0.1f); //last val is shadow size
        //this.addFeature(new SlimeOverlayFeatureRenderer<>(this, context.getModelLoader())); //this allows for transparent outer layer?
    }

    @Override
    public Identifier getTexture(PixieEntity entity) {
        return Identifier.of(Thaumaturgy.MOD_ID, "textures/entity/pixie/pixie.png");
    }

    @Override
    public void render(PixieEntity livingEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if(livingEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f,1f,1f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
