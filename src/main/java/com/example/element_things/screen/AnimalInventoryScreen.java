package com.example.element_things.screen;

import com.example.element_things.ElementThingsMod;
import com.example.element_things.screenHandler.AnimalInventoryScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AnimalInventoryScreen extends HandledScreen<AnimalInventoryScreenHandler> {
    public static final Identifier path = Identifier.of(ElementThingsMod.MOD_ID,"textures/gui/animal_inventory.png");
    public AnimalInventoryScreen(AnimalInventoryScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(path, x, y,  0, 0, backgroundWidth, backgroundHeight);
    }
}
