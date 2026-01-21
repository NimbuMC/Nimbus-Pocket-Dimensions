package net.nimbu.thaumaturgy.item;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class RevisualisedItemRenderer {

    public static void render(
            ItemStack stack,
            ModelTransformationMode mode,
            int light,
            int overlay,
            MatrixStack matrices,
            VertexConsumerProvider providers
    ) {
        // Your custom rendering logic here
        System.out.println("Custom renderer active for: " + stack);
    }
}
