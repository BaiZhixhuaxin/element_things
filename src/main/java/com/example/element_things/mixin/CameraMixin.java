package com.example.element_things.mixin;

import com.example.element_things.event.KeyInputHandler;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow public abstract Entity getFocusedEntity();

    @Shadow protected abstract void setPos(Vec3d pos);

    @Shadow protected abstract void setRotation(float yaw, float pitch);

    @Inject(method = "update",at=@At("RETURN"))
    public void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if(KeyInputHandler.screenKeyBeenPressed()){
            this.setPos(this.getFocusedEntity().getEyePos().add(new Vec3d(0.0f,5.0f,0.0f)));
            this.setRotation(this.getFocusedEntity().getYaw(),90.0f);
        }
    }
}
