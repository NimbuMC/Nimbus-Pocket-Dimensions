package net.nimbu.thaumaturgy.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.block.ModBlocks;
import net.nimbu.thaumaturgy.block.entity.custom.PedestalBlockEntity;
import net.nimbu.thaumaturgy.block.entity.custom.PocketDimensionPortalBlockEntity;

public class ModBlockEntityTypes {

    public static final BlockEntityType<PedestalBlockEntity> PEDESTAL_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Thaumaturgy.MOD_ID, "pedestal_block_entity"),
                    BlockEntityType.Builder.create(
                            PedestalBlockEntity::new,
                            ModBlocks.PEDESTAL
                    ).build(null));

    public static BlockEntityType<PocketDimensionPortalBlockEntity> POCKET_DIMENSION_PORTAL;

    public static void registerBlockEntities() {
        Thaumaturgy.LOGGER.info("Registering block entities for "+Thaumaturgy.MOD_ID);


        //Please change this to format in the same way as the above example/s. i'd do it myself but idk what's breaking your code lols
        POCKET_DIMENSION_PORTAL =
        Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Thaumaturgy.MOD_ID, "pocket_dimension_portal"),
                BlockEntityType.Builder.create(
                        PocketDimensionPortalBlockEntity::new,
                        ModBlocks.POCKET_DIMENSION_PORTAL
                ).build());;
    }
}
