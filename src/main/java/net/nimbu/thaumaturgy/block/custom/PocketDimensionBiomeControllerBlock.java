package net.nimbu.thaumaturgy.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.block.entity.custom.PocketDimensionBiomeControllerBlockEntity;
import net.nimbu.thaumaturgy.block.entity.custom.RevisualisingTableBlockEntity;
import org.jetbrains.annotations.Nullable;

public class PocketDimensionBiomeControllerBlock extends BlockWithEntity {
    public PocketDimensionBiomeControllerBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends PocketDimensionBiomeControllerBlock> getCodec() {
        return createCodec(PocketDimensionBiomeControllerBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PocketDimensionBiomeControllerBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(world.getBlockEntity(pos) instanceof PocketDimensionBiomeControllerBlockEntity blockentity) {
            if (!world.isClient) {
                //player.openHandledScreen();
                return ActionResult.CONSUME;
            }
        }
        return ActionResult.SUCCESS;
    }
}
