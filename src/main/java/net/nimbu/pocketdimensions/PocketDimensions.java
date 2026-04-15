package net.nimbu.pocketdimensions;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.nimbu.pocketdimensions.block.ModBlocks;
import net.nimbu.pocketdimensions.block.custom.GatewayBlock;
import net.nimbu.pocketdimensions.block.entity.ModBlockEntityTypes;
import net.nimbu.pocketdimensions.component.ModComponentInitializer;
import net.nimbu.pocketdimensions.component.PlayerGatewayComponent;
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
		PayloadTypeRegistry.playC2S().register(
				GatewayMaterialPayload.ID,
				GatewayMaterialPayload.CODEC
		);

		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntityTypes.registerBlockEntities();
		//ModDataComponentTypes.registerDataComponentTypes();
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

		ServerPlayConnectionEvents.JOIN.register(
				(handler, a, b) -> {
					PocketDimensionSync.sync(handler.getPlayer().getServerWorld(), handler.getPlayer());
					PocketDimensionSync.syncDynamicBiome(handler.getPlayer().getServerWorld(), handler.getPlayer());
				}
		);

		ServerPlayerEvents.AFTER_RESPAWN.register(
				(oldPlayer, newPlayer, alive) -> {
					PocketDimensionSync.sync(newPlayer.getServerWorld(), newPlayer);
					PocketDimensionSync.syncDynamicBiome(newPlayer.getServerWorld(), newPlayer);
				}
		);

		//for sending gateway material decided on the clientside player screen to player gateway component
		ServerPlayNetworking.registerGlobalReceiver(
				GatewayMaterialPayload.ID,
				(payload, context) -> {

					ServerPlayerEntity player = context.player();

					context.server().execute(() -> {
						//Save material type to player
						PlayerGatewayComponent comp =
								ModComponentInitializer.PLAYER_GATEWAY_KEY.get(player);

						comp.setGatewayMaterial(payload.material());

						ModComponentInitializer.PLAYER_GATEWAY_KEY.sync(player);

						//Update material type in pocket dimension
						ServerWorld world = player.getServerWorld();

						BlockPos pos = new BlockPos(6,148,1); //block position to override;
						Block doortype;
						switch (comp.getGatewayMaterial()){
							case 1: doortype=ModBlocks.OAK_GATEWAY; break;
							case 2: doortype=ModBlocks.SPRUCE_GATEWAY; break;
							case 3: doortype=ModBlocks.BIRCH_GATEWAY; break;
							case 4: doortype=ModBlocks.JUNGLE_GATEWAY; break;
							case 5: doortype=ModBlocks.ACACIA_GATEWAY; break;
							//case 6: doortype=ModBlocks.DARK_OAK_GATEWAY; break;   //unneeded
							case 7: doortype=ModBlocks.MANGROVE_GATEWAY; break;
							case 8: doortype=ModBlocks.CHERRY_GATEWAY; break;
							case 9: doortype=ModBlocks.CRIMSON_GATEWAY; break;
							case 10: doortype=ModBlocks.WARPED_GATEWAY; break;
							default: doortype=ModBlocks.DARK_OAK_GATEWAY; break;
						}


						//TODO: sometimes doesn't update right and appears invisible.... but it ain't that deep... probably
						BlockState lower = doortype.getDefaultState()
								.with(GatewayBlock.FACING, Direction.SOUTH).with(GatewayBlock.EXIT, true);
						BlockState upper = lower.with(GatewayBlock.HALF, DoubleBlockHalf.UPPER);

						world.setBlockState(pos, lower, Block.NOTIFY_ALL);
						world.setBlockState(pos.up(), upper, Block.NOTIFY_ALL);
					});
				}
		);
	}
}