package net.nimbu.pocketdimensions.network;

import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.worldgen.biome.DynamicBiomeEffects;

public record DynamicBiomePayload(boolean inPocketDimension, DynamicBiomeEffects dynamicBiomeEffects, Identifier skybox) implements CustomPayload {

    public static final Id<DynamicBiomePayload> ID =
            new Id<>(Identifier.of(PocketDimensions.MOD_ID, "dynamic_biome_sync"));

    public static final PacketCodec<RegistryByteBuf, DynamicBiomePayload> CODEC =
            PacketCodec.of(
                    DynamicBiomePayload::write,
                    DynamicBiomePayload::read
            );

    private void write(RegistryByteBuf buf) {
        buf.writeBoolean(inPocketDimension);
        buf.writeNbt(DynamicBiomeEffects.CODEC.encodeStart(NbtOps.INSTANCE, dynamicBiomeEffects).getOrThrow());
        buf.writeIdentifier(skybox);
    }

    private static DynamicBiomePayload read(RegistryByteBuf buf) {
        return new DynamicBiomePayload(
                buf.readBoolean(),
                DynamicBiomeEffects.CODEC.parse(NbtOps.INSTANCE, buf.readNbt()).getOrThrow(),
                buf.readIdentifier());
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
