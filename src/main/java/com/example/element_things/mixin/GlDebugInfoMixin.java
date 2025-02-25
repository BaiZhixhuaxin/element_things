package com.example.element_things.mixin;

import com.example.element_things.ElementThingsMod;
import com.example.element_things.ElementThingsModClient;
import com.mojang.blaze3d.platform.GlDebugInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlDebugInfo.class)
public class GlDebugInfoMixin {
    @Inject(method = "getRenderer",at=@At("RETURN"), cancellable = true)
    private static void set(CallbackInfoReturnable<String> cir){
        if(ElementThingsModClient.config.gpu_type.isEmpty())
            return;
        cir.setReturnValue(ElementThingsModClient.config.gpu_type);
        cir.cancel();
    }
}
