package com.example.element_things.mixin;

import com.example.element_things.access.BucketTrainingManagerAccess;
import com.example.element_things.util.bucket_training.BucketTrainingManager;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.component.ComponentHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class WaterBucketMixin implements ComponentHolder, FabricItemStack, BucketTrainingManagerAccess {
    @Shadow public abstract boolean isOf(Item item);

    @Inject(method = "use",at=@At("HEAD"))
    private void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
        if(this.isOf(Items.WATER_BUCKET) && !world.isClient && user.isSneaking()){
            BucketTrainingManager bucketTrainingManager = ((BucketTrainingManagerAccess) user).getBucketTrainingManager();
            boolean isOn = bucketTrainingManager.isOn;
            bucketTrainingManager.setOn(!isOn);
            user.swingHand(hand);
        }
    }
}
