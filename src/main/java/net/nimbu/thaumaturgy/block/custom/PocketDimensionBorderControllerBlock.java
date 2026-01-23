package net.nimbu.thaumaturgy.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.nimbu.thaumaturgy.block.entity.custom.PocketDimensionBorderControllerBlockEntity;
import org.jetbrains.annotations.Nullable;

public class PocketDimensionBorderControllerBlock extends BlockWithEntity {
    public PocketDimensionBorderControllerBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends PocketDimensionBorderControllerBlock> getCodec() {
        return createCodec(PocketDimensionBorderControllerBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PocketDimensionBorderControllerBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }
}
