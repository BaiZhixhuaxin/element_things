package com.example.element_things.recipe;

import com.example.element_things.ElementThingsMod;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface ModRecipes {
  //  public static final RecipeSerializer<ArrowRecipe> ARROW_RECIPE_RECIPE = registerRecipes("arrow_recipe",new SpecialRecipeSerializer<>(ArrowRecipe::new));
  RecipeSerializer<ArrowRecipe> ARROW_RECIPE = Registry.register(Registries.RECIPE_SERIALIZER,Identifier.of(ElementThingsMod.MOD_ID,"arrow_recipe"),new SpecialRecipeSerializer<>(ArrowRecipe::new));

 //   private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S registerRecipes(String id, S serializer) {
  //      return Registry.register(Registries.RECIPE_SERIALIZER, id, serializer);
  //  }
 static void registerForRecipe(){}
}
