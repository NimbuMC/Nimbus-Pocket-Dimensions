package net.nimbu.pocketdimensions.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.Identifier;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.worldgen.biome.DynamicBiomeEffects;

public record UpdateBiomePacket(DynamicBiomeEffects biome) implements CustomPayload {

    public static final Id<UpdateBiomePacket> ID =
            new Id<>(Identifier.of(PocketDimensions.MOD_ID, "update_dynamic_biome"));

    public static final PacketCodec<RegistryByteBuf, UpdateBiomePacket> CODEC =
            PacketCodec.of(UpdateBiomePacket::write, UpdateBiomePacket::read);

    @Override
    public Id<UpdateBiomePacket> getId() {
        return ID;
    }


    private void write(RegistryByteBuf buf) {
        buf.writeNbt(DynamicBiomeEffects.CODEC.encodeStart(NbtOps.INSTANCE, biome).getOrThrow());
    }

    private static UpdateBiomePacket read(RegistryByteBuf buf) {
        return new UpdateBiomePacket(DynamicBiomeEffects.CODEC.parse(NbtOps.INSTANCE,buf.readNbt()).getOrThrow());
    }
}
