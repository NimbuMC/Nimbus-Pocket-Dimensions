package net.nimbu.thaumaturgy.spell.spells;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;
import net.nimbu.thaumaturgy.particle.ModParticles;
import net.nimbu.thaumaturgy.spell.Spell;

import java.util.List;
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
    public void OnSpellEquip() {

    }

    @Override
    public void OnSpellUnequip() {

    }

    @Override
    public void castSpell(World world, PlayerEntity user, Hand hand) {

        Vec3d start = user.getPos();
        if(!world.isClient) {

            start = new Vec3d(start.x, start.y+1, start.z); //nudge up to eye height
            Vec3d length = user.getRotationVector().multiply(6.0);
            start=start.add(length.normalize()); //push the start a block in the direction the player is looking
            Vec3d end = start.add(length);

            raycastDamage(start, end, world, user);

            ((ServerWorld) world).spawnParticles(ParticleTypes.GUST,
                    start.x, start.y, start.z, 1, 0, 0, 0, 0);

            //ClientWorld clientWorld = (ClientWorld) world;
            //clientWorld.addParticle(ParticleTypes.DAMAGE_INDICATOR,
            //        start.x, start.y, start.z, 1, 0, 0);
        }
    }


    private void raycastDamage(Vec3d start, Vec3d end, World world, PlayerEntity user) {

        Vec3d direction = end.subtract(start);
        double length = direction.length();
        if (length == 0) return;

        Vec3d baseDir = direction.normalize();

        //perpendicular basis, stops breaking if direction perfectly vertical
        Vec3d up = Math.abs(baseDir.y) < 0.99
                ? new Vec3d(0, 1, 0)
                : new Vec3d(1, 0, 0);

        Vec3d right = baseDir.crossProduct(up).normalize();
        Vec3d realUp = right.crossProduct(baseDir).normalize();

        Vec3d[] offsets = new Vec3d[] {
                // center
                Vec3d.ZERO,

                //horizontal
                right.multiply(1.5),
                right.multiply(-1.5),
                right.multiply(0.75),
                right.multiply(-0.75),

                //vertical
                realUp.multiply(1.5),
                realUp.multiply(-1.5),
                realUp.multiply(0.75),
                realUp.multiply(-0.75),

                //diagonals
                right.multiply(1).add(realUp.multiply(1)),
                right.multiply(1).add(realUp.multiply(-1)),
                right.multiply(-1).add(realUp.multiply(1)),
                right.multiply(-1).add(realUp.multiply(-1)),
        };

        if (!world.isClient()) {
            ServerWorld serverWorld = (ServerWorld) world;

            for (Vec3d offset : offsets) {
                drawDebugRay(
                        serverWorld,
                        start.add(offset),
                        end.add(offset)
                );
            }
        }

        //tilt knockback upwards
        double horizontal = Math.sqrt(baseDir.x * baseDir.x + baseDir.z * baseDir.z);

        //only apply set tilt if sufficient horizontal input, otherwise pointless
        Vec3d knockDir = horizontal > 0.5
                ? new Vec3d(baseDir.x, horizontal * 0.5, baseDir.z).normalize()
                : baseDir;

        double strength = 3.0;

        Box broadPhase = new Box(start, end).expand(2.5);
        List<Entity> entities = world.getOtherEntities(user, broadPhase);

        for (Entity entity : entities) {
            if (!entity.isAlive()) continue;
            boolean hit = false;

            //raycasts
            for (Vec3d offset : offsets) {
                Optional<Vec3d> result = entity
                        .getBoundingBox()
                        .expand(0.3)
                        .raycast(start.add(offset), end.add(offset));

                if (result.isPresent()) {
                    hit = true;
                    break;
                }
            }

            if (!hit) continue;

            // knockback resistance
            double finalStrength = strength;
            if (entity instanceof LivingEntity living) {
                double resistance = living.getAttributeValue(
                        EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE
                );
                finalStrength *= (1.0 - resistance);
            }

            Vec3d knockback = knockDir.multiply(finalStrength);

            entity.setVelocity(entity.getVelocity().add(knockback));
            entity.velocityModified = true;
        }
    }




    private void drawDebugRay(ServerWorld world, Vec3d start, Vec3d end) {
        Vec3d dir = end.subtract(start);
        double length = dir.length();
        Vec3d step = dir.normalize().multiply(0.2); // spacing

        Vec3d pos = start;
        for (double traveled = 0; traveled <= length; traveled += 0.2) {
            world.spawnParticles(
                    ParticleTypes.END_ROD,
                    pos.x, pos.y, pos.z,
                    1,
                    0, 0, 0,
                    0
            );
            pos = pos.add(step);
        }
    }

    @Override
    public String toString() {
        return "aerodetonation_spell";
    }
}
