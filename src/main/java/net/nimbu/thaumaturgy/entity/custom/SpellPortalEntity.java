package net.nimbu.thaumaturgy.entity.custom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.block.ModBlocks;
import net.nimbu.thaumaturgy.particle.ModParticles;
import net.nimbu.thaumaturgy.worldgen.dimension.ModDimensions;

import static net.minecraft.block.HorizontalFacingBlock.FACING;
import static net.nimbu.thaumaturgy.block.custom.DoorwayBlock.HALF;

public class SpellPortalEntity extends ProjectileEntity {
    public SpellPortalEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    private void createPortal(World world){
        world.playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.BLOCK_BEACON_ACTIVATE,
                SoundCategory.NEUTRAL,
                1f,
                1.5f);

        if(!world.isClient() && world.getRegistryKey() != ModDimensions.POCKET_DIM_LEVEL_KEY) {

            //create door halves (THIS CURRENTLY FLIPS INCORRECTLY IN WEST/EAST?? WHAT?) also erases blocks oops lol
            BlockPos bottomHalf = this.getBlockPos();
            BlockPos topHalf = bottomHalf.up();

            float yaw = this.getYaw();
            Direction direction = null;
            if(45<yaw && yaw<135){
                direction = Direction.WEST;
            }
            else if (-45<yaw && yaw<45){
                direction = Direction.NORTH;
            }
            else if (-135<yaw && yaw<-45){
                direction = Direction.EAST;
            }
            else {
                direction = Direction.SOUTH;
            }
            world.setBlockState(bottomHalf, ModBlocks.DOORWAY.getDefaultState().with(FACING, direction));
            world.setBlockState(topHalf, ModBlocks.DOORWAY.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(FACING, direction));

            //horizontal facing appears to be broken for projectile entities??
            //world.setBlockState(bottomHalf, ModBlocks.DOORWAY.getDefaultState().with(FACING, this.getHorizontalFacing().getOpposite()));
            //world.setBlockState(topHalf, ModBlocks.DOORWAY.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(FACING, this.getHorizontalFacing().getOpposite()));//ctx.getHorizontalPlayerFacing().getOpposite()));

            //create particle effect
            if (!world.isClient()){
                Position pos = this.getPos();
                ((ServerWorld) world).spawnParticles(ModParticles.MAGIC_PARTICLE,
                        pos.getX(), pos.getY(), pos.getZ(), 50, 0.5, 1, 0.5, 0.5);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        Vec3d vec3d = this.getVelocity();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.hitOrDeflect(hitResult);
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.updateRotation();
        float g = 0.95F;
        if (this.getWorld().getStatesInBox(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) {
            this.discard();
        } else if (this.isInsideWaterOrBubbleColumn()) {
            this.discard();
        } else {
            this.setVelocity(vec3d.multiply(g));
            this.applyGravity();
            this.setPosition(d, e, f);
        }

        World world = this.getWorld();
        if (!world.isClient()){
            Position pos = this.getPos();
            ((ServerWorld) world).spawnParticles(ModParticles.MAGIC_PARTICLE,
                   pos.getX(), pos.getY()+0.25, pos.getZ(), 5, 0, 0, 0, 0);
        }
        //Can alternatively use "world.addParticle();" for clientside?
    }

    @Override
    protected double getGravity() {
        return 0.03;
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
        double d = this.getBoundingBox().getAverageSideLength() * 4.0;
        if (Double.isNaN(d)) {
            d = 4.0;
        }

        d *= 64.0;
        return distance < d * d;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        createPortal(this.getWorld());
        super.onBlockHit(blockHitResult);
        this.discard();
    }
}
