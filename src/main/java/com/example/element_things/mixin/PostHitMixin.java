package com.example.element_things.mixin;

import com.example.element_things.access.FlattenManagerAccess;
import com.example.element_things.enchantment.Enchantments;
import com.example.element_things.event.SoundCheckKey;
import com.example.element_things.sound.ModSoundEvents;
import com.example.element_things.util.ModEnchantmentHelper;
import com.example.element_things.util.SoundRMS;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.component.ComponentHolder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class PostHitMixin implements ComponentHolder, FabricItemStack,FlattenManagerAccess {
    @Inject(method = "postHit",at=@At("HEAD"))
    public void postHit(LivingEntity target, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
            ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
            int fo_lvl = ModEnchantmentHelper.getLevel(stack,Enchantments.FROST);
            if(fo_lvl > 0){
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,60,fo_lvl - 1));
        }
            if(stack.isOf(Items.MACE) && player.fallDistance >= 2){
                player.getWorld().playSound(null,player.getBlockPos(), ModSoundEvents.HIT_SOUND, SoundCategory.PLAYERS,1.0f,1.0f);
                 ((FlattenManagerAccess) target).getFlattenManager().set();
                NbtCompound nbt = new NbtCompound();
                NbtCompound entity = target.writeNbt(nbt);
                System.out.println(entity.getBoolean("beenFlattened"));
            }
            if(SoundCheckKey.isSoundKeyPressed()){
                double extra_damage = SoundRMS.SoundRMS * 6.5;
                if(target.isAlive()){
                   if(target.getHealth() > extra_damage) target.setHealth(target.getHealth() - (float) extra_damage);
                   else {target.damage(target.getDamageSources().playerAttack(player), (float) extra_damage);
                   }
                }
            }
    }
}
