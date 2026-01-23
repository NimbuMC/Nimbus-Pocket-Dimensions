package net.nimbu.thaumaturgy;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.nimbu.thaumaturgy.datagen.*;
import net.nimbu.thaumaturgy.enchantment.ModEnchantments;

public class ThaumaturgyDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		//add each of the datagen providers
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModWorldGenerator::new);
		pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(ModRegistryDataGenerator::new);
	}

	public void buildRegistry(RegistryBuilder registryBuilder){
		registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, ModEnchantments::bootstrap);
	}
}
