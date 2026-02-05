package net.nimbu.thaumaturgy.spell.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.particle.ModParticleTypes;

public class AerodetonationSpellEntity extends ProjectileEntity {
    private int lifeTime; //sets the ticks that the entity exists for AND how many particles are created

    public AerodetonationSpellEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {


        World world = this.getWorld();
        if (!world.isClient()){
            Position pos = this.getPos();

            ((ServerWorld) world).spawnParticles(ModParticleTypes.BLAST_PARTICLE,
                    pos.getX(), pos.getY(), pos.getZ(), 1, 0, 0, 0, 0);
        }

        Vec3d vec3d = this.getVelocity();
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.setPosition(d, e, f);

        //sfx
        world.playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENTITY_BREEZE_WIND_BURST,
                SoundCategory.NEUTRAL,
                1f,
                0.35f+(lifeTime*0.15f));


        lifeTime--;
        if (lifeTime<=0) this.discard();
    }

    public void setLifeTime(int lifeTime){
        this.lifeTime=lifeTime;
    }

    @Override
    protected double getGravity() {
        return 0;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
    }

    @Override
    public boolean canUsePortals(boolean allowVehicles) {
        return false;
    }

    @Override
    public boolean shouldRender(double distance) {
        return false;
    }
}
