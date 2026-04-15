package net.nimbu.pocketdimensions.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.nimbu.pocketdimensions.PocketDimensions;

public record GatewayMaterialPayload(int material) implements CustomPayload {

    public static final Id<GatewayMaterialPayload> ID =
            new Id<>(Identifier.of(PocketDimensions.MOD_ID, "set_gateway_material"));

    public static final PacketCodec<RegistryByteBuf, GatewayMaterialPayload> CODEC =
            PacketCodec.of(
                    GatewayMaterialPayload::write,
                    GatewayMaterialPayload::read
            );

    private void write(RegistryByteBuf buf) {
        buf.writeInt(material);
    }

    private static GatewayMaterialPayload read(RegistryByteBuf buf) {
        return new GatewayMaterialPayload(buf.readInt());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
