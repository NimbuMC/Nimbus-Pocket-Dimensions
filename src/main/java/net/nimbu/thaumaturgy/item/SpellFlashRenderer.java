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
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.nimbu.thaumaturgy.component.ModDataComponentTypes;

import java.util.List;

public class SpellFlashRenderer {

    public static void renderFlash(
            BakedModel model,
            ItemStack stack,
            ModelTransformationMode renderMode,
            boolean leftHanded,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay
    ) {
        //THIS MAY BE BETTER TO DO BY GETTING A MODEL AND OVERLAPPING IT - MAY CAUSE Z FIGHTING HOWEVER. SEE "ItemRendererMixin" FOR CODE TO GET MODELS, PASS TO HERE

        matrices.push();

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        //BakedModel model = itemRenderer.getModel(ModItems.STAFF.getDefaultStack(), null,null,0);

        model.getTransformation().getTransformation(renderMode).apply(leftHanded, matrices);

        matrices.scale(1.01f,1.01f,1.01f);
        matrices.translate(-0.503F, -0.503F, -0.5F);


        RenderLayer renderLayer = RenderLayers.getItemLayer(stack, true);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
        renderBakedItemModel(model, light, overlay, matrices, vertexConsumer);


        matrices.pop();
    }

    private static void renderBakedItemModel(BakedModel model, int light, int overlay, MatrixStack matrices, VertexConsumer vertices) {
        Random random = Random.create();
        long l = 42L;

        for (Direction direction : Direction.values()) {
            random.setSeed(42L);
            renderBakedItemQuads(matrices, vertices, model.getQuads(null, direction, random), light, overlay);
        }

        random.setSeed(42L);
        renderBakedItemQuads(matrices, vertices, model.getQuads(null, null, random), light, overlay);
    }

    private static void renderBakedItemQuads(MatrixStack matrices, VertexConsumer vertices, List<BakedQuad> quads, int light, int overlay) {
        MatrixStack.Entry entry = matrices.peek();

        for (BakedQuad bakedQuad : quads) {
            float r = 0.8f;
            float g = 0.0f;
            float b = 1.0f;
            float a = 1.0f;
            int l = light;

            if (bakedQuad.getColorIndex() > 0) { //the spell flash
                //light = 0xF000F0;
                g=0.0f;
            }





            vertices.quad(entry, bakedQuad, r, g, b, a, 255, overlay);
        }
    }
}




/*
        ModelIdentifier modelId = new ModelIdentifier(
                Identifier.of("minecraft", "item/diamond_sword"),
                "inventory");

        BakedModel model =
                MinecraftClient.getInstance()
                        .getBakedModelManager()
                        .getModel(modelId);

        model = model.getOverrides().apply(model, stack, null, null, 0);*/