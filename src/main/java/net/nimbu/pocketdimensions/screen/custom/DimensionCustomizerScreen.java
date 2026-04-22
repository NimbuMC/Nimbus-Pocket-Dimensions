package net.nimbu.pocketdimensions.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.block.ModBlocks;
import net.nimbu.pocketdimensions.component.ModComponentInitializer;
import net.nimbu.pocketdimensions.network.GatewayMaterialPayload;
import net.nimbu.pocketdimensions.screen.widgets.InvisibleButton;
import net.nimbu.pocketdimensions.screen.widgets.RGBSliderGroup;
import net.nimbu.pocketdimensions.screen.widgets.Slider;

public class DimensionCustomizerScreen extends HandledScreen<DimensionCustomizerScreenHandler> {

    public static final Identifier SLIDER_KNOB = Identifier.of(PocketDimensions.MOD_ID, "textures/gui/widgets/slider_knob.png");
    public static final Identifier BACKGROUND = Identifier.of(PocketDimensions.MOD_ID, "textures/gui/pocket_dimension_customizer/pocket_dimension_customizer.png");
    public static final Identifier BACKGROUND_0 = Identifier.of(PocketDimensions.MOD_ID, "textures/gui/pocket_dimension_customizer/pocket_dimension_customizer_0.png");
    public static final Identifier BACKGROUND_1 = Identifier.of(PocketDimensions.MOD_ID, "textures/gui/pocket_dimension_customizer/pocket_dimension_customizer_1.png");
    public static final Identifier BACKGROUND_2 = Identifier.of(PocketDimensions.MOD_ID, "textures/gui/pocket_dimension_customizer/pocket_dimension_customizer_2.png");
    public static final Identifier BACKGROUND_3 = Identifier.of(PocketDimensions.MOD_ID, "textures/gui/pocket_dimension_customizer/pocket_dimension_customizer_3.png");
    public static final Identifier BACKGROUND_4 = Identifier.of(PocketDimensions.MOD_ID, "textures/gui/pocket_dimension_customizer/pocket_dimension_customizer_4.png");
    private RGBSliderGroup grassSliders;
    private RGBSliderGroup leavesSliders;
    private RGBSliderGroup waterSliders;
    private RGBSliderGroup skySliders;
    private Slider doorSlider;

    public DimensionCustomizerScreen(DimensionCustomizerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, new PlayerInventory(inventory.player), Text.of(""));
    }

    @Override
    protected void init() {
        super.init();

        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;

        grassSliders = new RGBSliderGroup(x + backgroundWidth - 101, y + 114, 89, 46, 5, 3, handler.getGrassColour());
        grassSliders.forEachChild(this::addDrawableChild);
        leavesSliders = new RGBSliderGroup(x + backgroundWidth - 101, y + 114, 89, 46, 5, 3, handler.getFoliageColour());
        leavesSliders.forEachChild(this::addDrawableChild);
        waterSliders = new RGBSliderGroup(x + backgroundWidth - 101, y + 114, 89, 46, 5, 3, handler.getWaterColour());
        waterSliders.forEachChild(this::addDrawableChild);
        skySliders = new RGBSliderGroup(x + backgroundWidth - 101, y + 114, 89, 46, 5, 3, handler.getSkyColour());
        skySliders.forEachChild(this::addDrawableChild);
        doorSlider = new Slider(x + backgroundWidth - 113, y + 130, 68, 46, Text.of("Door Material"), ModComponentInitializer.PLAYER_GATEWAY_KEY.get(player).getGatewayMaterial(), 11);
        doorSlider.forEachChild(this::addDrawableChild);


        InvisibleButton grassColours = InvisibleButton.builder( //grass colours
                Text.literal("Grass colours"),
                button -> {
                    grassSliders.setVisibility(true);
                    leavesSliders.setVisibility(false);
                    waterSliders.setVisibility(false);
                    skySliders.setVisibility(false);
                    doorSlider.setVisibility(false);
                }
        ).dimensions(x+1, y + 7, 46, 17).build();
        addDrawableChild(grassColours);

        InvisibleButton foliageColours = InvisibleButton.builder( //leaf colours
                Text.literal("Foliage colours"),
                button -> {
                    grassSliders.setVisibility(false);
                    leavesSliders.setVisibility(true);
                    waterSliders.setVisibility(false);
                    skySliders.setVisibility(false);
                    doorSlider.setVisibility(false);
                }
        ).dimensions(x + 1, y + 24, 46, 17).build();
        addDrawableChild(foliageColours);

        InvisibleButton waterMenuButton = InvisibleButton.builder( //water colours and water fog colours
                Text.literal("Water colours"),
                button -> {
                    grassSliders.setVisibility(false);
                    leavesSliders.setVisibility(false);
                    waterSliders.setVisibility(true);
                    skySliders.setVisibility(false);
                    doorSlider.setVisibility(false);
                }
        ).dimensions(x + 1, y + 41, 46, 17).build();
        addDrawableChild(waterMenuButton);

        InvisibleButton skyMenuButton = InvisibleButton.builder(
                Text.literal("Sky colour"),
                button -> {
                    grassSliders.setVisibility(false);
                    leavesSliders.setVisibility(false);
                    waterSliders.setVisibility(false);
                    skySliders.setVisibility(true);
                    doorSlider.setVisibility(false);
                }
        ).dimensions(x + 1, y + 58, 46, 17).build();
        addDrawableChild(skyMenuButton);

        InvisibleButton doorMenuButton = InvisibleButton.builder(
                Text.literal("Door material"),
                button -> {
                    grassSliders.setVisibility(false);
                    leavesSliders.setVisibility(false);
                    waterSliders.setVisibility(false);
                    skySliders.setVisibility(false);
                    doorSlider.setVisibility(true);
                }
        ).dimensions(x + 1, y + 75, 46, 17).build();
        addDrawableChild(doorMenuButton);

        grassSliders.setVisibility(true);
        leavesSliders.setVisibility(false);
        waterSliders.setVisibility(false);
        skySliders.setVisibility(false);
        doorSlider.setVisibility(false);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);



        int[] skyColour = skySliders.getColour();
        int[] waterColour = waterSliders.getColour();
        int[] leavesColour = leavesSliders.getColour();
        int[] grassColour = grassSliders.getColour();

        if(grassSliders.getVisibility()) {
            renderBlock(context, Blocks.DIRT.getDefaultState(),51, 255, 255, 255);
            renderBlock(context, ModBlocks.GUI_GRASS.getDefaultState(), 51, grassColour[0], grassColour[1], grassColour[2]);
        }
        else if(skySliders.getVisibility()) {
            renderBlock(context, Blocks.WHITE_STAINED_GLASS.getDefaultState(), 51, skyColour[0], skyColour[1], skyColour[2]);
        }
        else if(waterSliders.getVisibility()) {
            renderBlock(context, ModBlocks.GUI_WATER.getDefaultState(), 51, waterColour[0], waterColour[1], waterColour[2]);
        }
        else if(leavesSliders.getVisibility()) {
            renderBlock(context, ModBlocks.GUI_OAK_LEAVES.getDefaultState(), 51, leavesColour[0], leavesColour[1], leavesColour[2]);
        }
        else if(doorSlider.getVisibility()){
            Block blockType;
            switch (doorSlider.getValue()){
                case 1: blockType=Blocks.OAK_PLANKS; break;
                case 2: blockType=Blocks.SPRUCE_PLANKS; break;
                case 3: blockType=Blocks.BIRCH_PLANKS; break;
                case 4: blockType=Blocks.JUNGLE_PLANKS; break;
                case 5: blockType=Blocks.ACACIA_PLANKS; break;
                //case 6: blockType=Blocks.DARK_OAK_PLANKS; break;
                case 7: blockType=Blocks.MANGROVE_PLANKS; break;
                case 8: blockType=Blocks.CHERRY_PLANKS; break;
                case 9: blockType=Blocks.CRIMSON_PLANKS; break;
                case 10: blockType=Blocks.WARPED_PLANKS; break;
                case 11: blockType=Blocks.STRIPPED_BAMBOO_BLOCK; break;
                default: blockType=Blocks.DARK_OAK_PLANKS; break;
            }
            renderBlock(context, blockType.getDefaultState(), 51, 255, 255, 255);
        }
//        else {
//            testSample(context, x + backgroundWidth - 70, y, 70, 70,
//                    0xFF000000 | (fogColour[0] << 16) | (fogColour[1] << 8) | fogColour[2],
//                    0xFF000000 | (waterColour[0] << 16) | (waterColour[1] << 8) | waterColour[2],
//                    0xFF000000 | ((waterColour[0] / 10) << 16) | ((waterColour[1] / 10) << 8) | (waterColour[2] / 10),
//                    0xFF000000 | (leavesColour[0] << 16) | (leavesColour[1] << 8) | leavesColour[2],
//                    0xFF000000 | (grassColour[0] << 16) | (grassColour[1] << 8) | grassColour[2]
//            );
//        }
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
    }

//    private void testSample(DrawContext context, int x, int y, int width, int height, int C1, int C2,int C3,int C4,int C5) {
//
//        int widthPer = width/5;
//
//        context.fill(x, y, x + widthPer, y + height, C1);
//        context.fill(x + widthPer, y, x + 2 * widthPer, y + height, C2);
//        context.fill(x + 2 * widthPer, y, x + 3 * widthPer, y + height, C3);
//        context.fill(x + 3 * widthPer, y, x + 4 * widthPer, y + height, C4);
//        context.fill(x + 4 * widthPer, y, x + 5 * widthPer, y + height, C5);
//    }

    private void renderBlock(
            DrawContext context,
            BlockState state,
            float scale,
            int r, int g, int b
    ) {

        int x = this.x + 72;
        int y = this.y + 80;
        MinecraftClient client = MinecraftClient.getInstance();

        MatrixStack matrices = context.getMatrices();
        matrices.push();

        matrices.translate(x, y, 100);
        matrices.scale(scale, -scale, scale); //flip vertically
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

        context.draw(); //flush

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

        if(grassSliders.getVisibility()){context.drawTexture(BACKGROUND_0, x,y,0,0, 256, 256);}
        if(leavesSliders.getVisibility()){context.drawTexture(BACKGROUND_1, x,y,0,0, 256, 256);}
        if(waterSliders.getVisibility()){context.drawTexture(BACKGROUND_2, x,y,0,0, 256, 256);}
        if(skySliders.getVisibility()){context.drawTexture(BACKGROUND_3, x,y,0,0, 256, 256);}
        if(doorSlider.getVisibility()){context.drawTexture(BACKGROUND_4, x,y,0,0, 256, 256);}

    }

    @Override
    public void removed() {
        applyChanges();
        super.removed();
    }

    private void applyChanges() {
        int[] fogColour = skySliders.getColour();
        int[] waterColour = waterSliders.getColour();
        int[] foliageColour = leavesSliders.getColour();
        int[] grassColour = grassSliders.getColour();

        int lighten = 40; //lighten fog uniformly for all colours
        int fogHex = (Math.min(255, fogColour[0] + lighten) << 16)
                | (Math.min(255, fogColour[1] + lighten) << 8)
                |  (Math.min(255, fogColour[2] + lighten));
        int skyHex = (fogColour[0] << 16) | (fogColour[1] << 8) | fogColour[2];
        int waterHex = (waterColour[0] << 16) | (waterColour[1] << 8) | waterColour[2];
        int waterFogHex = (waterColour[0] / 10 << 16) | (waterColour[1] / 10 << 8) | waterColour[2] / 10;
        int foliageHex = (foliageColour[0] << 16) | (foliageColour[1] << 8) | foliageColour[2];
        int grassHex = (grassColour[0] << 16) | (grassColour[1] << 8) | grassColour[2];

        handler.setBiomeColours(
                fogHex, skyHex, waterHex, waterFogHex, foliageHex, grassHex);

        ClientPlayNetworking.send(
                new GatewayMaterialPayload(doorSlider.getValue())
        );
    }
}
