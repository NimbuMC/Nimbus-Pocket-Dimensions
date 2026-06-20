//package net.nimbu.pocketdimensions.network;
//
//import net.minecraft.network.PacketByteBuf;
//import net.minecraft.network.codec.PacketCodec;
//import net.minecraft.network.packet.CustomPayload;
//import net.minecraft.util.Identifier;
//import net.nimbu.pocketdimensions.PocketDimensions;
//
//public record ReloadChunksRequestPayload() implements CustomPayload {
//
//    public static final CustomPayload.Id<ReloadChunksRequestPayload> ID =
//            new CustomPayload.Id<>(Identifier.of(PocketDimensions.MOD_ID, "reload_chunks_request"));
//
//    public static final PacketCodec<PacketByteBuf, ReloadChunksRequestPayload> CODEC =
//            PacketCodec.unit(new ReloadChunksRequestPayload());
//
//    @Override
//    public Id<? extends CustomPayload> getId() {
//        return ID;
//    }
//}