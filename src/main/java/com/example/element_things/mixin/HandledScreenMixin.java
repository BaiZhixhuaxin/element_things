package com.example.element_things.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin extends Screen {
    @Shadow @Nullable protected abstract Slot getSlotAt(double x, double y);

    protected HandledScreenMixin(Text title) {
        super(title);
    }
    @Inject(method = "render",at=@At("RETURN"))
    public void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        Slot slot = getSlotAt(mouseX,mouseY);
        if(slot != null){
            ItemStack stack = slot.getStack();
            if(!stack.isEmpty()){
                MatrixStack matrixStack = context.getMatrices();
                matrixStack.push();
                float time = 5.0f;
                float time1 = time - 1.0f;
                matrixStack.translate( -((float) context.getScaledWindowWidth() / 8.0f) * time1,((float) - context.getScaledWindowHeight() / 2.0f) * time1,0);
                matrixStack.scale(time,time,1f);
                context.drawItem(stack,context.getScaledWindowWidth() / 8 - 8,context.getScaledWindowHeight() / 2 - 8);
                matrixStack.pop();
            }
        }
    }
}
