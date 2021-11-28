package com.tcn.cosmoslibrary.common.block;

import com.tcn.cosmoslibrary.common.util.CompatUtil;
import com.tcn.cosmoslibrary.common.util.CosmosUtil;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class BlockRemovable extends CosmosBlock {

	public BlockRemovable(Block.Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		player.swing(Hand.MAIN_HAND);
		
		worldIn.sendBlockUpdated(pos, state, state, 3);
		//TileEntity tile = worldIn.getTileEntity(pos);
		
		if (CosmosUtil.holdingWrench(player) && player.isShiftKeyDown() && !worldIn.isClientSide) {
			if (!worldIn.isClientSide) {
				CompatUtil.spawnStack(CompatUtil.generateItemStackFromTile(this), worldIn, pos.getX(), pos.getY(), pos.getZ(), 0);
				worldIn.removeBlock(pos, false);
				return ActionResultType.SUCCESS;
			}
			
			return ActionResultType.SUCCESS;
		}	
		return ActionResultType.FAIL;
	}
}