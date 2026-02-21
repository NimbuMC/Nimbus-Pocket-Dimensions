package net.nimbu.pocketdimensions.entity.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;


import net.minecraft.util.Identifier;
import net.nimbu.pocketdimensions.PocketDimensions;

@Environment(EnvType.CLIENT)
public class PocketDimensionOrbModel extends Model {

    public static final EntityModelLayer ORB = new EntityModelLayer(Identifier.of(PocketDimensions.MOD_ID, "orb"), "main");

    private final ModelPart root;
    private final ModelPart orb;
    public final ModelPart ring;

    public PocketDimensionOrbModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
        this.orb = root.getChild("orb");
        this.ring = root.getChild("ring");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData orb = modelPartData.addChild("orb", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 16.5F, 0.0F));
        ModelPartData cube_r1 = orb.addChild("cube_r1", ModelPartBuilder.create()
                .uv(0, 40)
                .cuboid(-4.5F, -4.5F, -4.5F, 9.0F, 9.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 0.0F, -0.7854F, 0.0F, 0.6154F));


        ModelPartData ring = modelPartData.addChild("ring",
                ModelPartBuilder.create()
                        // Left side (YZ plane)
                        .uv(0, 0)
                        .cuboid(-9.0F, -2.0F, -9.0F, 0.0F, 2.0F, 18.0F)

                        // Right side (YZ plane)
                        .uv(0, 20)
                        .cuboid(9.0F, -2.0F, -9.0F, 0.0F, 2.0F, 18.0F)

                        // Front side (XZ plane)
                        .uv(36, 0)
                        .cuboid(-9.0F, -2.0F, -9.0F, 18.0F, 2.0F, 0.0F)

                        // Back side (XZ plane)
                        .uv(36, 20)
                        .cuboid(-9.0F, -2.0F, 9.0F, 18.0F, 2.0F, 0.0F),

                ModelTransform.pivot(0.0F, 16.5F, 0.0F)
        );
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        renderOrb(matrices, vertexConsumer, light, overlay, color);
    }

    public void renderOrb(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.root.render(matrices, vertices, light, overlay, color);
    }
}
