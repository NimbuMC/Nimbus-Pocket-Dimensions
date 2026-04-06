package net.nimbu.pocketdimensions.component;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.entity.RespawnableComponent;

public class PlayerGatewayComponentImpl implements PlayerGatewayComponent, RespawnableComponent<PlayerGatewayComponent> {

    private BlockPos pos;
    private RegistryKey<World> dim;

    @Override
    public BlockPos getGatewayPos() {return pos;}
    @Override
    public void setGatewayPos(BlockPos pos) {this.pos=pos;}

    @Override
    public RegistryKey<World> getGatewayDim() {return dim;}
    @Override
    public void setGatewayDim(RegistryKey<World> dim) {this.dim=dim;}

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {}

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {}


    @Override
    public void copyForRespawn(PlayerGatewayComponent original, RegistryWrapper.WrapperLookup registryLookup, boolean lossless, boolean keepInventory, boolean sameCharacter) {
        PlayerGatewayComponentImpl copy = new PlayerGatewayComponentImpl();
        copy.setGatewayPos(original.getGatewayPos());
        copy.setGatewayDim(original.getGatewayDim());
    }
}
