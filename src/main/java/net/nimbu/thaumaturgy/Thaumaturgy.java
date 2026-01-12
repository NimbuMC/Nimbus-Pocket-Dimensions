package net.nimbu.thaumaturgy;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.block.ModBlockEntityTypes;
import net.nimbu.thaumaturgy.block.ModBlocks;
import net.nimbu.thaumaturgy.component.ModDataComponentTypes;
import net.nimbu.thaumaturgy.effect.ModEffects;
import net.nimbu.thaumaturgy.item.ModItemGroups;
import net.nimbu.thaumaturgy.item.ModItems;
import net.nimbu.thaumaturgy.sound.ModSounds;
import net.nimbu.thaumaturgy.util.HammerUsageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Thaumaturgy implements ModInitializer {

	public static final String MOD_ID = "thaumaturgy";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntityTypes.register();
		ModDataComponentTypes.registerDataComponentTypes();

		// Below are "events". These are general things that happen during gameplay that can have custom
		// methods linked to them.
		// Find events by clicking an example, and clicking "player" on the imported package.
		// Client only events should be handled in the Thaumaturgy client class.
		PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent());
		AttackEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
			if(entity instanceof SheepEntity sheepEntity && !world.isClient()){
				if(playerEntity.getMainHandStack().getItem() == Items.END_ROD){
					((SheepEntity) entity).headYaw=90;
					playerEntity.sendMessage(Text.literal("The Player just hit a sheep with an end rod you sick fuck!"));
					playerEntity.getMainHandStack().decrement(1);
					sheepEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 600, 1));
				}
			}
			return ActionResult.PASS; //signifies the code to continue on to the next step
		});
	}
}