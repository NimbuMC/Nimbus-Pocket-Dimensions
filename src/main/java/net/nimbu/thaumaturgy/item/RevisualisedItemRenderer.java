package net.nimbu.thaumaturgy.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Colors;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.joml.Quaternionf;

import java.util.List;

public class RevisualisedItemRenderer {

    //private final ItemModels models = new ItemModels();

    public static void renderItem(
            ItemStack stack,
            ModelTransformationMode renderMode,
            boolean leftHanded,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay,
            BakedModel model
    ) {
        /*System.out.println("Custom renderer active for: " + stack);
        matrices.push();

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        model = itemRenderer.getModel(Items.DIAMOND_SWORD.getDefaultStack(), null,null,0);

        boolean bl = renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED;
        boolean bl2 = true;
        model.getTransformation().getTransformation(renderMode).apply(leftHanded, matrices);

        //float scale = 1.1f;
        //matrices.scale(scale, scale, scale);

        matrices.translate(-0.5F, -0.5F, -0.5F);



        RenderLayer renderLayer = RenderLayers.getItemLayer(stack, bl2);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
        renderBakedItemModel(model, stack, 255, overlay, matrices, vertexConsumer);


        matrices.pop();*/
    }

    private static void renderBakedItemModel(BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices) {
        Random random = Random.create();
        long l = 42L;

        for (Direction direction : Direction.values()) {
            random.setSeed(42L);
            renderBakedItemQuads(matrices, vertices, model.getQuads(null, direction, random), stack, light, overlay);
        }

        random.setSeed(42L);
        renderBakedItemQuads(matrices, vertices, model.getQuads(null, null, random), stack, light, overlay);
    }

    private static void renderBakedItemQuads(MatrixStack matrices, VertexConsumer vertices, List<BakedQuad> quads, ItemStack stack, int light, int overlay) {
        boolean bl = !stack.isEmpty();
        MatrixStack.Entry entry = matrices.peek();

        for (BakedQuad bakedQuad : quads) {
            int i = Colors.WHITE;
            //if (bl && bakedQuad.hasColor()) {
            //    i = this.colors.getColor(stack, bakedQuad.getColorIndex());
            //}

            float f = ColorHelper.Argb.getAlpha(i) / 255.0F;
            float g = ColorHelper.Argb.getRed(i) / 255.0F;
            float h = ColorHelper.Argb.getGreen(i) / 255.0F;
            float j = ColorHelper.Argb.getBlue(i) / 255.0F;
            vertices.quad(entry, bakedQuad, g, h, j, f, light, overlay);
        }
    }


    private static boolean usesDynamicDisplay(ItemStack stack) {
        return stack.isIn(ItemTags.COMPASSES) || stack.isOf(Items.CLOCK);
    }
}






/*
        if (!model.isBuiltin() && bl) {
            boolean bl2;
            if (renderMode != ModelTransformationMode.GUI && !renderMode.isFirstPerson() && stack.getItem() instanceof BlockItem blockItem) {
                Block block = blockItem.getBlock();
                bl2 = !(block instanceof TranslucentBlock) && !(block instanceof StainedGlassPaneBlock);
            } else {
                bl2 = true;
            }

            RenderLayer renderLayer = RenderLayers.getItemLayer(stack, bl2);
            VertexConsumer vertexConsumer;

            //THIS CODE RUNS IF IT'S A COMPASS OR CLOCK WITH A GLINT
            if (usesDynamicDisplay(stack) && stack.hasGlint()) { //HAS GLINT WILL ALWAYS RETURN FALSE
                MatrixStack.Entry entry = matrices.peek().copy();
                if (renderMode == ModelTransformationMode.GUI) {
                    MatrixUtil.scale(entry.getPositionMatrix(), 0.5F);
                } else if (renderMode.isFirstPerson()) {
                    MatrixUtil.scale(entry.getPositionMatrix(), 0.75F);
                }

                vertexConsumer = getDynamicDisplayGlintConsumer(vertexConsumers, renderLayer, entry);
            } else if (bl2) {
                vertexConsumer = getDirectItemGlintConsumer(vertexConsumers, renderLayer, true, stack.hasGlint());
            } else {
                vertexConsumer = getItemGlintConsumer(vertexConsumers, renderLayer, true, stack.hasGlint());
            }

            this.renderBakedItemModel(model, stack, light, overlay, matrices, vertexConsumer);
        } else {
            this.builtinModelItemRenderer.renderItem(stack, renderMode, matrices, vertexConsumers, light, overlay);
        }*/