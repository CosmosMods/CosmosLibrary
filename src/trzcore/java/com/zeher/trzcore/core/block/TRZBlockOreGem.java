package com.zeher.trzcore.core.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TRZBlockOreGem extends TRZBlock {
	private Item itemDrop;
	private Item itemID;

	public TRZBlockOreGem(String name, Item item, String toolClass, int level, int hardness, CreativeTabs tab) {
		super(name, Material.ROCK, toolClass, level, hardness, 8, tab);
		this.setHardness(hardness);
		this.setCreativeTab(tab);
		this.itemDrop = item;
		this.itemID = item;
		this.setHarvestLevel(toolClass, level);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return this.itemID;
	}
}
