package com.zeher.trzlib.core.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TRZItemBase extends Item{

	public TRZItemBase(String name, CreativeTabs tab, int maxStackSize){
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(maxStackSize);
		setCreativeTab(tab);
	}
	public TRZItemBase(String name, CreativeTabs tab){
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
	}
}
