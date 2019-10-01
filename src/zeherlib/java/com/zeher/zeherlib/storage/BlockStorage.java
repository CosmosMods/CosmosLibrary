package com.zeher.zeherlib.storage;

import com.zeher.zeherlib.core.block.ModBlockContainer;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockStorage extends ModBlockContainer {
	
	public int power;

	public BlockStorage(String name, Material material, String tool, int harvest, int hardness, int resistance, CreativeTabs tab) {
		super(tool, material, name, harvest, hardness, resistance, tab);
	}

	public TileEntity createNewTileEntity(World world, int arg1) {
		return null;
	}
}
