package com.example.element_things;

import com.example.element_things.component.ModComponent;
import com.example.element_things.effect.ModEffects;
import com.example.element_things.enchantment.Enchantments;
import com.example.element_things.item.ModItems;
import com.example.element_things.item.tools.ModItemGroups;
import com.example.element_things.network.ServerPacket;
import com.example.element_things.particle.ModParticles;
import com.example.element_things.recipe.ModRecipes;
import com.example.element_things.sound.ModSoundEvents;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElementThingsMod implements ModInitializer {
	public static final String MOD_ID = "element_things";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		Enchantments.register();
		LOGGER.info("Hello World!");
		ModComponent.register();
		ModParticles.init();
		ModSoundEvents.registerSoundEvents();
		ServerPacket.init();
		ModEffects.onInitialize();
		ModItemGroups.registerItemGroups();
		ModRecipes.registerForRecipe();
	}
}