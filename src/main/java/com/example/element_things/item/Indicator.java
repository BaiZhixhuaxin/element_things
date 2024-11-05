package com.example.element_things.item;

import com.example.element_things.component.ModComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Indicator extends Item {
    public Indicator(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient && hand == Hand.MAIN_HAND){
            BlockPos pos =  new BlockPos(100,0,100);
            ItemStack stack = user.getEquippedStack(EquipmentSlot.MAINHAND);
            stack.set(ModComponent.INDICATOR_X,pos.getX());
            stack.set(ModComponent.INDICATOR_Y,pos.getY());
            stack.set(ModComponent.INDICATOR_Z,pos.getZ());
        }
        return super.use(world, user, hand);
    }
}
