package com.example.element_things.screenHandler;

import com.example.element_things.util.animal_inventory.AnimalInventory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class AnimalInventoryScreenHandler extends ScreenHandler {
    public Inventory inventory;
    public AnimalInventoryScreenHandler( int syncId,PlayerInventory playerInventory) {
        this(syncId,playerInventory,new SimpleInventory(16));
    }
    public AnimalInventoryScreenHandler(int syncId, PlayerInventory playerInventory,Inventory inventory){
        super(ModScreenHandler.ANIMAL_INVENTORY_SCREEN_HANDLER,syncId);
        this.inventory = inventory;
        for(int i = 0;i<3;i++){
            for(int j = 0;j < 9;j++){
                this.addSlot(new Slot(playerInventory,j + i * 9 + 9,8 + j * 18,84 + i * 18));
            }
        }
        for(int i = 0;i < 9;i++){
            this.addSlot(new Slot(playerInventory,i,8 + i * 18,142));
        }
        for(int i = 0; i < 2; i++){
            for(int j = 0;j < 8; j++){
                this.addSlot(new Slot(inventory,8 * i + j,8 + j * 18,16 + i * 18));
            }
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot < player.getInventory().size()) {
                if (!this.insertItem(itemStack2, 0, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, player.getInventory().size(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
        }

        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
    public LivingEntity getEntity(){
        if(inventory instanceof AnimalInventory animalInventory) return animalInventory.getEntity();
        return null;
    }
}
