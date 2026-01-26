package net.nimbu.thaumaturgy.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import org.lwjgl.glfw.GLFW;

public class SpellScreen extends HandledScreen<SpellScreenHandler> {

    public static final Identifier GUI_TEXTURE =
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/spell_dial/spell_dial.png");

    public SpellScreen(SpellScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.setShaderColor(1f,1f,1f,0.9f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - backgroundWidth) / 2; //background with and height variables are the dimensions of a default inventory menu
        int y =((height - backgroundHeight) / 2)-3;

        context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight); //image width/height can be added as additional parameters
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        //super.render(context, mouseX, mouseY, delta);
        this.drawBackground(context, delta, mouseX, mouseY);
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {

    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();

        long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();
        boolean rightHeld = GLFW.glfwGetMouseButton(windowHandle, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;

        if (!rightHeld) {
            this.close(); //if right click is released
        }
    }
}
