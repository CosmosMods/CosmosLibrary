package com.zeher.trzcore.transfer;

import com.zeher.trzcore.core.block.TRZBlockContainer;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TRZBlockEnergyPipe extends TRZBlockContainer {

	protected TRZBlockEnergyPipe(String name, Material material, String tool, int harvest, int hardness, int resistance, CreativeTabs tab) {
		super(tool, material, name, harvest, hardness, resistance, tab);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		// TODO Auto-generated method stub
		return null;
	}

	public void sendEnergy(World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		
	}

}
