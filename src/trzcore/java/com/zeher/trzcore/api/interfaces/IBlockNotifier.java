package com.zeher.trzcore.api.interfaces;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public interface IBlockNotifier {
	
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state);

	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack);
    
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor);

}
