package com.example.element_things.item.tools;

import com.example.element_things.component.ModComponent;
import com.example.element_things.effect.ModEffects;
import com.example.element_things.sound.ModSoundEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.component.type.UnbreakableComponent;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class FireSword extends SwordItem {
    public FireSword(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient && hand.equals(Hand.MAIN_HAND)) {
            ItemStack stack = user.getStackInHand(hand);
            int a = stack.getOrDefault(ModComponent.MOD_DATA, 0);
            a++;
            System.out.println(a);
            if (a == 2) {
                user.getAttributes().getCustomInstance(EntityAttributes.GENERIC_SCALE).setBaseValue(1);
                user.removeStatusEffect(ModEffects.BLUE_LIGHT);
                user.removeStatusEffect(ModEffects.RED_LIGHT);
                a = 0;
            } else if (a == 1) {
                world.playSound(null, user.getBlockPos(), ModSoundEvents.TRANSFORM, SoundCategory.AMBIENT, 10.0f, 1.0f);
                user.getAttributes().getCustomInstance(EntityAttributes.GENERIC_SCALE).setBaseValue(20);
                user.addStatusEffect(new StatusEffectInstance(ModEffects.BLUE_LIGHT,2400,0));
            }
            stack.set(ModComponent.MOD_DATA, a);
            stack.set(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(true));
            //   NbtCompound nbt = new NbtCompound();
            //   nbt.putInt("123",6);
            //   NbtComponent nbtComponent2 = NbtComponent.of(nbt);
            //   if(a <=1) stack.set(DataComponentTypes.CUSTOM_DATA,nbtComponent2);
            //  NbtComponent nbtComponent1 = stack.get(DataComponentTypes.CUSTOM_DATA);
            //  NbtCompound nbtCompound;
            //   if (nbtComponent1 != null && a ==2) {
            //      nbtCompound = nbtComponent1.copyNbt();
            //     nbtCompound.putBoolean("123?",true);
            //    NbtComponent nbtComponent = NbtComponent.of(nbtCompound);
            //    stack.set(DataComponentTypes.CUSTOM_DATA,nbtComponent);
            // }
            return TypedActionResult.success(stack);
        }
        else return TypedActionResult.fail(user.getStackInHand(Hand.MAIN_HAND));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if(stack.contains(ModComponent.MOD_DATA)){
            tooltip.add(Text.literal(stack.get(ModComponent.MOD_DATA).toString()));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}
