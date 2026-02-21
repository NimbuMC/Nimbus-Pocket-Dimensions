package net.nimbu.pocketdimensions.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.block.ModBlocks;
import net.nimbu.pocketdimensions.block.entity.custom.DoorwayBlockEntity;
import net.nimbu.pocketdimensions.block.entity.custom.PocketDimensionCustomizerBlockEntity;

public class ModBlockEntityTypes {
    public static BlockEntityType<DoorwayBlockEntity> DOORWAY_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(PocketDimensions.MOD_ID, "doorway_block"),
                    BlockEntityType.Builder.create(
                            DoorwayBlockEntity::new,
                            ModBlocks.GATEWAY
                    ).build());

    public static BlockEntityType<PocketDimensionCustomizerBlockEntity> POCKET_DIMENSION_CUSTOMIZER_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(PocketDimensions.MOD_ID, "dimension_customizer_block_entity"),
                    BlockEntityType.Builder.create(
                            PocketDimensionCustomizerBlockEntity::new,
                            ModBlocks.DIMENSION_CUSTOMIZER
                    ).build(null));

    public static void registerBlockEntities() {
        PocketDimensions.LOGGER.info("Registering block entities for "+ PocketDimensions.MOD_ID);
    }
}
