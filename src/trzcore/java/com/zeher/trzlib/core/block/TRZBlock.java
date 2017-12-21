package com.zeher.trzlib.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class TRZBlock extends Block {

	public TRZBlock(String name, Material arg0, String tool, int harvest, int hardness, int resistance, CreativeTabs tab) {
		super(arg0);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		setHarvestLevel(tool, harvest);
		setHardness(hardness);
		setResistance(resistance);
	}

}
