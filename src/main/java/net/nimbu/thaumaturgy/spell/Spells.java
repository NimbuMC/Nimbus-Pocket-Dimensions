package net.nimbu.thaumaturgy.spell;

import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.spell.spells.EffectCleansingSpell;
import net.nimbu.thaumaturgy.spell.spells.SoaringSpell;
import net.nimbu.thaumaturgy.spell.spells.PocketDimensionSpell;
import net.nimbu.thaumaturgy.spell.spells.UnbakingBreadSpell;

public class Spells {

    public static final Spell POCKET_DIMENSION = new PocketDimensionSpell();
    public static final Spell EFFECT_CLEANSING = new EffectCleansingSpell();
    public static final Spell SOARING = new SoaringSpell();
    public static final Spell UNBAKING_BREAD = new UnbakingBreadSpell();

    public static void registerSpells(){
        Thaumaturgy.LOGGER.info("Registering spells for "+Thaumaturgy.MOD_ID);
    }
}
