package com.example.element_things.screen;

import com.example.element_things.ElementThingsMod;
import com.example.element_things.network.packet.StealInventoryPacket;
import com.example.element_things.screenHandler.AnimalInventoryScreenHandler;
import com.example.element_things.util.animal_inventory.AnimalInventory;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AnimalInventoryScreen extends HandledScreen<AnimalInventoryScreenHandler> {
    public AnimalInventoryScreenHandler animalInventoryScreenHandler;
    public ButtonWidget stealButton;
    public static final Identifier path = Identifier.of(ElementThingsMod.MOD_ID,"textures/gui/animal_inventory.png");
    public AnimalInventoryScreen(AnimalInventoryScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        animalInventoryScreenHandler = handler;
    }

    @Override
    protected void init() {
        super.init();
        stealButton = ButtonWidget.builder(Text.literal("steal"),button -> steal()).size(100,20).position(10,10).build();
        this.addDrawableChild(stealButton);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(path, x, y,  0, 0, backgroundWidth, backgroundHeight);
        drawMouseoverTooltip(context,mouseX,mouseY);
    }
    public void steal(){
        if(handler.getEntity() != null) ClientPlayNetworking.send(new StealInventoryPacket(handler.getEntity().getId()));
    }
}
