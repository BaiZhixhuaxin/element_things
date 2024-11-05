package com.example.element_things.mixin;

import com.example.element_things.component.ModComponent;
import com.example.element_things.item.ModItems;
import com.example.element_things.util.AngleHelper;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.featuretoggle.ToggleableFeature;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class InventoryTickMixin implements ToggleableFeature, ItemConvertible, FabricItem {
    @Inject(method = "inventoryTick",at=@At("HEAD"))
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        if(stack.isOf(ModItems.INDICATOR) && entity instanceof LivingEntity user){
            Vec3d start = user.getRotationVec(0.1f).multiply(1.0f,0.0f,1.0f).normalize();
            Vec3d end = new Vec3d(stack.getOrDefault(ModComponent.INDICATOR_X,0),stack.getOrDefault(ModComponent.INDICATOR_Y,0),stack.getOrDefault(ModComponent.INDICATOR_Z,0)).subtract(user.getEyePos()).multiply(1.0f,0.0f,1.0f).normalize();
            Vec3d vec3d = new Vec3d(1,0,0);
            double move = start.z > 0 ? AngleHelper.getAngle(vec3d,start) : -AngleHelper.getAngle(vec3d,start);
            end = end.rotateY((float) move);
            double angle = AngleHelper.getAngle(vec3d,end);
            if(end.z < 0) angle = -angle;
            double a = Math.PI / 4;
            if(angle > -a && angle <=a){
                stack.set(DataComponentTypes.CUSTOM_MODEL_DATA,new CustomModelDataComponent(3));
            }
            else if(angle > a && angle <= 3 * a){
                stack.set(DataComponentTypes.CUSTOM_MODEL_DATA,new CustomModelDataComponent(2));
            }
            else if(angle > - 3 * a && angle <= -a){
                stack.set(DataComponentTypes.CUSTOM_MODEL_DATA,new CustomModelDataComponent(4));
            }
            else {
                stack.set(DataComponentTypes.CUSTOM_MODEL_DATA,new CustomModelDataComponent(1));
            }
        }
    }
}
