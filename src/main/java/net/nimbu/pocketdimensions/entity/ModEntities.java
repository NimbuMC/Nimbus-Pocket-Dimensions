package net.nimbu.pocketdimensions.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.entity.custom.GatewayProjectileEntity;

public class ModEntities {


    public static final EntityType<GatewayProjectileEntity> SPELL_PORTAL = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(PocketDimensions.MOD_ID, "spell_portal"),
            EntityType.Builder.<GatewayProjectileEntity>create(GatewayProjectileEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f).maxTrackingRange(4).trackingTickInterval(10).build());


    public static void registerModEntities(){
        PocketDimensions.LOGGER.info("Registering mod entities for "+ PocketDimensions.MOD_ID);
    }
}
