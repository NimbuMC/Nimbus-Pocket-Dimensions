package net.nimbu.pocketdimensions.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nimbu.pocketdimensions.block.entity.ModBlockEntityTypes;
import net.nimbu.pocketdimensions.block.entity.custom.PocketDimensionCustomizerBlockEntity;
import net.nimbu.pocketdimensions.screen.custom.PocketDimensionBiomeControllerScreenHandler;
import org.jetbrains.annotations.Nullable;

public class PocketDimensionCustomizerBlock extends BlockWithEntity {
    public PocketDimensionCustomizerBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(PocketDimensionCustomizerBlock::new);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PocketDimensionCustomizerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? validateTicker(type, ModBlockEntityTypes.POCKET_DIMENSION_CUSTOMIZER_BLOCK_ENTITY, PocketDimensionCustomizerBlockEntity::tick) : null;
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
