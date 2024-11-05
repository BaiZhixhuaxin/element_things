package com.example.element_things.item;

import com.example.element_things.component.ModComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

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
}
