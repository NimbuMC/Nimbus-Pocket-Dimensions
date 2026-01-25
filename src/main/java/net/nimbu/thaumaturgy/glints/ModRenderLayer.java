package net.nimbu.thaumaturgy.glints;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;


@Environment(EnvType.CLIENT)
public final class ModRenderLayer {

    private static final Identifier ENERGY_TEXTURE =
            Identifier.of("minecraft", "textures/misc/white.png");

    public static final RenderLayer ENERGY_OVERLAY = RenderLayer.MultiPhase.of(
            "energy_overlay",
            VertexFormats.POSITION_TEXTURE_COLOR,
            VertexFormat.DrawMode.QUADS,
            256,
            RenderLayer.MultiPhaseParameters.builder()
                    .program(RenderPhase.ENTITY_TRANSLUCENT_PROGRAM)
                    .texture(new RenderPhase.Texture(ENERGY_TEXTURE, false, false))
                    //.transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY) // Bright translucent blending
                    .cull(RenderPhase.DISABLE_CULLING) // No culling, always visible
                    .depthTest(RenderPhase.ALWAYS_DEPTH_TEST)
                    //.lightmap(RenderPhase.ENABLE_LIGHTMAP) // Lighting ON (or use fullbright light)
                    //.writeMaskState(RenderPhase.COLOR_MASK)
                    .build(false)
    );
/*
    public static final RenderLayer.MultiPhase DEBUG_FILLED_BOX = RenderLayer.of(
            "debug_filled_box",
            VertexFormats.POSITION_COLOR,
            VertexFormat.DrawMode.TRIANGLE_STRIP,
            1536,
            false,
            true,
            RenderLayer.MultiPhaseParameters.builder()
                    .program(RenderPhase.COLOR_PROGRAM)
                    //.layering(RenderPhase.VIEW_OFFSET_Z_LAYERING)
                    .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
                    .build(false)
    );

    public static final RenderPhase.Texturing GLINT_TEXTURING = new RenderPhase.Texturing(
            "glint_texturing", () -> setupGlintTexturing(8.0F), () -> RenderSystem.resetTextureMatrix()
    );
    private static void setupGlintTexturing(float scale) {
        long l = (long)(Util.getMeasuringTimeMs() * MinecraftClient.getInstance().options.getGlintSpeed().getValue() * 8.0);
        float f = (float)(l % 110000L) / 110000.0F;
        float g = (float)(l % 30000L) / 30000.0F;
        Matrix4f matrix4f = new Matrix4f().translation(-f, g, 0.0F); //animates?, doesnt move whole
        matrix4f.rotateZ((float) (Math.PI / 18)).scale(scale); //changes the scale




        RenderSystem.setTextureMatrix(matrix4f);
    }

    private static ShaderProgram MOD_GLINT_SHADER;


    public static final RenderLayer MOD_GLINT = RenderLayer.of(
            "mod_glint",
            VertexFormats.POSITION_TEXTURE,
            VertexFormat.DrawMode.QUADS,
            1536,
            RenderLayer.MultiPhaseParameters.builder()
                    .program(RenderPhase.GLINT_PROGRAM)
                    .texture(new RenderPhase.Texture(ItemRenderer.ITEM_ENCHANTMENT_GLINT, true, false))
                    .writeMaskState(RenderPhase.COLOR_MASK)
                    .cull(RenderPhase.DISABLE_CULLING)
                    .depthTest(RenderPhase.EQUAL_DEPTH_TEST)
                    .transparency(RenderPhase.GLINT_TRANSPARENCY)
                    .texturing(RenderPhase.GLINT_TEXTURING)
                    .build(false)
    )
*/
}


/*
@Environment(EnvType.CLIENT)
public abstract class ModRenderLayer extends RenderLayer{



    private static final RenderLayer MOD_GLINT = RenderLayer.of(
            "mod_glint",
            VertexFormats.POSITION_TEXTURE_COLOR,
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

    public ModRenderLayer(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    public static RenderLayer getGlint() {
        return MOD_GLINT;
    }



    /*
    //private static final Identifier CUSTOM_GLINT =
    //        Identifier.of(Thaumaturgy.MOD_ID, "textures/item/spirit_sword.png");



    public static RenderLayer getCustomGlint() {
        return RenderLayer.of(
                "custom_glint",
                VertexFormats.POSITION_TEXTURE,
                VertexFormat.DrawMode.QUADS,
                256,
                RenderLayer.MultiPhaseParameters.builder()
                        .program(RenderPhase.GLINT_PROGRAM)
                        .texture(new RenderPhase.Texture(CUSTOM_GLINT, true, false))
                        .writeMaskState(RenderPhase.COLOR_MASK)
                        .cull(RenderPhase.DISABLE_CULLING)
                        .depthTest(RenderPhase.EQUAL_DEPTH_TEST)
                        .transparency(RenderPhase.GLINT_TRANSPARENCY)
                        .texturing(RenderPhase.GLINT_TEXTURING)
                        .build(false)
        );
    }

    public static final RenderLayer GLINT = RenderLayer.of(
                "my_glint",
                VertexFormats.POSITION_TEXTURE,
                VertexFormat.DrawMode.QUADS,
                1536,
                RenderLayer.MultiPhaseParameters.builder()
                        .program(RenderPhase.GLINT_PROGRAM)
                        .texture(new RenderPhase.Texture(ItemRenderer.ITEM_ENCHANTMENT_GLINT, true, false))
                        .writeMaskState(RenderPhase.COLOR_MASK)
                        .cull(RenderPhase.DISABLE_CULLING)
                        .depthTest(RenderPhase.EQUAL_DEPTH_TEST)
                        .transparency(RenderPhase.GLINT_TRANSPARENCY)
                        .texturing(RenderPhase.GLINT_TEXTURING)
                        .build(false)
        );

     */

//}
