package net.nimbu.thaumaturgy.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.nimbu.thaumaturgy.Thaumaturgy;

import java.util.HashSet;
import java.util.Set;

public record RoomSyncPayload(Set<BlockPos> rooms) implements CustomPayload {

    public static final Id<RoomSyncPayload> ID =
            new Id<>(Identifier.of(Thaumaturgy.MOD_ID, "room_sync"));

    public static final PacketCodec<RegistryByteBuf, RoomSyncPayload> CODEC =
            PacketCodec.of(
                    RoomSyncPayload::write,
                    RoomSyncPayload::read
            );

    private void write(RegistryByteBuf buf) {
        buf.writeInt(rooms.size());
        for (BlockPos pos : rooms) {
            buf.writeBlockPos(pos);
        }
    }

    private static RoomSyncPayload read(RegistryByteBuf buf) {
        int count = buf.readInt();
        Set<BlockPos> rooms = new HashSet<>();

        for (int i = 0; i < count; i++) {
            rooms.add(buf.readBlockPos());
        }

        return new RoomSyncPayload(rooms);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

