package com.zeher.trzcore.core.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TRZItemEffect extends TRZItemBase {

	public TRZItemEffect(String name, CreativeTabs tab, int stack) {
		super(name, tab, stack);
		this.setMaxStackSize(stack);
	}
	
	public TRZItemEffect(String name, CreativeTabs tab) {
		super(name, tab);
	}
	
	public boolean hasEffect(ItemStack itemStack)
	{
		return true;
		
	}

}
