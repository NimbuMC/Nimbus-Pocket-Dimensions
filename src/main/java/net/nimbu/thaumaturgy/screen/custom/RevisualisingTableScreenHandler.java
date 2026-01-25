package net.nimbu.thaumaturgy.screen.custom;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.nimbu.thaumaturgy.component.ModDataComponentTypes;
import net.nimbu.thaumaturgy.item.ModItems;
import net.nimbu.thaumaturgy.screen.ModScreenHanders;

public class RevisualisingTableScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    //private final List<>

    public RevisualisingTableScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(pos), new ArrayPropertyDelegate(2));
    }

    public RevisualisingTableScreenHandler(int syncId, PlayerInventory playerInventory,
                                           BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
        super(ModScreenHanders.REVISUALISING_TABLE_SCREEN_HANDLER, syncId);
        this.inventory = ((Inventory) blockEntity);
        this.propertyDelegate = arrayPropertyDelegate;

        this.addSlot(new Slot(inventory, 0, 15,47) {
            @Override
            public int getMaxItemCount() {
                return 1;
            }
        });
        this.addSlot(new Slot(inventory, 1, 35,47) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(ModItems.PIXIE_DUST);
            }

            //@Override
            //public Pair<Identifier, Identifier> getBackgroundSprite() {
            //    return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, EnchantmentScreenHandler.EMPTY_LAPIS_SLOT_TEXTURE);
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }


    @Override
    public ItemStack quickMove(PlayerEntity player, int slotPosition) { //handles shift clicking to place an item in slot
        //============================================================
        //testing purposes only
        //inventory.getStack(0).set(DataComponentTypes.CUSTOM_NAME, Text.literal("Revisualised"));
        //inventory.getStack(0).set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false);
        inventory.getStack(0).set(ModDataComponentTypes.REVISUALISED, true);
        inventory.getStack(0).set(ModDataComponentTypes.REPLACE_MODEL_NAMESPACE, "minecraft");
        inventory.getStack(0).set(ModDataComponentTypes.REPLACE_MODEL_PATH, "diamond_sword");
        //============================================================
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slotPosition);
        //System.out.println(!EnchantmentHelper.getEnchantments(slot2.getStack()).isEmpty()); CHECK ENCHANTMENTS EXIST
        if (slot2 != null && slot2.hasStack()){
            ItemStack originalStack = slot2.getStack();
            newStack = originalStack.copy();
            if(slotPosition < this.inventory.size()){ //if inventory is clicked AND item is enchanted
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(),true)){
                    return ItemStack.EMPTY;
                }
            } else //if outside inventory clicked
                if (!this.insertItem(originalStack, 0, this.inventory.size(), false)){
                    return ItemStack.EMPTY;
                }

            if(originalStack.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory){
        for (int i = 0; i < 3; ++i){
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l+i*9+9,8+l*18,84+i*18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory){
        for(int i = 0; i<9; ++i){
            this.addSlot(new Slot(playerInventory, i, 8+i*18,142));
        }
    }
}
