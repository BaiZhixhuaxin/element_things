package com.example.element_things.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

public class ToolMessageHud implements HudRenderCallback {
    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer renderer = client.textRenderer;
        PlayerEntity player = client.player;
        if(player != null){
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();
            ItemStack main = player.getStackInHand(Hand.MAIN_HAND);
            ItemStack off = player.getStackInHand(Hand.OFF_HAND);
            ItemStack head = player.getEquippedStack(EquipmentSlot.HEAD);
            ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
            ItemStack legs = player.getEquippedStack(EquipmentSlot.LEGS);
            ItemStack feet = player.getEquippedStack(EquipmentSlot.FEET);
            if(!main.isEmpty()){
                drawContext.drawItem(main, width - 45, height - 45 );
                if(main.getMaxDamage() > 0) drawContext.drawText(renderer, Text.literal(String.valueOf(main.getMaxDamage() - main.getDamage())),width - 25,height - 40,16777215,true);
                else drawContext.drawText(renderer,Text.literal(String.valueOf(main.getCount())),width - 25,height - 40,16777215,true);
            }
            if(!off.isEmpty()){
                drawContext.drawItem(off, width - 45, height - 25 );
                if(off.getMaxDamage() > 0) drawContext.drawText(renderer, Text.literal(String.valueOf(off.getMaxDamage() - off.getDamage())),width - 25,height - 20,16777215,true);
                else drawContext.drawText(renderer,Text.literal(String.valueOf(off.getCount())),width - 25,height - 20,16777215,true);
            }
            if(!head.isEmpty()){
                drawContext.drawItem(head, width - 85, height - 60 );
                if(head.getMaxDamage() > 0) drawContext.drawText(renderer, Text.literal(String.valueOf(head.getMaxDamage() - off.getDamage())),width - 65,height - 55,16777215,true);
                else drawContext.drawText(renderer,Text.literal(String.valueOf(head.getCount())),width - 65,height - 55,16777215,true);
            }
            if(!chest.isEmpty()){
                drawContext.drawItem(chest, width - 85, height - 45 );
                if(chest.getMaxDamage() > 0) drawContext.drawText(renderer, Text.literal(String.valueOf(chest.getMaxDamage() - off.getDamage())),width - 65,height - 40,16777215,true);
                else drawContext.drawText(renderer,Text.literal(String.valueOf(chest.getCount())),width - 65,height - 40,16777215,true);
            }
            if(!legs.isEmpty()){
                drawContext.drawItem(legs, width - 85, height - 30 );
                if(legs.getMaxDamage() > 0) drawContext.drawText(renderer, Text.literal(String.valueOf(legs.getMaxDamage() - off.getDamage())),width - 65,height - 25,16777215,true);
                else drawContext.drawText(renderer,Text.literal(String.valueOf(legs.getCount())),width - 65,height - 25,16777215,true);
            }
            if(!feet.isEmpty()){
                drawContext.drawItem(feet, width - 85, height - 15 );
                if(feet.getMaxDamage() > 0) drawContext.drawText(renderer, Text.literal(String.valueOf(feet.getMaxDamage() - off.getDamage())),width - 65,height - 10,16777215,true);
                else drawContext.drawText(renderer,Text.literal(String.valueOf(feet.getCount())),width - 65,height - 10,16777215,true);
            }
            if(main.getItem() instanceof BowItem){
                int count = 0;
                drawContext.drawItem(new ItemStack(Items.ARROW),10,10);
                Inventory inventory = player.getInventory();
                for(int i = 0;i < inventory.size() ; i++){
                    ItemStack stack = inventory.getStack(i);
                    if(stack.isOf(Items.ARROW)) count += stack.getCount();
                }
                drawContext.drawText(client.textRenderer,String.valueOf(count),25,25,count != 0? 16777215 : 16732231,true);
            }
        }
    }
}
