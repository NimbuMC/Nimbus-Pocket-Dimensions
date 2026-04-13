package net.nimbu.pocketdimensions.persistentstates;

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
import net.minecraft.world.biome.BiomeParticleConfig;
import net.nimbu.pocketdimensions.worldgen.biome.DynamicBiomeEffects;

import java.util.HashSet;
import java.util.Set;

public class PocketDimensionPersistentState extends PersistentState {

    private final HashSet<BlockPos> unlockedRooms = new HashSet<>();
    private DynamicBiomeEffects dynamicBiomeEffects =
            new DynamicBiomeEffects.Builder()
                    .skyColor(0xFFFFFF)
                    .foliageColor(0x77AB2F)
                    .grassColor(0x91BD59)
                    .waterColor(0x3F76E4)
                    .waterFogColor(0x050533)
                    .music(MusicType.createIngameMusic(SoundEvents.MUSIC_CREATIVE))
                    .fogColor(0xFFFFFF)
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


