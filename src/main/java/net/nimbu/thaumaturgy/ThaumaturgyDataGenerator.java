package net.nimbu.thaumaturgy;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.nimbu.thaumaturgy.datagen.ModBlockTagProvider;
import net.nimbu.thaumaturgy.datagen.ModItemTagProvider;
import net.nimbu.thaumaturgy.datagen.ModModelProvider;

public class ThaumaturgyDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		//add each of the datagen providers
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModItemTagProvider::new);
	}
}
