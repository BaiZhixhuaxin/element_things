package com.example.element_things.mixin;

import com.example.element_things.event.ZoomKey;
import com.example.element_things.util.zoom.Zoom;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public abstract class MouseMixin {
    @ModifyExpressionValue(method = "updateMouse",at= @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;smoothCameraEnabled:Z"))
    private boolean isSmoothEnabled(boolean original){
        return original || (ZoomKey.isZoomKeyPressed() && MinecraftClient.getInstance().options.getPerspective().isFirstPerson());
    }
    @Inject(method = "onMouseScroll",at=@At("RETURN"))
    private void changeFov(long window, double horizontal, double vertical, CallbackInfo ci){
        Zoom.INSTANCE.onMouseScroll(vertical);
    }
    @WrapWithCondition(method = "onMouseScroll",at= @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;scrollInHotbar(D)V"))
    private boolean addCondition(PlayerInventory instance, double scrollAmount){
        return !(ZoomKey.isZoomKeyPressed() && MinecraftClient.getInstance().options.getPerspective().isFirstPerson());
    }
}
