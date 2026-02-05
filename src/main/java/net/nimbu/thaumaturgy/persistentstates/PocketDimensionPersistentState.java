package net.nimbu.thaumaturgy.persistentstates;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.BiomeParticleConfig;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.worldgen.biome.DynamicBiomeEffects;

import java.util.HashSet;
import java.util.Set;

public class PocketDimensionPersistentState extends PersistentState {

    private final HashSet<BlockPos> unlockedRooms = new HashSet<>();
    private DynamicBiomeEffects dynamicBiomeEffects =
            new DynamicBiomeEffects.Builder()
                    .skyColor(0xFF00FF)
                    .foliageColor(0xFF00FF)
                    .additionsSound(new BiomeAdditionsSound(SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS,0.05))
                    .particleConfig(new BiomeParticleConfig(ParticleTypes.END_ROD,0.001f))
                    .grassColor(0xFF00FF)
                    .waterColor(0xFF00FF)
                    .waterFogColor(0xFF00FF)
                    .loopSound(SoundEvents.AMBIENT_WARPED_FOREST_LOOP)
                    .moodSound(new BiomeMoodSound(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0))
                    .music(MusicType.createIngameMusic(SoundEvents.MUSIC_NETHER_NETHER_WASTES))
                    .fogColor(0xFF00FF)
                    .build();
    private Identifier skybox = Identifier.ofVanilla("textures/environment/end_sky.png");

    public void addRoom(BlockPos pos) {
        unlockedRooms.add(pos.toImmutable());
        markDirty();
    }

    public boolean isRoomUnlocked(BlockPos pos) { return unlockedRooms.contains(pos); }

    public Set<BlockPos> getUnlockedRooms() { return Set.copyOf(unlockedRooms); }
    public DynamicBiomeEffects getDynamicBiomeEffects(){ return dynamicBiomeEffects; }
    public void setDynamicBiomeEffects(DynamicBiomeEffects newBiomeEffects){ dynamicBiomeEffects = newBiomeEffects; }
    public Identifier getSkybox(){ return skybox; }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        int i = 0;
        NbtCompound rooms = new NbtCompound();
        for (BlockPos pos : unlockedRooms) {
            rooms.putIntArray("r" + i++, new int[]{
                    pos.getX(),
                    pos.getY(),
                    pos.getZ()
            });
        }
        nbt.put("Rooms", rooms);
        nbt.put("DimensionEffects", DynamicBiomeEffects.CODEC
                        .encodeStart(NbtOps.INSTANCE, dynamicBiomeEffects)
                        .getOrThrow());
        nbt.putString("Skybox", skybox.getPath());
        return nbt;
    }

    public static PocketDimensionPersistentState fromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        PocketDimensionPersistentState state = new PocketDimensionPersistentState();
        NbtCompound rooms = nbt.getCompound("Rooms");
        for (String key : rooms.getKeys()) {
            int[] arr = rooms.getIntArray(key);
            if (arr.length == 3) state.unlockedRooms.add(new BlockPos(arr[0], arr[1], arr[2]));
        }
        state.dynamicBiomeEffects = DynamicBiomeEffects.CODEC
                .parse(NbtOps.INSTANCE, nbt.get("DimensionEffects"))
                .getOrThrow();
        state.skybox = Identifier.of(nbt.getString("Skybox"));
        return state;
    }

    public static final Type<PocketDimensionPersistentState> TYPE =
            new Type<>(
                    PocketDimensionPersistentState::new,
                    PocketDimensionPersistentState::fromNbt,
                    null
            );

    public static PocketDimensionPersistentState get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(TYPE, "pocket_dimension_state");
    }
}


