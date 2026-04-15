package net.nimbu.pocketdimensions.component;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.entity.RespawnableComponent;

public class PlayerGatewayComponentImpl implements PlayerGatewayComponent, RespawnableComponent<PlayerGatewayComponent> {

    private BlockPos pos;
    private RegistryKey<World> dim;
    private int material;

    @Override
    public BlockPos getGatewayPos() {return pos;}
    @Override
    public void setGatewayPos(BlockPos pos) {this.pos=pos;}

    @Override
    public RegistryKey<World> getGatewayDim() {return dim;}
    @Override
    public void setGatewayDim(RegistryKey<World> dim) {this.dim=dim;}

    @Override
    public int getGatewayMaterial() {return material;}
    @Override
    public void setGatewayMaterial(int material) {this.material=material;}

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        if (pos != null) {
            nbtCompound.putInt("GatewayX", pos.getX());
            nbtCompound.putInt("GatewayY", pos.getY());
            nbtCompound.putInt("GatewayZ", pos.getZ());
        }

        if (dim != null) {
            nbtCompound.putString("GatewayDim", dim.getValue().toString());
        }

        nbtCompound.putInt("GatewayMaterial", material);
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        if (nbtCompound.contains("GatewayX")) {
            pos = new BlockPos(
                    nbtCompound.getInt("GatewayX"),
                    nbtCompound.getInt("GatewayY"),
                    nbtCompound.getInt("GatewayZ")
            );
        }

        if (nbtCompound.contains("GatewayDim")) {
            dim = RegistryKey.of(
                    RegistryKeys.WORLD,
                    Identifier.of(nbtCompound.getString("GatewayDim"))
            );
        }

        if (nbtCompound.contains("GatewayMaterial")) {
            material = nbtCompound.getInt("GatewayMaterial");
        }
    }


    @Override
    public void copyForRespawn(PlayerGatewayComponent original, RegistryWrapper.WrapperLookup registryLookup, boolean lossless, boolean keepInventory, boolean sameCharacter) {
        PlayerGatewayComponentImpl copy = new PlayerGatewayComponentImpl();
        copy.setGatewayPos(original.getGatewayPos());
        copy.setGatewayDim(original.getGatewayDim());
    }
}
