package net.nimbu.pocketdimensions;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.nimbu.pocketdimensions.block.ModBlocks;
import net.nimbu.pocketdimensions.block.custom.GatewayBlock;
import net.nimbu.pocketdimensions.block.entity.ModBlockEntityTypes;
import net.nimbu.pocketdimensions.block.entity.custom.GatewayBlockEntity;
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

						BlockPos pocketDimensionGatewayPos = new BlockPos(6,148,1); //block position to override;

						BlockEntity blockEntity = world.getBlockEntity(pocketDimensionGatewayPos);
						if (blockEntity instanceof GatewayBlockEntity previousPocketDimensionGatewayEntity) { //if gateway found, create new gateway with previous gateway data

							Block gatewayType;
							switch (comp.getGatewayMaterial()){
								case 1: gatewayType=ModBlocks.OAK_GATEWAY; break;
								case 2: gatewayType=ModBlocks.SPRUCE_GATEWAY; break;
								case 3: gatewayType=ModBlocks.BIRCH_GATEWAY; break;
								case 4: gatewayType=ModBlocks.JUNGLE_GATEWAY; break;
								case 5: gatewayType=ModBlocks.ACACIA_GATEWAY; break;
								//case 6: gatewayType=ModBlocks.DARK_OAK_GATEWAY; break;   //unneeded
								case 7: gatewayType=ModBlocks.MANGROVE_GATEWAY; break;
								case 8: gatewayType=ModBlocks.CHERRY_GATEWAY; break;
								case 9: gatewayType=ModBlocks.CRIMSON_GATEWAY; break;
								case 10: gatewayType=ModBlocks.WARPED_GATEWAY; break;
								default: gatewayType=ModBlocks.DARK_OAK_GATEWAY; break;
							}


							//TODO: sometimes doesn't update right and appears as it did the previous load.... but it ain't that deep... probably
							//TODO: much of the system below (and other systems) rely on gateways not moving or breaking abnormally. might wanna make ways to handle this. and make a funcition or something to replace gateway types

							placeGateway(world, pocketDimensionGatewayPos, gatewayType.getDefaultState()
									.with(GatewayBlock.FACING, Direction.SOUTH)
									.with(GatewayBlock.EXIT, true));

							BlockEntity be = world.getBlockEntity(pocketDimensionGatewayPos);
							if (!(be instanceof GatewayBlockEntity exitGatewayEntity)) return;

							//set the in-pocket dimension (exit) gateway's location dimension to the saved location and dimension
							BlockPos entranceGatewayBlockPos = previousPocketDimensionGatewayEntity.getExitBlockPos();
							exitGatewayEntity.setExitPosition(entranceGatewayBlockPos, previousPocketDimensionGatewayEntity.getExitDimension()); //replace exit position when creating gateway


							//after this, update the entrance gateway to new design
							ServerWorld targetWorld = world.getServer().getWorld(exitGatewayEntity.getExitDimension()); //get the entrance dimension
							if (targetWorld == null) return;

							BlockEntity entranceBlockEntity = targetWorld.getBlockEntity(entranceGatewayBlockPos); //get the previous block entity also, to store its data
							BlockState entranceBlock = targetWorld.getBlockState(entranceGatewayBlockPos); //get the entrance block to verify that it is a gateway, so that the gateway's properties can be taken
							if (!(entranceBlock.getBlock() instanceof GatewayBlock)) return;
							if (!(entranceBlockEntity instanceof GatewayBlockEntity previousEntranceGatewayBlockEntity)) return;

							placeGateway(targetWorld, entranceGatewayBlockPos, gatewayType.getDefaultState()
									.with(GatewayBlock.FACING, entranceBlock.get(GatewayBlock.FACING))
									.with(GatewayBlock.OPEN, entranceBlock.get(GatewayBlock.OPEN)));

							BlockEntity newEntranceBe = targetWorld.getBlockEntity(entranceGatewayBlockPos);
							if (!(newEntranceBe instanceof GatewayBlockEntity entranceGatewayEntity)) return;

							//set the outside-pocket dimension gateway
							entranceGatewayEntity.setExitPosition(previousEntranceGatewayBlockEntity.getExitBlockPos(), previousEntranceGatewayBlockEntity.getExitDimension()); //replace exit position when creating gateway
						}

					});

				}
		);
	}

	private static void placeGateway(ServerWorld world, BlockPos pos, BlockState baseState) {
		world.setBlockState(pos, baseState.with(GatewayBlock.HALF, DoubleBlockHalf.LOWER), Block.NOTIFY_ALL);
		world.setBlockState(pos.up(), baseState.with(GatewayBlock.HALF, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL);
	}
}