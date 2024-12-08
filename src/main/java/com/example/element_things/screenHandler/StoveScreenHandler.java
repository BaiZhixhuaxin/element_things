package com.example.element_things.screenHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class StoveScreenHandler extends ScreenHandler {
    private Inventory inventory;
    private PropertyDelegate delegate;
    public StoveScreenHandler(int syncId , PlayerInventory playerInventory) {
        this(syncId,playerInventory,new SimpleInventory(2),new ArrayPropertyDelegate(2));
    }
    public StoveScreenHandler(int syncId , PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(ModScreenHandler.STOVE_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.delegate = delegate;


        this.addSlot(new Slot(inventory,0,56,34));
        this.addSlot(new Slot(inventory,1,111,34));

        for(int i = 0;i<3;i++){
            for(int j = 0;j < 9;j++){
                this.addSlot(new Slot(playerInventory,j + i * 9 + 9,8 + j * 18,84 + i * 18));
            }
        }
        for(int i = 0;i < 9;i++){
            this.addSlot(new Slot(playerInventory,i,8 + i * 18,142));
        }
        this.addProperties(delegate);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }
    public float getRate(){
        float total = delegate.get(1);
        if(total != 0){
            return delegate.get(0) / total;
        }
        return 0;
    }
}
