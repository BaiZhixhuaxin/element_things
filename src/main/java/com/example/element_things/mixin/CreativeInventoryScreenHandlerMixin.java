package com.example.element_things.mixin;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.CreativeScreenHandler.class)
public abstract class CreativeInventoryScreenHandlerMixin extends ScreenHandler {
    protected CreativeInventoryScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }
    @Inject(method = "<init>",at=@At("RETURN"))
    private void onConstructed(PlayerEntity player, CallbackInfo ci){
        PlayerInventory inventory = player.getInventory();
        this.addSlot(new Slot(inventory, 41, 78, 43) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isIn(ItemTags.ARROWS);
            }

            @Override
            public boolean canTakeItems(PlayerEntity playerEntity) {
                return true;
            }
        });
    }
}
