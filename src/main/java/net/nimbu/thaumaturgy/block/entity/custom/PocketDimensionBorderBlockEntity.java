package net.nimbu.thaumaturgy.block.entity.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.nimbu.thaumaturgy.block.ModBlocks;
import net.nimbu.thaumaturgy.block.entity.ModBlockEntityTypes;

public class PocketDimensionBorderBlockEntity extends BlockEntity {
    private final int WORLD_HEIGHT = 128;
    private final int ROOMS_IN_HEIGHT = 10;
    private final int FLOOR_HEIGHT = 4;
    public PocketDimensionBorderBlockEntity[] neighbours;
    public boolean[][] chunksStatus;
    /*
    Z
    ^ 2    3
    |   .
    | 0    1
    +------->x
     position of Entity in chunk 0 (represented by the . ) at position 15,15 in the chunk
     Z
      ^   2
      | 3 . 1
      |   0
      +------->x
      storage of neighbour chunks
     */
    public PocketDimensionBorderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.POCKET_DIMENSION_BORDER_BLOCK, pos, state);

    }

    public boolean[] getColumn(int i)
    {
        return chunksStatus[i];
    }

    public void UpdateColumns()
    {
        UpdateColumn(0);
        UpdateColumn(1);
        UpdateColumn(2);
        UpdateColumn(3);
    }

    public void UpdateColumn(int column)
    {
        int roomHeight = WORLD_HEIGHT / ROOMS_IN_HEIGHT;
        for (int i = 0; i < ROOMS_IN_HEIGHT; i++) {
            BlockPos position = new BlockPos(
                    pos.getX() + (column % 2),
                    roomHeight * i + FLOOR_HEIGHT,
                    pos.getZ() + (column / 2));
            BlockState currentBlock = world.getBlockState(position);
            chunksStatus[column][i] = currentBlock.isOf(ModBlocks.POCKET_DIMENSION_BORDER);
        }
    }

    public PocketDimensionBorderBlockEntity getOtherBlockEntity(int dir) {
        if(neighbours[dir] != null) return neighbours[dir];
        BlockPos otherPosition = switch (dir) {
            case 1 -> new BlockPos(pos.getX() + 32, pos.getY(), pos.getZ());
            case 2 -> new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 32);
            case 3 -> new BlockPos(pos.getX() - 32, pos.getY(), pos.getZ());
            default -> new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 32);
        };
        if (world.getBlockEntity(otherPosition) instanceof PocketDimensionBorderBlockEntity entity)
        {
            neighbours[dir] = entity;
            return entity;
        }
        else
        {
            world.setBlockState(otherPosition, ModBlocks.POCKET_DIMENSION_BORDER.getDefaultState());
            PocketDimensionBorderBlockEntity entity = (PocketDimensionBorderBlockEntity) world.getBlockEntity(otherPosition);
            neighbours[dir] = entity;
            return entity;
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
    }


    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
    }
}
