package net.nimbu.pocketdimensions.component;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.entity.RespawnableComponent;

public class PlayerGatewayComponentImpl implements PlayerGatewayComponent, RespawnableComponent<PlayerGatewayComponent>,
        AutoSyncedComponent {

    private BlockPos pos;
    private RegistryKey<World> dim;
    private int material = 6;

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
    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        if (nbt.contains("GatewayX")) {
            pos = new BlockPos(
                    nbt.getInt("GatewayX"),
                    nbt.getInt("GatewayY"),
                    nbt.getInt("GatewayZ")
            );
        }
        if (nbt.contains("GatewayDim")) {
            try {
                Identifier id = Identifier.of(nbt.getString("GatewayDim"));
                dim = RegistryKey.of(RegistryKeys.WORLD, id);
            } catch (Exception e) {
                System.out.println("FAILED TO LOAD DIM: " + e.getMessage());
                dim = null;
            }
        }
        if (nbt.contains("GatewayMaterial")) {
            material = nbt.getInt("GatewayMaterial");
        }
    }

    @Override
    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        if (pos != null) {
            nbt.putInt("GatewayX", pos.getX());
            nbt.putInt("GatewayY", pos.getY());
            nbt.putInt("GatewayZ", pos.getZ());
        }

        if (dim != null) {
            nbt.putString("GatewayDim", dim.getValue().toString());
        }
        nbt.putInt("GatewayMaterial", material);
    }


    @Override
    public void copyForRespawn(PlayerGatewayComponent original,
                               RegistryWrapper.WrapperLookup registryLookup,
                               boolean lossless,
                               boolean keepInventory,
                               boolean sameCharacter) {

        this.setGatewayPos(original.getGatewayPos());
        this.setGatewayDim(original.getGatewayDim());
        this.setGatewayMaterial(original.getGatewayMaterial());
    }
}
