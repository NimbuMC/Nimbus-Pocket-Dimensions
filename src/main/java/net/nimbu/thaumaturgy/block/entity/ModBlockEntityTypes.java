package net.nimbu.thaumaturgy.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.block.ModBlocks;
import net.nimbu.thaumaturgy.block.entity.custom.DoorwayBlockEntity;
import net.nimbu.thaumaturgy.block.entity.custom.PocketDimensionBorderControllerBlockEntity;
import net.nimbu.thaumaturgy.block.entity.custom.RevisualisingTableBlockEntity;

public class ModBlockEntityTypes {
    public static BlockEntityType<DoorwayBlockEntity> DOORWAY_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Thaumaturgy.MOD_ID, "doorway_block"),
                    BlockEntityType.Builder.create(
                            DoorwayBlockEntity::new,
                            ModBlocks.DOORWAY
                    ).build());


    public static final BlockEntityType<PocketDimensionBorderControllerBlockEntity> POCKET_DIMENSION_BORDER_CONTROLLER =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Thaumaturgy.MOD_ID, "pocket_dimension_border"),
                    BlockEntityType.Builder.create(
                            PocketDimensionBorderControllerBlockEntity::new,
                            ModBlocks.POCKET_DIMENSION_BORDER_CONTROLLER
                    ).build());

    public static final BlockEntityType<RevisualisingTableBlockEntity> REVISUALISING_TABLE_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Thaumaturgy.MOD_ID, "revisualising_table_block_entity"),
                    BlockEntityType.Builder.create(
                            RevisualisingTableBlockEntity::new,
                            ModBlocks.REVISUALISING_TABLE
                    ).build(null));

    public static void registerBlockEntities() {
        Thaumaturgy.LOGGER.info("Registering block entities for "+Thaumaturgy.MOD_ID);
    }
}
