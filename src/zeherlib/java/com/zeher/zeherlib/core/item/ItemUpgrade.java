package com.zeher.zeherlib.core.item;

import net.minecraft.creativetab.CreativeTabs;

public class ItemUpgrade extends ItemBase {
	
	public ItemUpgrade(String name, CreativeTabs tab, int maxStackSize){
		super(name, tab, maxStackSize);
	}
	
	public ItemUpgrade(String name, CreativeTabs tab){
		super(name, tab);
	}
}
