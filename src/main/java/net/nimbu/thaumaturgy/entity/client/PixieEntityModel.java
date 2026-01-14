package net.nimbu.thaumaturgy.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.entity.custom.PixieEntity;


public class PixieEntityModel<T extends PixieEntity> extends SinglePartEntityModel<T> {
    //see "CamelEntity" for referencing

    public static final EntityModelLayer PIXIE = new EntityModelLayer(Identifier.of(Thaumaturgy.MOD_ID, "pixie"), "main");

    private final ModelPart WingR;
    private final ModelPart WingL;
    private final ModelPart bb_main;
    public PixieEntityModel(ModelPart root) {
        this.WingR = root.getChild("WingR");
        this.WingL = root.getChild("WingL");
        this.bb_main = root.getChild("bb_main");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData WingR = modelPartData.addChild("WingR", ModelPartBuilder.create(), ModelTransform.pivot(6.0F, 19.0F, 0.0F));

        ModelPartData cube_r1 = WingR.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(1.0F, -3.0F, -1.0F, 0.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-9.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        ModelPartData WingL = modelPartData.addChild("WingL", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData cube_r2 = WingL.addChild("cube_r2", ModelPartBuilder.create().uv(0, 10).cuboid(1.0F, -3.0F, -1.0F, 0.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, -5.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(14, 0).cuboid(-2.0F, -5.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(14, 8).cuboid(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F))
                .uv(14, 14).cuboid(-3.0F, -6.0F, 3.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData cube_r3 = bb_main.addChild("cube_r3", ModelPartBuilder.create().uv(0, 26).cuboid(-11.0F, -5.0F, 0.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F))
                .uv(12, 20).cuboid(-11.0F, -5.0F, -6.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(8.0F, 0.0F, -2.0F, -1.5708F, 0.0F, 0.0F));

        ModelPartData cube_r4 = bb_main.addChild("cube_r4", ModelPartBuilder.create().uv(24, 20).cuboid(-3.0F, -6.0F, 5.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 20).cuboid(-3.0F, -6.0F, -1.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public void setAngles(PixieEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        WingR.render(matrices, vertexConsumer, light, overlay, color);
        WingL.render(matrices, vertexConsumer, light, overlay, color);
        bb_main.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return bb_main;
    }
}
