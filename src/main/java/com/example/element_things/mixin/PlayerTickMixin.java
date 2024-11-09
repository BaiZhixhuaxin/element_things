package com.example.element_things.mixin;

import com.example.element_things.block.ModBlocks;
import com.example.element_things.enchantment.Enchantments;
import com.example.element_things.util.ModEnchantmentHelper;
import com.example.element_things.util.TickHelper;
import com.example.element_things.util.dynamic_light.DynamicLight;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerTickMixin<T extends BlockEntity> extends LivingEntity{
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract PlayerAbilities getAbilities();

    protected PlayerTickMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method = "tick",at=@At("HEAD"))
    public void tick(CallbackInfo ci) {
        World world = this.getWorld();
        if(!this.getWorld().isClient) TickHelper.add_tick();
        ItemStack stack = this.getEquippedStack(EquipmentSlot.FEET);
        int lvl = ModEnchantmentHelper.getLevel(stack, Enchantments.ACCELERATION);
        if(lvl > 0) {
            BlockPos pos = this.getBlockPos().down(1);
            BlockState state = this.getWorld().getBlockState(pos);
            Block block = this.getWorld().getBlockState(pos).getBlock();
            if (block instanceof BlockWithEntity blockWithEntity) {
                BlockEntity entity = this.getWorld().getBlockEntity(pos);
                if (entity != null) {
                    BlockEntityTicker<T> ticker = (BlockEntityTicker<T>) blockWithEntity.getTicker(this.getWorld(), state, entity.getType());
                    try {
                        for (int i = 0; i < 2 * lvl - 1; i++) {
                            if (ticker != null) {
                                ticker.tick(this.getWorld(), pos, state, (T) entity);
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        if(DynamicLight.isOpened()) {
            if (this.getStackInHand(Hand.MAIN_HAND).isOf(Items.TORCH) || this.getStackInHand(Hand.OFF_HAND).isOf(Items.TORCH)) {
                BlockState state = world.getBlockState(this.getBlockPos());
                BlockState state1 = world.getBlockState(this.getBlockPos());
                BlockState state2 = world.getBlockState(this.getBlockPos());
                if (state.isIn(BlockTags.AIR)) {
                    world.setBlockState(this.getBlockPos(), ModBlocks.LIGHT_AIR_BLOCK.getDefaultState());
                } else if (state1.isIn(BlockTags.AIR)) {
                    world.setBlockState(this.getBlockPos().up(1), ModBlocks.LIGHT_AIR_BLOCK.getDefaultState());
                } else if (state2.isIn(BlockTags.AIR)) {
                    world.setBlockState(this.getBlockPos().up(1), ModBlocks.LIGHT_AIR_BLOCK.getDefaultState());
                }
            }
        }
    }
}
