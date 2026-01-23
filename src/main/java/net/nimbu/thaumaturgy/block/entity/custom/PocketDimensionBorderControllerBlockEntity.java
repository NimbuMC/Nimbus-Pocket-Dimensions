package net.nimbu.thaumaturgy.block.entity.custom;

import net.fabricmc.loader.impl.lib.sat4j.specs.IVecInt;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.nimbu.thaumaturgy.block.entity.ModBlockEntityTypes;

import java.util.Dictionary;
import java.util.Hashtable;

public class PocketDimensionBorderControllerBlockEntity extends BlockEntity {
    public Dictionary<Vec3i, Boolean> roomStatus;
    public PocketDimensionBorderControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.POCKET_DIMENSION_BORDER_CONTROLLER, pos, state);
        roomStatus = new Hashtable<>();
    }



    /*
        Z
        ^   2      4 /\
        | 3 . 1
        |   0      5 \/
        +------->x
    */

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound rooms = new NbtCompound();

        roomStatus.keys().asIterator().forEachRemaining(pos ->
                rooms.putBoolean(pos.getX() + "|" + pos.getY() + "|" + pos.getZ(), roomStatus.get(pos))
        );

        nbt.put("Rooms", rooms);
        super.writeNbt(nbt, registryLookup);
    }


    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound compound = nbt.getCompound("Rooms");
        for (String key : compound.getKeys()) {
            // Expecting keys like "x|y|z"
            if (!key.contains("|")) continue;

            String[] parts = key.split("\\|");
            if (parts.length != 3) continue;

            try {
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                int z = Integer.parseInt(parts[2]);

                boolean value = nbt.getBoolean(key);
                roomStatus.put(new Vec3i(x, y, z), value);
            } catch (NumberFormatException ignored) {
                // Skip malformed keys safely
            }
        }

        super.readNbt(nbt, registryLookup);
    }
}
