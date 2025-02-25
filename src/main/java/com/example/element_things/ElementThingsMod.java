package com.example.element_things;

import com.example.element_things.block.ModBlocks;
import com.example.element_things.component.ModComponent;
import com.example.element_things.config.ModConfig;
import com.example.element_things.effect.ModEffects;
import com.example.element_things.enchantment.Enchantments;
import com.example.element_things.entity.AmmoEntity;
import com.example.element_things.entity.BulletEntity;
import com.example.element_things.entity.ModBlockEntityType;
import com.example.element_things.entity.ModEntities;
import com.example.element_things.entity.custom.IceEntity;
import com.example.element_things.item.ModItems;
import com.example.element_things.item.tools.ModItemGroups;
import com.example.element_things.network.ServerPacket;
import com.example.element_things.particle.ModParticles;
import com.example.element_things.recipe.ModRecipes;
import com.example.element_things.screenHandler.ModScreenHandler;
import com.example.element_things.sound.ModSoundEvents;
import com.example.element_things.tag.ModItemTags;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElementThingsMod implements ModInitializer {
	public static final String MOD_ID = "element_things";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final EntityType<BulletEntity> BULLET_ENTITY_TYPE = EntityType.Builder.create(BulletEntity::new,SpawnGroup.MISC).dimensions(0.8f,0.8f).maxTrackingRange(4).alwaysUpdateVelocity(true).trackingTickInterval(5).eyeHeight(0.13F).trackingTickInterval(20).build();
	public static final EntityType<AmmoEntity> AMMO_ENTITY_ENTITY = EntityType.Builder.create(AmmoEntity::new,SpawnGroup.MISC).dimensions(0.5f,0.5f).alwaysUpdateVelocity(true).trackingTickInterval(20).build();
//	public static ModConfig config;
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
		ModBlocks.registerBlock();
		ModBlockEntityType.register_block_entity();
		ModItemTags.registerItemTags();
		Registry.register(Registries.ENTITY_TYPE, Identifier.of(ElementThingsMod.MOD_ID,"bullet"),BULLET_ENTITY_TYPE);
		ModScreenHandler.registerScreenHandler();
		Registry.register(Registries.ENTITY_TYPE,Identifier.of(ElementThingsMod.MOD_ID,"ammo"),AMMO_ENTITY_ENTITY);
		FabricDefaultAttributeRegistry.register(ModEntities.ICE_ENTITY, IceEntity.createArmorStandAttributes());
//		AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
//		config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
	}
}