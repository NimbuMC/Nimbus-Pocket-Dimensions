package net.nimbu.thaumaturgy.spell;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.spell.entity.AerodetonationSpellEntity;
import net.nimbu.thaumaturgy.spell.entity.SpellPortalEntity;

public class SpellEntities {

    public static final EntityType<SpellPortalEntity> SPELL_PORTAL = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(Thaumaturgy.MOD_ID, "spell_portal"),
            EntityType.Builder.<SpellPortalEntity>create(SpellPortalEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f).maxTrackingRange(4).trackingTickInterval(10).build());

    public static final EntityType<AerodetonationSpellEntity> AERODETONATION_SPELL = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(Thaumaturgy.MOD_ID, "aerodetonation_spell"),
            EntityType.Builder.<AerodetonationSpellEntity>create(AerodetonationSpellEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f).maxTrackingRange(4).trackingTickInterval(10).build());


    public static void registerModEntities(){
        Thaumaturgy.LOGGER.info("Registering mod spell entities for "+Thaumaturgy.MOD_ID);
    }
}
