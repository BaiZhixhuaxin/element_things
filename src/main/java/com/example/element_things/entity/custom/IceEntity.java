package com.example.element_things.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class IceEntity extends MobEntity {
    public IceEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        this.setInvulnerable(true);
    }
    public static DefaultAttributeContainer.Builder createArmorStandAttributes() {
        return createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH,20)
                .add(EntityAttributes.GENERIC_GRAVITY,0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE,1)
    ;}

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return !damageSource.isOf(DamageTypes.GENERIC_KILL);
    }
}
