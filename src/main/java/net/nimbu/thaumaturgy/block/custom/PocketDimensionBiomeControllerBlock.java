package net.nimbu.thaumaturgy.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.screen.custom.PocketDimensionBiomeControllerScreenHandler;
import net.nimbu.thaumaturgy.screen.custom.SpellScreenHandler;
import org.jetbrains.annotations.Nullable;

public class PocketDimensionBiomeControllerBlock extends Block {
    public PocketDimensionBiomeControllerBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends PocketDimensionBiomeControllerBlock> getCodec() {
        return createCodec(PocketDimensionBiomeControllerBlock::new);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            player.openHandledScreen(
                    new SimpleNamedScreenHandlerFactory(
                            (syncId, inv, p) -> new PocketDimensionBiomeControllerScreenHandler(syncId, inv),
                            Text.literal("Pocket dimension customiser")
                    ));
            return ActionResult.CONSUME;
        }
        return ActionResult.SUCCESS;
    }
}
