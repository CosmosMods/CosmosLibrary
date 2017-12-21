package com.zeher.trzlib.tool.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;

public class TRZPickaxeEffect extends ItemPickaxe {
	public TRZPickaxeEffect(ToolMaterial par2EnumToolMaterial2, int harvestLvl, CreativeTabs tab, String name) {
		super(par2EnumToolMaterial2);
		this.setCreativeTab(tab);
		this.setHarvestLevel("pickaxe", harvestLvl);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
	}

	public boolean hasEffect(ItemStack itemStack) {
		return true;
	}
}
