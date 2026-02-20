package net.nimbu.thaumaturgy.block.entity.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.BookModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.block.entity.custom.PocketDimensionCustomizerBlockEntity;
import net.nimbu.thaumaturgy.block.entity.custom.RevisualisingTableBlockEntity;
import net.nimbu.thaumaturgy.entity.client.PixieEntityModel;
import net.nimbu.thaumaturgy.entity.client.PocketDimensionOrbModel;

public class PocketDimensionCustomizerBlockEntityRenderer implements BlockEntityRenderer<PocketDimensionCustomizerBlockEntity> {

    public static final Identifier ORB_TEXTURE =
            Identifier.of("thaumaturgy", "textures/entity/pocket_dimension_orb/pocket_dimension_orb.png");
    private final PocketDimensionOrbModel orb;

    public PocketDimensionCustomizerBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.orb = new PocketDimensionOrbModel(context.getLayerModelPart(PocketDimensionOrbModel.ORB));
    }

    @Override
    public void render(PocketDimensionCustomizerBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack = Items.BRICKS.getDefaultStack();
        float g = entity.ticks + tickDelta; //tickDelta is the change in ticks since last call - prevents lag causing issues with rendering

        matrices.push();
        matrices.translate(0.5f, 1.0f, 0.5f);
        matrices.translate(0,MathHelper.sin(g * 0.03F) * 0.1F,0);
        matrices.scale(1.2f, 1.2f, 1.2f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.rotation));

        float time = entity.getWorld().getTime() + tickDelta;
        this.orb.ring.yaw = -time * 0.03F;

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(ORB_TEXTURE));
        this.orb.renderOrb(matrices, vertexConsumer, 255, overlay, -1);
        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}