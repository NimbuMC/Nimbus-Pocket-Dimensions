package net.nimbu.thaumaturgy.item.custom;

import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.block.entity.custom.RevisualisingTableBlockEntity;
import net.nimbu.thaumaturgy.item.SpellUnlockHandler;
import net.nimbu.thaumaturgy.screen.custom.SpellScreenHandler;

public class SpellcasterItem extends Item{

    public SpellcasterItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);


        if (!world.isClient) {
                user.openHandledScreen(
                        new SimpleNamedScreenHandlerFactory(
                                (syncId, inv, p) -> new SpellScreenHandler(syncId, inv),
                                Text.literal("Spells")
                        )
                );
        }
        return TypedActionResult.success(stack);
    }
}
