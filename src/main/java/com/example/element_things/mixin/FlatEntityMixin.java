package com.example.element_things.mixin;

import com.example.element_things.access.FlattenManagerAccess;
import com.example.element_things.network.packet.EntityC2SPacket;
import com.example.element_things.network.packet.FlattenS2CPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicBoolean;
@Environment(EnvType.CLIENT)
@Mixin(LivingEntityRenderer.class)
public abstract class FlatEntityMixin <T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> ,FlattenManagerAccess{
    protected FlatEntityMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }
    @Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",at= @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;scale(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/util/math/MatrixStack;F)V"))
    public void render(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci){
        AtomicBoolean b = new AtomicBoolean(false);
        ClientPlayNetworking.send(new EntityC2SPacket(livingEntity.getId()));
        ClientPlayNetworking.registerGlobalReceiver(FlattenS2CPacket.PACKET_ID,(payload, context) ->{
                if(payload.shouldBeFlattened()) ((FlattenManagerAccess)livingEntity).getFlattenManager().set();
        } );
        if(((FlattenManagerAccess)livingEntity).getFlattenManager().beenFlattened){
            matrixStack.scale(1.0f,0.5f,1.0f);
        }
    }
}
