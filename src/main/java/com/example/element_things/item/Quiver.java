package com.example.element_things.item;

import com.example.element_things.component.ModComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class Quiver extends Item {
    public Quiver(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("text.element_things.arrow_amount").formatted(Formatting.DARK_AQUA).append(Text.literal(String.valueOf(stack.getOrDefault(ModComponent.ARROW_AMOUNT,0)))).formatted(Formatting.DARK_AQUA));
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        NbtCompound nbtCompound = NbtPredicate.entityToNbt(user);
        nbtCompound.putInt("example",1);
        user.writeCustomDataToNbt(nbtCompound);
        user.readNbt(nbtCompound);
        return super.use(world, user, hand);
    }
}
