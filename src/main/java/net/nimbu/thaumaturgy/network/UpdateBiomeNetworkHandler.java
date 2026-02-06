package net.nimbu.thaumaturgy.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.nimbu.thaumaturgy.network.PocketDimensionSync;
import net.nimbu.thaumaturgy.persistentstates.PocketDimensionPersistentState;

public final class UpdateBiomeNetworkHandler {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(
                UpdateBiomePacket.ID,
                (payload, context) -> {
                    context.player().server.execute(() -> {
                        ServerWorld world = context.player().getServerWorld();

                        PocketDimensionPersistentState state =
                                PocketDimensionPersistentState.get(world);

                        state.setDynamicBiomeEffects(payload.biome());
                        state.markDirty();

                        // sync to all players in this dimension
                        for (ServerPlayerEntity player : world.getPlayers()) {
                            PocketDimensionSync.syncDynamicBiome(world, player);
                        }
                    });
                }
        );
    }
}