package net.nimbu.thaumaturgy.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.item.SpellUnlockHandler;




//--------------THIS IS A TEST FILE. MUST BE DELETED----------------------


public class TomeItem extends Item {
    public TomeItem(Settings settings, int spellIndex) {
        super(settings);

    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient) {
            int spellIndex=0;

            SpellUnlockHandler data = (SpellUnlockHandler) user;
            boolean unlocked = data.getSpellUnlockFlags(spellIndex);

            if(!unlocked){
                data.setSpellUnlockFlags(spellIndex,true);
                itemStack.decrement(1);
                return TypedActionResult.consume(itemStack);
            }
        }
        return TypedActionResult.success(itemStack);
    }
}
