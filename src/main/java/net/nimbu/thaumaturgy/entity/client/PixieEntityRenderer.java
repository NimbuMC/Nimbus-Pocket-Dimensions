package net.nimbu.thaumaturgy.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BeeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.entity.client.feature.PixieEyesFeatureRenderer;
import net.nimbu.thaumaturgy.entity.client.feature.PixieOverlayFeatureRenderer;
import net.nimbu.thaumaturgy.entity.custom.PixieEntity;

public class PixieEntityRenderer extends MobEntityRenderer<PixieEntity, PixieEntityModel> {

    public PixieEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PixieEntityModel(context.getPart(PixieEntityModel.PIXIE)), 0.1f); //last val is shadow size
        //this.addFeature(new PixieOverlayFeatureRenderer<>(this, context.getModelLoader())); //this allows for transparent outer layer
        //this.addFeature(new PixieEyesFeatureRenderer<>(this, context.getModelLoader()));
    }

    @Override
    public Identifier getTexture(PixieEntity entity) {
        return Identifier.of(Thaumaturgy.MOD_ID, "textures/entity/pixie/pixie.png");
    }


    @Override
    protected int getBlockLight(PixieEntity entity, BlockPos pos) {
        return 15;
    }

    @Override
    protected int getSkyLight(PixieEntity entity, BlockPos pos) {
        return 15;
    }
}
