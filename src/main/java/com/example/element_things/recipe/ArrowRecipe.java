package com.example.element_things.recipe;

import com.example.element_things.component.ModComponent;
import com.example.element_things.item.ModItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ArrowRecipe extends SpecialCraftingRecipe {
    public ArrowRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
      /*  ArrayList<ItemStack> list = new ArrayList<>();
        for(int i = 0;i < input.getSize();++i){
            ItemStack stack = input.getStackInSlot(i);
            if(!stack.isEmpty()){
                if(stack.isOf(Items.ARROW)) list.add(stack);
                else if(stack.isOf(ModItems.QUIVER)) list.add(stack);
            }
        }
        return list.size() == 2;*/
        return true;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
       /* ArrayList<ItemStack> list = new ArrayList<>();
        boolean has_arrow = false;
        boolean has_quiver = false;
        boolean has_another = false;
        ItemStack quiver = null;
        int arrow_count = 0;
        for(int i = 0;i < input.getSize();++i){
            ItemStack stack = input.getStackInSlot(i);
            if(!stack.isEmpty()){
                if(stack.isOf(Items.ARROW)) {
                    list.add(stack);
                    has_arrow = true;
                    arrow_count = stack.getCount();
                }
                else if(stack.isOf(ModItems.QUIVER)) {
                    list.add(stack);
                    quiver = stack.copy();
                    has_quiver = true;
                }
                else {
                    has_another = true;
                }
            }
        }
        if(has_arrow && has_quiver && !has_another ){
            quiver.set(ModComponent.ARROW_AMOUNT,arrow_count);
            return quiver;
        }
        return null;*/
        for(int i = 0;i < input.getSize();++i){
            if(input.getStackInSlot(i).isOf(ModItems.QUIVER)) {
                System.out.println(true);
                return new ItemStack(Items.DIAMOND);
            }
        }
        return null;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ARROW_RECIPE;
    }
}
