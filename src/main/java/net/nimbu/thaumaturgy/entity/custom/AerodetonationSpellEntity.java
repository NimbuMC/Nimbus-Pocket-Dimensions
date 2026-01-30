package net.nimbu.thaumaturgy.entity.custom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.nimbu.thaumaturgy.particle.ModParticles;

import java.util.List;


public class AerodetonationSpellEntity extends ProjectileEntity {

    private int lifeTime = 6;

    public AerodetonationSpellEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();
        //damageEntities(world, 1);
        lifeTime--;
        if (lifeTime <=0) this.discard();
        else if (lifeTime<=3){

        if (!world.isClient()){
            Position pos = this.getPos();
            ((ServerWorld) world).spawnParticles(ParticleTypes.GUST_EMITTER_SMALL,
                    pos.getX(), pos.getY(), pos.getZ(), 5, 0, 0, 0, 0);
        }}

        Vec3d vec3d = this.getVelocity();
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.updateRotation();
        this.setPosition(d, e, f);


    }

    private void damageEntities(World world, float powerModifier) {

        //this.affectedBlocks.addAll(set);
        int k = MathHelper.floor(this.getX() - powerModifier - 1.0);
        int lx = MathHelper.floor(this.getX() + powerModifier + 1.0);
        int r = MathHelper.floor(this.getY() - powerModifier - 1.0);
        int s = MathHelper.floor(this.getY() + powerModifier + 1.0);
        int t = MathHelper.floor(this.getZ() - powerModifier - 1.0);
        int u = MathHelper.floor(this.getZ() + powerModifier + 1.0);
        List<Entity> list = world.getOtherEntities(this, new Box(k, r, t, lx, s, u));
        Vec3d vec3d = new Vec3d(this.getX(), this.getY(), this.getZ());

        for (Entity entity : list) {
            //if (!entity.isImmuneToExplosion(this)) {    stop some entites dealing with this stuff
                double v = Math.sqrt(entity.squaredDistanceTo(vec3d)) / powerModifier;
                if (v <= 1.0) {
                    double w = entity.getX() - this.getX();
                    double x = (entity instanceof TntEntity ? entity.getY() : entity.getEyeY()) - this.getY();
                    double y = entity.getZ() - this.getZ();
                    double z = Math.sqrt(w * w + x * x + y * y);
                    if (z != 0.0) {
                        w /= z;
                        x /= z;
                        y /= z;

                        //if (this.behavior.shouldDamage(this, entity)) {
                        //    entity.damage(this.damageSource, this.behavior.calculateDamage(this, entity));
                        //}

                        double aa = 1.0f;//(1.0 - v) * getExposure(vec3d, entity) * this.behavior.getKnockbackModifier(entity);
                        double ab;
                        if (entity instanceof LivingEntity livingEntity) {
                            ab = aa * (1.0 - livingEntity.getAttributeValue(EntityAttributes.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE));
                        } else {
                            ab = aa;
                        }

                        w *= ab;
                        x *= ab;
                        y *= ab;
                        Vec3d vec3d2 = new Vec3d(w, x, y);
                        entity.setVelocity(entity.getVelocity().add(vec3d2));
                        if (entity instanceof PlayerEntity playerEntity && !playerEntity.isSpectator() && (!playerEntity.isCreative() || !playerEntity.getAbilities().flying)) {
                            //this.affectedPlayers.put(playerEntity, vec3d2);
                        }

                        entity.onExplodedBy(this);
                    }
                }
            //}
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }
}
