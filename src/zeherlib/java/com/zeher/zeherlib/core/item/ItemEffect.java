package com.zeher.zeherlib.core.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemEffect extends ItemBase {

	public ItemEffect(String name, CreativeTabs tab, int stack) {
		super(name, tab, stack);
		this.setMaxStackSize(stack);
	}
	
	public ItemEffect(String name, CreativeTabs tab) {
		super(name, tab);
	}
	
	public boolean hasEffect(ItemStack itemStack)
	{
		return true;
		
	}

}
