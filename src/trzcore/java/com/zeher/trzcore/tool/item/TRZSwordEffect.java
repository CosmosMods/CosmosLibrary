package com.zeher.trzcore.tool.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;

public class TRZSwordEffect extends ItemSword {
	public TRZSwordEffect(ToolMaterial par2EnumToolMaterial2, CreativeTabs tab, String name) {
		super(par2EnumToolMaterial2);
		this.setCreativeTab(tab);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
	}

	public boolean hasEffect(ItemStack itemStack) {
		return true;
	}
}
