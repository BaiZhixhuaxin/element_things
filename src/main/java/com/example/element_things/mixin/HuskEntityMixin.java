package com.example.element_things.mixin;

import com.example.element_things.access.AnimalInventoryAccess;
import com.example.element_things.screenHandler.AnimalInventoryScreenHandler;
import com.example.element_things.util.animal_inventory.AnimalInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class HuskEntityMixin extends Entity implements AnimalInventoryAccess , Inventory , NamedScreenHandlerFactory {
    @Unique
    LivingEntity livingEntity = (LivingEntity) (Object)this;
    @Unique
    final AnimalInventory animalInventory = new AnimalInventory(livingEntity);
    @Override
    public AnimalInventory getAnimalInventory() {
        return animalInventory;
    }

    public HuskEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "writeCustomDataToNbt",at=@At("RETURN"))
    private void writeNbt(NbtCompound nbt, CallbackInfo ci){
        if(livingEntity instanceof HuskEntity){
            nbt.put("Inventory", this.animalInventory.writeNbt(new NbtList()));
        }
    }
    @Inject(method = "readCustomDataFromNbt",at=@At("RETURN"))
    private void readNbt(NbtCompound nbt, CallbackInfo ci){
        if(livingEntity instanceof HuskEntity) {
            NbtList nbtList = nbt.getList("Inventory", 10);
            this.animalInventory.readNbt(nbtList);
        }
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        if(livingEntity instanceof HuskEntity){
            return new AnimalInventoryScreenHandler(syncId,playerInventory,animalInventory);
        }
        return null;
    }
}
