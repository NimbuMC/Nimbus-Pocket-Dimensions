package net.nimbu.thaumaturgy.block.entity.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.block.entity.custom.RevisualisingTableBlockEntity;

public class RevisualisingTableBlockEntityRenderer implements BlockEntityRenderer<RevisualisingTableBlockEntity> {

    public static final SpriteIdentifier BOOK_TEXTURE = new SpriteIdentifier(
            SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier.ofVanilla("entity/enchanting_table_book")
    );
    private final BookModel book;

    public RevisualisingTableBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){
        this.book = new BookModel(ctx.getLayerModelPart(EntityModelLayers.BOOK));
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
    public void render(RevisualisingTableBlockEntity entity, float tickDelta, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {

        float g = entity.ticks + tickDelta; //tickDelta is the change in ticks since last call - prevents lag causing issues with rendering


        //the tool---------------------

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack = entity.getStack(entity.INPUT_TOOL_SLOT);
        matrixStack.push();

        int itemHeight = getRenderedItemTextureHeight(stack.getItem());
        float itemHeightModifier = (float) itemHeight/32; //divided by 16 then 2 as drawn from centre

        matrixStack.translate(0.5f,1.2+itemHeightModifier,0.5f);
        matrixStack.translate(0,MathHelper.sin(g * 0.03F) * 0.1F,0);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.rotation));



        itemRenderer.renderItem(stack, ModelTransformationMode.GUI, getLightLevel(entity.getWorld(),
                entity.getPos()), OverlayTexture.DEFAULT_UV, matrixStack, vertexConsumerProvider, entity.getWorld(), 1);


        matrixStack.pop();
        matrixStack.push();

        //the book-------------------------

        matrixStack.translate(0.5F, 0.75F, 0.5F);

        matrixStack.translate(0.0F, 0.1F + MathHelper.sin(g * 0.1F) * 0.01F, 0.0F);
        float h = entity.bookRotation - entity.lastBookRotation;

        while (h >= (float) Math.PI) {
            h -= (float) (Math.PI * 2);
        }

        while (h < (float) -Math.PI) {
            h += (float) (Math.PI * 2);
        }

        float k = entity.lastBookRotation + h * tickDelta;
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotation(-k));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(80.0F));
        float l = MathHelper.lerp(tickDelta, entity.pageAngle, entity.nextPageAngle);
        float m = MathHelper.fractionalPart(l + 0.25F) * 1.6F - 0.3F;
        float n = MathHelper.fractionalPart(l + 0.75F) * 1.6F - 0.3F;
        float o = MathHelper.lerp(tickDelta, entity.pageTurningSpeed, entity.nextPageTurningSpeed);
        this.book.setPageAngles(g, MathHelper.clamp(m, 0.0F, 1.0F), MathHelper.clamp(n, 0.0F, 1.0F), o);
        VertexConsumer vertexConsumer = BOOK_TEXTURE.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntitySolid);
        this.book.renderBook(matrixStack, vertexConsumer, light, overlay, -1);
        matrixStack.pop();
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