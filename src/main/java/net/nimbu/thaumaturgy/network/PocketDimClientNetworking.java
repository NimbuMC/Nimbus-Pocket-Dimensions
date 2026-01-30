package net.nimbu.thaumaturgy.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

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
    }
}
