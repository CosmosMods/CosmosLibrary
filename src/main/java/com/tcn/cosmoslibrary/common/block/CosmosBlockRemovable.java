package com.tcn.cosmoslibrary.common.block;

import com.tcn.cosmoslibrary.common.util.CosmosCompatUtil;
import com.tcn.cosmoslibrary.common.util.CosmosUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CosmosBlockRemovable extends CosmosBlock {

	public CosmosBlockRemovable(Block.Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		player.swing(InteractionHand.MAIN_HAND);
		
		worldIn.sendBlockUpdated(pos, state, state, 3);
		//TileEntity tile = worldIn.getTileEntity(pos);
		
		if (CosmosUtil.holdingWrench(player) && player.isShiftKeyDown() && !worldIn.isClientSide) {
			if (!worldIn.isClientSide) {
				CosmosCompatUtil.spawnStack(CosmosCompatUtil.generateItemStackFromTile(this), worldIn, pos.getX(), pos.getY(), pos.getZ(), 0);
				worldIn.removeBlock(pos, false);
				return InteractionResult.SUCCESS;
			}
			
			return InteractionResult.SUCCESS;
		}	
		return InteractionResult.FAIL;
	}
}