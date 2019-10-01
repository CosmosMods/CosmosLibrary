package com.zeher.zeherlib.core.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemMaterial extends Item {
	
	public ItemMaterial(int stackSize, CreativeTabs tab, String tex){
		super();
		this.setMaxStackSize(stackSize);
		this.setCreativeTab(tab);
	}
}
