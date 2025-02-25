package com.example.element_things.util.entity_type_check;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;

public class CheckEntity {
    public EntityType<?> getEntityType(String type){
        for(EntityType<?> type1 : Registries.ENTITY_TYPE){
            if(type1.getName().toString().equals(type)) return type1;
        }
        return null;
    }
}
