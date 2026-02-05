package net.nimbu.thaumaturgy.screen.custom;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
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

    public PocketDimensionBiomeControllerScreen(PocketDimensionBiomeControllerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, Text.of(""));
    }

    @Override
    protected void init() {
        super.init();
        fogSliders = new RGBSliderGroup(x, y + 30, 60, 50, 5, 5, handler.getFogColour());
        fogSliders.forEachChild(this::addDrawableChild);
        ButtonWidget applyButton = ButtonWidget.builder(
                Text.literal("Apply"),
                button -> applyChanges()
        ).dimensions(x, y, 60, 20).build();
        addDrawableChild(applyButton);
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
        handler.setFogColour(fogColour[0], fogColour[1], fogColour[2]);
    }

}
