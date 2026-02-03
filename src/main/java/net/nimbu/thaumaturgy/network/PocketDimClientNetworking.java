package net.nimbu.thaumaturgy.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.nimbu.thaumaturgy.Thaumaturgy;

public class PocketDimClientNetworking {

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(
                RoomSyncPayload.ID,
                (payload, context) -> {
                    context.client().execute(() -> {
                        ClientPocketDimensionPersistentState.setRooms(payload.rooms());
                    });
                }
        );

        ClientPlayNetworking.registerGlobalReceiver(
                SingularRoomPayload.ID,
                (payload, context) -> {
                    context.client().execute(() -> {
                        ClientPocketDimensionPersistentState.addRoom(payload.room());
                    });
                }
        );

        ClientPlayNetworking.registerGlobalReceiver(
                DynamicBiomePayload.ID,
                (payload, context) -> {
                    context.client().execute(() -> {
                        Thaumaturgy.LOGGER.info("done biome sync with inPocketDim " + payload.inPocketDimension());
                        ClientPocketDimensionPersistentState.setIsClientInPocketDimension(payload.inPocketDimension());
                        ClientPocketDimensionPersistentState.setDynamicBiomeBiomeEffects(payload.dynamicBiomeEffects());
                        ClientPocketDimensionPersistentState.setSkybox(payload.skybox());
                    });
                }
        );
    }
}
