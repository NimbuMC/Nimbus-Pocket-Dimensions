package net.nimbu.thaumaturgy.network;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.BiomeParticleConfig;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.persistentstates.PocketDimensionPersistentState;
import net.nimbu.thaumaturgy.worldgen.biome.DynamicBiomeEffects;

import java.util.HashSet;
import java.util.Set;

public final class ClientPocketDimensionPersistentState {

    private static final Set<BlockPos> ROOMS = new HashSet<>();
    private static boolean isClientInPocketDimension = false;
    private static DynamicBiomeEffects dynamicBiomeBiomeEffects =
            new DynamicBiomeEffects.Builder()
                    .skyColor(0xFF00FF)
                    .foliageColor(0xFF00FF)
                    .additionsSound(
                            new BiomeAdditionsSound(SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS,0.05))
                    .particleConfig(
                            new BiomeParticleConfig(ParticleTypes.END_ROD,
                                    0.001f)
                    )
                    .grassColor(0xFF00FF)
                    .waterColor(0xFF00FF)
                    .waterFogColor(0xFF00FF)
                    .loopSound(SoundEvents.AMBIENT_WARPED_FOREST_LOOP)
                    .moodSound(new BiomeMoodSound(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0))
                    .music(MusicType.createIngameMusic(SoundEvents.MUSIC_NETHER_NETHER_WASTES))
                    .fogColor(0xFF00FF)
                    .build();
    private static Identifier skybox = Identifier.ofVanilla("textures/environment/end_sky.png");

    public static void setRooms(Set<BlockPos> rooms) {
        ROOMS.clear();
        ROOMS.addAll(rooms);
        Thaumaturgy.LOGGER.info(
                "Client received {} rooms",
                rooms.size()
        );
    }

    public static void addRoom(BlockPos room)
    {
        ROOMS.add(room);
        Thaumaturgy.LOGGER.info("Client received room {}", room);
    }

    public static boolean hasRoom(BlockPos pos) {
        return ROOMS.contains(pos);
    }

    public static Set<BlockPos> getRooms() {
        return ROOMS;
    }

    public static void addRoom(ServerWorld world, BlockPos pos) {
        PocketDimensionPersistentState state = PocketDimensionPersistentState.get(world);

        // Avoid resending if already unlocked
        if (state.isRoomUnlocked(pos)) return;

        state.addRoom(pos);

        // Sync to all players currently in this dimension
        for (ServerPlayerEntity player : world.getPlayers()) {
            PocketDimensionSync.updateSingularRoom(player, pos);
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
        Set<BlockPos> state = getRooms();
        boolean[] dirs = new boolean[6];
        if (!state.contains(pos)) return dirs;
        for (int i = 0; i < 6; i++) {
            dirs[i] = !state.contains(pos.add(neighbourPositions[i][0], neighbourPositions[i][1], neighbourPositions[i][2]));
        }
        dirs[2] &= pos.getY() <= 20;
        dirs[3] &= pos.getY() >= 0;
        return dirs;
    }

    public static boolean hasAdjacents(BlockPos pos) {
        Set<BlockPos> state = getRooms();
        for (int i = 0; i < 6; i++) {
            if(state.contains(pos.add(neighbourPositions[i][0], neighbourPositions[i][1], neighbourPositions[i][2]))) return true;
        }
        return false;
    }

    public static Identifier getSkybox() {
        return skybox;
    }

    public static void setSkybox(Identifier skybox) {
        ClientPocketDimensionPersistentState.skybox = skybox;
    }

    public static DynamicBiomeEffects getDynamicBiomeEffects() {
        return dynamicBiomeBiomeEffects;
    }

    public static void setDynamicBiomeBiomeEffects(DynamicBiomeEffects dynamicBiomeBiomeEffects) {
        ClientPocketDimensionPersistentState.dynamicBiomeBiomeEffects = dynamicBiomeBiomeEffects;
    }

    public static boolean isClientInPocketDimension() {
        return isClientInPocketDimension;
    }

    public static void setIsClientInPocketDimension(boolean isClientInPocketDimension) {
        ClientPocketDimensionPersistentState.isClientInPocketDimension = isClientInPocketDimension;
    }
}
