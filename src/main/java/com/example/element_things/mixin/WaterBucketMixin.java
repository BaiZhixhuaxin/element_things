package com.example.element_things.mixin;

import com.example.element_things.access.BucketTrainingManagerAccess;
import com.example.element_things.util.bucket_training.BucketTrainingManager;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.component.ComponentHolder;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Mixin(ItemStack.class)
public abstract class WaterBucketMixin implements ComponentHolder, FabricItemStack, BucketTrainingManagerAccess, RegistryEntryLookup.RegistryLookup{
    @Unique
    private static int food_tick = 0;
    @Shadow public abstract boolean isOf(Item item);

    @Shadow public abstract Item getItem();

    @Shadow public abstract ComponentMap getComponents();

    @Shadow @Nullable public abstract <T> T set(ComponentType<? super T> type, @Nullable T value);

    @Shadow public abstract boolean isEmpty();

    @Inject(method = "use",at=@At("HEAD"))
    private void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
        if(this.isOf(Items.WATER_BUCKET) && !world.isClient && user.isSneaking()){
            BucketTrainingManager bucketTrainingManager = ((BucketTrainingManagerAccess) user).getBucketTrainingManager();
            boolean isOn = bucketTrainingManager.isOn;
            bucketTrainingManager.setOn(!isOn);
            user.swingHand(hand);
        }
//        if(!this.isEmpty() && !world.isClient && user.isSneaking()){
//            Optional<RecipeEntry<?>> optionalRecipe = world.getRecipeManager().values().stream().filter(recipeEntry -> ( recipeEntry.value().getResult(new RegistryWrapper.WrapperLookup() {
//                @Override
//                public Stream<RegistryKey<? extends Registry<?>>> streamAllRegistryKeys() {
//                    return Stream.empty();
//                }
//
//                @Override
//                public <T> Optional<RegistryWrapper.Impl<T>> getOptionalWrapper(RegistryKey<? extends Registry<? extends T>> registryRef) {
//                    return Optional.empty();
//                }
//            }).getItem() == this.getItem()) && recipeEntry.value().getType().equals(RecipeType.CRAFTING)).findAny();
//            if(optionalRecipe.isPresent())
//            {
//                System.out.println(1);
//                Recipe<?> recipe = optionalRecipe.get().value();
//                if(recipe != null){
//                    List<Ingredient> ingredients = recipe.getIngredients();
//                    for(Ingredient ingredient : ingredients){
//                        ItemStack stack = ingredient.getMatchingStacks()[0].copy();
//                        user.giveItemStack(stack);
//                    }
//                }
//            }
//        }
    }
}
