package net.nimbu.thaumaturgy;

import net.fabricmc.api.ModInitializer;

import net.nimbu.thaumaturgy.block.ModBlocks;
import net.nimbu.thaumaturgy.item.ModItemGroups;
import net.nimbu.thaumaturgy.item.ModItems;
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
	}
}