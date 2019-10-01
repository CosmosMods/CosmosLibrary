package com.zeher.zeherlib.storage;

import com.zeher.zeherlib.core.block.ModBlockContainer;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockItemStorage extends ModBlockContainer {

	public BlockItemStorage(String name, Material material, String tool, int harvest, int hardness, int resistance, CreativeTabs tab) {
		super(name, material, tool, harvest, hardness, resistance, tab);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}

}
