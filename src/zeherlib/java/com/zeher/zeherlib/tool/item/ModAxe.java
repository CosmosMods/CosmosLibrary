package com.zeher.zeherlib.tool.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ModAxe extends ItemAxe {
	
	public boolean has_effect;
	
	public ModAxe(ToolMaterial tool_material, CreativeTabs tab, String name, boolean effect) {
		super(tool_material, tool_material.getAttackDamage(), tool_material.getEfficiency());
		this.setCreativeTab(tab);
		this.setHarvestLevel("axe", tool_material.getHarvestLevel());
		
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		
		this.has_effect = effect;
	}
	
	public boolean hasEffect(ItemStack itemStack) {
		return this.has_effect;
	}
}
