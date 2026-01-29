package net.nimbu.thaumaturgy.spell.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.component.ModDataComponentTypes;
import net.nimbu.thaumaturgy.spell.Spell;

public class UnbakingBreadSpell extends Spell {
    public UnbakingBreadSpell() {
        super(Identifier.ofVanilla("textures/item/bread.png"),
                0x897057,
                0xFF514131);
    }

    @Override
    public void castSpell(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient){
            Hand targetHand;
            if (hand==Hand.MAIN_HAND){
                targetHand=Hand.OFF_HAND;
            }
            else{
                targetHand=Hand.MAIN_HAND;
            }

            if (user.getStackInHand(targetHand).isOf(Items.BREAD)){
                user.getStackInHand(targetHand).decrement(1);

                ItemStack wheat = new ItemStack(Items.WHEAT, 3);

                user.getInventory().offerOrDrop(wheat);

                user.getStackInHand(hand).set(ModDataComponentTypes.SPELL_FLASH_TIMER, 8);
            }
        }
    }
}
