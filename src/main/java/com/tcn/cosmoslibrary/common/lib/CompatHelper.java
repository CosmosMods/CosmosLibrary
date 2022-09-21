package com.tcn.cosmoslibrary.common.lib;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Collection of useful utilities connected to AZRF.
 */
public class CompatHelper {
	
	/**
	 * Used to generate an {@link ItemStack} from a block to produce a {@link ItemBlock} with the required NBT data.
	 * @param {@link World} [given world the block is in]
	 * @param {@link BlockPos} [position of the given block]
	 */
	@SuppressWarnings({ "deprecation" })
	public static void generateStack(Level world, BlockPos pos) {
		BlockEntity tile = world.getBlockEntity(pos);
		BlockState state = world.getBlockState(pos);
		Block block = world.getBlockState(pos).getBlock();
		
		ItemStack stack = new ItemStack(block);
		stack.setTag(new CompoundTag());
		
		if (tile != null) {
			tile.saveToItem(stack);
		}
		
		block.spawnAfterBreak(state, (ServerLevel) world, pos, stack);
		spawnStack(stack, world, pos.getX(), pos.getY(), pos.getZ(), 0);
		world.removeBlock(pos, false);
	}
	
	/**
	 * Generates a blank itemstack when NBT data is not required.
	 * @param block [block to generate from]
	 * @return {@link ItemStack} [itemstack containing the block]
	 */
	public static ItemStack generateItemStackFromTile(Block block) {
		return new ItemStack(block);
	}
	
	public static void spawnStack(ItemStack itemStack, Level world, double d, double e, double f, int delayBeforePickup) {
		ItemEntity entityItem = new ItemEntity(world, d, e, f, itemStack);
		entityItem.setPickUpDelay(delayBeforePickup);

		world.addFreshEntity(entityItem);
	}

	/**
	 * Returns null, while claiming to never return null.
	 * Useful for constants with @ObjectHolder who's values are null at compile time, but not at runtime
	 *
	 * @return null
	 */
	@Nonnull
	// Get rid of "Returning null from Nonnull method" warnings
	public static <T> T _null() {
		return null;
	}
}