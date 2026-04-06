package net.nimbu.pocketdimensions.entity.custom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.registry.RegistryKey;
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
import net.nimbu.pocketdimensions.block.ModBlocks;
import net.nimbu.pocketdimensions.block.entity.custom.GatewayBlockEntity;
import net.nimbu.pocketdimensions.component.ModComponentInitializer;
import net.nimbu.pocketdimensions.component.PlayerGatewayComponent;
import net.nimbu.pocketdimensions.particle.ModParticleTypes;
import net.nimbu.pocketdimensions.worldgen.dimension.ModDimensions;

import static net.minecraft.block.HorizontalFacingBlock.FACING;
import static net.nimbu.pocketdimensions.block.custom.GatewayBlock.HALF;

public class GatewayProjectileEntity extends ProjectileEntity {

    RegistryKey<World> exitDimensionID;
    public GatewayProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);

    }

    public void setExitDimension(RegistryKey<World> exitDimension)
    {
        exitDimensionID = exitDimension;
    }

    private void createPortal(World world){


        if(!world.isClient()) {
            if (!world.getDimensionEntry().matchesKey(ModDimensions.POCKET_DIM_TYPE)){






                //create door halves (THIS CURRENTLY FLIPS INCORRECTLY IN WEST/EAST?? WHAT?) also erases blocks oops lol
                BlockPos bottomHalf = this.getBlockPos();
                BlockPos topHalf = bottomHalf.up();
                if ((world.getBlockState(bottomHalf).isOf(Blocks.AIR) ||
                        world.getBlockState(bottomHalf).isOf(Blocks.SNOW) ||
                        world.getBlockState(bottomHalf).isOf(Blocks.TALL_GRASS) ||
                        world.getBlockState(bottomHalf).isOf(Blocks.SHORT_GRASS)) &&
                        (world.getBlockState(topHalf).isOf(Blocks.AIR) ||
                        world.getBlockState(bottomHalf).isOf(Blocks.TALL_GRASS))) {

                    float yaw = this.getYaw();
                    Direction direction;
                    if (45 < yaw && yaw < 135) {
                        direction = Direction.WEST;
                    } else if (-45 < yaw && yaw < 45) {
                        direction = Direction.NORTH;
                    } else if (-135 < yaw && yaw < -45) {
                        direction = Direction.EAST;
                    } else {
                        direction = Direction.SOUTH;
                    }
                    world.setBlockState(bottomHalf, ModBlocks.DARK_OAK_GATEWAY.getDefaultState().with(FACING, direction));
                    if (world.getBlockEntity(bottomHalf) instanceof GatewayBlockEntity portalData) {
                        portalData.TriggerInitialIDUpdate(world, bottomHalf, exitDimensionID);
                    }
                    world.setBlockState(topHalf, ModBlocks.DARK_OAK_GATEWAY.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(FACING, direction));


                    //Delete data at saved position
                    Entity owner = this.getOwner();
                    if (!(owner instanceof PlayerEntity)) return; //ensures cannot create portal if not player
                    PlayerGatewayComponent comp = ModComponentInitializer.PLAYER_GATEWAY_KEY.get(owner);
                    BlockPos previousPos = comp.getGatewayPos();
                    RegistryKey<World> previousDimension = comp.getGatewayDim();

                    if (previousPos != null && previousDimension != null) {
                        ServerWorld targetWorld = world.getServer().getWorld(previousDimension); // get the correct dimension
                        if (targetWorld != null) {
                            //TODO: Only set block state if its a gateway (and IS the last gateway, not a different one)
                            //if (targetWorld.getBlockState(previousPos).isOf(ModBlocks.)){
                            targetWorld.setBlockState(previousPos, Blocks.AIR.getDefaultState());

                        }
                    }

                    //Save the position of placement for deletion later
                    comp.setGatewayPos(bottomHalf);
                    comp.setGatewayDim(world.getRegistryKey());


                    //horizontal facing appears to be broken for projectile entities??
                    //world.setBlockState(bottomHalf, ModBlocks.DOORWAY.getDefaultState().with(FACING, this.getHorizontalFacing().getOpposite()));
                    //world.setBlockState(topHalf, ModBlocks.DOORWAY.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(FACING, this.getHorizontalFacing().getOpposite()));//ctx.getHorizontalPlayerFacing().getOpposite()));

                    world.playSound(null, this.getX(), this.getY(), this.getZ(),
                            SoundEvents.BLOCK_BEACON_ACTIVATE,
                            SoundCategory.NEUTRAL,
                            1f,
                            1.5f);

                    //create particle effect
                    Position pos = this.getPos();
                    ((ServerWorld) world).spawnParticles(ModParticleTypes.GATEWAY_PROJECTILE_PARTICLE,
                            pos.getX(), pos.getY(), pos.getZ(), 50, 0.5, 1, 0.5, 0.5);
                }
                else{
                    world.playSound(null, this.getX(), this.getY(), this.getZ(),
                            SoundEvents.BLOCK_TRIAL_SPAWNER_PLACE,
                            SoundCategory.NEUTRAL,
                            1f,
                            1.4f);
                }
            }
            else{
                world.playSound(null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.BLOCK_TRIAL_SPAWNER_PLACE,
                        SoundCategory.NEUTRAL,
                        1f,
                        1.4f);
            }
        }


    }

    public void recordPortalPosition(PlayerEntity owner){
        //save block position to nbt using cardinal components
        //
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

        this.setVelocity(vec3d.multiply(g));
        this.applyGravity();
        this.setPosition(d, e, f);

        World world = this.getWorld();
        if (!world.isClient()){
            Position pos = this.getPos();
            ((ServerWorld) world).spawnParticles(ModParticleTypes.GATEWAY_PROJECTILE_PARTICLE,
                   pos.getX(), pos.getY()+0.25, pos.getZ(), 5, 0, 0, 0, 0);
        }
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
