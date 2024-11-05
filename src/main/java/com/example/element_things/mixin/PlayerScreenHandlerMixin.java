package com.example.element_things.mixin;

import com.example.element_things.item.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends AbstractRecipeScreenHandler<CraftingRecipeInput, CraftingRecipe> {
    public PlayerScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }
    @Inject(method = "<init>",at=@At("RETURN"))
    private void onConstructed(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo ci){
            MinecraftClient client = MinecraftClient.getInstance();
            this.addSlot(new Slot(owner.getInventory(), 41, 78, 43) {
                @Override
                public boolean canInsert(ItemStack stack) {
                    return stack.isIn(ItemTags.ARROWS) || stack.isOf(ModItems.QUIVER);
                }

                @Override
                public boolean canTakeItems(PlayerEntity playerEntity) {
                    return true;
                }
            });
    }
}
