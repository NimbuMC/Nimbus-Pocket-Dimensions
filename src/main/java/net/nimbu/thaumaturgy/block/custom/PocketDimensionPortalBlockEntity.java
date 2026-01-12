package net.nimbu.thaumaturgy.block.custom;

import com.mojang.logging.LogUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.block.ModBlockEntityTypes;
import net.nimbu.thaumaturgy.block.ModBlocks;
import net.nimbu.thaumaturgy.worldgen.dimension.ModDimensions;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class PocketDimensionPortalBlockEntity extends BlockEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private long age;
    @Nullable
    private BlockPos exitPortalPos;
    private int exitDimensionID; //0-overworld, 1-nether, 2-end, 3 PocketDimension
    private int pocketDimensionID;
    private boolean exactTeleport;
    private int teleportCooldown;
    public PocketDimensionPortalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.POCKET_DIMENSION_PORTAL, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putLong("Age", this.age);
        if (this.exitPortalPos != null) {
            nbt.put("exit_portal", NbtHelper.fromBlockPos(this.exitPortalPos));
        }
        nbt.putInt("pocket_dimension_id", this.pocketDimensionID);
        nbt.putInt("exit_dimension_id", this.exitDimensionID);

        if (this.exactTeleport) {
            nbt.putBoolean("ExactTeleport", true);
        }
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.age = nbt.getLong("Age");
        NbtHelper.toBlockPos(nbt, "exit_portal").filter(World::isValid).ifPresent(exitPortalPos -> this.exitPortalPos = exitPortalPos);
        this.pocketDimensionID = nbt.getInt("pocket_dimension_id");
        this.exitDimensionID = nbt.getInt("exit_dimension_id");
        this.exactTeleport = nbt.getBoolean("ExactTeleport");
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, PocketDimensionPortalBlockEntity blockEntity) {
        blockEntity.age++;
        if (blockEntity.needsCooldownBeforeTeleporting()) {
            blockEntity.teleportCooldown--;
        }
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, PocketDimensionPortalBlockEntity blockEntity) {
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

    public void TriggerInitialIDUpdate(World world, BlockPos entryPortalPosition, int PortalID)
    {
        if(world.getBlockEntity(entryPortalPosition) instanceof PocketDimensionPortalBlockEntity pocketDimensionPortalBlockEntity && !world.isClient) {
            pocketDimensionPortalBlockEntity.setPortalID(PortalID);
            BlockPos exitPosition = targetPortalFromID(PortalID);
            MinecraftServer server = world.getServer();
            ServerWorld targetWorld = server.getWorld(ModDimensions.POCKET_DIM_LEVEL_KEY);
            if(targetWorld.getBlockEntity(exitPosition) instanceof PocketDimensionPortalBlockEntity exitPortal)
            {
                int DimKey = 0;
                if(world.getRegistryKey() == World.NETHER) DimKey = 1;
                else if(world.getRegistryKey() == World.END) DimKey = 2;
                exitPortal.setExitPosition(entryPortalPosition, DimKey);
            }
            else
            {
                targetWorld.setBlockState(exitPosition, ModBlocks.POCKET_DIMENSION_PORTAL.getDefaultState());
                BlockPos platformPos = new BlockPos(exitPortalPos.getX(), exitPortalPos.getY() - 1, exitPortalPos.getZ());
                targetWorld.setBlockState(platformPos, Blocks.GLOWSTONE.getDefaultState());
                platformPos = platformPos.add(1,0,0);
                targetWorld.setBlockState(platformPos, Blocks.GLOWSTONE.getDefaultState());
                platformPos = platformPos.add(0,0,1);
                targetWorld.setBlockState(platformPos, Blocks.GLOWSTONE.getDefaultState());
                platformPos = platformPos.add(-1,0,0);
                targetWorld.setBlockState(platformPos, Blocks.GLOWSTONE.getDefaultState());
                platformPos = platformPos.add(-1,0,0);
                targetWorld.setBlockState(platformPos, Blocks.GLOWSTONE.getDefaultState());
                platformPos = platformPos.add(0,0,-1);
                targetWorld.setBlockState(platformPos, Blocks.GLOWSTONE.getDefaultState());
                platformPos = platformPos.add(0,0,-1);
                targetWorld.setBlockState(platformPos, Blocks.GLOWSTONE.getDefaultState());
                platformPos = platformPos.add(1,0,0);
                targetWorld.setBlockState(platformPos, Blocks.GLOWSTONE.getDefaultState());
                platformPos = platformPos.add(1,0,0);
                targetWorld.setBlockState(platformPos, Blocks.GLOWSTONE.getDefaultState());
                if(targetWorld.getBlockEntity(exitPosition) instanceof PocketDimensionPortalBlockEntity exitPortal)
                {
                    int DimKey = 0;
                    if(world.getRegistryKey() == World.NETHER) DimKey = 1;
                    else if(world.getRegistryKey() == World.END) DimKey = 2;
                    exitPortal.setExitPosition(entryPortalPosition, DimKey);
                }
            }
        }
    }

    public void setPortalID(int ID)
    {
        pocketDimensionID = ID;
        exitPortalPos = targetPortalFromID(ID);
        exitDimensionID = 3;
    }


    public void setExitPosition(BlockPos exitPosition, int DimensionID)
    {
        exitDimensionID = DimensionID;
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

    public static void startTeleportCooldown(World world, BlockPos pos, BlockState state, PocketDimensionPortalBlockEntity blockEntity) {
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

    public BlockPos targetPortalFromID(int id)
    {
        int x;
        int y;
        int k = (int) Math.ceil((Math.sqrt(id) - 1.0)/2.0);
        int t = 2 * k + 1;
        int d = (t * t) - id;
        if (d < t){ x =  k - d; y = -k;}
        else if(d < 2 * t){ x = -k; y = -k + (d - t);}
        else if (d < 3 * t){ x = -k + (d - 2 * t); y = k; }
        else{ x =  k; y =  k - (d - 3 * t);}
        return new BlockPos(x * PocketDimensionPortal.PocketDimensionMaximumSize * 2, 128, y * PocketDimensionPortal.PocketDimensionMaximumSize * 2);
    }

    public int GetExitDimension()
    {
        return exitDimensionID;
    }

    public BlockPos getExitPosition()
    {
        return exitPortalPos;
    }

    public boolean shouldDrawSide(Direction direction) {
        return Block.shouldDrawSide(this.getCachedState(), this.world, this.getPos(), direction, this.getPos().offset(direction));
    }

    public int getDrawnSidesCount() {
        int i = 0;

        for (Direction direction : Direction.values()) {
            i += this.shouldDrawSide(direction) ? 1 : 0;
        }

        return i;
    }

    public void setExitPortalPos(BlockPos pos, boolean exactTeleport) {
        this.exactTeleport = exactTeleport;
        this.exitPortalPos = pos;
        this.markDirty();
    }
}
