package net.nimbu.thaumaturgy.spell.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.spell.Spell;

public class SoaringSpell extends Spell {
    public SoaringSpell() {
        super(Identifier.ofVanilla("textures/item/feather.png"),
                0xFFe2ffdf,
                0xFFbbff5c);
    }

    @Override
    public void OnSpellEquip() {

    }

    @Override
    public void OnSpellUnequip() {

    }

    @Override
    public void castSpell(World world, PlayerEntity user, Hand hand) {
        //user.getAbilities().allowFlying = true; //this also disables fall damage!
        user.getAbilities().flying = true;
        user.sendAbilitiesUpdate();
    }

    @Override
    public String toString() {
        return "soaring_spell";
    }
}
