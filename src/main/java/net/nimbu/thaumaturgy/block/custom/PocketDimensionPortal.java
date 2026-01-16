package net.nimbu.thaumaturgy.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.block.entity.ModBlockEntityTypes;
import net.nimbu.thaumaturgy.block.entity.custom.PocketDimensionPortalBlockEntity;
import net.nimbu.thaumaturgy.worldgen.dimension.ModDimensions;
import org.jetbrains.annotations.Nullable;


public class PocketDimensionPortal extends BlockWithEntity implements Portal {
    public static int PocketDimensionMaximumSize = 128;

    public static final MapCodec<PocketDimensionPortal> CODEC =
            createCodec(PocketDimensionPortal::new);

    @Override
    public MapCodec<PocketDimensionPortal> getCodec() {
        return CODEC;
    }


    public PocketDimensionPortal(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PocketDimensionPortalBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            World world,
            BlockState state,
            BlockEntityType<T> type
    ) {
        return validateTicker(
                type,
                ModBlockEntityTypes.POCKET_DIMENSION_PORTAL,
                world.isClient
                        ? PocketDimensionPortalBlockEntity::clientTick
                        : PocketDimensionPortalBlockEntity::serverTick
        );
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
        if (entity.canUsePortals(false)
                && !world.isClient
                && world.getBlockEntity(pos) instanceof PocketDimensionPortalBlockEntity pocketDimensionPortalBlockEntity
                && !pocketDimensionPortalBlockEntity.needsCooldownBeforeTeleporting()) {

            entity.tryUsePortal(this, pos);
            PocketDimensionPortalBlockEntity.startTeleportCooldown(world, pos, state, pocketDimensionPortalBlockEntity);
        }
    }

    @Nullable
    @Override
    public TeleportTarget createTeleportTarget(ServerWorld world, Entity entity, BlockPos pos) {
        BlockPos exitPos = new BlockPos(0,100000,0);
        ServerWorld serverWorld = null;
        if(world.getBlockEntity(pos) instanceof PocketDimensionPortalBlockEntity pocketDimensionPortalBlockEntity)
        {
            Thaumaturgy.LOGGER.info("FoundBlockEntity");
            RegistryKey<World> registryKey = switch (pocketDimensionPortalBlockEntity.GetExitDimension()) {
                case 1 -> World.NETHER;
                case 2 -> World.END;
                case 3 -> ModDimensions.POCKET_DIM_LEVEL_KEY;
                default -> World.OVERWORLD;
            };
            Thaumaturgy.LOGGER.info("exitDimension of " + pocketDimensionPortalBlockEntity.GetExitDimension());
            serverWorld = world.getServer().getWorld(registryKey);
            exitPos = pocketDimensionPortalBlockEntity.getExitPosition();
            Thaumaturgy.LOGGER.info("exitPos of " + exitPos);
        }

        if(exitPos == null) {
            Thaumaturgy.LOGGER.warn("Fuck you");
            return null;
        }
        else return new TeleportTarget(serverWorld, exitPos.toBottomCenterPos(), getTeleportVelocity(entity), entity.getYaw(), entity.getPitch(), TeleportTarget.ADD_PORTAL_CHUNK_TICKET);
    }

    private static Vec3d getTeleportVelocity(Entity entity) {
        return entity instanceof EnderPearlEntity ? new Vec3d(0.0, -1.0, 0.0) : entity.getVelocity();
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
        return new BlockPos(x * PocketDimensionMaximumSize * 2, y * PocketDimensionMaximumSize * 2, 128);
    }
/*


    public int idFromPortalPosition(int x, int y)
    {
        if (x == 0 && y == 0) {
            return 0;
        }

        int k = Math.max(Math.abs(x), Math.abs(y));
        int m =  (2 * k + 1) * (2 * k + 1); // (2k+1)^2

        if (y == -k) return m - (k - x);
        else if (x == -k) return m - (2 * k + (y + k));
        else if (y == k) return m - (4 * k + (x + k));
        else return m - (6 * k + (k - y));
    }
    @Nullable
    @Override
    public TeleportTarget createTeleportTarget(ServerWorld world, Entity entity, BlockPos pos) {
        RegistryKey<World> registryKey = null;
        int exitDimension = world.getBlockState(pos).get(EXIT_DIMENSION);
        registryKey = switch (exitDimension) {
            case 0 -> World.OVERWORLD;
            case 1 -> World.NETHER;
            case 2 -> World.END;
            case 3 -> ModDimensions.POCKET_DIM_LEVEL_KEY;
            default -> registryKey;
        };
        ServerWorld serverWorld = world.getServer().getWorld(registryKey);
        if (serverWorld == null) {
            return null;
        } else {
            WorldBorder worldBorder = serverWorld.getWorldBorder();
            double d = DimensionType.getCoordinateScaleFactor(world.getDimension(), serverWorld.getDimension());
            BlockPos blockPos = worldBorder.clamp(entity.getX() * d, entity.getY(), entity.getZ() * d);
            return this.getOrCreateExitPortalTarget(serverWorld, entity, pos, blockPos, world.getRegistryKey(), registryKey, worldBorder);
        }
    }

    @Nullable
    private TeleportTarget getOrCreateExitPortalTarget(
            ServerWorld world,
            Entity entity,
            BlockPos pos,
            BlockPos scaledPos,
            RegistryKey<World> currentDimension,
            RegistryKey<World> outDimension,
            WorldBorder worldBorder) {
        TeleportTarget.PostDimensionTransition postDimensionTransition;
        MinecraftServer server = world.getServer();
        ServerWorld targetWorld = server.getWorld(outDimension);

        Vec3d outputPosition;

        if(currentDimension == ModDimensions.POCKET_DIM_LEVEL_KEY)
        { // get the exit XYZ and dim and teleport there
            BlockState state = world.getBlockState(pos);
            int id = idFromPortalPosition(pos.getX() / (PocketDimensionMaximumSize * 2), pos.getY() / (PocketDimensionMaximumSize * 2));
            PocketDimensionLinks links = PocketDimensionLinks.get(world);
            BlockPos destination = links.getLink(id);
            outputPosition = new Vec3d(destination.getX(),destination.getY(),destination.getZ());
        }
        else
        {
            int currentDim = 0;
            if(World.OVERWORLD == currentDimension){currentDim = 0;}
            if(World.NETHER == currentDimension){currentDim = 1;}
            if(World.END == currentDimension){currentDim = 2;}
            //use the PocketDImensionID to get the correct position and then teleport the player there
            world.setBlockState(pos, ModBlocks.POCKET_DIMENSION_PORTAL.getDefaultState().with(EXIT_DIMENSION, currentDim));
            outputPosition = targetPortalFromID(0);
        }

        postDimensionTransition = TeleportTarget.SEND_TRAVEL_THROUGH_PORTAL_PACKET.then(TeleportTarget.ADD_PORTAL_CHUNK_TICKET);
        return new TeleportTarget(targetWorld, outputPosition, entity.getVelocity(), entity.getYaw(), entity.getPitch(), postDimensionTransition);
    }
*/
}
