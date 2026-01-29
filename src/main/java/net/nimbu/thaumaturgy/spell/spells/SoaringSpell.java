package net.nimbu.thaumaturgy.spell.spells;

import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.spell.Spell;

public class SoaringSpell extends Spell {
    public SoaringSpell() {
        super(Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/spell_icons/door_magic.png"),
                0xFFe2ffdf,
                0xFFbbff5c);
    }
}
