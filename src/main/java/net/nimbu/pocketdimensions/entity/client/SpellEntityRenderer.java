package net.nimbu.pocketdimensions.entity.client;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.entity.custom.SpellPortalEntity;

public class SpellEntityRenderer extends EntityRenderer<SpellPortalEntity> {
    private static final Identifier TEXTURE = Identifier.of(PocketDimensions.MOD_ID, "textures/particle/spell_portal.png");
    private static final RenderLayer LAYER = RenderLayer.getEntityCutoutNoCull(TEXTURE);

    public SpellEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    protected int getBlockLight(SpellPortalEntity spellPortalEntity, BlockPos blockPos) {
        return 15;
    }

    public void render(SpellPortalEntity spellPortalEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(0.5F, 0.5F, 0.5F); //defines the bounding box size in terms of blocks. 0.5 in each dimention looks the same as a dropped item
        matrixStack.multiply(this.dispatcher.getRotation());
        MatrixStack.Entry entry = matrixStack.peek();
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);
        produceVertex(vertexConsumer, entry, i, 0.0F, 0, 0, 1);
        produceVertex(vertexConsumer, entry, i, 1.0F, 0, 1, 1);
        produceVertex(vertexConsumer, entry, i, 1.0F, 1, 1, 0);
        produceVertex(vertexConsumer, entry, i, 0.0F, 1, 0, 0);
        matrixStack.pop();
        super.render(spellPortalEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    private static void produceVertex(VertexConsumer vertexConsumer, MatrixStack.Entry matrix, int light, float x, int z, int textureU, int textureV) {
        vertexConsumer.vertex(matrix, x - 0.5F, z - 0.25F, 0.0F)
                .color(255,255,255,255)//.color(Colors.WHITE)
                .texture(textureU, textureV)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(matrix, 0.0F, 1.0F, 0.0F);
    }

    public Identifier getTexture(SpellPortalEntity spellPortalEntity) {
        return TEXTURE;
    }
}
