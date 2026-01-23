package net.nimbu.thaumaturgy.block.entity.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.block.entity.custom.RevisualisingTableBlockEntity;

public class RevisualisingTableBlockEntityRenderer implements BlockEntityRenderer<RevisualisingTableBlockEntity> {

    public RevisualisingTableBlockEntityRenderer(BlockEntityRendererFactory.Context context){

    }

    @Environment(EnvType.CLIENT)
    private static int getRenderedItemTextureHeight(Item item) {
        MinecraftClient client = MinecraftClient.getInstance();

        BakedModel model = client.getItemRenderer()
                .getModel(new ItemStack(item), null, null, 0);

        for (BakedQuad quad : model.getQuads(null, null, client.world.random)) {
            Sprite sprite = quad.getSprite();
            if (sprite != null) {
                return sprite.getContents().getHeight();
            }
        }
        return 16; // fallback
    }

    @Override
    public void render(RevisualisingTableBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack = entity.getStack(0);
        matrices.push();

        int itemHeight = getRenderedItemTextureHeight(stack.getItem());
        float itemHeightModifier = (float) itemHeight/32; //divided by 16 then 2 as drawn from centre

        matrices.translate(0.5f,1.0+itemHeightModifier,0.5f);
        matrices.scale(1f, 1f, 1f); //z values seems to scale incorrectly for larger items
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getRenderingRotation()));



        itemRenderer.renderItem(stack, ModelTransformationMode.GUI, getLightLevel(entity.getWorld(),
                entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos){
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }

    public void setItemTextureHeight(ItemStack itemStack){
        itemStack.getItem().getName();
        //must navigate json file to find png height x scale
    }
}