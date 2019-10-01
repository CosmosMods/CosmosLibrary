package com.zeher.zeherlib.machine;

import com.zeher.zeherlib.core.block.ModBlockContainer;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMachine extends ModBlockContainer {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public int power;

	public BlockMachine(String name, Material material, String tool, int harvest, int hardness, int resistance, CreativeTabs tab) {
		super(tool, material, name, harvest, hardness, resistance, tab);
	}

	public TileEntity createNewTileEntity(World world, int arg1) {
		return null;
	}
}
