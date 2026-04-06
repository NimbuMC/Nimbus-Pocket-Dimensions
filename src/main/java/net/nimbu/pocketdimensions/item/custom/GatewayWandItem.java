package net.nimbu.pocketdimensions.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.nimbu.pocketdimensions.component.ModComponentInitializer;
import net.nimbu.pocketdimensions.component.PlayerGatewayComponent;
import net.nimbu.pocketdimensions.dimensions.DimensionalInstancer;
import net.nimbu.pocketdimensions.entity.ModEntities;
import net.nimbu.pocketdimensions.entity.custom.GatewayProjectileEntity;

public class GatewayWandItem extends Item {
    public GatewayWandItem(Settings settings) {
        super(settings);
    }



    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        world.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.ENTITY_SHULKER_SHOOT,
                SoundCategory.NEUTRAL,
                0.7f,
                0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));

        if(!world.isClient) {

            ServerWorld targetDimension = DimensionalInstancer.createInstance(world.getServer(), user.getUuid());
            GatewayProjectileEntity spellPortal = new GatewayProjectileEntity(ModEntities.SPELL_PORTAL, world);
            spellPortal.setPosition(new Vec3d(user.getX(), user.getY() + 1.5, user.getZ()));
            spellPortal.setVelocity(user, user.getPitch() - 30, user.getYaw(), 0.0f, 0.45f, 0f);
            spellPortal.setExitDimension(targetDimension.getRegistryKey());
            spellPortal.setOwner(user);
            world.spawnEntity(spellPortal);
        }

        return TypedActionResult.success(itemStack, world.isClient);
    }
}
