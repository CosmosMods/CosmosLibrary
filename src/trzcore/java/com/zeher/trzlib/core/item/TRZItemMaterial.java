package com.zeher.trzlib.core.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TRZItemMaterial extends Item {
	
	public TRZItemMaterial(int stackSize, CreativeTabs tab, String tex){
		super();
		this.setMaxStackSize(stackSize);
		this.setCreativeTab(tab);
	}
}
