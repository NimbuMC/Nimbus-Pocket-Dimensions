package net.nimbu.thaumaturgy.item.glints;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;


public abstract class WandCustomRenderLayer extends RenderLayer{
    public static final Identifier ALTERNATE_ENTITY_ENCHANTMENT_GLINT = Identifier.ofVanilla("textures/misc/alternate_enchanted_glint_entity.png");
    public static final Identifier ALTERNATE_ITEM_ENCHANTMENT_GLINT = Identifier.ofVanilla("textures/misc/alternate_enchanted_glint_item.png");

    private static final RenderLayer ALTERNATE_ARMOR_ENTITY_GLINT;
    private static final RenderLayer ALTERNATE_GLINT_TRANSLUCENT;
    private static final RenderLayer ALTERNATE_GLINT;
    private static final RenderLayer ALTERNATE_ENTITY_GLINT;
    private static final RenderLayer ALTERNATE_DIRECT_ENTITY_GLINT;

    public WandCustomRenderLayer(String name, VertexFormat vertexFormat, DrawMode drawMode, int expectedBufferSize,
                                 boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    public static RenderLayer getArmorEntityGlint() {
        return ALTERNATE_ARMOR_ENTITY_GLINT;
    }

    public static RenderLayer getGlintTranslucent() {
        return ALTERNATE_GLINT_TRANSLUCENT;
    }

    public static RenderLayer getGlint() {
        return ALTERNATE_GLINT;
    }

    public static RenderLayer getEntityGlint() {
        return ALTERNATE_ENTITY_GLINT;
    }

    public static RenderLayer getDirectEntityGlint() {
        return ALTERNATE_DIRECT_ENTITY_GLINT;
    }

    static
    {
        ALTERNATE_ARMOR_ENTITY_GLINT = of("alternate_armor_entity_glint", VertexFormats.POSITION_TEXTURE, DrawMode.QUADS, 1536, RenderLayer.MultiPhaseParameters.builder().program(ARMOR_ENTITY_GLINT_PROGRAM).texture(new RenderPhase.Texture(ALTERNATE_ENTITY_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(ENTITY_GLINT_TEXTURING).layering(VIEW_OFFSET_Z_LAYERING).build(false));
        ALTERNATE_GLINT_TRANSLUCENT = of("alternate_glint_translucent", VertexFormats.POSITION_TEXTURE, DrawMode.QUADS, 1536, RenderLayer.MultiPhaseParameters.builder().program(TRANSLUCENT_GLINT_PROGRAM).texture(new RenderPhase.Texture(ALTERNATE_ITEM_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).target(ITEM_ENTITY_TARGET).build(false));
        ALTERNATE_GLINT = of("alternate_glint", VertexFormats.POSITION_TEXTURE, DrawMode.QUADS, 1536, RenderLayer.MultiPhaseParameters.builder().program(GLINT_PROGRAM).texture(new RenderPhase.Texture(ALTERNATE_ITEM_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).build(false));
        ALTERNATE_ENTITY_GLINT = of("alternate_entity_glint", VertexFormats.POSITION_TEXTURE, DrawMode.QUADS, 1536, RenderLayer.MultiPhaseParameters.builder().program(ENTITY_GLINT_PROGRAM).texture(new RenderPhase.Texture(ALTERNATE_ENTITY_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).target(ITEM_ENTITY_TARGET).texturing(ENTITY_GLINT_TEXTURING).build(false));
        ALTERNATE_DIRECT_ENTITY_GLINT = of("alternate_entity_glint_direct", VertexFormats.POSITION_TEXTURE, DrawMode.QUADS, 1536, RenderLayer.MultiPhaseParameters.builder().program(DIRECT_ENTITY_GLINT_PROGRAM).texture(new RenderPhase.Texture(ALTERNATE_ENTITY_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(ENTITY_GLINT_TEXTURING).build(false));
    }
}