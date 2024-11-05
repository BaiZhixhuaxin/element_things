package com.example.element_things.mixin;

import com.example.element_things.effect.ModEffects;
import com.example.element_things.enchantment.Enchantments;
import com.example.element_things.util.ModEnchantmentHelper;
import com.example.element_things.util.TickHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
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
    }
}
