package net.nimbu.thaumaturgy.screen.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3i;

import java.util.List;
import java.util.function.Consumer;

public class RGBSliderGroup implements Widget, Drawable {
    private ColourSlider rSlider;
    private ColourSlider gSlider;
    private ColourSlider bSlider;
    private TextFieldWidget rTextBox;
    private TextFieldWidget gTextBox;
    private TextFieldWidget bTextBox;

    protected int width;
    protected int height;
    private int x;
    private int y;

    public RGBSliderGroup(int x, int y, int width, int height, int borderWidth, int barSeparation, int[] initialValues) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rSlider = new ColourSlider(
                x + borderWidth,
                y + borderWidth,
                width - (borderWidth * 2) - 20,
                (height - ((borderWidth + barSeparation) * 2)) / 3,
                Text.of("R"),
                initialValues[0],
                0xFF0000
        );

        int greenY = y + borderWidth + ((height - (borderWidth * 2)) / 3) + barSeparation;
        gSlider = new ColourSlider(
                x + borderWidth,
                greenY,
                width - (borderWidth * 2) - 20,
                (height - ((borderWidth + barSeparation) * 2)) / 3,
                Text.of("G"),
                initialValues[1],
                0x00FF00
        );

        int blueY = y + borderWidth + (((height - (borderWidth * 2)) * 2) / 3) + (2 * barSeparation);
        bSlider = new ColourSlider(
                x + borderWidth,
                blueY,
                width - (borderWidth * 2) - 20,
                (height - ((borderWidth + barSeparation) * 2)) / 3,
                Text.of("B"),
                initialValues[2],
                0x0000FF
        );

        rTextBox = new TextFieldWidget(MinecraftClient.getInstance().textRenderer,
                rSlider.getX() + rSlider.getWidth() + 5,
                y + borderWidth,
                30,
                (height - ((borderWidth + barSeparation) * 2)) / 3,
                Text.of("R"));

        rTextBox.setMaxLength(3);
        rTextBox.setText(rSlider.getValue() + "");

        gTextBox = new TextFieldWidget(MinecraftClient.getInstance().textRenderer,
                gSlider.getX() + gSlider.getWidth() + 5,
                greenY,
                30,
                (height - ((borderWidth + barSeparation) * 2)) / 3,
                Text.of("G"));

        gTextBox.setMaxLength(3);
        gTextBox.setText(gSlider.getValue() + "");

        bTextBox = new TextFieldWidget(MinecraftClient.getInstance().textRenderer,
                bSlider.getX() + bSlider.getWidth() + 5,
                blueY,
                30,
                (height - ((borderWidth + barSeparation) * 2)) / 3,
                Text.of("B"));

        bTextBox.setMaxLength(3);
        bTextBox.setText(bSlider.getValue() + "");


        rSlider.setOtherColourSliders(rSlider, gSlider, bSlider, rTextBox, gTextBox, bTextBox);
        gSlider.setOtherColourSliders(rSlider, gSlider, bSlider, rTextBox, gTextBox, bTextBox);
        bSlider.setOtherColourSliders(rSlider, gSlider, bSlider, rTextBox, gTextBox, bTextBox);
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public int[] getColour(){return new int[]{rSlider.getValue(), gSlider.getValue(), bSlider.getValue()};}

    @Override
    public void forEachChild(Consumer<ClickableWidget> consumer) {
        for(ClickableWidget child : List.of(rSlider, gSlider, bSlider, rTextBox, rSlider, bSlider))
        {
            consumer.accept(child);
        }
    }
}
