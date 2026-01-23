package net.nimbu.thaumaturgy.block.entity.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EnchantingTableBlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.block.entity.ImplementedInventory;
import net.nimbu.thaumaturgy.block.entity.ModBlockEntityTypes;
import net.nimbu.thaumaturgy.component.ModDataComponentTypes;
import net.nimbu.thaumaturgy.item.ModItems;
import net.nimbu.thaumaturgy.screen.custom.RevisualisingTableScreenHandler;
import org.jetbrains.annotations.Nullable;

public class RevisualisingTableBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos> {
    public int ticks = 0;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    public static final int INPUT_TOOL_SLOT = 0;
    public static final int INPUT_RESOURCE_SLOT = 1;
    public float rotation = 0;

    public float nextPageAngle;
    public float pageAngle;
    public float flipRandom;
    public float flipTurn;
    public float nextPageTurningSpeed;
    public float pageTurningSpeed;
    public float bookRotation;
    public float lastBookRotation;
    public float targetBookRotation;
    private static final Random RANDOM = Random.create();



    public RevisualisingTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.REVISUALISING_TABLE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, RevisualisingTableBlockEntity blockEntity){


        BlockPos pos = blockEntity.getPos();


        blockEntity.ticks++;

        blockEntity.rotation+=0.5f;
        if(blockEntity.rotation>=360){
            blockEntity.rotation=0;
        }



        blockEntity.pageTurningSpeed = blockEntity.nextPageTurningSpeed;
        blockEntity.lastBookRotation = blockEntity.bookRotation;
        PlayerEntity playerEntity = world.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 3.0, false);
        if (playerEntity != null) {
            double d = playerEntity.getX() - (pos.getX() + 0.5);
            double e = playerEntity.getZ() - (pos.getZ() + 0.5);
            blockEntity.targetBookRotation = (float) MathHelper.atan2(e, d);
            blockEntity.nextPageTurningSpeed += 0.1F; //this controls the book opening
            if (blockEntity.nextPageTurningSpeed < 0.5F || RANDOM.nextInt(40) == 0) {
                float f = blockEntity.flipRandom;

                do {
                    blockEntity.flipRandom = blockEntity.flipRandom + (RANDOM.nextInt(4) - RANDOM.nextInt(4));
                } while (f == blockEntity.flipRandom);
            }
        } else if (!blockEntity.inventory.get(0).isEmpty()){ //keeps book open if item held
             blockEntity.nextPageTurningSpeed += 0.1F;
        }
        else {
            blockEntity.targetBookRotation += 0.02F;
            blockEntity.nextPageTurningSpeed -= 0.1F;
        }

        while (blockEntity.bookRotation >= (float) Math.PI) {
            blockEntity.bookRotation -= (float) (Math.PI * 2);
        }

        while (blockEntity.bookRotation < (float) -Math.PI) {
            blockEntity.bookRotation += (float) (Math.PI * 2);
        }

        while (blockEntity.targetBookRotation >= (float) Math.PI) {
            blockEntity.targetBookRotation -= (float) (Math.PI * 2);
        }

        while (blockEntity.targetBookRotation < (float) -Math.PI) {
            blockEntity.targetBookRotation += (float) (Math.PI * 2);
        }

        float g = blockEntity.targetBookRotation - blockEntity.bookRotation;

        while (g >= (float) Math.PI) {
            g -= (float) (Math.PI * 2);
        }

        while (g < (float) -Math.PI) {
            g += (float) (Math.PI * 2);
        }

        blockEntity.bookRotation += g * 0.4F;
        blockEntity.nextPageTurningSpeed = MathHelper.clamp(blockEntity.nextPageTurningSpeed, 0.0F, 1.0F);
        blockEntity.pageAngle = blockEntity.nextPageAngle;
        float h = (blockEntity.flipRandom - blockEntity.nextPageAngle) * 0.4F;
        float i = 0.2F;
        h = MathHelper.clamp(h, -0.2F, 0.2F);
        blockEntity.flipTurn = blockEntity.flipTurn + (h - blockEntity.flipTurn) * 0.9F;
        blockEntity.nextPageAngle = blockEntity.nextPageAngle + blockEntity.flipTurn;


    }

    //Applies revisualisation. DOES NOT EFFECT ACTUAL STORED ITEM - ONLY VISUAL. DO NOT USE
    public void displayRevisualisedItem(ItemStack itemStack){

        //THESE ARE ONLY APPLYING TO THE DISPLAYED ITEM, AND NOT THE ITEM THAT GETS RETURNED TO THE PLAYER!!!

        itemStack.set(ModDataComponentTypes.REVISUALISED, true);
        itemStack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false);
        itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("Revisualised"));
        //itemStack.hasGlint()
    }


    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
    }


    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.thaumaturgy.revisualising_table");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new RevisualisingTableScreenHandler(syncId, playerInventory, this.pos);
    }


    //Synchronises client and server:
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}
