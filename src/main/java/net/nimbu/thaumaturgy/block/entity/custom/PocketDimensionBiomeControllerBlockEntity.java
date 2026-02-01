package net.nimbu.thaumaturgy.block.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.nimbu.thaumaturgy.block.entity.ModBlockEntityTypes;

import java.util.Dictionary;
import java.util.Hashtable;

public class PocketDimensionBiomeControllerBlockEntity extends BlockEntity {
    public PocketDimensionBiomeControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.POCKET_DIMENSION_BIOME_CONTROLLER, pos, state);
    }


    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
    }


    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
    }
}
