package net.nimbu.thaumaturgy.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class PortalWandItem extends Item{

    public PortalWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();

        if(!world.isClient()) {
            BlockPos sourceBlockPos = new BlockPos(context.getBlockPos().getX(), context.getBlockPos().getY() + 1, context.getBlockPos().getZ());
            List<BlockPos> positions = new ArrayList<>();
            positions.add(sourceBlockPos);
            positions.add(new BlockPos(sourceBlockPos.getX(), sourceBlockPos.getY()+1, sourceBlockPos.getZ()));
            /*positions.add(new BlockPos(sourceBlockPos.getX(), sourceBlockPos.getY(), sourceBlockPos.getZ()));
            positions.add(new BlockPos(sourceBlockPos.getX(), sourceBlockPos.getY(), sourceBlockPos.getZ()));
            positions.add(new BlockPos(sourceBlockPos.getX(), sourceBlockPos.getY(), sourceBlockPos.getZ()));
            positions.add(new BlockPos(sourceBlockPos.getX(), sourceBlockPos.getY(), sourceBlockPos.getZ()));*/

            BlockState netherPortalBlockState=Blocks.NETHER_PORTAL.getDefaultState();
            Float playerAbsoluteYaw = Math.abs(context.getPlayer().getYaw());


            for(BlockPos pos : positions) {
                if(world.getBlockState(pos).getBlock()==Blocks.AIR){
                    if(135 > playerAbsoluteYaw && playerAbsoluteYaw > 45){
                        world.setBlockState(pos, netherPortalBlockState.rotate(BlockRotation.CLOCKWISE_90));
                    }
                    else{
                    world.setBlockState(pos, netherPortalBlockState);}
                }
            }

            world.playSound(null, context.getBlockPos(), SoundEvents.ENTITY_SHULKER_SHOOT, SoundCategory.BLOCKS);
        }
        return ActionResult.SUCCESS;
    }
}
