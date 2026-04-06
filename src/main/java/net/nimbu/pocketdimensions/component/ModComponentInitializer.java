package net.nimbu.pocketdimensions.component;

import net.minecraft.util.Identifier;
import net.nimbu.pocketdimensions.PocketDimensions;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class ModComponentInitializer implements EntityComponentInitializer {

    public static ComponentKey<PlayerGatewayComponent> PLAYER_GATEWAY_KEY = ComponentRegistryV3.INSTANCE.getOrCreate(
            Identifier.of(PocketDimensions.MOD_ID, "player_gateway"),

            PlayerGatewayComponent.class
    );;

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(
                PLAYER_GATEWAY_KEY,
                player -> new PlayerGatewayComponentImpl(),
                RespawnCopyStrategy.ALWAYS_COPY //keeps data after death
        );
    }
}
