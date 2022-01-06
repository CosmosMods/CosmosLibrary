package com.tcn.cosmoslibrary.common.lib;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ItemStackHelper {

	public static void applyColourToItemStack(ItemStack stackIn, int colour) {
		CompoundTag compound = stackIn.getOrCreateTag();
		
		CompoundTag colourData = new CompoundTag();
		
		colourData.putInt("intColour", colour);
		
		compound.put("colourData", colourData);
	}
	
	public static int getColourFromStack(ItemStack stackIn) {
		CompoundTag compound = stackIn.getOrCreateTag();
		
		if (compound.contains("colourData")) {
			CompoundTag colourData = compound.getCompound("colourData");
			
			return colourData.getInt("intColour");
		}
		
		return -1;
	}
}
