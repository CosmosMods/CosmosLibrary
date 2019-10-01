package com.zeher.zeherlib.tool.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;

public class ModPickaxe extends ItemPickaxe {
	
	public boolean has_effect;
	
	public ModPickaxe(ToolMaterial tool_material, CreativeTabs tab, String name, boolean effect) {
		super(tool_material);
		this.setCreativeTab(tab);
		this.setHarvestLevel("pickaxe", tool_material.getHarvestLevel());
		
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		
		this.has_effect = effect;
	}
	
	public boolean hasEffect(ItemStack itemStack) {
		return this.has_effect;
	}
}
