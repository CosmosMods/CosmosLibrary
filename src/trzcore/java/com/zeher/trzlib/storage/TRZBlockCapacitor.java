package com.zeher.trzlib.storage;

import com.zeher.trzlib.core.block.TRZBlockContainer;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TRZBlockCapacitor extends TRZBlockContainer {
	
	public int power;

	public TRZBlockCapacitor(String name, Material material, String tool, int harvest, int hardness, int resistance, CreativeTabs tab) {
		super(tool, material, name, harvest, hardness, resistance, tab);
	}

	public TileEntity createNewTileEntity(World world, int arg1) {
		return null;
	}
}
