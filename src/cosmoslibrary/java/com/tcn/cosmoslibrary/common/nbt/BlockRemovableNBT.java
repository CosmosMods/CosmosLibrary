package com.tcn.cosmoslibrary.common.nbt;

import com.tcn.cosmoslibrary.common.block.CosmosBlock;
import com.tcn.cosmoslibrary.common.util.CompatUtil;
import com.tcn.cosmoslibrary.common.util.CosmosUtil;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

/**
 * Class used to implement shift-right click removal with a wrench. 
 * NBT supported for {@link IInventory} and {@link IEnergyHandler}
 * @author TheRealZeher
 *
 */
public class BlockRemovableNBT extends CosmosBlock {
	
	public BlockRemovableNBT(Block.Properties builder) {
		super(builder);
	}
	
	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		player.swing(Hand.MAIN_HAND);
		
		worldIn.sendBlockUpdated(pos, state, state, 3);
		
		if (CosmosUtil.holdingWrench(player) && player.isShiftKeyDown() && !worldIn.isClientSide) {
			CompatUtil.generateStack(worldIn, pos);
			
			return ActionResultType.SUCCESS;
		}	
		return ActionResultType.FAIL;
	}
	
}