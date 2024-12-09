package com.example.element_things.mixin;

import com.example.element_things.access.AnimalInventoryAccess;
import com.example.element_things.access.BucketTrainingManagerAccess;
import com.example.element_things.block.ModBlocks;
import com.example.element_things.enchantment.Enchantments;
import com.example.element_things.tag.ModItemTags;
import com.example.element_things.util.BlockPosList;
import com.example.element_things.util.ModEnchantmentHelper;
import com.example.element_things.util.TickHelper;
import com.example.element_things.util.animal_inventory.AnimalInventory;
import com.example.element_things.util.bucket_training.BucketTrainingManager;
import com.example.element_things.util.dynamic_light.DynamicLight;
import com.google.common.collect.Sets;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

@Mixin(PlayerEntity.class)
public abstract class PlayerTickMixin<T extends BlockEntity> extends LivingEntity implements BucketTrainingManagerAccess ,AnimalInventoryAccess{
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
    @Unique
    private static final int max_block = 125;

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
        if(this.isSneaking()){
            HitResult result = MinecraftClient.getInstance().crosshairTarget;
            if (result != null && result.getType().equals(HitResult.Type.BLOCK)) {
                BlockHitResult blockHitResult = (BlockHitResult) result;
                BlockPos pos = blockHitResult.getBlockPos();
                BlockState state;
                state = world.getBlockState(pos);
                if (!state.isIn(BlockTags.AIR) && !state.isOf(Blocks.WATER) && !state.isOf(Blocks.LAVA)) {
                    int blocks = 1;
                    Set<BlockPos> visited = Sets.newHashSet(pos);
                    LinkedList<BlockPos> candidates = new LinkedList<>();
                    candidates.add(pos);
                    addNeighbors(candidates, pos);
                    while (blocks < max_block && !candidates.isEmpty()) {
                        BlockPos blockPos = candidates.poll();
                        BlockState state1 = world.getBlockState(blockPos);
                        if (state1.equals(state) && visited.add(blockPos)) {
                            blocks++;
                            addNeighbors(candidates, blockPos);
                        }
                    }
                    BlockPosList.list = visited;
                }
            }
        }
        else BlockPosList.list.clear();
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
    @Unique
    private static void addNeighbors(LinkedList<BlockPos> candidates, BlockPos source){
        BlockPos up = source.up();
        BlockPos down = source.down();
        candidates.add(up);
        candidates.add(down);
        BlockPos[] blockPositions = new BlockPos[]{up,source,down};
        for(BlockPos pos : blockPositions){
            candidates.add(pos.west());
            candidates.add(pos.east());
            candidates.add(pos.north());
            candidates.add(pos.south());
            candidates.add(pos.north().west());
            candidates.add(pos.north().east());
            candidates.add(pos.south().west());
            candidates.add(pos.south().east());
        }
    }
    @Inject(method = "interact",at=@At("RETURN"))
    private void set(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir){
        if(entity instanceof LivingEntity livingEntity && !entity.getWorld().isClient) {
            if (livingEntity instanceof HuskEntity) {
                AnimalInventory animalInventory = ((AnimalInventoryAccess) livingEntity).getAnimalInventory();
                if (!animalInventory.isEmpty()) {
                    for (int i = 0; i < animalInventory.size(); i++) {
                        if (!animalInventory.pack.get(i).isEmpty()) {
                            ItemStack itemStack = animalInventory.pack.get(i).copy();
                            itemStack.decrement(1);
                            ItemStack stack = animalInventory.pack.get(i).copy();
                            stack.setCount(1);
                            this.giveItemStack(stack);
                            animalInventory.pack.set(i, itemStack);
                            break;
                        }
                    }
                }
            }
        }
    }
}
