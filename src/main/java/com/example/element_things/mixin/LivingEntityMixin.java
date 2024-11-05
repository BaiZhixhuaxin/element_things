package com.example.element_things.mixin;

import com.example.element_things.access.FlattenManagerAccess;
import com.example.element_things.effect.ModEffects;
import com.example.element_things.util.flatten.FlattenManager;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, FlattenManagerAccess {
    @Shadow public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

    @Shadow @Nullable public abstract StatusEffectInstance getStatusEffect(RegistryEntry<StatusEffect> effect);

    @Shadow public abstract Map<RegistryEntry<StatusEffect>, StatusEffectInstance> getActiveStatusEffects();

    @Shadow public abstract AttributeContainer getAttributes();

    @Unique
    private FlattenManager flattenManager = new FlattenManager();
    @Override
    public FlattenManager getFlattenManager(){
        return this.flattenManager;
    }
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @Inject(method = "readCustomDataFromNbt",at=@At("TAIL"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.flattenManager.readNbt(nbt);
    }
    @Inject(method = "writeCustomDataToNbt",at=@At("TAIL"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci){
        this.flattenManager.writeNbt(nbt);
    }
    @Inject(method = "tick",at=@At("HEAD"))
    private void tick(CallbackInfo ci){
        if(this.getActiveStatusEffects().containsKey(ModEffects.RED_LIGHT)){
            int duration = this.getActiveStatusEffects().get(ModEffects.RED_LIGHT).getDuration();
            System.out.println(duration);
            if(duration == 1){
                this.getAttributes().getCustomInstance(EntityAttributes.GENERIC_SCALE).setBaseValue(1);
            }
        }
    }
}
