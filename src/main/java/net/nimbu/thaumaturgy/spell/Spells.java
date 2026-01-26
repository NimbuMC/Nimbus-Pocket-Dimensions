package net.nimbu.thaumaturgy.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.entity.ModEntities;
import net.nimbu.thaumaturgy.entity.custom.SpellPortalEntity;
import net.nimbu.thaumaturgy.spell.spells.PocketDimensionSpell;

public class Spells {

    public static final Spell POCKET_DIMENSION = new PocketDimensionSpell();

    public static void registerSpells(){
        Thaumaturgy.LOGGER.info("Registering spells for "+Thaumaturgy.MOD_ID);
    }
}
