package net.nimbu.thaumaturgy.util;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.nimbu.thaumaturgy.item.custom.HammerItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;


//Usage events should not be used by all items - this here is simply the case as it requires block breaking / world stuff IDK


public class HammerUsageEvent implements PlayerBlockBreakEvents.Before{
    // Done with the help of https://github.com/CoFH/CoFHCore/blob/c23d117dcd3b3b3408a138716b15507f709494cd/src/main/java/cofh/core/event/AreaEffectEvents.java
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    @Override
    public boolean beforeBlockBreak(World world, PlayerEntity player, BlockPos pos,
                                    BlockState state, @Nullable BlockEntity blockEntity) {
        ItemStack mainHandItem = player.getMainHandStack();

        //if the held item is a hammer, and the server version of the player is being checked
        if(mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayerEntity serverPlayer) {
            if(HARVESTED_BLOCKS.contains(pos)) {
                return true; //stops recursive algorithm looping infinitely
            }

            for(BlockPos position : HammerItem.getBlocksToBeDestroyed(1, pos, serverPlayer)) {
                if(pos == position || !hammer.isCorrectForDrops(mainHandItem, world.getBlockState(position))) {
                    continue; //- skip if starting position is being checked (broken automatically?)
                              //OR if block is not meant to be broken by hammer/pickaxe
                }

                HARVESTED_BLOCKS.add(position); //if position wasn't added and removed, recursive function would loop forever
                serverPlayer.interactionManager.tryBreakBlock(position); //recursively (kinda) calls THIS algorithm
                HARVESTED_BLOCKS.remove(position);
            }
        }

        return true;
    }
}