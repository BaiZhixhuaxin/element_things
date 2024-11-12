package com.example.element_things.mixin;

import com.example.element_things.event.ZoomKey;
import com.example.element_things.util.zoom.Zoom;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin implements AutoCloseable{
    @Inject(method = "getFov",at=@At("RETURN"), cancellable = true)
    private void getFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
        if(ZoomKey.isZoomKeyPressed() && MinecraftClient.getInstance().options.getPerspective().isFirstPerson()){
            cir.setReturnValue(cir.getReturnValue() / Zoom.INSTANCE.getCurrentLevel());
        }
    }
}
