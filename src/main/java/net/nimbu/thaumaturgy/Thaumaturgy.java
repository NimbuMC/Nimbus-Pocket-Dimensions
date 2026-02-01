package net.nimbu.thaumaturgy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nimbu.thaumaturgy.block.ModBlocks;
import net.nimbu.thaumaturgy.block.entity.ModBlockEntityTypes;
import net.nimbu.thaumaturgy.component.ModDataComponentTypes;
import net.nimbu.thaumaturgy.effect.ModEffects;
import net.nimbu.thaumaturgy.enchantment.ModEnchantmentEffects;
import net.nimbu.thaumaturgy.entity.ModEntities;
import net.nimbu.thaumaturgy.entity.custom.PixieEntity;
import net.nimbu.thaumaturgy.item.ModItemGroups;
import net.nimbu.thaumaturgy.item.ModItems;
import net.nimbu.thaumaturgy.network.DynamicBiomePayload;
import net.nimbu.thaumaturgy.network.PocketDimensionSync;
import net.nimbu.thaumaturgy.network.RoomSyncPayload;
import net.nimbu.thaumaturgy.network.SingularRoomPayload;
import net.nimbu.thaumaturgy.particle.ModParticles;
import net.nimbu.thaumaturgy.sound.ModSoundEvents;
import net.nimbu.thaumaturgy.spell.Spells;
import net.nimbu.thaumaturgy.util.HammerUsageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Thaumaturgy implements ModInitializer {

	public static final String MOD_ID = "thaumaturgy";
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
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModItems.registerGrimoires();
		ModBlocks.registerModBlocks();
		ModBlockEntityTypes.registerBlockEntities();
		ModDataComponentTypes.registerDataComponentTypes();
		ModEntities.registerModEntities();
		ModEffects.registerEffects();
		ModSoundEvents.registerSounds();
		ModParticles.registerParticles();
		ModEnchantmentEffects.registerEnchantmentEffects();

		Spells.registerSpells();




		// Below are "events". These are general things that happen during gameplay that can have custom
		// methods linked to them.
		// Find events by clicking an example, and clicking "player" on the imported package.
		// Client only events should be handled in the Thaumaturgy client class.
		PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent());
		/*AttackEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
			if(entity instanceof SheepEntity sheepEntity && !world.isClient()){
				if(playerEntity.getMainHandStack().getItem() == Items.END_ROD){
					((SheepEntity) entity).headYaw=90;
					playerEntity.sendMessage(Text.literal("The Player just hit a sheep with an end rod you sick fuck!"));
					playerEntity.getMainHandStack().decrement(1);
					sheepEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 600, 1));
				}
			}
			return ActionResult.PASS; //signifies the code to continue on to the next step
		});*/

		//Entities
		FabricDefaultAttributeRegistry.register(ModEntities.PIXIE, PixieEntity.createAttributes());



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
	}
}