package net.nimbu.pocketdimensions.block.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.block.ModBlocks;
import net.nimbu.pocketdimensions.block.custom.DoorwayBlock;
import net.nimbu.pocketdimensions.block.entity.ModBlockEntityTypes;
import net.nimbu.pocketdimensions.network.ClientPocketDimensionPersistentState;
import net.nimbu.pocketdimensions.renderer.PocketDimensionBorderRenderer;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class DoorwayBlockEntity extends BlockEntity {
    private long age;
    @Nullable
    private BlockPos exitPortalPos;
    private RegistryKey<World> exitDimension;
    private boolean exactTeleport;
    private int teleportCooldown;

    public DoorwayBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.DOORWAY_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putLong("Age", this.age);
        if (this.exitPortalPos != null) {
            nbt.put("exit_portal", NbtHelper.fromBlockPos(this.exitPortalPos));
        }
        if (exitDimension != null) nbt.putString("exit_dimension", this.exitDimension.getValue().toString());
        else nbt.putString("exit_dimension", "");

        if (this.exactTeleport) {
            nbt.putBoolean("ExactTeleport", true);
        }
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.age = nbt.getLong("Age");
        NbtHelper.toBlockPos(nbt, "exit_portal").filter(World::isValid).ifPresent(exitPortalPos -> this.exitPortalPos = exitPortalPos);
        if (!nbt.getString("exit_dimension").equals(""))
            this.exitDimension = RegistryKey.of(RegistryKeys.WORLD, Identifier.of(nbt.getString("exit_dimension")));
        this.exactTeleport = nbt.getBoolean("ExactTeleport");
    }


    public static void clientTick(World world, BlockPos pos, BlockState state, DoorwayBlockEntity blockEntity) {
        blockEntity.age++;
        if (blockEntity.needsCooldownBeforeTeleporting()) {
            blockEntity.teleportCooldown--;
        }
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, DoorwayBlockEntity blockEntity) {
        boolean bl = blockEntity.isRecentlyGenerated();
        boolean bl2 = blockEntity.needsCooldownBeforeTeleporting();
        blockEntity.age++;
        if (bl2) {
            blockEntity.teleportCooldown--;
        } else if (blockEntity.age % 2400L == 0L) {
            startTeleportCooldown(world, pos, state, blockEntity);
        }

        if (bl != blockEntity.isRecentlyGenerated() || bl2 != blockEntity.needsCooldownBeforeTeleporting()) {
            markDirty(world, pos, state);
        }
    }

    public void TriggerInitialIDUpdate(World world, BlockPos entryPortalPosition, RegistryKey<World> exitID) {
        if (world.getBlockEntity(entryPortalPosition) instanceof DoorwayBlockEntity DoorwayBlockEntity && !world.isClient) {
            PocketDimensions.LOGGER.info("Created with ID \n" + exitID);
            DoorwayBlockEntity.setPortalID(exitID);
            BlockPos exitPosition = new BlockPos(6, 148, 1);
            ServerWorld targetWorld = world.getServer().getWorld(exitID);
            if (targetWorld.getBlockEntity(exitPosition) instanceof DoorwayBlockEntity exitPortal) {
                //if(exitDimension.getValue().toString().startsWith(Thaumaturgy.MOD_ID+":pocket_dimension"))
                exitPortal.setExitPosition(entryPortalPosition, world.getRegistryKey());
            } else {
                targetWorld.setBlockState(exitPosition, ModBlocks.GATEWAY.getDefaultState()
                        .with(DoorwayBlock.FACING, Direction.SOUTH)
                        .with(DoorwayBlock.OPEN, true));
                targetWorld.setBlockState(exitPosition.add(0, 1, 0), ModBlocks.GATEWAY.getDefaultState()
                        .with(DoorwayBlock.FACING, Direction.SOUTH)
                        .with(DoorwayBlock.HALF, DoubleBlockHalf.UPPER)
                        .with(DoorwayBlock.OPEN, true));
                targetWorld.setBlockState(exitPosition.add(-1,-1,-1), Blocks.GLOWSTONE.getDefaultState());
                targetWorld.setBlockState(exitPosition.add(-1,-1,0), Blocks.GLOWSTONE.getDefaultState());
                targetWorld.setBlockState(exitPosition.add(-1,-1,1), Blocks.GLOWSTONE.getDefaultState());

                targetWorld.setBlockState(exitPosition.add(0,-1,-1), Blocks.GLOWSTONE.getDefaultState());
                targetWorld.setBlockState(exitPosition.add(0,-1,0), Blocks.GLOWSTONE.getDefaultState());
                targetWorld.setBlockState(exitPosition.add(0,-1,1), Blocks.GLOWSTONE.getDefaultState());

                targetWorld.setBlockState(exitPosition.add(1,-1,-1), Blocks.GLOWSTONE.getDefaultState());
                targetWorld.setBlockState(exitPosition.add(1,-1,0), Blocks.GLOWSTONE.getDefaultState());
                targetWorld.setBlockState(exitPosition.add(1,-1,1), Blocks.GLOWSTONE.getDefaultState());
                if (targetWorld.getBlockEntity(exitPosition) instanceof DoorwayBlockEntity exitPortal) {
                    exitPortal.setExitPosition(entryPortalPosition, world.getRegistryKey());
                }
            }

            BlockPos roomPos = new BlockPos(
                    Math.floorDiv(exitPosition.getX(), PocketDimensionBorderRenderer.BorderLength),
                    Math.floorDiv(exitPosition.getY() - 2, PocketDimensionBorderRenderer.BorderHeight),
                    Math.floorDiv(exitPosition.getZ(), PocketDimensionBorderRenderer.BorderLength)
            );

            ClientPocketDimensionPersistentState.addRoom(targetWorld, roomPos);
        }
    }

    public void setPortalID(RegistryKey<World> exitWorld) {
        exitDimension = exitWorld;
        exitPortalPos = new BlockPos(6, 148, 1);
    }


    public void setExitPosition(BlockPos exitPosition, RegistryKey<World> exitWorld) {
        exitDimension = exitWorld;
        exitPortalPos = exitPosition;
    }

    public boolean isRecentlyGenerated() {
        return this.age < 200L;
    }

    public boolean needsCooldownBeforeTeleporting() {
        return this.teleportCooldown > 0;
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return this.createComponentlessNbt(registryLookup);
    }

    public static void startTeleportCooldown(World world, BlockPos pos, BlockState state, DoorwayBlockEntity blockEntity) {
        if (!world.isClient) {
            blockEntity.teleportCooldown = 40;
            world.addSyncedBlockEvent(pos, state.getBlock(), 1, 0);
            markDirty(world, pos, state);
        }
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            this.teleportCooldown = 40;
            return true;
        } else {
            return super.onSyncedBlockEvent(type, data);
        }
    }


    public RegistryKey<World> GetExitDimension() {
        return exitDimension;
    }

    public BlockPos getExitBlock() {
        return exitPortalPos;
    }

    public Vec3d getExitPosition(Direction facingDir) {
        assert exitPortalPos != null;
        Vector3f vec = facingDir.getUnitVector().mul(0.5f);
        return exitPortalPos.toBottomCenterPos().add(vec.x,vec.y,vec.z);
    }
}
