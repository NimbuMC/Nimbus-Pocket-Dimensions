package net.nimbu.pocketdimensions;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nimbu.pocketdimensions.block.ModBlocks;
import net.nimbu.pocketdimensions.block.entity.ModBlockEntityTypes;
import net.nimbu.pocketdimensions.component.ModDataComponentTypes;
import net.nimbu.pocketdimensions.entity.ModEntities;
import net.nimbu.pocketdimensions.item.ModItemGroups;
import net.nimbu.pocketdimensions.item.ModItems;
import net.nimbu.pocketdimensions.network.*;
import net.nimbu.pocketdimensions.network.DynamicBiomePayload;
import net.nimbu.pocketdimensions.network.PocketDimensionSync;
import net.nimbu.pocketdimensions.network.RoomSyncPayload;
import net.nimbu.pocketdimensions.network.SingularRoomPayload;
import net.nimbu.pocketdimensions.particle.ModParticleTypes;
import net.nimbu.pocketdimensions.sound.ModSoundEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PocketDimensions implements ModInitializer {

	public static final String MOD_ID = "pocketdimensions";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitialize() {

		PayloadTypeRegistry.playS2C().register(
				RoomSyncPayload.ID,
				RoomSyncPayload.CODEC
		);
		PayloadTypeRegistry.playS2C().register(
				SingularRoomPayload.ID,
				SingularRoomPayload.CODEC
		);
		PayloadTypeRegistry.playS2C().register(
				DynamicBiomePayload.ID,
				DynamicBiomePayload.CODEC
		);
		PayloadTypeRegistry.playC2S().register(
				UpdateBiomePacket.ID,
				UpdateBiomePacket.CODEC
		);

		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntityTypes.registerBlockEntities();
		ModDataComponentTypes.registerDataComponentTypes();
		ModEntities.registerModEntities();
		ModSoundEvents.registerSounds();
		ModParticleTypes.registerParticles();
		UpdateBiomeNetworkHandler.register();




		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerPlayerEntity player = handler.getPlayer();
			server.execute(() -> {
				PocketDimensionSync.sync(player.getServerWorld(), player);
				PocketDimensionSync.syncDynamicBiome(player.getServerWorld(), player);
			});
		});

		ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(
				(player, origin, destination) -> {
					PocketDimensionSync.sync(destination, player);
					PocketDimensionSync.syncDynamicBiome(player.getServerWorld(), player);
				}
		);

		ServerPlayerEvents.AFTER_RESPAWN.register(
				(oldPlayer, newPlayer, alive) -> {
					PocketDimensionSync.sync(newPlayer.getServerWorld(), newPlayer);
					PocketDimensionSync.syncDynamicBiome(newPlayer.getServerWorld(), newPlayer);
				}
		);
	}
}