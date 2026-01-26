package net.nimbu.thaumaturgy.spell.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.spell.Spell;

public class EffectCleansingSpell extends Spell {
    public EffectCleansingSpell() {
        super(Identifier.ofVanilla("textures/item/milk_bucket.png"));
    }

    @Override
    public void castSpell(World world, PlayerEntity user, Hand hand) {
        user.clearStatusEffects();
    }
}
