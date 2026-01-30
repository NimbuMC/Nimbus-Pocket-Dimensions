package net.nimbu.thaumaturgy.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.ThaumaturgyClient;
import net.nimbu.thaumaturgy.spell.Spell;
import net.nimbu.thaumaturgy.spell.Spells;

import java.util.ArrayList;
import java.util.List;

public class SpellScreen extends HandledScreen<SpellScreenHandler> {

    private static final Identifier GUI_TEXTURE =
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/spell_dial/spell_dial.png");


    private static final Identifier[] WHEEL_HIGHLIGHTS = new Identifier[]
            {
                    Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/spell_dial/spell_dial_highlight_0.png"),
                    Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/spell_dial/spell_dial_highlight_1.png"),
                    Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/spell_dial/spell_dial_highlight_2.png"),
                    Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/spell_dial/spell_dial_highlight_3.png"),
                    Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/spell_dial/spell_dial_highlight_4.png"),
                    Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/spell_dial/spell_dial_highlight_5.png"),
                    Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/spell_dial/spell_dial_highlight_6.png"),
                    Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/spell_dial/spell_dial_highlight_7.png"),
            };

    private final List<Spell> EQUIPPED_SPELLS = new ArrayList<>();
    private Vec2f lastSignificantMovedMouseDirection = Vec2f.ZERO;


    public SpellScreen(SpellScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        EQUIPPED_SPELLS.add(Spells.AERODETONATION);
        EQUIPPED_SPELLS.add(Spells.POCKET_DIMENSION);
        EQUIPPED_SPELLS.add(Spells.EFFECT_CLEANSING);
        EQUIPPED_SPELLS.add(Spells.UNBAKING_BREAD);
        EQUIPPED_SPELLS.add(Spells.SOARING);
        EQUIPPED_SPELLS.add(Spells.AMBIENCE_PIGSTEP);

    }

    protected void drawSpellSprites(DrawContext context, float delta, int mouseX, int mouseY) {
        int spellNo = 0;
        for (Spell spell : EQUIPPED_SPELLS) {
            Identifier spellSprite = spell.getSpriteIdentifier();

            RenderSystem.setShader(GameRenderer::getPositionProgram);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.setShaderTexture(0, spellSprite);

            int x = getButtonXPos(spellNo);
            int y = getButtonYPos(spellNo);

            context.drawTexture(spellSprite, x, y, 0, 0, 16, 16, 16, 16); //image width/height can be added as additional parameters
            spellNo++;
        }
    }

    protected void drawHighlight(DrawContext context, int index) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            context.drawTexture(WHEEL_HIGHLIGHTS[index], this.x, this.y, 0, 0, backgroundWidth, backgroundHeight);

            RenderSystem.disableBlend();
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 0.5f); //alpha not working, colour is
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        //background width and height variables are the dimensions of a default inventory menu (not the sprite, the actual image)

        context.drawTexture(GUI_TEXTURE, this.x, this.y - 3, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        //super.render(context, mouseX, mouseY, delta);
        this.drawBackground(context, delta, mouseX, mouseY);


        Vec2f mousePos = new Vec2f(
                (float) mouseX - (context.getScaledWindowWidth()/2f),
                (float) mouseY - (context.getScaledWindowHeight()/2f));
        if (mousePos.length() > 4) {
            lastSignificantMovedMouseDirection = mousePos.normalize();
        }
        float angle = vectorAngle(lastSignificantMovedMouseDirection) + 22.5f;
        int spellSlotChosen = (int) ((Math.floor(angle / 45f) + 10) % 8);
        this.drawHighlight(context, spellSlotChosen);

        this.drawSpellSprites(context, delta, mouseX, mouseY);
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {

    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        //close if right click is released
        if (!ThaumaturgyClient.openSpellWheel.isPressed()) {
            this.close();
        }
    }

    private float vectorAngle(Vec2f dir) {
        if (dir.x == 0) return (dir.y > 0) ? 90 : (dir.y == 0) ? 0 : 270;
        else if (dir.y == 0) return (dir.x >= 0) ? 0 : 180;
        int ret = (int) Math.round(Math.toDegrees(Math.atan(dir.y / dir.x)));
        if (dir.x < 0 && dir.y < 0) ret = 180 + ret;
        else if (dir.x < 0) ret = 180 + ret;
        else if (dir.y < 0) ret = 270 + (90 + ret);
        return ret;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        float angle = vectorAngle(lastSignificantMovedMouseDirection) + 22.5f;
        int spellSlotChosen = (int) ((Math.floor(angle / 45f) + 10) % 8);
        Thaumaturgy.LOGGER.info("spell slot chosen -> " + spellSlotChosen);
        if (spellSlotChosen >= EQUIPPED_SPELLS.size()) return false;
        assert this.client.interactionManager != null;
        this.client.interactionManager.clickButton(this.handler.syncId, spellSlotChosen);
        //for(Spell spell : EQUIPPED_SPELLS) {
        //    int x=getButtonXPos(spellNo);
        //    int y=getButtonYPos(spellNo);
        //    assert this.client.interactionManager != null;
        //    if(mouseX > x && mouseX < x+16 && mouseY > y && mouseY < y+16){
        //        this.client.interactionManager.clickButton(this.handler.syncId, spellNo);
        //    }
        //    spellNo++;
        //}


        this.close();

        return super.mouseClicked(mouseX, mouseY, spellSlotChosen);
    }

    private int getButtonXPos(int buttonNo) {
        switch (buttonNo) {
            case 0: case 4:
                return this.x + 80;
            case 1: case 3:
                return this.x + 120;
            case 2:
                return this.x + 137;
            case 5: case 7:
                return this.x + 40;
            case 6:
                return this.x + 23;
            default:
                return 0;
        }
    }

    private int getButtonYPos(int buttonNo) {
        switch (buttonNo) {
            case 0:
                return this.y + 18;
            case 1: case 7:
                return this.y + 35;
            case 2: case 6:
                return this.y + 75;
            case 3: case 5:
                return this.y + 115;
            case 4:
                return this.y + 132;
            default:
                return 0;
        }
    }
}