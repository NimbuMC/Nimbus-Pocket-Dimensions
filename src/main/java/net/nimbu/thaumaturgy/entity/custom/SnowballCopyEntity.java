package net.nimbu.thaumaturgy.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.entity.ModEntities;

public class SnowballCopyEntity extends ThrownItemEntity {
    public SnowballCopyEntity(EntityType<? extends SnowballCopyEntity> entityType, World world) {
        super(entityType, world);
    }

    public SnowballCopyEntity(World world, LivingEntity owner) {
        super(ModEntities.SNOWBALL_COPY, owner, world);
    }

    public SnowballCopyEntity(World world, double x, double y, double z) {
        super(ModEntities.SNOWBALL_COPY, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.FLINT;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENTITY_SHULKER_SHOOT,
                SoundCategory.NEUTRAL,
                1.0f,
                0.4f );
        super.onBlockHit(blockHitResult);
    }
}
