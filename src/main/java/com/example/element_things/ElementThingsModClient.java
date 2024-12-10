package com.example.element_things;

import com.example.element_things.client.RMSHud;
import com.example.element_things.client.ToolMessageHud;
import com.example.element_things.entity.entityRnederer.BulletRenderer;
import com.example.element_things.event.DeleteItemKey;
import com.example.element_things.event.KeyInputHandler;
import com.example.element_things.event.SoundCheckKey;
import com.example.element_things.event.ZoomKey;
import com.example.element_things.network.ClientPacket;
import com.example.element_things.particle.FireSweepAttackParticle;
import com.example.element_things.particle.ModParticles;
import com.example.element_things.screen.AnimalInventoryScreen;
import com.example.element_things.screen.StoveScreen;
import com.example.element_things.screenHandler.ModScreenHandler;
import com.example.element_things.util.LineRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ElementThingsModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
     //   HudRenderCallback.EVENT.register(new RhomboidHud());
        HudRenderCallback.EVENT.register(new ToolMessageHud());
        HudRenderCallback.EVENT.register(new RMSHud());
        ParticleFactoryRegistry.getInstance().register(ModParticles.FIRE_SWEEP, FireSweepAttackParticle.Factory::new);
        ClientPacket.init();
        KeyInputHandler.register();
        ZoomKey.register();
        DeleteItemKey.register();
        SoundCheckKey.register();
        EntityRendererRegistry.register(ElementThingsMod.BULLET_ENTITY_TYPE, BulletRenderer::new);
        WorldRenderEvents.LAST.register(LineRenderer::render);
        HandledScreens.register(ModScreenHandler.STOVE_SCREEN_HANDLER, StoveScreen::new);
        HandledScreens.register(ModScreenHandler.ANIMAL_INVENTORY_SCREEN_HANDLER, AnimalInventoryScreen::new);
    }
}
