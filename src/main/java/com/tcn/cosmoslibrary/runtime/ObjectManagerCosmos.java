package com.tcn.cosmoslibrary.runtime;

import com.tcn.cosmoslibrary.common.crafting.CosmosShieldDecorationRecipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ObjectHolder;

public class ObjectManagerCosmos {

	@ObjectHolder(registryName = "minecraft:recipe_serializer", value = "cosmoslibrary:crafting_special_shielddecoration")
	public static final RecipeSerializer<CosmosShieldDecorationRecipe> crafting_special_shielddecoration = null;
	
}
