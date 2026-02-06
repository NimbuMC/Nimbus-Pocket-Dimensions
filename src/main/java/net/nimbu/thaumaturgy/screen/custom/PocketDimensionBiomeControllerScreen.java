package net.nimbu.thaumaturgy.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3i;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.network.ClientPocketDimensionPersistentState;
import net.nimbu.thaumaturgy.network.UpdateBiomePacket;
import net.nimbu.thaumaturgy.screen.widgets.ColourSlider;
import net.nimbu.thaumaturgy.screen.widgets.RGBSliderGroup;
import net.nimbu.thaumaturgy.worldgen.biome.DynamicBiomeEffects;

public class PocketDimensionBiomeControllerScreen extends HandledScreen<PocketDimensionBiomeControllerScreenHandler> {

    public static final Identifier SLIDER_BAR = Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/widgets/slider_bar.png");
    public static final Identifier SLIDER_KNOB = Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/widgets/slider_knob.png");
    private RGBSliderGroup fogSliders;
    private RGBSliderGroup waterSliders;
    private RGBSliderGroup waterFogSliders;
    private RGBSliderGroup foliageSliders;
    private RGBSliderGroup grassSliders;

    public PocketDimensionBiomeControllerScreen(PocketDimensionBiomeControllerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, Text.of(""));
    }

    @Override
    protected void init() {
        super.init();
        fogSliders = new RGBSliderGroup(x, y + 30, 90, 50, 5, 5, handler.getFogColour());
        fogSliders.forEachChild(this::addDrawableChild);
        waterSliders = new RGBSliderGroup(x + 120, y + 30, 90, 50, 5, 5, handler.getFogColour());
        waterSliders.forEachChild(this::addDrawableChild);
        waterFogSliders = new RGBSliderGroup(x + 240, y + 30, 90, 50, 5, 5, handler.getFogColour());
        waterFogSliders.forEachChild(this::addDrawableChild);
        foliageSliders = new RGBSliderGroup(x + 360, y + 30, 90, 50, 5, 5, handler.getFogColour());
        foliageSliders.forEachChild(this::addDrawableChild);
        grassSliders = new RGBSliderGroup(x + 480, y + 30, 90, 50, 5, 5, handler.getFogColour());
        grassSliders.forEachChild(this::addDrawableChild);

        ButtonWidget applyButton = ButtonWidget.builder(
                Text.literal("Apply"),
                button -> applyChanges()
        ).dimensions(x, y, 60, 20).build();
        addDrawableChild(applyButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        int[] colour = fogSliders.getColour();
        renderBlock(context, Blocks.OAK_LEAVES.getDefaultState(), x + 100,y, 100,
                colour[0], colour[1], colour[2]);
    }

    private void renderBlock(
            DrawContext context,
            BlockState state,
            int x, int y,
            float scale,
            int r, int g, int b
    ) {
        MinecraftClient client = MinecraftClient.getInstance();

        MatrixStack matrices = context.getMatrices();
        matrices.push();

        matrices.translate(x, y, 100);
        matrices.scale(scale, scale, scale);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45));

        int light = LightmapTextureManager.MAX_LIGHT_COORDINATE;

        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(r / 255f,g / 255f,b / 255f,1f);

        client.getBlockRenderManager().renderBlockAsEntity(
                state,
                matrices,
                context.getVertexConsumers(),
                light,
                OverlayTexture.DEFAULT_UV
        );

        context.draw(); // flush

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();

        matrices.pop();
    }

    @Override
    public void setFocused(boolean focused) {
        super.setFocused(true);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {

    }

    private void applyChanges() {
        int[] fogColour = fogSliders.getColour();
        int[] waterColour = waterSliders.getColour();
        int[] waterFogColour = waterFogSliders.getColour();
        int[] foliageColour = foliageSliders.getColour();
        int[] grassColour = grassSliders.getColour();
        handler.setBiomeColours(
                fogColour[0], fogColour[1], fogColour[2],
                waterColour[0], waterColour[1], waterColour[2],
                waterFogColour[0], waterFogColour[1], waterFogColour[2],
                foliageColour[0], foliageColour[1], foliageColour[2],
                grassColour[0], grassColour[1], grassColour[2]);
    }

}
