package net.nimbu.thaumaturgy;

import net.fabricmc.api.ClientModInitializer;
import net.nimbu.thaumaturgy.util.ModModelPredicates;

public class ThaumaturgyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelPredicates.registerModelPredicates();
    }
}
