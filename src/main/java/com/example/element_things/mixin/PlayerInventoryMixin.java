package com.example.element_things.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin implements Inventory, Nameable {
    @Shadow @Final public PlayerEntity player;
    @Mutable
    @Shadow @Final private List<DefaultedList<ItemStack>> combinedInventory;
    @Unique
    private DefaultedList<ItemStack> slot;
    @Inject(method = "<init>",at=@At("RETURN"))
    private void initMixin(PlayerEntity player, CallbackInfo ci){
        this.slot = DefaultedList.ofSize(1,ItemStack.EMPTY);
        this.combinedInventory = new ArrayList<>(combinedInventory);
        this.combinedInventory.add(slot);
        this.combinedInventory = ImmutableList.copyOf(this.combinedInventory);
    }
    @Inject(method = "writeNbt",at=@At("TAIL"))
    public void writeNbt(NbtList nbtList, CallbackInfoReturnable<NbtList> cir) {
        if(!this.slot.get(0).isEmpty()){
            NbtCompound nbtTag = new NbtCompound();
            nbtTag.putByte("Slot",(byte) (110));
            nbtList.add(this.slot.get(0).encode(this.player.getRegistryManager(),nbtTag));
        }
    }
    @Inject(method = "readNbt",at=@At("TAIL"))
    public void readNbt(NbtList nbtList, CallbackInfo ci) {
        this.slot.clear();
        for(int i = 0;i < nbtList.size(); ++i){
            NbtCompound nbtTag = nbtList.getCompound(i);
            int slot = nbtTag.getByte("Slot") & 255;
            ItemStack stack = ItemStack.fromNbt(this.player.getRegistryManager(),nbtTag).orElse(ItemStack.EMPTY);
            if(slot == 110 && !stack.isEmpty()){
                this.slot.set(0,stack);
            }
        }
    }
    @Inject(method = "size",at=@At("RETURN"), cancellable = true)
    private void size(CallbackInfoReturnable<Integer> cir){
        cir.setReturnValue(cir.getReturnValue() + 1);
    }
}
