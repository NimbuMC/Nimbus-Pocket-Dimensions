package net.nimbu.thaumaturgy.network;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.nimbu.thaumaturgy.Thaumaturgy;

import java.util.HashSet;
import java.util.Set;

public final class ClientPocketDimensionPersistentState {

    private static final Set<BlockPos> ROOMS = new HashSet<>();
    private static final int grassColour = 0xFFFFFF;
    private static final int waterColour = 0xFFFFFF;
    private static final int fogColour = 0xFFFFFF;
    private static final Identifier skyboxId = Identifier.of(Thaumaturgy.MOD_ID, "textures/skyboxes/default_sky.png");

    public static void setRooms(Set<BlockPos> rooms) {
        ROOMS.clear();
        ROOMS.addAll(rooms);
        Thaumaturgy.LOGGER.info(
                "Client received {} rooms",
                rooms.size()
        );
    }

    public static void addRoom(BlockPos room)
    {
        ROOMS.add(room);
        Thaumaturgy.LOGGER.info("Client received room {}", room);
    }

    public static boolean hasRoom(BlockPos pos) {
        return ROOMS.contains(pos);
    }

    public static Set<BlockPos> getRooms() {
        return ROOMS;
    }
}
