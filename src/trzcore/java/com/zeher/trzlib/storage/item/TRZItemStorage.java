package com.zeher.trzlib.storage.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TRZItemStorage extends Item {
	
	public static int maxPower;
	
	public TRZItemStorage(String name, int maxPower, CreativeTabs tab){
		super();
		this.maxPower = maxPower;
		this.setMaxDamage(maxPower);
		this.setMaxStackSize(1);
		this.setCreativeTab(tab);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
	}
}
