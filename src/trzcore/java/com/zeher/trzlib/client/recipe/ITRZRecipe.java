package com.zeher.trzlib.client.recipe;

import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public interface ITRZRecipe {
	
	boolean matches(List<ItemStack> stack, World worldIn);
	
	ItemStack getCraftingResult(List<ItemStack> stack);
	
	ItemStack getFocusStack(List<ItemStack> stack);
	
	Integer getProcessTime(List<ItemStack> stack);

	int getRecipeSize();

	ItemStack getRecipeOutput();

	List<ItemStack> getRecipeInput();

	NonNullList<ItemStack> getRemainingItems(List<ItemStack> inv);
	
}