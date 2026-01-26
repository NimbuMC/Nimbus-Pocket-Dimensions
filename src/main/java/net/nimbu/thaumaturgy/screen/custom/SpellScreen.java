package net.nimbu.thaumaturgy.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
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
        EQUIPPED_SPELLS.add(Spells.POCKET_DIMENSION);
    }

    protected void drawSpellSprites(DrawContext context, float delta, int mouseX, int mouseY){
        int spellNo=0;
        for(Spell spell : EQUIPPED_SPELLS) {
            Identifier spellSprite = spell.getSpriteIdentifier();

            RenderSystem.setShader(GameRenderer::getPositionProgram);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.setShaderTexture(0, spellSprite);

            int x=0;
            int y=0;
            switch (spellNo){
                case 0:
                    x = this.x + 80;
                    y = this.y + 18;
                    break;
                case 1:
                    x = this.x + 120;
                    y = this.y + 35;
                    break;
            }


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
        this.close();
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
