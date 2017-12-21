package com.zeher.trzlib.api.interfaces;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface ITileProvider extends ITileEntityProvider {

	public TileEntity createNewTileEntity(World worldIn, int meta);
    
}
