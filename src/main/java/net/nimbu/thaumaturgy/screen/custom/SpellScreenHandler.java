package net.nimbu.thaumaturgy.screen.custom;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.nimbu.thaumaturgy.item.SpellUnlockHandler;
import net.nimbu.thaumaturgy.screen.ModScreenHanders;
import org.jetbrains.annotations.Nullable;

public class SpellScreenHandler extends ScreenHandler {

    private SpellUnlockHandler spellUnlockData;


    public SpellScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreenHanders.SPELL_SCREEN_HANDLER, syncId);
    }


    private int equipSpell(){
        PlayerEntity player = MinecraftClient.getInstance().player;
        spellUnlockData = (SpellUnlockHandler) player;
        //boolean unlocked = data.getSpellUnlockFlags(spellIndex);
        return 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

}
