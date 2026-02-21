package net.nimbu.pocketdimensions.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.nimbu.pocketdimensions.PocketDimensions;

public record SingularRoomPayload(BlockPos room) implements CustomPayload {

    public static final Id<SingularRoomPayload> ID =
            new Id<>(Identifier.of(PocketDimensions.MOD_ID, "singlular_room_update"));

    public static final PacketCodec<RegistryByteBuf, SingularRoomPayload> CODEC =
            PacketCodec.of(
                    SingularRoomPayload::write,
                    SingularRoomPayload::read
            );

    private void write(RegistryByteBuf buf) {
        buf.writeBlockPos(room);
    }

    private static SingularRoomPayload read(RegistryByteBuf buf) {
        return new SingularRoomPayload(buf.readBlockPos());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
