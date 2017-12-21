package com.zeher.trzlib.api.connect;

import com.zeher.trzlib.machine.TRZBlockFluidMachine;
import com.zeher.trzlib.machine.TRZBlockMachine;
import com.zeher.trzlib.production.TRZBlockFluidProducer;
import com.zeher.trzlib.production.TRZBlockProducer;
import com.zeher.trzlib.storage.TRZBlockFluidTank;
import com.zeher.trzlib.storage.TRZBlockItemStorage;
import com.zeher.trzlib.transfer.TRZBlockEnergyPipe;
import com.zeher.trzlib.transfer.TRZBlockFluidPipe;
import com.zeher.trzlib.transfer.TRZBlockItemPipe;

import net.minecraft.block.state.IBlockState;
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
