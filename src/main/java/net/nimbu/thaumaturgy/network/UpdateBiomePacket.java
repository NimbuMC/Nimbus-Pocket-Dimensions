package net.nimbu.thaumaturgy.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.network.DynamicBiomePayload;
import net.nimbu.thaumaturgy.worldgen.biome.DynamicBiomeEffects;

public record UpdateBiomePacket(DynamicBiomeEffects biome) implements CustomPayload {

    public static final Id<UpdateBiomePacket> ID =
            new Id<>(Identifier.of(Thaumaturgy.MOD_ID, "update_dynamic_biome"));

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
