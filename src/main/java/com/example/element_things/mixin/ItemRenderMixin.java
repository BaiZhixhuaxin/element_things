package com.example.element_things.mixin;

import com.example.element_things.util.TickHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemRenderer.class)
public abstract class ItemRenderMixin implements SynchronousResourceReloader ,RenderTickCounter{
    @Shadow @Final private ItemColors colors;
    @Unique
    private RenderTickCounter renderTickCounter = RenderTickCounter.ZERO;
    @Unique
    private static float totalTickDelta;
    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",at= @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderBakedItemModel(Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/item/ItemStack;IILnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;)V"))
    public void renderItem1(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci){
        if(stack.isOf(Items.BREAD)){
            totalTickDelta +=renderTickCounter.getTickDelta(true);
            float rotation = (float) (TickHelper.tick * 2 * Math.PI / 180);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(rotation),0.5f,0.5f,0);
        }
    }
   @Inject(method = "renderBakedItemQuads",at=@At("HEAD"), cancellable = true)
    private void render_item(MatrixStack matrices, VertexConsumer vertices, List<BakedQuad> quads, ItemStack stack, int light, int overlay, CallbackInfo ci){
        if(stack.contains(DataComponentTypes.FOOD)) {
            boolean bl = !stack.isEmpty();
            MatrixStack.Entry entry = matrices.peek();

            for (BakedQuad bakedQuad : quads) {
                int i = -1;
                if (bl && bakedQuad.hasColor()) {
                    i = colors.getColor(stack, bakedQuad.getColorIndex());
                }

                float f = (float) ColorHelper.Argb.getAlpha(i) / 255.0F;
                float g = (float) 124 / 255.0F;
                float h = (float) 186 / 255.0F;
                float j = (float) 43 / 255.0F;
                vertices.quad(entry, bakedQuad, g, h, j, f, light, overlay);
            }
            ci.cancel();
        }
   }
   @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",at=@At("HEAD"), cancellable = true)
    private void render(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci){
        if(stack.isOf(Items.LEAD)){
            if(renderMode.equals(ModelTransformationMode.GUI)) {
                return;
            }
            ci.cancel();
            matrices.push();
            World world = MinecraftClient.getInstance().world;
            Quaternionf quaternionf = new Quaternionf().rotateAxis((float) Math.toRadians(180),new Vector3f(0,1,0));
            matrices.multiply(quaternionf);
            matrices.scale(0.5f,0.5f,0.5f);
            matrices.translate(1.0f,0f,0f);
            if(world != null) {
                WanderingTraderEntity wanderingTrader = new WanderingTraderEntity(EntityType.WANDERING_TRADER,world);
                wanderingTrader.setAiDisabled(true);
                MinecraftClient.getInstance().getEntityRenderDispatcher().render(wanderingTrader,0,0,0,0.0f,1.0f,matrices,vertexConsumers,light);
            }
            matrices.pop();
        }
   }
}
