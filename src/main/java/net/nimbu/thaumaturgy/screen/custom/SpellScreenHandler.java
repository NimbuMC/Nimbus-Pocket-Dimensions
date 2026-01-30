package net.nimbu.thaumaturgy.screen.custom;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.component.ModDataComponentTypes;
import net.nimbu.thaumaturgy.item.SpellEquipHandler;
import net.nimbu.thaumaturgy.screen.ModScreenHanders;
import net.nimbu.thaumaturgy.spell.Spell;
import net.nimbu.thaumaturgy.spell.Spells;

import java.util.ArrayList;
import java.util.List;

public class SpellScreenHandler extends ScreenHandler {

    private SpellEquipHandler spellUnlockData;
    private final List<Spell> EQUIPPED_SPELLS = new ArrayList<>();

    private final PlayerEntity USER;
    private final World WORLD;
    private final Hand HAND;

    public World getWorld(){return WORLD;}

    public SpellScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreenHanders.SPELL_SCREEN_HANDLER, syncId);
        USER = playerInventory.player;
        WORLD = USER.getWorld();
        HAND = Hand.MAIN_HAND;

        EQUIPPED_SPELLS.add(Spells.AERODETONATION);
        if(WORLD.getRegistryKey().getValue().toString().contains("pocket_dimension"))
             EQUIPPED_SPELLS.add(Spells.EXPAND_POCKET_DIMENSION);
        else EQUIPPED_SPELLS.add(Spells.POCKET_DIMENSION);

        EQUIPPED_SPELLS.add(Spells.EFFECT_CLEANSING);
        EQUIPPED_SPELLS.add(Spells.UNBAKING_BREAD);
        EQUIPPED_SPELLS.add(Spells.SOARING);
        EQUIPPED_SPELLS.add(Spells.AMBIENCE_PIGSTEP);

    }


    private int equipSpell(){
        PlayerEntity player = MinecraftClient.getInstance().player;
        spellUnlockData = (SpellEquipHandler) player;
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

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        String spell = player.getStackInHand(HAND).get(ModDataComponentTypes.EQUIPPED_SPELL);
        if(spell != null) {
            if(!spell.isEmpty()) Spells.SPELL_DICTIONARY.get(spell).OnSpellUnequip();
            player.getStackInHand(HAND).set(ModDataComponentTypes.EQUIPPED_SPELL, EQUIPPED_SPELLS.get(id).toString());
            EQUIPPED_SPELLS.get(id).OnSpellEquip();
            System.out.println("spell selected " + player.getStackInHand(HAND).get(ModDataComponentTypes.EQUIPPED_SPELL));
        }

        return super.onButtonClick(player, id);
    }
}
