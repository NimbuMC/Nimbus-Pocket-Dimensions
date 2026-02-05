package net.nimbu.thaumaturgy.screen.custom;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.network.ClientPocketDimensionPersistentState;
import net.nimbu.thaumaturgy.network.UpdateBiomePacket;
import net.nimbu.thaumaturgy.screen.ModScreenHandlers;
import net.nimbu.thaumaturgy.worldgen.biome.DynamicBiomeEffects;

public class PocketDimensionBiomeControllerScreenHandler extends ScreenHandler {

    public final Property fogR = Property.create();
    public final Property fogG = Property.create();
    public final Property fogB = Property.create();

    public PocketDimensionBiomeControllerScreenHandler(
            int syncId,
            PlayerInventory inventory
    ) {
        super(ModScreenHandlers.POCKET_DIM_BIOME_SCREEN_HANDLER, syncId);
        int fog = ClientPocketDimensionPersistentState.getDynamicBiomeEffects().getFogColor();
        fogR.set((fog >> 16) & 0xFF);
        fogG.set((fog >> 8) & 0xFF);
        fogB.set(fog & 0xFF);
        Thaumaturgy.LOGGER.info(fogR.get() + " - " + fogG.get() + " - " + fogB.get());

        addProperties();
    }

    private void addProperties() {
        addProperty(fogR);
        addProperty(fogG);
        addProperty(fogB);
    }

    public void setFogColour(int r, int g, int b) {
        fogR.set(r);
        fogG.set(g);
        fogB.set(b);

        int fogColor = (fogR.get() << 16) | (fogG.get() << 8) | fogB.get();
        ClientPocketDimensionPersistentState.getDynamicBiomeEffects().setFogColor(fogColor);

        // Sync to clients in this dimension
        sendDynamicBiome(ClientPocketDimensionPersistentState.getDynamicBiomeEffects());
    }


    public static void sendDynamicBiome(DynamicBiomeEffects effects) {
        ClientPlayNetworking.send(new UpdateBiomePacket(effects));
    }

    public int[] getFogColour() {
        DynamicBiomeEffects fx = ClientPocketDimensionPersistentState.getDynamicBiomeEffects();
        int R = ((fx.getFogColor() >> 16) & 0xFF);
        int G = ((fx.getFogColor() >> 8) & 0xFF);
        int B = (fx.getFogColor() & 0xFF);
        return new int[]{R, G, B};
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }
}
