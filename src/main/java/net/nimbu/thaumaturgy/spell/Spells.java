package net.nimbu.thaumaturgy.spell;

import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.spell.spells.EffectCleansingSpell;
import net.nimbu.thaumaturgy.spell.spells.PocketDimensionExpansionSpell;
import net.nimbu.thaumaturgy.spell.spells.PocketDimensionSpell;
import net.nimbu.thaumaturgy.spell.spells.*;

import java.util.Dictionary;
import java.util.Hashtable;

public class Spells {

    public static Dictionary<String, Spell> SPELL_DICTIONARY = new Hashtable<>();
    public static final Spell POCKET_DIMENSION = new PocketDimensionSpell();
    public static final Spell EXPAND_POCKET_DIMENSION = new PocketDimensionExpansionSpell();
    public static final Spell EFFECT_CLEANSING = new EffectCleansingSpell();
    public static final Spell SOARING = new SoaringSpell();
    public static final Spell AMBIENCE_PIGSTEP = new AmbiencePigstepSpell();
    public static final Spell UNBAKING_BREAD = new UnbakingBreadSpell();
    public static final Spell AERODETONATION = new AerodetonationSpell();

    public static void registerSpells(){
        SPELL_DICTIONARY.put(POCKET_DIMENSION.toString(), POCKET_DIMENSION);
        SPELL_DICTIONARY.put(EXPAND_POCKET_DIMENSION.toString(), EXPAND_POCKET_DIMENSION);
        SPELL_DICTIONARY.put(EFFECT_CLEANSING.toString(), EFFECT_CLEANSING);
        SPELL_DICTIONARY.put(SOARING.toString(), SOARING);
        SPELL_DICTIONARY.put(AMBIENCE_PIGSTEP.toString(), AMBIENCE_PIGSTEP);
        SPELL_DICTIONARY.put(UNBAKING_BREAD.toString(), UNBAKING_BREAD);
        SPELL_DICTIONARY.put(AERODETONATION.toString(), AERODETONATION);

        Thaumaturgy.LOGGER.info("Registering spells for "+Thaumaturgy.MOD_ID);
    }
}
