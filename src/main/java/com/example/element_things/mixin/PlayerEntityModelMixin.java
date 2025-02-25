package com.example.element_things.mixin;

import com.example.element_things.event.DeleteItemKey;
import com.example.element_things.util.animation.Animation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityModel.class)
public abstract class PlayerEntityModelMixin<T extends LivingEntity> extends BipedEntityModel<T> {
    @Shadow @Final public ModelPart rightSleeve;
    @Unique
    private static int tick2 = 0;
    @Unique
    private static int count = 0;
    @Unique
    Animation animation =  new Animation();
    public PlayerEntityModelMixin(ModelPart root) {
        super(root);
    }
    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V",at=@At("RETURN"))
    public void setAngles(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if(player != null && player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof SwordItem) {
            ModelPart modelPart = this.rightArm;
            ModelPart modelPart1 = this.rightSleeve;
            if (DeleteItemKey.isDeleteKeyPressed()) {
                if (tick2 == 0 && count == 0) {
                    animation.applyAnimation();
                    count++;
                }
                else if(count != 0 && tick2 == 0) {
                    count = 0;
                    animation.tick = 0;
                }
                tick2++;
            }
            else if(!DeleteItemKey.isDeleteKeyPressed() && tick2 > 0){
                tick2 = 0;
            }
            if(animation != null && animation.tick != 0 && count != 0 && !player.handSwinging) {
                modelPart.yaw = (float) Math.toRadians(2.25 * animation.tick);
                modelPart.pitch = (float) Math.toRadians(2.25 * animation.tick);
                modelPart.roll = (float) Math.toRadians(2.25 * animation.tick);
                modelPart1.yaw = (float) Math.toRadians(2.25 * animation.tick);
                modelPart1.pitch = (float) Math.toRadians(2.25 * animation.tick);
                modelPart1.roll = (float) Math.toRadians(2.25 * animation.tick);
            }
        }
    }
}
