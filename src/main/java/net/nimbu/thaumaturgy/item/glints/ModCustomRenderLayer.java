package net.nimbu.thaumaturgy.item.glints;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.item.ItemRenderer;

public class ModCustomRenderLayer extends RenderLayer {

    public ModCustomRenderLayer(String name, VertexFormat vertexFormat,
                                VertexFormat.DrawMode drawMode, int expectedBufferSize,
                                boolean hasCrumbling, boolean translucent, Runnable startAction,
                                Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize,
                hasCrumbling, translucent, startAction, endAction);
    }

    private static final RenderLayer NEW_GLINT = of(
            "new_glint",
            VertexFormats.POSITION_TEXTURE,
            VertexFormat.DrawMode.QUADS,
            1536,
            RenderLayer.MultiPhaseParameters.builder()
                    .program(GLINT_PROGRAM)
                    .texture(new RenderPhase.Texture(ItemRenderer.ITEM_ENCHANTMENT_GLINT, true, false))
                    .writeMaskState(COLOR_MASK)
                    .cull(DISABLE_CULLING)
                    .depthTest(EQUAL_DEPTH_TEST)
                    .transparency(GLINT_TRANSPARENCY)
                    .texturing(GLINT_TEXTURING)
                    .build(false)
    );
}
