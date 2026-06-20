//package net.nimbu.pocketdimensions.network;
//
//import net.minecraft.network.PacketByteBuf;
//import net.minecraft.network.codec.PacketCodec;
//import net.minecraft.network.packet.CustomPayload;
//import net.minecraft.util.Identifier;
//import net.nimbu.pocketdimensions.PocketDimensions;
//
//public record ReloadChunksS2CPayload() implements CustomPayload {
//
//    public static final Id<ReloadChunksS2CPayload> ID =
//            new Id<>(Identifier.of(PocketDimensions.MOD_ID, "reload_chunks"));
//
//    public static final PacketCodec<PacketByteBuf, ReloadChunksS2CPayload> CODEC =
//            PacketCodec.unit(new ReloadChunksS2CPayload());
//
//    @Override
//    public Id<? extends CustomPayload> getId() {
//        return ID;
//    }
//}