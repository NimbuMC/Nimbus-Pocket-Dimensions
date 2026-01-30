package net.nimbu.thaumaturgy.persistentstates;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.nimbu.thaumaturgy.Thaumaturgy;

import java.util.HashSet;
import java.util.Set;

public class PocketDimensionPersistentState extends PersistentState {

    private final HashSet<BlockPos> unlockedRooms = new HashSet<>();
    private int grassColour = 0xFFFFFF;
    private int waterColour = 0xFFFFFF;
    private int fogColour = 0xFFFFFF;
    private Identifier skyboxId = Identifier.of(Thaumaturgy.MOD_ID, "textures/skyboxes/default_sky.png");

    public void addRoom(BlockPos pos) {
        unlockedRooms.add(pos.toImmutable());
        markDirty();
    }

    public boolean isRoomUnlocked(BlockPos pos) {
        return unlockedRooms.contains(pos);
    }

    public Set<BlockPos> getUnlockedRooms() {
        return Set.copyOf(unlockedRooms);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putInt("Grass", grassColour);
        nbt.putInt("Water", waterColour);
        nbt.putInt("Fog", fogColour);
        nbt.putString("Skybox", skyboxId.toString());
        int i = 0;
        for (BlockPos pos : unlockedRooms) {
            nbt.putIntArray("r" + i++, new int[]{
                    pos.getX(),
                    pos.getY(),
                    pos.getZ()
            });
        }
        return nbt;
    }

    public static PocketDimensionPersistentState fromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        PocketDimensionPersistentState state = new PocketDimensionPersistentState();
        state.grassColour = nbt.getInt("Grass");
        state.waterColour = nbt.getInt("Water");
        state.fogColour = nbt.getInt("Fog");
        state.skyboxId = Identifier.of(nbt.getString("Skybox"));
        for (String key : nbt.getKeys()) {
            int[] arr = nbt.getIntArray(key);
            if (arr.length == 3) state.unlockedRooms.add(new BlockPos(arr[0], arr[1], arr[2]));
        }

        return state;
    }

    public static final Type<PocketDimensionPersistentState> TYPE =
            new Type<>(
                    PocketDimensionPersistentState::new,
                    PocketDimensionPersistentState::fromNbt,
                    null
            );

    public static PocketDimensionPersistentState get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(TYPE, "pocket_dimension_rooms");
    }
}


