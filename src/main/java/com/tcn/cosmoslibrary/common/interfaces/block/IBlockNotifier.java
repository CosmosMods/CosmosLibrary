package com.tcn.cosmoslibrary.common.interfaces.block;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * An interface to hand-over functions from a {@link Block} to a
 * {@link TileEntity}.
 */
public interface IBlockNotifier {

	public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player);

	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack);

	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving);

	public void onPlace(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving);
}