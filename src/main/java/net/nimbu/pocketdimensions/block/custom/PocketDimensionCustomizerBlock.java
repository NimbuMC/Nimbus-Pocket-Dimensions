package net.nimbu.pocketdimensions.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.nimbu.pocketdimensions.block.entity.ModBlockEntityTypes;
import net.nimbu.pocketdimensions.block.entity.custom.PocketDimensionCustomizerBlockEntity;
import net.nimbu.pocketdimensions.screen.custom.DimensionCustomizerScreenHandler;
import net.nimbu.pocketdimensions.worldgen.dimension.ModDimensions;
import org.jetbrains.annotations.Nullable;

public class PocketDimensionCustomizerBlock extends BlockWithEntity {
    public static final VoxelShape BASE = Block.createCuboidShape(
            0, 0, 0,
            16, 9, 16);
    public static final VoxelShape PILLAR_NW = Block.createCuboidShape(
            0, 9, 0,
            4, 16, 4);
    public static final VoxelShape PILLAR_NE = Block.createCuboidShape(
            12, 9, 0,
            16, 16, 4);
    public static final VoxelShape PILLAR_SW = Block.createCuboidShape(
            0, 9, 12,
            4, 16, 16);
    public static final VoxelShape PILLAR_SE = Block.createCuboidShape(
            12, 9, 12,
            16, 16, 16);
    public static final VoxelShape SHAPE = VoxelShapes.union(
            BASE,
            PILLAR_NW,
            PILLAR_NE,
            PILLAR_SW,
            PILLAR_SE);


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
        if (!world.isClient && world.getDimensionEntry().matchesKey(ModDimensions.POCKET_DIM_TYPE)) {
            player.openHandledScreen(
                    new SimpleNamedScreenHandlerFactory(
                            (syncId, inv, p) -> new DimensionCustomizerScreenHandler(syncId, inv),
                            Text.literal("Pocket dimension customiser")
                    ));
            return ActionResult.CONSUME;
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
