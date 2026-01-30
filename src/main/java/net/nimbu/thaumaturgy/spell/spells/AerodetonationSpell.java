package net.nimbu.thaumaturgy.spell.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;
import net.nimbu.thaumaturgy.dimensions.DimensionalInstancer;
import net.nimbu.thaumaturgy.entity.ModEntities;
import net.nimbu.thaumaturgy.entity.custom.AerodetonationSpellEntity;
import net.nimbu.thaumaturgy.entity.custom.SpellPortalEntity;
import net.nimbu.thaumaturgy.spell.Spell;

import java.util.Optional;
import java.util.function.Function;

public class AerodetonationSpell extends Spell {
    public AerodetonationSpell() {
        super(Identifier.ofVanilla("textures/item/wind_charge.png"),
                0xFF41533d,
                0xFFcdffc3);
    }

    public static final ExplosionBehavior EXPLOSION_BEHAVIOR = new AdvancedExplosionBehavior(
            false, false, Optional.empty(), Registries.BLOCK.getEntryList(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity())
    );

    @Override
    public void castSpell(World world, PlayerEntity user, Hand hand) {

        if(!world.isClient) {
            AerodetonationSpellEntity aerodetonationSpellEntity = new AerodetonationSpellEntity(ModEntities.AERODETONATION_SPELL, world);
            aerodetonationSpellEntity.setPosition(new Vec3d(user.getX(), user.getY() + 1.0, user.getZ()));
            aerodetonationSpellEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.0f, 0f);
            world.spawnEntity(aerodetonationSpellEntity);
        }

        /*
        Position pos = user.getPos();
        world.createExplosion(
                user,
                null,
                EXPLOSION_BEHAVIOR,
                pos.getX(),
                pos.getY()+1,
                pos.getZ(),
                30.0F,
                false,
                World.ExplosionSourceType.TRIGGER,
                ParticleTypes.GUST_EMITTER_SMALL,
                ParticleTypes.GUST_EMITTER_LARGE,
                SoundEvents.ENTITY_BREEZE_WIND_BURST
        );
        */
    }
}
