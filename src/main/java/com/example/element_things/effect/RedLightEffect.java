package com.example.element_things.effect;

import com.example.element_things.sound.ModSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.sound.SoundCategory;

public class RedLightEffect extends StatusEffect {
    protected RedLightEffect() {
        super(StatusEffectCategory.NEUTRAL,0xD63F3F);
    }
    private static int tick = 0;

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(!entity.getWorld().isClient){
            if(tick == 0){
                entity.getWorld().playSound(null,entity.getBlockPos(), ModSoundEvents.RED_LIGHT, SoundCategory.AMBIENT,10.0f,1.0f);
            }
            if(tick <= 43){
                tick ++;
            }
            else {
                tick = 0;
            }
        }
        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        if(!entity.getWorld().isClient) tick = 0;
        super.onApplied(entity, amplifier);
    }

    @Override
    public void onRemoved(AttributeContainer attributeContainer) {
        tick = 0;
        super.onRemoved(attributeContainer);
    }

    @Override
    public void onEntityRemoval(LivingEntity entity, int amplifier, Entity.RemovalReason reason) {
        if(!entity.getWorld().isClient) {
            entity.getAttributes().getCustomInstance(EntityAttributes.GENERIC_SCALE).setBaseValue(1);
            tick = 0;
        }
        super.onEntityRemoval(entity, amplifier, reason);
    }
}
