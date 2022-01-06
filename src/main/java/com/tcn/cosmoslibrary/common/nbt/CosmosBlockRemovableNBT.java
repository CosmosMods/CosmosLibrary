package com.tcn.cosmoslibrary.common.nbt;

import javax.annotation.Nullable;

import com.tcn.cosmoslibrary.common.block.CosmosBlock;
import com.tcn.cosmoslibrary.common.lib.CompatHelper;
import com.tcn.cosmoslibrary.common.util.CosmosUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
			CompatHelper.generateStack(worldIn, pos);
			
			return InteractionResult.SUCCESS;
		}	
		return InteractionResult.FAIL;
	}
	
	@SuppressWarnings("unchecked")
	@Nullable
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
		return p_152134_ == p_152133_ ? (BlockEntityTicker<A>) p_152135_ : null;
	}
}