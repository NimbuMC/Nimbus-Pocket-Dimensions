package net.nimbu.thaumaturgy.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.BiomeEffects;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.persistentstates.PocketDimensionPersistentState;
import net.nimbu.thaumaturgy.worldgen.biome.DynamicBiomeEffects;

import java.util.Set;

public class PocketDimensionSync {

    public static void sync(ServerWorld world, ServerPlayerEntity player) {
        PocketDimensionPersistentState state = PocketDimensionPersistentState.get(world);

        Set<BlockPos> rooms = state.getUnlockedRooms();

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(rooms.size());

        for (BlockPos pos : rooms) {
            buf.writeBlockPos(pos);
        }
        Thaumaturgy.LOGGER.info(
                "Sending {} rooms to {}",
                rooms.size(),
                player.getName().getString()
        );
        ServerPlayNetworking.send(player, new RoomSyncPayload(rooms));
    }

    public static void updateSingularRoom(ServerPlayerEntity player, BlockPos posToSync) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(posToSync);
        ServerPlayNetworking.send(player, new SingularRoomPayload(posToSync));
    }

    public static void syncDynamicBiome(ServerWorld world, ServerPlayerEntity player)
    {
        PocketDimensionPersistentState state = PocketDimensionPersistentState.get(world);
        PacketByteBuf buf = PacketByteBufs.create();
        Thaumaturgy.LOGGER.info(
                "doingDynamicBiome with skybox id of {}",
                state.getSkybox().getPath()
        );
        buf.writeBoolean(world.getRegistryKey().getValue().toString().contains("pocket_dimension"));
        buf.writeNbt(DynamicBiomeEffects.CODEC
                        .encodeStart(NbtOps.INSTANCE, state.getDynamicBiomeEffects())
                        .getOrThrow()
        );
        buf.writeIdentifier(state.getSkybox());
        ServerPlayNetworking.send(player,
                new DynamicBiomePayload(
                        world.getRegistryKey().getValue().toString().contains("pocket_dimension"),
                        state.getDynamicBiomeEffects(),
                        state.getSkybox()));
    }
}
