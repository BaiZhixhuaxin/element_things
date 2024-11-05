package com.example.element_things.util.flatten;

import com.example.element_things.access.FlattenManagerAccess;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;

public class BeenFlattenedOrNot {
    public static boolean orNot(LivingEntity livingEntity){
       return  ((FlattenManagerAccess)livingEntity).getFlattenManager().beenFlattened;
    }
}
