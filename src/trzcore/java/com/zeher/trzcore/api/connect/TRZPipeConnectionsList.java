package com.zeher.trzcore.api.connect;

import com.zeher.trzcore.core.block.*;
import com.zeher.trzcore.machine.TRZBlockFluidMachine;
import com.zeher.trzcore.machine.TRZBlockMachine;
import com.zeher.trzcore.production.TRZBlockFluidProducer;
import com.zeher.trzcore.production.TRZBlockProducer;
import com.zeher.trzcore.storage.TRZBlockFluidTank;
import com.zeher.trzcore.storage.TRZBlockItemStorage;
import com.zeher.trzcore.transfer.TRZBlockEnergyPipe;
import com.zeher.trzcore.transfer.TRZBlockFluidPipe;
import com.zeher.trzcore.transfer.TRZBlockItemPipe;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TRZPipeConnectionsList {

	public static boolean getEnergyConnectionBlocks(EnumFacing side, IBlockState blockId, World world, BlockPos pos) {
		if (blockId.getBlock() instanceof TRZBlockMachine 
				|| blockId.getBlock() instanceof TRZBlockEnergyPipe
	    		|| blockId.getBlock() instanceof TRZBlockFluidMachine
	    		|| blockId.getBlock() instanceof TRZBlockFluidProducer
	    		|| blockId.getBlock() instanceof TRZBlockProducer) {
			return true;
		}
		return false;
	}

	public static boolean getEnergyBlockConnections(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		IBlockState blockId = worldIn.getBlockState(pos);
		if (blockId.getBlock() instanceof TRZBlockMachine 
				|| blockId.getBlock() instanceof TRZBlockEnergyPipe
	    		|| blockId.getBlock() instanceof TRZBlockFluidMachine
	    		|| blockId.getBlock() instanceof TRZBlockFluidProducer
	    		|| blockId.getBlock() instanceof TRZBlockProducer) {
			return true;
		}
		return false;
	}

	public static boolean getItemConnectionBlocks(EnumFacing side, IBlockState blockId, World world, BlockPos pos) {
		if (blockId.getBlock() instanceof TRZBlockItemPipe 
				|| blockId.getBlock() instanceof TRZBlockMachine
				|| blockId.getBlock() instanceof TRZBlockProducer
				|| blockId.getBlock() instanceof TRZBlockItemStorage) {
			return true;
		}
		return false;
	}

	public static boolean getItemBlockConnections(IBlockAccess par1IBlockAccess, BlockPos pos, EnumFacing side) {
		IBlockState blockId = par1IBlockAccess.getBlockState(pos);
		if (blockId.getBlock() instanceof TRZBlockItemPipe 
				|| blockId.getBlock() instanceof TRZBlockMachine
				|| blockId.getBlock() instanceof TRZBlockProducer
				|| blockId.getBlock() instanceof TRZBlockItemStorage) {
			return true;
		}
		return false;
	}

	public static boolean getFluidConnectionBlocks(EnumFacing side, IBlockState blockId, World world, BlockPos pos) {
		if (blockId.getBlock() instanceof TRZBlockFluidPipe 
				|| blockId.getBlock() instanceof TRZBlockFluidTank
				|| blockId.getBlock() instanceof TRZBlockFluidMachine
				|| blockId.getBlock() instanceof TRZBlockFluidProducer) {
			return true;
		}
		return false;
	}

	public static boolean getFluidBlockConnections(IBlockAccess par1IBlockAccess, BlockPos pos, EnumFacing side) {
		IBlockState blockId = par1IBlockAccess.getBlockState(pos);
		if (blockId.getBlock() instanceof TRZBlockFluidPipe 
				|| blockId.getBlock() instanceof TRZBlockFluidTank
				|| blockId.getBlock() instanceof TRZBlockFluidMachine
				|| blockId.getBlock() instanceof TRZBlockFluidProducer) {
			return true;
		}
		return false;
	}
}
