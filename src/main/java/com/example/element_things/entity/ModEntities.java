package com.example.element_things.entity;

import com.example.element_things.ElementThingsMod;
import com.example.element_things.entity.custom.IceEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<IceEntity> ICE_ENTITY = Registry.register(Registries.ENTITY_TYPE, Identifier.of(ElementThingsMod.MOD_ID,"ice_entity"),
            EntityType.Builder.create(IceEntity::new, SpawnGroup.MISC).dimensions(1f,2f).build());
}
