package net.nimbu.thaumaturgy.block;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.block.custom.PocketDimensionPortalBlockEntity;

public class ModBlockEntityTypes {

    public static BlockEntityType<PocketDimensionPortalBlockEntity> POCKET_DIMENSION_PORTAL;

    public static void register() {
        POCKET_DIMENSION_PORTAL = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of("thaumaturgy", "pocket_dimension_portal"),
                BlockEntityType.Builder.create(
                        PocketDimensionPortalBlockEntity::new,
                        ModBlocks.POCKET_DIMENSION_PORTAL
                ).build()
        );
    }
}
