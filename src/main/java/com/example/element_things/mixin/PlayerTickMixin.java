package com.example.element_things.mixin;

import com.example.element_things.access.BucketTrainingManagerAccess;
import com.example.element_things.block.ModBlocks;
import com.example.element_things.enchantment.Enchantments;
import com.example.element_things.tag.ModItemTags;
import com.example.element_things.util.ModEnchantmentHelper;
import com.example.element_things.util.TickHelper;
import com.example.element_things.util.bucket_training.BucketTrainingManager;
import com.example.element_things.util.dynamic_light.DynamicLight;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(PlayerEntity.class)
public abstract class PlayerTickMixin<T extends BlockEntity> extends LivingEntity implements BucketTrainingManagerAccess {
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract boolean giveItemStack(ItemStack stack);

    @Unique
    private BucketTrainingManager bucketTrainingManager = new BucketTrainingManager();
    @Override
    public BucketTrainingManager getBucketTrainingManager(){
        return bucketTrainingManager;
    }
    @Unique
    private static int tick = 0;

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
            if (this.getStackInHand(Hand.MAIN_HAND).isIn(ModItemTags.GLOWING_ITEM) || this.getStackInHand(Hand.OFF_HAND).isIn(ModItemTags.GLOWING_ITEM)) {
                BlockState state = world.getBlockState(this.getBlockPos());
                BlockState state1 = world.getBlockState(this.getBlockPos().up(1));
                BlockState state2 = world.getBlockState(this.getBlockPos().up(2));
                if (state.isIn(BlockTags.AIR)) {
                    world.setBlockState(this.getBlockPos(), ModBlocks.LIGHT_AIR_BLOCK.getDefaultState());
                } else if (state1.isIn(BlockTags.AIR) && !state.isOf(ModBlocks.LIGHT_AIR_BLOCK)) {
                    world.setBlockState(this.getBlockPos().up(1), ModBlocks.LIGHT_AIR_BLOCK.getDefaultState());
                } else if (state2.isIn(BlockTags.AIR) && !state.isOf(ModBlocks.LIGHT_AIR_BLOCK) && !state1.isOf(ModBlocks.LIGHT_AIR_BLOCK)) {
                    world.setBlockState(this.getBlockPos().up(2), ModBlocks.LIGHT_AIR_BLOCK.getDefaultState());
                }
            }
        }
        if(((BucketTrainingManagerAccess) this).getBucketTrainingManager().isOn && world instanceof ServerWorld serverWorld) {
            if (tick >= 100) {
                tick = 0;
                Random random1 = new Random();
                if (checkUp(this.getBlockPos(), this.getWorld()) && this.isOnGround()) {
                    double b = random1.nextDouble();
                    if (b <= 0.05) {
                    Vec3d vec3d = this.getPos();
                    vec3d = vec3d.add(0,this.random.nextInt(100) + 8,0);
                        this.teleportTo(new TeleportTarget(serverWorld, vec3d, this.getVelocity(), this.getYaw(), this.getPitch(), TeleportTarget.NO_OP));
                        this.giveItemStack(new ItemStack(Items.DIAMOND));
                    }
                }
            }
            tick++;
        }
    }
    @Inject(method = "readCustomDataFromNbt",at=@At("TAIL"))
    private void readNbt(NbtCompound nbt, CallbackInfo ci){
        this.bucketTrainingManager.readNbt(nbt);
    }
    @Inject(method = "writeCustomDataToNbt",at=@At("TAIL"))
    private void writeNbt(NbtCompound nbt, CallbackInfo ci){
        this.bucketTrainingManager.writeNbt(nbt);
    }
    @Unique
    private boolean checkUp(BlockPos pos,World world){
        int a;
        RegistryKey<World> dimension = world.getRegistryKey();
        if(dimension.equals(World.OVERWORLD)) a = 319;
        else a = 255;
        for(int i = 0;i <= a - pos.getY();i++){
            if(!world.getBlockState(pos.up(i)).isIn(BlockTags.AIR)) return false;
        }
        return true;
    }
}
