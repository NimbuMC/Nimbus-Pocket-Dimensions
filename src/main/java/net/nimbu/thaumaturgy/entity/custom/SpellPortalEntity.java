package net.nimbu.thaumaturgy.entity.custom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.entity.ModEntities;
import net.nimbu.thaumaturgy.item.ModItems;
import net.nimbu.thaumaturgy.worldgen.dimension.ModDimensions;

import java.util.ArrayList;
import java.util.List;

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

            List<BlockPos> positions = new ArrayList<>();
            BlockPos sourceBlockPos = this.getBlockPos();
            positions.add(sourceBlockPos);
            positions.add(new BlockPos(sourceBlockPos.getX(), sourceBlockPos.getY()+1, sourceBlockPos.getZ()));

            //Need to acquire spell direction
            //Float playerAbsoluteYaw = Math.abs(context.getPlayer().getYaw());

            //BlockState pocketDimPortalState = ModBlocks.POCKET_DIMENSION_PORTAL.getDefaultState();

            for(BlockPos pos : positions) {
                //world.setBlockState(pos, pocketDimPortalState);
                //if(world.getBlockEntity(pos) instanceof PocketDimensionPortalBlockEntity portalData) {
                //    portalData.TriggerInitialIDUpdate(world, pos, 3);
                //}
                if(world.getBlockState(pos).getBlock()== Blocks.AIR){
                    // if(135 > playerAbsoluteYaw && playerAbsoluteYaw > 45){
                    //    world.setBlockState(pos, pocketDimPortalState.rotate(BlockRotation.CLOCKWISE_90));
                    // }
                    //else{
                    world.setBlockState(pos, Blocks.NETHER_PORTAL.getDefaultState());}
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
