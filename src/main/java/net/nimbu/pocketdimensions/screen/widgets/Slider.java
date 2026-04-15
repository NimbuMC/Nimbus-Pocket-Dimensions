package net.nimbu.pocketdimensions.screen.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.function.Consumer;

import static net.nimbu.pocketdimensions.screen.custom.DimensionCustomizerScreen.SLIDER_KNOB;

public class Slider extends ClickableWidget {

    private final int min = 0;
    private final int max;
    private int value;
    private boolean mouseEnteredWithClick;
    private boolean mouseOutLastFrame;
    private boolean held;
    private final Text name;
    private TextFieldWidget textBox;

    public Slider(int x, int y, int width, int height, Text objName, int initial, int max) {
        super(x, y, width, height, Text.empty());
        name = objName;
        this.max=max;
        this.value = initial;
        textBox = new TextFieldWidget(MinecraftClient.getInstance().textRenderer,
                x + width+3,
                y-11,
                20,
                17,
                Text.of(initial+""));
        textBox.setText(initial+"");
    }


    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        boolean mouseDown = GLFW.glfwGetMouseButton(MinecraftClient.getInstance().getWindow().getHandle(),GLFW.GLFW_MOUSE_BUTTON_1) == GLFW.GLFW_PRESS;
        if(mouseOutLastFrame && isHovered() && mouseDown) mouseEnteredWithClick = true;
        if(!mouseDown) mouseEnteredWithClick = false;
        if(mouseDown && !mouseEnteredWithClick && isHovered()) held = true;
        if(!mouseDown && held) held = false;
        if(held) this.updateValue(mouseX);
        else if(this.textBox.getText().matches("[0-9]+") && Integer.parseInt(textBox.getText()) != this.getValue()) //if value has changed after slider moved
        {
            this.setValue(Integer.parseInt(textBox.getText())); //confirm change at integer level
        }

        //title text
        context.drawText(MinecraftClient.getInstance().textRenderer,
                name.getString(),
                getX(),
                getY()-10,
                0xFFFFFF,
                false);

        //bar
        context.fill(
                getX(),
                getY() + 2,
                getX() + width-1,
                getY() + 5,
                0xFF404040
        );

        // knob
        int knobX = getX() + (int)((value - min) / (float)(max - min) * (width - 8));
        context.drawTexture(
                SLIDER_KNOB,
                knobX, getY(),
                0, 0,
                7, 7,
                7, 7
        );
        mouseOutLastFrame = !isHovered();
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}


    private void updateValue(double mouseX) {
        double t = (mouseX - getX()) / (double)(width - 5);
        t = Math.clamp(t, 0.0, 1.0);
        value = min + (int)Math.round(t * (max - min));

        textBox.setText(getValue() + "");
    }
    public void setValue(int x)
    {
        x = Math.clamp(x, 0, 255);
        value = x;

        textBox.setText(getValue() + "");
    }
    public int getValue() {
        return value;
    }

    public boolean getVisibility() { return this.visible; }

    public void setVisibility(boolean visibility) {
        forEachChild(x -> x.visible = visibility);
    }

    @Override
    public void forEachChild(Consumer<ClickableWidget> consumer) {
        for (ClickableWidget child : List.of(this, textBox)) {
            consumer.accept(child);
        }
    }
}
