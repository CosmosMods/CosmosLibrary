package com.zeher.zeherlib.storage.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemStorage extends Item {
	
	public static int maxPower;
	
	public ItemStorage(String name, int maxPower, CreativeTabs tab){
		super();
		this.maxPower = maxPower;
		this.setMaxDamage(maxPower);
		this.setMaxStackSize(1);
		this.setCreativeTab(tab);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
	}
}
