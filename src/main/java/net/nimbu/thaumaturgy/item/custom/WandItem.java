package net.nimbu.thaumaturgy.item.custom;

import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.component.ModDataComponentTypes;

import java.util.List;

public class WandItem extends Item {

    public WandItem(Settings settings) {super(settings);}

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        //Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();

        if(!world.isClient()){
            context.getStack().set(ModDataComponentTypes.COORDINATES, context.getBlockPos());
            context.getStack().set(DataComponentTypes.CUSTOM_NAME, null);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if(stack.get(ModDataComponentTypes.COORDINATES)!=null){
            tooltip.add(Text.literal("Last block interacted at "+stack.get(ModDataComponentTypes.COORDINATES)));
        }
    }
}
