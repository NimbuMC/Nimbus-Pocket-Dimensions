package net.nimbu.thaumaturgy.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.spell.Spell;
import net.nimbu.thaumaturgy.spell.Spells;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class SpellScreen extends HandledScreen<SpellScreenHandler> {

    private static final Identifier GUI_TEXTURE =
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/spell_dial/spell_dial.png");

    private final List<Spell> EQUIPPED_SPELLS = new ArrayList<>();



    public SpellScreen(SpellScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        EQUIPPED_SPELLS.add(Spells.POCKET_DIMENSION);
        EQUIPPED_SPELLS.add(Spells.EFFECT_CLEANSING);
    }

    protected void drawSpellSprites(DrawContext context, float delta, int mouseX, int mouseY){
        int spellNo=0;
        for(Spell spell : EQUIPPED_SPELLS) {
            Identifier spellSprite = spell.getSpriteIdentifier();

            RenderSystem.setShader(GameRenderer::getPositionProgram);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.setShaderTexture(0, spellSprite);

            int x=getButtonXPos(spellNo);
            int y=getButtonYPos(spellNo);

            context.drawTexture(spellSprite, x, y, 0, 0, 16, 16, 16, 16); //image width/height can be added as additional parameters
            spellNo++;
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.setShaderColor(1f,1f,1f,0.5f); //alpha not working, colour is
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        //background width and height variables are the dimensions of a default inventory menu (not the sprite, the actual image)

        context.drawTexture(GUI_TEXTURE, this.x, this.y-3, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        //super.render(context, mouseX, mouseY, delta);
        this.drawBackground(context, delta, mouseX, mouseY);
        this.drawSpellSprites(context, delta, mouseX, mouseY);
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {

    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();

        long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();
        boolean rightHeld = GLFW.glfwGetMouseButton(windowHandle, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;

        //close if right click is released
        if (!rightHeld){
            this.close();
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int spellNo = 0;
        for(Spell spell : EQUIPPED_SPELLS) {
            int x=getButtonXPos(spellNo);
            int y=getButtonYPos(spellNo);

            assert this.client.interactionManager != null;
            if(mouseX > x && mouseX < x+16 && mouseY > y && mouseY < y+16){
                this.client.interactionManager.clickButton(this.handler.syncId, spellNo);
            }

            spellNo++;
        }

        /*
        enchanting table button control
        for (int k = 0; k < 3; k++) {
            double d = mouseX - (i + 60);
            double e = mouseY - (j + 14 + 19 * k);
            if (d >= 0.0 && e >= 0.0 && d < 108.0 && e < 19.0 && this.handler.onButtonClick(this.client.player, k)) {
                this.client.interactionManager.clickButton(this.handler.syncId, k);
                return true;
            }
        }*/



        this.close();

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private int getButtonXPos(int buttonNo){
        switch (buttonNo){
            case 0:
                return this.x + 80;
            case 1:
                return this.x + 120;
            default:
                return 0;
        }
    }
    private int getButtonYPos(int buttonNo){
        switch (buttonNo){
            case 0:
                return this.y + 18;
            case 1:
                return this.y + 35;
            default:
                return 0;
        }
    }
}
