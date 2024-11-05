package com.example.element_things.effect;

import com.example.element_things.component.ModComponent;
import com.example.element_things.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;

public class BlueLightEffect extends StatusEffect {
    protected BlueLightEffect() {
        super(StatusEffectCategory.NEUTRAL,0x59C4DE);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration == 1;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(!entity.getWorld().isClient) {
            ItemStack stack = entity.getStackInHand(Hand.MAIN_HAND);
            if (stack.isOf(ModItems.FIRE_SWORD)) {
                int a = stack.getOrDefault(ModComponent.MOD_DATA, 0);
                if (a != 2) {
                    entity.addStatusEffect(new StatusEffectInstance(ModEffects.RED_LIGHT,1200,0));
                }
            } else {
                entity.addStatusEffect(new StatusEffectInstance(ModEffects.RED_LIGHT,1200,0));
            }
        }
        return super.applyUpdateEffect(entity, amplifier);
    }


    @Override
    public void onEntityRemoval(LivingEntity entity, int amplifier, Entity.RemovalReason reason) {
        entity.getAttributes().getCustomInstance(EntityAttributes.GENERIC_SCALE).setBaseValue(1);
        ItemStack stack = entity.getStackInHand(Hand.MAIN_HAND);
        if(stack.isOf(ModItems.FIRE_SWORD)){
            int a = stack.getOrDefault(ModComponent.MOD_DATA, 0);
            if(a != 2){
                entity.addStatusEffect(new StatusEffectInstance(ModEffects.RED_LIGHT,1200,0));
            }
        }
        else {
            entity.addStatusEffect(new StatusEffectInstance(ModEffects.RED_LIGHT,1200,0));
        }
        super.onEntityRemoval(entity, amplifier, reason);
    }
}
