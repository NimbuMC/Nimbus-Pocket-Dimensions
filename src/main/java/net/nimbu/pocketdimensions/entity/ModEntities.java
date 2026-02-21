package net.nimbu.pocketdimensions.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.entity.custom.SpellPortalEntity;

public class ModEntities {


    public static final EntityType<SpellPortalEntity> SPELL_PORTAL = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(PocketDimensions.MOD_ID, "spell_portal"),
            EntityType.Builder.<SpellPortalEntity>create(SpellPortalEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f).maxTrackingRange(4).trackingTickInterval(10).build());


    public static void registerModEntities(){
        PocketDimensions.LOGGER.info("Registering mod entities for "+ PocketDimensions.MOD_ID);
    }
}
