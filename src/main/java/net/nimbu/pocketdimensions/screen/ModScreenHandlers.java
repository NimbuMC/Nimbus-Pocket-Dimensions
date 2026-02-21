package net.nimbu.pocketdimensions.screen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.screen.custom.PocketDimensionBiomeControllerScreenHandler;

public class ModScreenHandlers {

    public static final ScreenHandlerType<PocketDimensionBiomeControllerScreenHandler> POCKET_DIM_BIOME_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(PocketDimensions.MOD_ID, "pocket_dimension_screen_handler"),
                    new ScreenHandlerType<>(PocketDimensionBiomeControllerScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static void registerScreenHandlers() {
        PocketDimensions.LOGGER.info("Registering screen handlers for "+ PocketDimensions.MOD_ID);
    }

}
