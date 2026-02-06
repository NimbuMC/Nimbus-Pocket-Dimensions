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

import java.util.Optional;

public class PocketDimensionBiomeControllerScreenHandler extends ScreenHandler {

    public final Property fogR = Property.create();
    public final Property fogG = Property.create();
    public final Property fogB = Property.create();

    public final Property waterR = Property.create();
    public final Property waterG = Property.create();
    public final Property waterB = Property.create();

    public final Property waterFogR = Property.create();
    public final Property waterFogG = Property.create();
    public final Property waterFogB = Property.create();

    public final Property foliageR = Property.create();
    public final Property foliageG = Property.create();
    public final Property foliageB = Property.create();

    public final Property grassR = Property.create();
    public final Property grassG = Property.create();
    public final Property grassB = Property.create();



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
        addProperty(waterR);
        addProperty(waterG);
        addProperty(waterB);
        addProperty(waterFogR);
        addProperty(waterFogG);
        addProperty(waterFogB);
        addProperty(foliageR);
        addProperty(foliageG);
        addProperty(foliageB);
        addProperty(grassR);
        addProperty(grassG);
        addProperty(grassB);
    }

    public void setBiomeColours(int fogR, int fogG, int fogB,
                             int waterR, int waterG, int waterB,
                             int waterFogR, int waterFogG, int waterFogB,
                             int foliageR, int foliageG, int foliageB,
                             int grassR, int grassG, int grassB) {
        this.fogR.set(fogR);
        this.fogG.set(fogG);
        this.fogB.set(fogB);
        this.waterR.set(waterR);
        this.waterG.set(waterG);
        this.waterB.set(waterB);
        this.waterFogR.set(waterFogR);
        this.waterFogG.set(waterFogG);
        this.waterFogB.set(waterFogB);
        this.foliageR.set(foliageR);
        this.foliageG.set(foliageG);
        this.foliageB.set(foliageB);
        this.grassR.set(grassR);
        this.grassG.set(grassG);
        this.grassB.set(grassB);

        int fogColour = (fogR << 16) | (fogG << 8) | fogB;
        int waterColour = (waterR << 16) | (waterG << 8) | waterB;
        int waterFogColour = (waterFogR << 16) | (waterFogG << 8) | waterFogB;
        int foliageColour = (foliageR << 16) | (foliageG << 8) | foliageB;
        int grassColour = (grassR << 16) | (grassG << 8) | grassB;
        ClientPocketDimensionPersistentState.getDynamicBiomeEffects().setFogColor(fogColour);
        ClientPocketDimensionPersistentState.getDynamicBiomeEffects().setFogColor(waterColour);
        ClientPocketDimensionPersistentState.getDynamicBiomeEffects().setFogColor(waterFogColour);
        ClientPocketDimensionPersistentState.getDynamicBiomeEffects().setFoliageColor(Optional.of(foliageColour));
        ClientPocketDimensionPersistentState.getDynamicBiomeEffects().setFoliageColor(Optional.of(grassColour));

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

    public int[] getFoliageColour() {
        DynamicBiomeEffects fx = ClientPocketDimensionPersistentState.getDynamicBiomeEffects();
        int R = ((fx.getFoliageColor().get() >> 16) & 0xFF);
        int G = ((fx.getFoliageColor().get() >> 8) & 0xFF);
        int B = (fx.getFoliageColor().get() & 0xFF);
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
