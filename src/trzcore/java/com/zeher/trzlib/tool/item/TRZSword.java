package com.zeher.trzlib.tool.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TRZSword extends ItemSword {
	public TRZSword(ToolMaterial par2EnumToolMaterial2, CreativeTabs tab, String name) {
		super(par2EnumToolMaterial2);
		this.setCreativeTab(tab);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
	}
	
	 @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
}
