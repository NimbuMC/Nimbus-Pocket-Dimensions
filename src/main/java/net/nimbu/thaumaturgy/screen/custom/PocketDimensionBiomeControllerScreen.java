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
import net.nimbu.thaumaturgy.screen.widgets.InvisibleButton;
import net.nimbu.thaumaturgy.screen.widgets.RGBSliderGroup;
import net.nimbu.thaumaturgy.worldgen.biome.DynamicBiomeEffects;

public class PocketDimensionBiomeControllerScreen extends HandledScreen<PocketDimensionBiomeControllerScreenHandler> {

    public static final Identifier SLIDER_BAR = Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/widgets/slider_bar.png");
    public static final Identifier SLIDER_KNOB = Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/widgets/slider_knob.png");
    public static final Identifier BACKGROUND = Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/pocket_dimension_customizer/pocket_dimension_customizer.png");
    public static final Identifier BACKGROUND_0 = Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/pocket_dimension_customizer/pocket_dimension_customizer_0.png");
    public static final Identifier BACKGROUND_1 = Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/pocket_dimension_customizer/pocket_dimension_customizer_1.png");
    public static final Identifier BACKGROUND_2 = Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/pocket_dimension_customizer/pocket_dimension_customizer_2.png");
    public static final Identifier BACKGROUND_3 = Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/pocket_dimension_customizer/pocket_dimension_customizer_3.png");
    public static final Identifier BACKGROUND_4 = Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/pocket_dimension_customizer/pocket_dimension_customizer_4.png");
    public static final Identifier BACKGROUND_5 = Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/pocket_dimension_customizer/pocket_dimension_customizer_5.png");
    public static final Identifier BACKGROUND_6 = Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/pocket_dimension_customizer/pocket_dimension_customizer_6.png");
    private RGBSliderGroup fogSliders;
    private RGBSliderGroup waterSliders;
    private RGBSliderGroup foliageSliders;
    private RGBSliderGroup grassSliders;

    public PocketDimensionBiomeControllerScreen(PocketDimensionBiomeControllerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, new PlayerInventory(inventory.player), Text.of(""));
    }

    @Override
    protected void init() {
        super.init();
        fogSliders = new RGBSliderGroup(x + backgroundWidth - 90, y + 70, 90, 40, 5, 2, handler.getFogColour());
        fogSliders.forEachChild(this::addDrawableChild);
        waterSliders = new RGBSliderGroup(x + backgroundWidth - 90, y + 70, 90, 40, 5, 2, handler.getWaterColour());
        waterSliders.forEachChild(this::addDrawableChild);
        foliageSliders = new RGBSliderGroup(x + backgroundWidth - 90, y + 70, 90, 40, 5, 2, handler.getFoliageColour());
        foliageSliders.forEachChild(this::addDrawableChild);
        grassSliders = new RGBSliderGroup(x + backgroundWidth - 90, y + 120, 90, 40, 5, 2, handler.getGrassColour());
        grassSliders.forEachChild(this::addDrawableChild);

        InvisibleButton applyButton = InvisibleButton.builder(
                Text.literal("Apply"),
                button -> applyChanges()
        ).dimensions(x, y /*+ ((backgroundHeight * 9) / 10)*/, 60, backgroundHeight / 10).build();
        addDrawableChild(applyButton);

        InvisibleButton grassColours = InvisibleButton.builder( //grass colours
                Text.literal("Grass colours"),
                button -> {
                    fogSliders.setVisibility(false);
                    waterSliders.setVisibility(false);
                    foliageSliders.setVisibility(false);
                    grassSliders.setVisibility(true);
                }
        ).dimensions(x+1, y + 7, 46, 16).build();
        addDrawableChild(grassColours);

        InvisibleButton foliageColours = InvisibleButton.builder( //leaf colours
                Text.literal("Foliage colours"),
                button -> {
                    fogSliders.setVisibility(false);
                    waterSliders.setVisibility(false);
                    foliageSliders.setVisibility(true);
                    grassSliders.setVisibility(false);
                }
        ).dimensions(x + 1, y + 23, 46, 16).build();
        addDrawableChild(foliageColours);

        InvisibleButton waterMenuButton = InvisibleButton.builder( //water colours and water fog colours
                Text.literal("Water colours"),
                button -> {
                    fogSliders.setVisibility(false);
                    waterSliders.setVisibility(true);
                    foliageSliders.setVisibility(false);
                    grassSliders.setVisibility(false);
                }
        ).dimensions(x + 1, y + 39, 46, 16).build();
        addDrawableChild(waterMenuButton);

        //menu buttons
        InvisibleButton fogMenuButton = InvisibleButton.builder(
                Text.literal("Fog colour"),
                button -> {
                    fogSliders.setVisibility(true);
                    waterSliders.setVisibility(false);
                    foliageSliders.setVisibility(false);
                    grassSliders.setVisibility(false);
                }
        ).dimensions(x + 1, y + 71, 46, 16).build();
        addDrawableChild(fogMenuButton);


        fogSliders.setVisibility(true);
        waterSliders.setVisibility(false);
        foliageSliders.setVisibility(false);
        grassSliders.setVisibility(false);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        int[] fogColour = fogSliders.getColour();
        int[] waterColour = waterSliders.getColour();
        int[] foliageColour = foliageSliders.getColour();
        int[] grassColour = grassSliders.getColour();


        testSample(context, x + backgroundWidth - 70, y, 70, 70,
                0xFF000000 | (fogColour[0] << 16) | (fogColour[1] << 8) | fogColour[2],
                0xFF000000 | (waterColour[0] << 16) | (waterColour[1] << 8) | waterColour[2],
                0xFF000000 | ((waterColour[0] / 10) << 16) | ((waterColour[1] / 10) << 8) | (waterColour[2] / 10),
                0xFF000000 | (foliageColour[0] << 16) | (foliageColour[1] << 8) | foliageColour[2],
                0xFF000000 | (grassColour[0] << 16) | (grassColour[1] << 8) | grassColour[2]
        );
        //renderBlock(context, Blocks.OAK_LEAVES.getDefaultState(), x + 100,y, 100,colour[0], colour[1], colour[2]);

        if(grassSliders.getVisibility()){context.drawTexture(BACKGROUND_0, x,y,0,0, 256, 256);}
        if(foliageSliders.getVisibility()){context.drawTexture(BACKGROUND_1, x,y,0,0, 256, 256);}
        if(waterSliders.getVisibility()){context.drawTexture(BACKGROUND_2, x,y,0,0, 256, 256);}
        if(fogSliders.getVisibility()){context.drawTexture(BACKGROUND_4, x,y,0,0, 256, 256);}
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
    }

    private void testSample(DrawContext context, int x, int y, int width, int height, int C1, int C2,int C3,int C4,int C5) {

        int widthPer = width/5;

        context.fill(x, y, x + widthPer, y + height, C1);
        context.fill(x + widthPer, y, x + 2 * widthPer, y + height, C2);
        context.fill(x + 2 * widthPer, y, x + 3 * widthPer, y + height, C3);
        context.fill(x + 3 * widthPer, y, x + 4 * widthPer, y + height, C4);
        context.fill(x + 4 * widthPer, y, x + 5 * widthPer, y + height, C5);
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
        int x = (width - backgroundWidth) / 2; //background with and height variables are the dimensions of a default inventory menu
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(BACKGROUND, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    private void applyChanges() {
        int[] fogColour = fogSliders.getColour();
        int[] waterColour = waterSliders.getColour();
        int[] foliageColour = foliageSliders.getColour();
        int[] grassColour = grassSliders.getColour();
        handler.setBiomeColours(
                fogColour[0], fogColour[1], fogColour[2],
                waterColour[0], waterColour[1], waterColour[2],
                waterColour[0] / 10, waterColour[1] / 10, waterColour[2] / 10,
                foliageColour[0], foliageColour[1], foliageColour[2],
                grassColour[0], grassColour[1], grassColour[2]);
    }

}
