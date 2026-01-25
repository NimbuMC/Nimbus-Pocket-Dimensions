package net.nimbu.thaumaturgy.persistentstates;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.nimbu.thaumaturgy.network.ClientPocketRooms;
import net.nimbu.thaumaturgy.network.PocketDimRoomSync;

import java.util.Set;

public class PocketDimRoomsHelper {
    public static void addRoom(ServerWorld world, BlockPos pos) {
        PocketDimensionPersistentState state =
                PocketDimensionPersistentState.get(world);

        // Avoid resending if already unlocked
        if (state.isRoomUnlocked(pos)) return;

        state.addRoom(pos);

        // Sync to all players currently in this dimension
        for (ServerPlayerEntity player : world.getPlayers()) {
            PocketDimRoomSync.updateSingularRoom(player, pos);
        }
    }

    public static final int[][] neighbourPositions = new int[][]{
            {1,0,0},
            {-1,0,0},
            {0,1,0},
            {0,-1,0},
            {0,0,1},
            {0,0,-1},
    };

    public static boolean[] getAdjacents(BlockPos pos) {
        Set<BlockPos> state = ClientPocketRooms.getRooms();
        boolean[] dirs = new boolean[6];
        if (!state.contains(pos)) return dirs;
        for (int i = 0; i < 6; i++) {
            dirs[i] = !state.contains(pos.add(neighbourPositions[i][0], neighbourPositions[i][1], neighbourPositions[i][2]));
        }
        dirs[2] &= pos.getY() <= 20;
        dirs[3] &= pos.getY() >= 0;
        return dirs;
    }
}
