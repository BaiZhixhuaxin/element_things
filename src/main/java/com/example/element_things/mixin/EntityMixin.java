package com.example.element_things.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract World getWorld();

    @Shadow public abstract int getId();

    @Inject(method = "isCollidable",at=@At("HEAD"), cancellable = true)
    private void set(CallbackInfoReturnable<Boolean> cir){
        World world = this.getWorld();
        Entity entity = world.getEntityById(this.getId());
        if(entity instanceof LivingEntity && !entity.isPlayer()) {
            cir.setReturnValue(true);
        }
    }
}
