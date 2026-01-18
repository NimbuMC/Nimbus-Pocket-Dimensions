package net.nimbu.thaumaturgy.block.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.nimbu.thaumaturgy.block.entity.ModBlockEntityTypes;

public class PocketDimensionBorderBlockEntity extends BlockEntity {
    public PocketDimensionBorderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.POCKET_DIMENSION_BORDER_BLOCK, pos, state);
    }
}
