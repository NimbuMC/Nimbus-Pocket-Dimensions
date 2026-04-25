package net.nimbu.pocketdimensions.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.nimbu.pocketdimensions.PocketDimensions;

public record ReloadRendererPayload() implements CustomPayload {

    public static final CustomPayload.Id<ReloadRendererPayload> ID =
            new CustomPayload.Id<>(Identifier.of(PocketDimensions.MOD_ID, "reload_renderer"));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<PacketByteBuf, ReloadRendererPayload> CODEC =
            PacketCodec.unit(new ReloadRendererPayload());
}