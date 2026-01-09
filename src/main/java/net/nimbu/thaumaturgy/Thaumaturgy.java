package net.nimbu.thaumaturgy;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.block.ModBlocks;
import net.nimbu.thaumaturgy.component.ModDataComponentTypes;
import net.nimbu.thaumaturgy.item.ModItemGroups;
import net.nimbu.thaumaturgy.item.ModItems;
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
		ModDataComponentTypes.registerDataComponentTypes();
		CustomPortalBuilder.beginPortal()
				.frameBlock(ModBlocks.PITCH_BLACK_BLOCK)
				.lightWithItem(ModItems.STAFF)
				.destDimID(Identifier.tryParse(Thaumaturgy.MOD_ID, "pocket_dim"))
				.tintColor(0,0,139)
				.registerPortal();

		PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent());
	}
}