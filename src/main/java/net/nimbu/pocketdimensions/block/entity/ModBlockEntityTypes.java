package net.nimbu.pocketdimensions.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.block.ModBlocks;
import net.nimbu.pocketdimensions.block.entity.custom.GatewayBlockEntity;
import net.nimbu.pocketdimensions.block.entity.custom.PocketDimensionCustomizerBlockEntity;

public class ModBlockEntityTypes {
    public static BlockEntityType<GatewayBlockEntity> GATEWAY_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(PocketDimensions.MOD_ID, "gateway_block"),
                    BlockEntityType.Builder.create(
                            GatewayBlockEntity::new,
                            ModBlocks.OAK_GATEWAY,
                            ModBlocks.SPRUCE_GATEWAY,
                            ModBlocks.BIRCH_GATEWAY,
                            ModBlocks.JUNGLE_GATEWAY,
                            ModBlocks.ACACIA_GATEWAY,
                            ModBlocks.DARK_OAK_GATEWAY,
                            ModBlocks.MANGROVE_GATEWAY,
                            ModBlocks.CHERRY_GATEWAY,
                            ModBlocks.CRIMSON_GATEWAY,
                            ModBlocks.WARPED_GATEWAY
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
