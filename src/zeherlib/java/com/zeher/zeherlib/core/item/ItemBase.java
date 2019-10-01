package com.zeher.zeherlib.core.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item {

	public ItemBase(String name, CreativeTabs tab, int maxStackSize){
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setMaxStackSize(maxStackSize);
		this.setCreativeTab(tab);
	}
	
	public ItemBase(String name, CreativeTabs tab){
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(tab);
	}
}
