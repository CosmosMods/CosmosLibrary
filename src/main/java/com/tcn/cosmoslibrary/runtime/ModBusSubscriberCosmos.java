package com.tcn.cosmoslibrary.runtime;

import com.tcn.cosmoslibrary.CosmosLibrary;
import com.tcn.cosmoslibrary.common.crafting.CosmosShieldDecorationRecipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = CosmosLibrary.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModBusSubscriberCosmos {

	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CosmosLibrary.MOD_ID);

	public static final RegistryObject<RecipeSerializer<CosmosShieldDecorationRecipe>> SHIELD_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_special_shielddecoration", () -> new SimpleRecipeSerializer<>(CosmosShieldDecorationRecipe::new));

	public static void register(IEventBus bus) {
		RECIPE_SERIALIZERS.register(bus);
	}
}