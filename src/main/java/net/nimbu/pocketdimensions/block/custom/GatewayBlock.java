package net.nimbu.pocketdimensions.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import net.minecraft.world.event.listener.GameEventListener;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.block.entity.ModBlockEntityTypes;
import net.nimbu.pocketdimensions.block.entity.custom.GatewayBlockEntity;
import org.jetbrains.annotations.Nullable;

public class GatewayBlock extends BlockWithEntity implements Portal {
    public static final MapCodec<GatewayBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    createSettingsCodec(),
                    BlockSetType.CODEC.fieldOf("block_set_type").forGetter(block -> block.blockSetType)
            ).apply(instance, GatewayBlock::new)
    );
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = Properties.OPEN;
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty EXIT = BooleanProperty.of("exit");
    private static final VoxelShape X_SHAPE = Block.createCuboidShape(
            0.0, 0.0, 6.0,
            16.0, 16.0, 10.0);
    private static final VoxelShape Z_SHAPE = Block.createCuboidShape(
            6.0, 0.0, 0.0,
            10.0, 16.0, 16.0);
    private final BlockSetType blockSetType;


    public GatewayBlock(Settings settings, BlockSetType type) {
        super(settings.strength(-1.0F, 3600000.0F).dropsNothing());
        this.setDefaultState(
                this.stateManager
                        .getDefaultState()
                        .with(FACING, Direction.NORTH)
                        .with(OPEN, false)
                        .with(HALF, DoubleBlockHalf.LOWER)
                        .with(EXIT, false) //if it leads back to the entrance portal
        );

        this.blockSetType=type;
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
    }

    public BlockState getTopHalfState() {
        return this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!state.get(EXIT)) {
            state = state.cycle(OPEN);
            world.setBlockState(pos, state, Block.NOTIFY_LISTENERS | Block.REDRAW_ON_MAIN_THREAD);
            world.playSound(null, pos, SoundEvents.BLOCK_CHERRY_WOOD_DOOR_OPEN, SoundCategory.BLOCKS);
            this.playOpenCloseSound(player, world, pos, state.get(OPEN));
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }


    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case NORTH:
            case SOUTH:
                return X_SHAPE;
            default:
                return Z_SHAPE;

                //todo: we're gonna need invisible helper blocks (make the door out of 4 blocks) in order to have the open door model work correctly... although the hitbox will still look wierd as it'll only cover half the door
                //EXCEPT THAT ISNT TRUE AT ALL AS WE CAN JUST HAVE TWO OVERLAPPING HITBOXES THAT DO THE SAME THING SO ITS FINE
        }
    }

    @Override
    protected MapCodec<? extends GatewayBlock> getCodec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        if (blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx)) {
            return this.getDefaultState()
                    .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                    .with(OPEN, false)
                    .with(HALF, DoubleBlockHalf.LOWER)
                    .with(EXIT, false);
        } else {
            return null;
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL); //places the above half portion of the door
    }


    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos
    ) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (direction.getAxis() != Direction.Axis.Y || doubleBlockHalf == DoubleBlockHalf.LOWER != (direction == Direction.UP)) { //if not vertical OR if lower half block is not upwards
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)
                    ? Blocks.AIR.getDefaultState()
                    : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        } else {
            return neighborState.getBlock() instanceof GatewayBlock && neighborState.get(HALF) != doubleBlockHalf
                    ? neighborState.with(HALF, doubleBlockHalf)
                    : Blocks.AIR.getDefaultState();
        }
    }


    //Redstone interactivity:
    /*@Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        boolean bl = world.isReceivingRedstonePower(pos)
                || world.isReceivingRedstonePower(pos.offset(state.get(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN)); //if the top or bottom is receiving power
        if (!this.getDefaultState().isOf(sourceBlock)) {
            if (bl != (Boolean)state.get(OPEN)) {
                //this.playOpenCloseSound(null, world, pos, bl);
                world.emitGameEvent(null, bl ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
            }

            world.setBlockState(pos, state.with(OPEN, bl), Block.NOTIFY_LISTENERS);
        }
    }*/

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, HALF, EXIT);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return state.get(HALF) == DoubleBlockHalf.LOWER
                ? new GatewayBlockEntity(pos, state)
                : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            World world, BlockState state, BlockEntityType<T> type
    ) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            return null;
        }

        return validateTicker(
                type,
                ModBlockEntityTypes.GATEWAY_BLOCK_ENTITY,
                world.isClient
                        ? GatewayBlockEntity::clientTick
                        : GatewayBlockEntity::serverTick
        );
    }


    @Override
    public @Nullable <T extends BlockEntity> GameEventListener getGameEventListener(ServerWorld world, T blockEntity) {
        return super.getGameEventListener(world, blockEntity);
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    @Override
    protected boolean canBucketPlace(BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            world.getBlockState(pos.add(0, -1, 0)).onEntityCollision(world, pos.add(0, -1, 0), entity);
            return;
        }


        if (getTeleportBox(pos, state.get(FACING)).intersects(entity.getBoundingBox())
                && entity.canUsePortals(false)
                && !world.isClient
                && world.getBlockEntity(pos) instanceof GatewayBlockEntity doorway
                && !doorway.needsCooldownBeforeTeleporting()) {

            entity.tryUsePortal(this, pos);
            GatewayBlockEntity.startTeleportCooldown(world, pos, state, doorway);
        }
    }


    @Nullable
    @Override
    public TeleportTarget createTeleportTarget(ServerWorld world, Entity entity, BlockPos pos) {
        Vec3d exitPos = null;
        BlockPos exitBlock = new BlockPos(0, 100000, 0);
        ServerWorld serverWorld = null;
        if (world.getBlockEntity(pos) instanceof GatewayBlockEntity gatewayBlockEntity) {
            PocketDimensions.LOGGER.info("FoundBlockEntity");
            RegistryKey<World> registryKey = gatewayBlockEntity.getExitDimension();

            serverWorld = world.getServer().getWorld(registryKey);
            exitBlock = gatewayBlockEntity.getExitBlockPos();
            BlockState blockState = serverWorld.getBlockState(exitBlock);

            Direction exitDirection = Direction.NORTH;

            if (blockState.getBlock() instanceof GatewayBlock) { //i dont know why this is necessary... because you the exit position gets moved to the portal anyways
                exitDirection = blockState.get(GatewayBlock.FACING);
            }

            if (registryKey != null) {
                PocketDimensions.LOGGER.info("exitDimension of " + gatewayBlockEntity.getExitDimension());
                exitPos = gatewayBlockEntity.getExitPosition(exitDirection);
                PocketDimensions.LOGGER.info("exitPos of " + exitPos);
            }


            if (exitPos == null) return null;
            else {
                assert serverWorld != null;
                return new TeleportTarget(serverWorld, exitPos, getTeleportVelocity(entity),
                        entity.getYaw() + exitDirection.asRotation() -
                                world.getBlockState(pos).get(GatewayBlock.FACING).asRotation() + 180,
                        entity.getPitch(), TeleportTarget.ADD_PORTAL_CHUNK_TICKET);
            }
        }
        return null;
    }

    private static Vec3d getTeleportVelocity(Entity entity) {
        return entity instanceof EnderPearlEntity ? new Vec3d(0.0, -1.0, 0.0) : entity.getVelocity();
    }

    private static Box getTeleportBox(BlockPos pos, Direction facing) {
        double depth = 0.05;

        if (facing == Direction.NORTH || facing == Direction.SOUTH) {
            return new Box(
                    pos.getX(),
                    pos.getY(),
                    pos.getZ() + 0.5 - depth,
                    pos.getX() + 1,
                    pos.getY() + 2,
                    pos.getZ() + 0.5 + depth
            );
        } else {
            return new Box(
                    pos.getX() + 0.5 - depth,
                    pos.getY(),
                    pos.getZ(),
                    pos.getX() + 0.5 + depth,
                    pos.getY() + 2,
                    pos.getZ() + 1
            );
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            if (state.get(EXIT)) {
                VoxelShape shape1 = rotateShape(Direction.NORTH, state.get(FACING), 15.99, 0, 3, 19.99, 16, 13);
                VoxelShape shape2 = VoxelShapes.combine(
                        rotateShape(Direction.NORTH, state.get(FACING), -3.99, 0, 3, 0.01, 16, 13),
                        rotateShape(Direction.NORTH, state.get(FACING), 0, 0, 9, 16, 16, 11),
                        BooleanBiFunction.OR
                );
                VoxelShape shape3 = VoxelShapes.combine(
                        rotateShape(Direction.NORTH, state.get(FACING), -2, 20, 2, 18, 24, 14),
                        rotateShape(Direction.NORTH, state.get(FACING), 0, 0, 9, 16, 16, 11),
                        BooleanBiFunction.OR
                );
                return VoxelShapes.combine(shape1, VoxelShapes.combine(shape2, shape3, BooleanBiFunction.OR), BooleanBiFunction.OR);
            } else if (state.get(OPEN)) {

                VoxelShape shape1 = VoxelShapes.combine(
                        rotateShape(Direction.NORTH, state.get(FACING), 13, 0, -7, 16, 16, 9),
                        rotateShape(Direction.NORTH, state.get(FACING), 15.99, 0, 3, 19.99, 16, 13),
                        BooleanBiFunction.OR
                );
                VoxelShape shape2 = VoxelShapes.combine(
                        rotateShape(Direction.NORTH, state.get(FACING), -3.99, 0, 3, 0.01, 16, 13),
                        rotateShape(Direction.NORTH, state.get(FACING), -4.8, 16, 2, 20.8, 20, 14),
                        BooleanBiFunction.OR
                );
                VoxelShape shape3 = VoxelShapes.combine(
                        rotateShape(Direction.NORTH, state.get(FACING), -2, 20, 2, 18, 24, 14),
                        rotateShape(Direction.NORTH, state.get(FACING), 0, 0, 9, 16, 16, 11),
                        BooleanBiFunction.OR
                );
                return VoxelShapes.combine(shape1, VoxelShapes.combine(shape2, shape3, BooleanBiFunction.OR), BooleanBiFunction.OR);
            } else {
                VoxelShape shape1 = VoxelShapes.combine(
                        rotateShape(Direction.NORTH, state.get(FACING), 0, 0, 6, 16, 16, 9),
                        rotateShape(Direction.NORTH, state.get(FACING), 15.99, 0, 3, 19.99, 16, 13),
                        BooleanBiFunction.OR
                );

                VoxelShape shape2 = VoxelShapes.combine(
                        rotateShape(Direction.NORTH, state.get(FACING), -3.99, 0, 3, 0.01, 16, 13),
                        rotateShape(Direction.NORTH, state.get(FACING), -4.8, 16, 2, 20.8, 20, 14),
                        BooleanBiFunction.OR
                );
                VoxelShape shape3 = VoxelShapes.combine(
                        rotateShape(Direction.NORTH, state.get(FACING), -2, 20, 2, 18, 24, 14),
                        rotateShape(Direction.NORTH, state.get(FACING), 0, 0, 9, 16, 16, 11),
                        BooleanBiFunction.OR
                );
                return VoxelShapes.combine(shape1, VoxelShapes.combine(shape2, shape3, BooleanBiFunction.OR), BooleanBiFunction.OR);
            }
        } else {
            if (state.get(EXIT)){
                VoxelShape shape1 = rotateShape(Direction.NORTH, state.get(FACING), 15.99, 0, 3, 19.99, 16, 13);
                VoxelShape shape2 = VoxelShapes.combine(
                        rotateShape(Direction.NORTH, state.get(FACING), -3.99, 0, 3, 0.01, 16, 13),
                        rotateShape(Direction.NORTH, state.get(FACING), 0, 0, 9, 16, 16, 11),
                        BooleanBiFunction.OR
                );
                return VoxelShapes.combine(shape1, shape2, BooleanBiFunction.OR);
            }
            else if (state.get(OPEN)) {
                VoxelShape shape1 = VoxelShapes.combine(
                        rotateShape(Direction.NORTH, state.get(FACING), 13, 0, -7, 16, 16, 9),
                        rotateShape(Direction.NORTH, state.get(FACING), 15.99, 0, 3, 19.99, 16, 13),
                        BooleanBiFunction.OR
                );

                VoxelShape shape2 = VoxelShapes.combine(
                        rotateShape(Direction.NORTH, state.get(FACING), -3.99, 0, 3, 0.01, 16, 13),
                        rotateShape(Direction.NORTH, state.get(FACING), 0, 0, 9, 16, 16, 11),
                        BooleanBiFunction.OR
                );
                return VoxelShapes.combine(shape1, shape2, BooleanBiFunction.OR);
            } else {
                VoxelShape shape1 = VoxelShapes.combine(
                        rotateShape(Direction.NORTH, state.get(FACING), 0, 0, 6, 16, 16, 9),
                        rotateShape(Direction.NORTH, state.get(FACING), 15.99, 0, 3, 19.99, 16, 13),
                        BooleanBiFunction.OR
                );
                VoxelShape shape2 = VoxelShapes.combine(
                        rotateShape(Direction.NORTH, state.get(FACING), -3.99, 0, 3, 0.01, 16, 13),
                        rotateShape(Direction.NORTH, state.get(FACING), 0, 0, 9, 16, 16, 11),
                        BooleanBiFunction.OR
                );
                return VoxelShapes.combine(shape1, shape2, BooleanBiFunction.OR);
            }
        }
    }

    public static VoxelShape rotateShape(Direction from, Direction to,
                                         double minX, double minY, double minZ,
                                         double maxX, double maxY, double maxZ
    ){
        int times = (to.getHorizontal() - from.getHorizontal() + 4) % 4;
        double sin = Math.sin((times / 4.0) * Math.TAU);
        double cos = Math.cos((times / 4.0) * Math.TAU);

        double x1 = (minX - 8) * cos - (minZ - 8) * sin + 8;
        double x2 = (maxX - 8) * cos - (maxZ - 8) * sin + 8;
        double z1 = (minX - 8) * sin + (minZ - 8) * cos + 8;
        double z2 = (maxX - 8) * sin + (maxZ - 8) * cos + 8;
        return Block.createCuboidShape(Math.min(x1, x2), minY, Math.min(z1, z2), Math.max(x1, x2), maxY, Math.max(z1, z2));
    }

    private void playOpenCloseSound(@Nullable Entity entity, World world, BlockPos pos, boolean open) {
        world.playSound(entity, pos, open ? this.blockSetType.doorOpen() : this.blockSetType.doorClose(), SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
    }
}
