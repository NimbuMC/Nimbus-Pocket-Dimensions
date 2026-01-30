package net.nimbu.thaumaturgy.spell.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.dimensions.DimensionalInstancer;
import net.nimbu.thaumaturgy.entity.ModEntities;
import net.nimbu.thaumaturgy.entity.custom.SpellPortalEntity;
import net.nimbu.thaumaturgy.spell.Spell;

public class PocketDimensionSpell extends Spell {
    public PocketDimensionSpell() {
        super(Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/spell_icons/door_magic.png"),
                0xFF1667ff,
                0xFF9500c7);
    }

    //THINKING OF RENAMING TO "Dimensional Expansion" - the pocketDimensionExpansionSpell will inherently need renaming

    @Override
    public void OnSpellEquip(){}

    @Override
    public void OnSpellUnequip(){}

    @Override
    public void castSpell(World world, PlayerEntity user, Hand hand) {
        if(!world.getRegistryKey().getValue().toString().contains("pocket_dimension")) createProjectile(world, user);
    }

    @Override
    public void createProjectile(World world, PlayerEntity user) {
        if(!world.isClient) {
            ServerWorld targetDimension = DimensionalInstancer.createInstance(world.getServer(), user.getUuid());
            SpellPortalEntity spellPortal = new SpellPortalEntity(ModEntities.SPELL_PORTAL, world);
            spellPortal.setPosition(new Vec3d(user.getX(), user.getY() + 1.5, user.getZ()));
            spellPortal.setVelocity(user, user.getPitch() - 30, user.getYaw(), 0.0f, 0.45f, 0f);
            spellPortal.setExitDimension(targetDimension.getRegistryKey());
            world.spawnEntity(spellPortal);
        }
    }

    @Override
    public String toString() {
        return "pocket_dimension_spell";
    }
}
