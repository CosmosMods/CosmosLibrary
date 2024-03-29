package com.tcn.cosmoslibrary.common.lib;

import net.minecraft.core.BlockPos;

public class MathHelper {

	public static BlockPos addBlockPos(BlockPos pos1, BlockPos pos2) {
		return new BlockPos(pos1.getX() + pos2.getX(), pos1.getY() + pos2.getY(), pos1.getZ() + pos2.getZ());
	}
	
	public static BlockPos addBlockPos(BlockPos pos1, BlockPos pos2, BlockPos pos3) {
		return new BlockPos(pos1.getX() + pos2.getX() + pos3.getX(), pos1.getY() + pos2.getY() + pos3.getY(), pos1.getZ() + pos2.getZ() + pos3.getZ());
	}
}