package net.nimbu.thaumaturgy.screen.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.*;
import net.minecraft.text.Text;
import net.nimbu.thaumaturgy.Thaumaturgy;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import static net.nimbu.thaumaturgy.screen.custom.PocketDimensionBiomeControllerScreen.SLIDER_KNOB;

public class ColourSlider extends ClickableWidget {

    private final int min = 0;
    private final int max = 255;
    private int value;
    private boolean mouseEnteredWithClick;
    private boolean mouseOutLastFrame;
    private final int colour;
    private final Text name;
    private ColourSlider rSlider;
    private ColourSlider gSlider;
    private ColourSlider bSlider;
    private int lowGrad;
    private int highGrad;

    public ColourSlider(int x, int y, int width, int height, Text objName, int initial, int colour) {
        super(x, y, width, height, Text.empty());
        name = objName;
        this.colour = colour;
        this.value = initial;
    }
    public void setOtherColourSliders(@Nullable ColourSlider r, ColourSlider g, ColourSlider b)
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
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        boolean mouseDown = GLFW.glfwGetMouseButton(MinecraftClient.getInstance().getWindow().getHandle(),GLFW.GLFW_MOUSE_BUTTON_1) == GLFW.GLFW_PRESS;
        if(mouseOutLastFrame && isHovered() && mouseDown)
        {
            mouseEnteredWithClick = true;
        }
        if(!mouseDown) mouseEnteredWithClick = false;

        if(mouseDown && !mouseEnteredWithClick && isHovered())
        {
            updateValue(mouseX);
        }

        context.drawText(MinecraftClient.getInstance().textRenderer,
                name.getString(),
                getX() - 20,
                getY(),
                colour,
                false);
        /*
        context.drawTexture(
                SLIDER_BAR,
                getX(), getY(),
                0, 0,
                width, height,
                width, height
        );


         */
        drawHorizontalGradient(
                context,
                getX(), getY(),
                width, height,
                lowGrad, highGrad
        );
        // KNOB
        int knobX = getX() + (int)((value - min) / (float)(max - min) * (width - 8));
        context.drawTexture(
                SLIDER_KNOB,
                knobX, getY(),
                0, 0,
                8, height,
                8, height
        );
        mouseOutLastFrame = !isHovered();
    }
    private void drawHorizontalGradient(
            DrawContext context,
            int x, int y, int width, int height,
            int leftColor, int rightColor
    ) {
        for (int i = 0; i < width; i+=4) {
            float t = i / (float)(width - 1);

            int r = (int)(lerp(t,
                    (leftColor >> 16) & 0xFF,
                    (rightColor >> 16) & 0xFF));
            int g = (int)(lerp(t,
                    (leftColor >> 8) & 0xFF,
                    (rightColor >> 8) & 0xFF));
            int b = (int)(lerp(t,
                    leftColor & 0xFF,
                    rightColor & 0xFF));

            int color = 0xFF000000 | (r << 16) | (g << 8) | b;

            context.fill(x + i, y + (height/2) - 1, x + i + 4, y + (height/2), color);
        }
    }

    float lerp(float t, int a, int b)
    {
        return Math.round(a + t * (b - a));
    }


    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}


    private void updateValue(double mouseX) {
        double t = (mouseX - getX()) / (double)(width - 1);
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
    }

    public void setGrads(int low, int high) {
        lowGrad  = 0xFF000000 | (low  & 0x00FFFFFF);
        highGrad = 0xFF000000 | (high & 0x00FFFFFF);
    }


    public int getValue() {
        return value;
    }
}
