package net.nimbu.thaumaturgy.item.custom;

import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
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
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.ThaumaturgyClient;
import net.nimbu.thaumaturgy.block.entity.custom.RevisualisingTableBlockEntity;
import net.nimbu.thaumaturgy.component.ModDataComponentTypes;
import net.nimbu.thaumaturgy.item.ModItems;
import net.nimbu.thaumaturgy.screen.custom.SpellScreenHandler;
import net.nimbu.thaumaturgy.spell.Spell;
import net.nimbu.thaumaturgy.spell.Spells;

public class SpellcasterItem extends Item{
    public SpellcasterItem(Item.Settings settings) {
        super(settings);
    }
    private boolean wheelCurrentlyActive = false;
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (stack.get(ModDataComponentTypes.SPELL_FLASH_TIMER) != null) {
            int spellFlashTimer = stack.get(ModDataComponentTypes.SPELL_FLASH_TIMER);
            if (spellFlashTimer > 0) {
                stack.set(ModDataComponentTypes.SPELL_FLASH_TIMER, spellFlashTimer - 1);
            } else {
                stack.set(ModDataComponentTypes.SPELL_FLASH_TIMER, null);
            }
        }

        if (!world.isClient) {
            if (entity instanceof PlayerEntity user) {
                String spellName = stack.get(ModDataComponentTypes.EQUIPPED_SPELL);
                if(spellName != null) {
                    updateSpells(stack, world, user, slot, selected);
                    if (selected) {
                        if (!spellName.isEmpty()) {
                            Spell currentSpell = Spells.SPELL_DICTIONARY.get(spellName);
                            currentSpell.renderReticle(world, user);
                        }

                        if (ThaumaturgyClient.openSpellWheel.isPressed()) {
                            if (!wheelCurrentlyActive) {
                                wheelCurrentlyActive = true;
                                user.openHandledScreen(
                                        new SimpleNamedScreenHandlerFactory(
                                                (syncId, inv, p) -> new SpellScreenHandler(syncId, inv),
                                                Text.literal("Spells")
                                        )
                                );
                            }
                        } else wheelCurrentlyActive = false;
                    } else if (!spellName.isEmpty()) {
                        Spell currentSpell = Spells.SPELL_DICTIONARY.get(spellName);
                        currentSpell.cancelRenderReticle(world, user);
                    }
                }
            }
        }
    }

    public void updateSpells(ItemStack stack, World world, PlayerEntity user, int slot, boolean selected)
    {
        String spellName = stack.get(ModDataComponentTypes.EQUIPPED_SPELL);
        if (spellName != null && !spellName.isEmpty()) {
            Spell currentSpell = Spells.SPELL_DICTIONARY.get(spellName);
            if(currentSpell.toString().equals(Spells.POCKET_DIMENSION.toString())
                    && world.getRegistryKey().getValue().toString().contains("pocket_dimension"))
            {
                stack.set(ModDataComponentTypes.EQUIPPED_SPELL, "");
            }
            else if(currentSpell.toString().equals(Spells.EXPAND_POCKET_DIMENSION.toString())
                    && !world.getRegistryKey().getValue().toString().contains("pocket_dimension"))
            {
                stack.set(ModDataComponentTypes.EQUIPPED_SPELL, "");
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        user.getStackInHand(hand).set(ModDataComponentTypes.SPELL_FLASH_TIMER, 9);

        if (!world.isClient) {
            String spellName = user.getStackInHand(hand).get(ModDataComponentTypes.EQUIPPED_SPELL);
            Thaumaturgy.LOGGER.info("Tried Using spell " + spellName);
            assert spellName != null;
            if (!spellName.isEmpty()) {
                Spell currentSpell = Spells.SPELL_DICTIONARY.get(spellName);
                currentSpell.castSpell(world, user, hand);
            }
        }
        return TypedActionResult.pass(stack);
    }
}
