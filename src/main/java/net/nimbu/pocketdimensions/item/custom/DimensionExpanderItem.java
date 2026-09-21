package net.nimbu.pocketdimensions.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.network.ClientPocketDimensionPersistentState;
import net.nimbu.pocketdimensions.persistentstates.PocketDimensionPersistentState;
import net.nimbu.pocketdimensions.renderer.PocketDimensionBorderRenderer;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static net.nimbu.pocketdimensions.renderer.PocketDimensionBorderRenderer.*;

public class DimensionExpanderItem extends Item {


    private int raycastCooldown = 0;


    public DimensionExpanderItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (world.isClient) {

            if (!(entity instanceof PlayerEntity player)) return;

            if (player.getMainHandStack() == stack) {
                PocketDimensionBorderRenderer.expansionModeActive = true;
                renderReticle(player);
            } else {
                cancelRenderReticle();
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack item = user.getStackInHand(hand);
        if (!world.isClient) {
            BlockPos pos = serverGridBasedRaycast((ServerWorld) world, user);
            PocketDimensions.LOGGER.info("Tried to expand pocket dimension at position " + pos);
            if (pos != null) {
                if (PocketDimensionPersistentState.get((ServerWorld) world).hasAdjacents(pos)) {
                    PocketDimensions.LOGGER.info("Adjacent exists for " + pos);
                    if (pos.getY() > -1 && pos.getY() < 30) {
                        if (!PocketDimensionPersistentState.get((ServerWorld) world).isRoomUnlocked(pos)) {
                            ClientPocketDimensionPersistentState.addRoom((ServerWorld) world, pos);
                            BlockState state = Blocks.AIR.getDefaultState();
                            for (int x = 0; x < BorderLength; x++) {
                                for (int y = 0; y < BorderHeight; y++) {
                                    for (int z = 0; z < BorderLength; z++) {
                                        BlockPos clearPos = new BlockPos(
                                                x + BorderLength * pos.getX(),
                                                y + BorderHeight * pos.getY() + 7,
                                                z + BorderLength * pos.getZ());
                                        if (world.getBlockState(clearPos) == Blocks.BARRIER.getDefaultState()) {
                                            world.setBlockState(clearPos, state);
                                        }
                                    }
                                }
                            }

                            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                                    SoundEvents.BLOCK_BEACON_ACTIVATE,
                                    SoundCategory.NEUTRAL,
                                    1f,
                                    1.5f);
                            if (!user.isCreative()) {
                                item.decrement(1);
                            }
                            renderReticle(user);
                        }
                    }
                }
            }
            if (item.isEmpty()) {
                PocketDimensionBorderRenderer.expansionModeActive = false;
            }
        }

        return TypedActionResult.success(item);
    }

    public void renderReticle(PlayerEntity user) {
        if (raycastCooldown-- <= 0) {

            BlockPos hit = clientGridBasedRaycast(user);

            if (hit != null) {
                PocketDimensionBorderRenderer.expansionValid = hit.getY() >= 0 && hit.getY() <= 29 && ClientPocketDimensionPersistentState.hasAdjacents(hit);
                PocketDimensionBorderRenderer.expansionModePosition = hit;
                raycastCooldown = 4;
            } else expansionValid = false;
        }
    }


    private BlockPos clientGridBasedRaycast(PlayerEntity user) {
        Vec3d start = globalCoordToGridCoords(user.getCameraPosVec(0f));
        Vec3d end = globalCoordToGridCoords(user.getCameraPosVec(0f).add(user.getRotationVec(0f).multiply(20)));

        double dx = end.x - start.x, dy = end.y - start.y, dz = end.z - start.z;
        int x = (int) Math.floor(start.x), y = (int) Math.floor(start.y), z = (int) Math.floor(start.z);
        int endX = (int) Math.floor(end.x), endY = (int) Math.floor(end.y), endZ = (int) Math.floor(end.z);
        int stepX = (int) Math.signum(dx), stepY = (int) Math.signum(dy), stepZ = (int) Math.signum(dz);

        double tDeltaX = dx == 0 ? Double.POSITIVE_INFINITY : Math.abs(1.0 / dx);
        double tDeltaY = dy == 0 ? Double.POSITIVE_INFINITY : Math.abs(1.0 / dy);
        double tDeltaZ = dz == 0 ? Double.POSITIVE_INFINITY : Math.abs(1.0 / dz);

        double tMaxX;
        double tMaxY;
        double tMaxZ;

        if (dx > 0) tMaxX = ((x + 1.0) - start.x) / dx;
        else if (dx < 0) tMaxX = (start.x - x) / -dx;
        else tMaxX = Double.POSITIVE_INFINITY;

        if (dy > 0) tMaxY = ((y + 1.0) - start.y) / dy;
        else if (dy < 0) tMaxY = (start.y - y) / -dy;
        else tMaxY = Double.POSITIVE_INFINITY;

        if (dz > 0) tMaxZ = ((z + 1.0) - start.z) / dz;
        else if (dz < 0) tMaxZ = (start.z - z) / -dz;
        else tMaxZ = Double.POSITIVE_INFINITY;

        while (x != endX || y != endY || z != endZ) {
            double nextT = Math.min(tMaxX, Math.min(tMaxY, tMaxZ));

            if (tMaxX == nextT) {
                x += stepX;
                tMaxX += tDeltaX;
            }

            if (tMaxY == nextT) {
                y += stepY;
                tMaxY += tDeltaY;
            }

            if (tMaxZ == nextT) {
                z += stepZ;
                tMaxZ += tDeltaZ;
            }

            BlockPos pos = new BlockPos(x, y, z);
            if (!ClientPocketDimensionPersistentState.hasRoom(pos)) return pos;
        }
        return null;
    }

    private BlockPos serverGridBasedRaycast(ServerWorld world, PlayerEntity user) {
        Vec3d start = globalCoordToGridCoords(user.getCameraPosVec(0f));
        Vec3d end = globalCoordToGridCoords(user.getCameraPosVec(0f).add(user.getRotationVec(0f).multiply(20)));

        double dx = end.x - start.x, dy = end.y - start.y, dz = end.z - start.z;
        int x = (int) Math.floor(start.x), y = (int) Math.floor(start.y), z = (int) Math.floor(start.z);
        int endX = (int) Math.floor(end.x), endY = (int) Math.floor(end.y), endZ = (int) Math.floor(end.z);
        int stepX = (int) Math.signum(dx), stepY = (int) Math.signum(dy), stepZ = (int) Math.signum(dz);

        double tDeltaX = dx == 0 ? Double.POSITIVE_INFINITY : Math.abs(1.0 / dx);
        double tDeltaY = dy == 0 ? Double.POSITIVE_INFINITY : Math.abs(1.0 / dy);
        double tDeltaZ = dz == 0 ? Double.POSITIVE_INFINITY : Math.abs(1.0 / dz);

        double tMaxX;
        double tMaxY;
        double tMaxZ;

        if (dx > 0) tMaxX = ((x + 1.0) - start.x) / dx;
        else if (dx < 0) tMaxX = (start.x - x) / -dx;
        else tMaxX = Double.POSITIVE_INFINITY;

        if (dy > 0) tMaxY = ((y + 1.0) - start.y) / dy;
        else if (dy < 0) tMaxY = (start.y - y) / -dy;
        else tMaxY = Double.POSITIVE_INFINITY;

        if (dz > 0) tMaxZ = ((z + 1.0) - start.z) / dz;
        else if (dz < 0) tMaxZ = (start.z - z) / -dz;
        else tMaxZ = Double.POSITIVE_INFINITY;

        while (x != endX || y != endY || z != endZ) {
            double nextT = Math.min(tMaxX, Math.min(tMaxY, tMaxZ));

            if (tMaxX == nextT) {
                x += stepX;
                tMaxX += tDeltaX;
            }

            if (tMaxY == nextT) {
                y += stepY;
                tMaxY += tDeltaY;
            }

            if (tMaxZ == nextT) {
                z += stepZ;
                tMaxZ += tDeltaZ;
            }

            BlockPos pos = new BlockPos(x, y, z);
            if (!PocketDimensionPersistentState.get(world).isRoomUnlocked(pos)) return pos;
        }
        return null;
    }

    private Vec3d globalCoordToGridCoords(Vec3d global) {
        return new Vec3d(global.getX() / BorderLength, (global.getY() - 7) / BorderHeight, global.getZ() / BorderLength);
    }

    public void cancelRenderReticle() {
        PocketDimensionBorderRenderer.expansionValid = false;
        PocketDimensionBorderRenderer.expansionModeActive = false;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal("Expands the size of a pocket dimension").formatted(Formatting.GRAY));
    }
}
