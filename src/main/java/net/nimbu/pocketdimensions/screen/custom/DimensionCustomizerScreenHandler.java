package net.nimbu.pocketdimensions.screen.custom;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.component.ModComponentInitializer;
import net.nimbu.pocketdimensions.component.PlayerGatewayComponent;
import net.nimbu.pocketdimensions.network.ClientPocketDimensionPersistentState;
import net.nimbu.pocketdimensions.network.UpdateBiomePacket;
import net.nimbu.pocketdimensions.screen.ModScreenHandlers;
import net.nimbu.pocketdimensions.worldgen.biome.DynamicBiomeEffects;

import java.util.Optional;

public class DimensionCustomizerScreenHandler extends ScreenHandler {
    public DimensionCustomizerScreenHandler(
            int syncId,
            PlayerInventory inventory
    ) {
        super(ModScreenHandlers.POCKET_DIM_BIOME_SCREEN_HANDLER, syncId);
    }


    public void setBiomeColours(int fogColour, int skyColour, int waterColour, int waterFogColour, int foliageColour, int grassColour) {

        ClientPocketDimensionPersistentState.getDynamicBiomeEffects().setFogColor(fogColour);
        ClientPocketDimensionPersistentState.getDynamicBiomeEffects().setSkyColor(skyColour);
        ClientPocketDimensionPersistentState.getDynamicBiomeEffects().setWaterColor(waterColour);
        ClientPocketDimensionPersistentState.getDynamicBiomeEffects().setWaterFogColor(waterFogColour);
        ClientPocketDimensionPersistentState.getDynamicBiomeEffects().setFoliageColor(Optional.of(foliageColour));
        ClientPocketDimensionPersistentState.getDynamicBiomeEffects().setGrassColor(Optional.of(grassColour));

        sendDynamicBiome(ClientPocketDimensionPersistentState.getDynamicBiomeEffects());
    }


    public static void sendDynamicBiome(DynamicBiomeEffects effects) {
        //todo add the check for the same dimension using clientpersistentstate (find a way to get a serverworld here)
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.world != null) {
            client.worldRenderer.reload();
        }
        ClientPlayNetworking.send(new UpdateBiomePacket(effects));
    }

    public int[] getSkyColour() {
        DynamicBiomeEffects fx = ClientPocketDimensionPersistentState.getDynamicBiomeEffects();
        int R = ((fx.getSkyColor() >> 16) & 0xFF);
        int G = ((fx.getSkyColor() >> 8) & 0xFF);
        int B = (fx.getSkyColor() & 0xFF);
        return new int[]{R, G, B};
    }

    public int[] getFoliageColour() {
        DynamicBiomeEffects fx = ClientPocketDimensionPersistentState.getDynamicBiomeEffects();
        int R = ((fx.getFoliageColor().get() >> 16) & 0xFF);
        int G = ((fx.getFoliageColor().get() >> 8) & 0xFF);
        int B = (fx.getFoliageColor().get() & 0xFF);
        return new int[]{R, G, B};
    }

    public int[] getWaterFogColour() {
        DynamicBiomeEffects fx = ClientPocketDimensionPersistentState.getDynamicBiomeEffects();
        int R = ((fx.getWaterFogColor() >> 16) & 0xFF);
        int G = ((fx.getWaterFogColor() >> 8) & 0xFF);
        int B = (fx.getWaterFogColor() & 0xFF);
        return new int[]{R, G, B};
    }

    public int[] getWaterColour() {
        DynamicBiomeEffects fx = ClientPocketDimensionPersistentState.getDynamicBiomeEffects();
        int R = ((fx.getWaterColor() >> 16) & 0xFF);
        int G = ((fx.getWaterColor() >> 8) & 0xFF);
        int B = (fx.getWaterColor() & 0xFF);
        return new int[]{R, G, B};
    }

    public int[] getGrassColour() {
        DynamicBiomeEffects fx = ClientPocketDimensionPersistentState.getDynamicBiomeEffects();
        int R = ((fx.getGrassColor().get() >> 16) & 0xFF);
        int G = ((fx.getGrassColor().get() >> 8) & 0xFF);
        int B = (fx.getGrassColor().get() & 0xFF);
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

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
    }
}
