package net.nimbu.thaumaturgy.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.screen.custom.RevisualisingTableScreenHandler;

public class ModScreenHanders {

    public static final ScreenHandlerType<RevisualisingTableScreenHandler> REVISUALISING_TABLE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Thaumaturgy.MOD_ID, "revisualising_table_screen_handler"),
                    new ExtendedScreenHandlerType<>(RevisualisingTableScreenHandler::new, BlockPos.PACKET_CODEC));

    public static void registerScreenHandlers() {
        Thaumaturgy.LOGGER.info("Registering screen handlers for "+Thaumaturgy.MOD_ID);
    }

}
