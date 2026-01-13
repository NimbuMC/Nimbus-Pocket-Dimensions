package net.nimbu.thaumaturgy.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.entity.custom.PixieEntity;

public class ModEntities {

    public static final EntityType<PixieEntity> PIXIE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(Thaumaturgy.MOD_ID, "pixie"),
            EntityType.Builder.create(PixieEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.5f, 0.5f).maxTrackingRange(20).build()); //dimensions of hit-box


    public static void registerModEntities(){
        Thaumaturgy.LOGGER.info("Registering mod entities for "+Thaumaturgy.MOD_ID);
    }
}
