package net.nimbu.pocketdimensions.screen.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import static net.nimbu.pocketdimensions.screen.custom.DimensionCustomizerScreen.SLIDER_KNOB;

public class ColourSlider extends ClickableWidget {

    private final int min = 0;
    private final int max = 255;
    private int value;
    private boolean mouseEnteredWithClick;
    private boolean mouseOutLastFrame;
    private boolean held;
    private final int colour;
    private final Text name;
    private ColourSlider rSlider;
    private ColourSlider gSlider;
    private ColourSlider bSlider;
    private TextFieldWidget rTextBox;
    private TextFieldWidget gTextBox;
    private TextFieldWidget bTextBox;
    private int lowGrad;
    private int highGrad;

    public ColourSlider(int x, int y, int width, int height, Text objName, int initial, int colour) {
        super(x, y, width, height, Text.empty());
        name = objName;
        this.colour = colour;
        this.value = initial;
    }
    public void setOtherColourSliders(
            ColourSlider r, ColourSlider g, ColourSlider b,
            TextFieldWidget rText, TextFieldWidget gText, TextFieldWidget bText)
    {
        rSlider = r;
        gSlider = g;
        bSlider = b;
        rSlider.setGrads(
                (gSlider.getValue() << 8) | bSlider.getValue(),
                (255 << 16) | (gSlider.getValue() << 8) | bSlider.getValue());
        gSlider.setGrads(
                (rSlider.getValue() << 16) | bSlider.getValue(),
                (rSlider.getValue() << 16) | (255 << 8) | bSlider.getValue());
        bSlider.setGrads(
                (rSlider.getValue() << 16) | (gSlider.getValue() << 8),
                (rSlider.getValue() << 16) | (gSlider.getValue() << 8) | 255);

        rTextBox = rText;
        gTextBox = gText;
        bTextBox = bText;

        rTextBox.setText(rSlider.getValue() + "");
        gTextBox.setText(gSlider.getValue() + "");
        bTextBox.setText(bSlider.getValue() + "");
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        boolean mouseDown = GLFW.glfwGetMouseButton(MinecraftClient.getInstance().getWindow().getHandle(),GLFW.GLFW_MOUSE_BUTTON_1) == GLFW.GLFW_PRESS;
        if(mouseOutLastFrame && isHovered() && mouseDown) mouseEnteredWithClick = true;
        if(!mouseDown) mouseEnteredWithClick = false;
        if(mouseDown && !mouseEnteredWithClick && isHovered()) held = true;
        if(!mouseDown && held) held = false;
        if(held) updateValue(mouseX);
        else if(rTextBox.getText().matches("[0-9]+") && Integer.parseInt(rTextBox.getText()) != rSlider.getValue())
        {
            rSlider.setValue(Integer.parseInt(rTextBox.getText()));
        }
        else if(gTextBox.getText().matches("[0-9]+") && Integer.parseInt(gTextBox.getText()) != gSlider.getValue())
        {
            gSlider.setValue(Integer.parseInt(gTextBox.getText()));
        }
        else if(bTextBox.getText().matches("[0-9]+") && Integer.parseInt(bTextBox.getText()) != bSlider.getValue())
        {
            bSlider.setValue(Integer.parseInt(bTextBox.getText()));
        }


        context.drawText(MinecraftClient.getInstance().textRenderer,
                name.getString(),
                getX() - 14,
                getY(),
                colour,
                false);

        drawHorizontalGradient(
                context,
                getX(),
                getY(),
                width, height,
                lowGrad, highGrad
        );

        // KNOB
        //TODO: Knob brightness changing
        float brightness = (float) ((double)1 - ((double)value/(double)max)*0.5);
        int knobX = getX() + (int)((value - min) / (float)(max - min) * (width - 8));
        RenderSystem.setShaderColor(brightness, brightness, brightness, 1.0f);
        context.drawTexture(
                SLIDER_KNOB,
                knobX, getY(),
                0, 0,
                7, 7,
                7, 7
        );
        mouseOutLastFrame = !isHovered();

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }
    private void drawHorizontalGradient(
            DrawContext context,
            int x, int y, int width, int height,
            int leftColor, int rightColor
    ) {
        for (int i = 0; i < width - 2; i += 3) {
            float t = i / (float) (width - 3);

            int r = (int) (lerp(t, (leftColor >> 16) & 0xFF, (rightColor >> 16) & 0xFF));
            int g = (int) (lerp(t, (leftColor >> 8) & 0xFF, (rightColor >> 8) & 0xFF));
            int b = (int) (lerp(t, leftColor & 0xFF, rightColor & 0xFF));

            int color = 0xFF000000 | (r << 16) | (g << 8) | b;

            context.fill(x + i, y + 2, x + i + 3, y + 5, color);
        }
    }

    float lerp(float t, int a, int b)
    {
        return Math.round(a + t * (b - a));
    }


    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}


    private void updateValue(double mouseX) {
        double t = (mouseX - getX()) / (double)(width - 5);
        t = Math.clamp(t, 0.0, 1.0);
        value = min + (int)Math.round(t * (max - min));
        rSlider.setGrads(
                (gSlider.getValue() << 8) | bSlider.getValue(),
                (255 << 16) | (gSlider.getValue() << 8) | bSlider.getValue());
        gSlider.setGrads(
                (rSlider.getValue() << 16) | bSlider.getValue(),
                (rSlider.getValue() << 16) | (255 << 8) | bSlider.getValue());
        bSlider.setGrads(
                (rSlider.getValue() << 16) | (gSlider.getValue() << 8),
                (rSlider.getValue() << 16) | (gSlider.getValue() << 8) | 255);

        rTextBox.setText(rSlider.getValue() + "");
        gTextBox.setText(gSlider.getValue() + "");
        bTextBox.setText(bSlider.getValue() + "");
    }

    public void setValue(int x)
    {
        x = Math.clamp(x, 0, 255);
        value = x;
        rSlider.setGrads(
                (gSlider.getValue() << 8) | bSlider.getValue(),
                (255 << 16) | (gSlider.getValue() << 8) | bSlider.getValue());
        gSlider.setGrads(
                (rSlider.getValue() << 16) | bSlider.getValue(),
                (rSlider.getValue() << 16) | (255 << 8) | bSlider.getValue());
        bSlider.setGrads(
                (rSlider.getValue() << 16) | (gSlider.getValue() << 8),
                (rSlider.getValue() << 16) | (gSlider.getValue() << 8) | 255);

        rTextBox.setText(rSlider.getValue() + "");
        gTextBox.setText(gSlider.getValue() + "");
        bTextBox.setText(bSlider.getValue() + "");
    }

    public void setGrads(int low, int high) {
        lowGrad  = 0xFF000000 | (low  & 0x00FFFFFF);
        highGrad = 0xFF000000 | (high & 0x00FFFFFF);
    }


    public int getValue() {
        return value;
    }
}
