package net.nimbu.thaumaturgy.screen.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

public class InvisibleButton extends PressableWidget {
    protected final InvisibleButton.PressAction onPress;
    public InvisibleButton(int i, int j, int k, int l, InvisibleButton.PressAction onPress) {
        super(i, j, k, l, Text.of(""));
        this.onPress = onPress;
    }

    @Override
    public void onPress() {
        this.onPress.onPress(this);
    }

    public static InvisibleButton.Builder builder(Text message, InvisibleButton.PressAction onPress) {
        return new InvisibleButton.Builder(message, onPress);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        //context.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0xFFFFFFFF);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    @Environment(EnvType.CLIENT)
    public interface PressAction {
        void onPress(InvisibleButton button);
    }

    @Environment(EnvType.CLIENT)
    public static class Builder {
        private final Text message;
        private final InvisibleButton.PressAction onPress;
        @Nullable
        private Tooltip tooltip;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;

        public Builder(Text message, InvisibleButton.PressAction onPress) {
            this.message = message;
            this.onPress = onPress;
        }

        public InvisibleButton.Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public InvisibleButton.Builder width(int width) {
            this.width = width;
            return this;
        }

        public InvisibleButton.Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public InvisibleButton.Builder dimensions(int x, int y, int width, int height) {
            return this.position(x, y).size(width, height);
        }

        public InvisibleButton.Builder tooltip(@Nullable Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public InvisibleButton build() {
            InvisibleButton InvisibleButton = new InvisibleButton(this.x, this.y, this.width, this.height, this.onPress);
            InvisibleButton.setTooltip(this.tooltip);
            return InvisibleButton;
        }
    }
}
