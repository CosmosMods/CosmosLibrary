package com.zeher.zeherlib.tool.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;

public class ModSpade extends ItemSpade {
	
	public boolean has_effect;
	
	public ModSpade(ToolMaterial tool_material, CreativeTabs tab, String name, boolean effect) {
		super(tool_material);
		this.setCreativeTab(tab);
		this.setHarvestLevel("shovel", tool_material.getHarvestLevel());
		
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		
		this.has_effect = effect;
	}
	
	public boolean hasEffect(ItemStack itemStack) {
		return this.has_effect;
	}
}
