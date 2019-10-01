package com.zeher.zeherlib.storage;

import com.zeher.zeherlib.core.block.ModBlock;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ModBlockFluidTank extends ModBlock implements ITileEntityProvider {

	public ModBlockFluidTank(String name, Material material, String tool, int harvest, int hardness, int resistance, CreativeTabs tab) {
		super(name, material, tool, harvest, hardness, resistance, tab);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}

}
