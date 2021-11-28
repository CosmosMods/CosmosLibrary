package com.tcn.cosmoslibrary.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CosmosItemEffect extends CosmosItem {

	public CosmosItemEffect(Item.Properties prop) {
		super(prop);
	}
	
	@Override
	public boolean isFoil(ItemStack itemStack) {
		return true;	
	}
}