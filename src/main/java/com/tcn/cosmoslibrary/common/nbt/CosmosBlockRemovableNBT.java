package com.tcn.cosmoslibrary.common.nbt;

import com.tcn.cosmoslibrary.common.block.CosmosBlock;
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

/**
 * Class used to implement shift-right click removal with a wrench. 
 * NBT supported for {@link IInventory} and {@link IEnergyHandler}
 * @author TheRealZeher
 *
 */
public class CosmosBlockRemovableNBT extends CosmosBlock {
	
	public CosmosBlockRemovableNBT(Block.Properties builder) {
		super(builder);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		player.swing(handIn);
		
		worldIn.sendBlockUpdated(pos, state, state, 3);
		
		if (CosmosUtil.holdingWrench(player) && player.isShiftKeyDown() && !worldIn.isClientSide) {
			CosmosCompatUtil.generateStack(worldIn, pos);
			
			return InteractionResult.SUCCESS;
		}	
		return InteractionResult.FAIL;
	}
	
}