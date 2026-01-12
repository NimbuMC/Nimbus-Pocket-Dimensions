package net.nimbu.thaumaturgy.item.custom;

import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.block.ModBlockEntityTypes;
import net.nimbu.thaumaturgy.block.ModBlocks;
import net.nimbu.thaumaturgy.block.custom.PocketDimensionPortal;
import net.nimbu.thaumaturgy.block.custom.PocketDimensionPortalBlockEntity;
import net.nimbu.thaumaturgy.component.ModDataComponentTypes;
import net.nimbu.thaumaturgy.util.ModModelPredicates;
import net.nimbu.thaumaturgy.worldgen.dimension.ModDimensions;

import java.util.ArrayList;
import java.util.List;

public class WandItem extends Item {


    public WandItem(Settings settings) {super(settings);}


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (stack.get(ModDataComponentTypes.SPELL_FLASH_TIMER)!=null){
            int spellFlashTimer=stack.get(ModDataComponentTypes.SPELL_FLASH_TIMER);
            if (spellFlashTimer>0) {stack.set(ModDataComponentTypes.SPELL_FLASH_TIMER, spellFlashTimer-1);}
            else {stack.set(ModDataComponentTypes.SPELL_FLASH_TIMER, null);}
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();

        context.getStack().set(ModDataComponentTypes.SPELL_FLASH_TIMER, 10);

        if(!world.isClient() && context.getWorld().getRegistryKey() != ModDimensions.POCKET_DIM_LEVEL_KEY) {
            BlockPos sourceBlockPos = new BlockPos(context.getBlockPos().getX(), context.getBlockPos().getY() + 1, context.getBlockPos().getZ());
            List<BlockPos> positions = new ArrayList<>();
            positions.add(sourceBlockPos);
            //positions.add(new BlockPos(sourceBlockPos.getX(), sourceBlockPos.getY()+1, sourceBlockPos.getZ()));
            /*positions.add(new BlockPos(sourceBlockPos.getX(), sourceBlockPos.getY(), sourceBlockPos.getZ()));
            positions.add(new BlockPos(sourceBlockPos.getX(), sourceBlockPos.getY(), sourceBlockPos.getZ()));
            positions.add(new BlockPos(sourceBlockPos.getX(), sourceBlockPos.getY(), sourceBlockPos.getZ()));
            positions.add(new BlockPos(sourceBlockPos.getX(), sourceBlockPos.getY(), sourceBlockPos.getZ()));*/

            BlockState netherPortalBlockState= Blocks.NETHER_PORTAL.getDefaultState();
            Float playerAbsoluteYaw = Math.abs(context.getPlayer().getYaw());

            BlockState pocketDimPortalState = ModBlocks.POCKET_DIMENSION_PORTAL.getDefaultState();
            //context.getPlayer().getGameProfile().getId();



            for(BlockPos pos : positions) {
                world.setBlockState(pos, pocketDimPortalState);
                if(world.getBlockEntity(pos) instanceof PocketDimensionPortalBlockEntity portalData) {
                    portalData.TriggerInitialIDUpdate(world, pos, 0);
                }
            //    if(world.getBlockState(pos).getBlock()==Blocks.AIR){
            //        if(135 > playerAbsoluteYaw && playerAbsoluteYaw > 45){
            //            world.setBlockState(pos, pocketDimPortalState.rotate(BlockRotation.CLOCKWISE_90));
            //        }
            //        else{
            //            world.setBlockState(pos, pocketDimPortalState);}
            //    }
            }

            world.playSound(null, context.getBlockPos(), SoundEvents.ENTITY_SHULKER_SHOOT, SoundCategory.BLOCKS);
        }
        return ActionResult.SUCCESS;
    }


    /*

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        //Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();

        if(!world.isClient()){
            context.getStack().set(ModDataComponentTypes.COORDINATES, context.getBlockPos());
            context.getStack().set(DataComponentTypes.CUSTOM_NAME, Text.literal("Test123"));
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if(stack.get(ModDataComponentTypes.COORDINATES)!=null){
            tooltip.add(Text.literal("Last block interacted at "+stack.get(ModDataComponentTypes.COORDINATES)));
        }
    }

     */
}
