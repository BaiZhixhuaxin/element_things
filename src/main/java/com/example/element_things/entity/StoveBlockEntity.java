package com.example.element_things.entity;

import com.example.element_things.screenHandler.ModScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class StoveBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {
    public StoveBlockEntity( BlockPos pos, BlockState state) {
        super(ModBlockEntityType.STOVE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("123");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return ModScreenHandler.STOVE_SCREEN_HANDLER.create(syncId,playerInventory);
    }
}
