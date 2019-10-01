package com.zeher.zeherlib.tool.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModSword extends ItemSword {
	
	public boolean has_effect;
	
	public ModSword(ToolMaterial par2EnumToolMaterial2, CreativeTabs tab, String name, boolean effect) {
		super(par2EnumToolMaterial2);
		this.setCreativeTab(tab);
		
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		
		this.has_effect = effect;
	}

	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}
	
	public boolean hasEffect(ItemStack itemStack) {
		return this.has_effect;
	}
}
