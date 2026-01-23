package net.nimbu.thaumaturgy.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.persistentstates.PocketDimensionPersistentState;

import java.util.Set;

public class PocketDimRoomSync {

    public static void sync(ServerWorld world, ServerPlayerEntity player) {
        PocketDimensionPersistentState state =
                PocketDimensionPersistentState.get(world);

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
        Thaumaturgy.LOGGER.info(
                "Syncing room {} rooms to {}",
                posToSync,
                player.getName().getString()
        );
        ServerPlayNetworking.send(player, new SingularRoomPayload(posToSync));
    }
}
