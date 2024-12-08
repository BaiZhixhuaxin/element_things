package com.example.element_things.entity;

import com.example.element_things.item.ModItems;
import com.example.element_things.screenHandler.StoveScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StoveBlockEntity extends BlockEntity implements NamedScreenHandlerFactory , Inventory {
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2,ItemStack.EMPTY);
    private boolean hasBeenSet = false;
    private int progress;
    private int progressTotal = 20 * 5;
    private final PropertyDelegate delegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index){
                case 0 -> StoveBlockEntity.this.progress;
                case 1 -> StoveBlockEntity.this.progressTotal;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index){
                case 0 -> StoveBlockEntity.this.progress = value;
                case 1 -> StoveBlockEntity.this.progressTotal = value;
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };
    public StoveBlockEntity( BlockPos pos, BlockState state) {
        super(ModBlockEntityType.STOVE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("123");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new StoveScreenHandler(syncId,playerInventory,this,this.delegate);
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemStack : inventory){
            if(!itemStack.isEmpty()){
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.inventory,slot,amount);
        if(!itemStack.isEmpty()){
            this.markDirty();
        }
        return itemStack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory,slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        ItemStack itemStack = this.inventory.get(slot);
        boolean bl = !stack.isEmpty() && ItemStack.areItemsAndComponentsEqual(itemStack, stack);
        this.inventory.set(slot, stack);
        stack.capCount(this.getMaxCount(stack));
        if (slot == 0 && !bl) {
            this.progress = 0;
            this.progressTotal = 20 * 5;
            this.markDirty();
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this,player);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    public static void tick(World world, BlockPos pos, BlockState state, StoveBlockEntity entity) {
        entity.tick(world,pos,state);
    }
    public void tick(World world,BlockPos pos,BlockState state){
        if(world.isRaining()) {
            ItemStack craftStack = this.inventory.get(0);
            ItemStack resultStack = this.inventory.get(1);
            if (craftStack.isOf(Items.BLAZE_POWDER) && ((resultStack.isEmpty()) || (resultStack.isOf(ModItems.FIRE_GEM) && resultStack.getCount() < resultStack.getMaxCount()))) {
                hasBeenSet = false;
                progress++;
                if (progress >= progressTotal) {
                    craftStack.decrement(1);
                    if (resultStack.isEmpty()) {
                        this.inventory.set(1, ModItems.FIRE_GEM.getDefaultStack());
                    } else if (resultStack.isOf(ModItems.FIRE_GEM)) {
                        resultStack.increment(1);
                    }
                    progress = 0;
                }
                markDirty();
            }
        }
        else if(!hasBeenSet){
            progress = 0;
            progressTotal = 20 * 5;
            hasBeenSet = true;
            markDirty();
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt,registryLookup);
        nbt.putInt("progress",this.progress);
        nbt.putInt("progressTotal",this.progressTotal);
        Inventories.writeNbt(nbt,this.inventory,registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt,registryLookup);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt,this.inventory,registryLookup);
        this.progress = nbt.getInt("progress");
        this.progressTotal = nbt.getInt("progressTotal");
    }
}
