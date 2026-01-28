package net.nimbu.thaumaturgy.spell.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.spell.Spell;

public class EffectCleansingSpell extends Spell {
    public EffectCleansingSpell() {
        super(Identifier.of(Thaumaturgy.MOD_ID,"textures/gui/spell_icons/milk_magic.png"),
                0xFFdae7ff,
                0xFFb9b9b9);
    }

    @Override
    public void castSpell(World world, PlayerEntity user, Hand hand) {
        user.clearStatusEffects();
    }
}
