package net.nimbu.thaumaturgy.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.component.ModDataComponentTypes;
import net.nimbu.thaumaturgy.dimensions.DimensionalInstancer;
import net.nimbu.thaumaturgy.entity.ModEntities;
import net.nimbu.thaumaturgy.spell.SpellEntities;
import net.nimbu.thaumaturgy.spell.entity.SpellPortalEntity;

public class WandPortalItem extends Item {
    public WandPortalItem(Settings settings) {
        super(settings);
    }

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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        world.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.ENTITY_SHULKER_SHOOT,
                SoundCategory.NEUTRAL,
                0.7f,
                0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));

        user.getStackInHand(hand).set(ModDataComponentTypes.SPELL_FLASH_TIMER, 15);

        if(!world.isClient) {
            ServerWorld targetDimension = DimensionalInstancer.createInstance(world.getServer(), user.getUuid());
            SpellPortalEntity spellPortal = new SpellPortalEntity(SpellEntities.SPELL_PORTAL, world);
            spellPortal.setPosition(new Vec3d(user.getX(), user.getY() + 1.5, user.getZ()));
            spellPortal.setVelocity(user, user.getPitch() - 30, user.getYaw(), 0.0f, 0.45f, 0f);
            spellPortal.setExitDimension(targetDimension.getRegistryKey());
            world.spawnEntity(spellPortal);
        }

        return TypedActionResult.success(itemStack, world.isClient);
    }
}
