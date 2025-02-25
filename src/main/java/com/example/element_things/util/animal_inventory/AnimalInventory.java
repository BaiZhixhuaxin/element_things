package com.example.element_things.util.animal_inventory;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.collection.DefaultedList;

public class AnimalInventory implements Inventory {
    public final DefaultedList<ItemStack> pack;
    public final LivingEntity entity;

    public AnimalInventory(LivingEntity livingEntity) {
        this.pack = DefaultedList.ofSize(16,ItemStack.EMPTY);
        this.entity = livingEntity;
    }

    @Override
    public int size() {
        return pack.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack stack : pack){
            if(!stack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return (slot <= pack.size() - 1 && slot >= 0) ? pack.get(slot) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.pack,slot,amount);
        if(!itemStack.isEmpty()){
            this.markDirty();
        }
        return itemStack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.pack,slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if(slot <= pack.size() - 1 && slot >= 0){
            pack.set(slot,stack);
            stack.capCount(this.getMaxCount(stack));
        }
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        pack.clear();
    }
    public NbtList writeNbt(NbtList list){
        int i;
        NbtCompound nbtCompound;
        for(i = 0; i < pack.size(); i++){
            if(!this.pack.get(i).isEmpty()) {
                nbtCompound = new NbtCompound();
                nbtCompound.putByte("Slot", (byte) (i));
                list.add(pack.get(i).encode(this.entity.getRegistryManager(),nbtCompound));
            }
        }
        return list;
    }
    public void readNbt(NbtList list){
        this.pack.clear();
        for(int i = 0;i < list.size();i++){
            NbtCompound nbtCompound = list.getCompound(i);
            int j = nbtCompound.getByte("Slot") & 255;
            ItemStack itemStack = ItemStack.fromNbt(entity.getRegistryManager(), nbtCompound).orElse(ItemStack.EMPTY);
            if (j < this.pack.size()) {
                pack.set(j,itemStack);
            }
        }
    }

    @Override
    public boolean canTransferTo(Inventory hopperInventory, int slot, ItemStack stack) {
        return true;
    }
    public LivingEntity getEntity(){
        return this.entity;
    }
}
