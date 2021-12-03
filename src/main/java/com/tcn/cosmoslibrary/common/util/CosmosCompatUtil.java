package com.tcn.cosmoslibrary.common.util;

import javax.annotation.Nonnull;

import com.tcn.cosmoslibrary.common.interfaces.blockentity.IBlockEntitySided;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Collection of useful utilities connected to AZRF.
 */
public class CosmosCompatUtil {
	
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
		CompoundTag compound = new CompoundTag();
		stack.setTag(new CompoundTag());
		//ListNBT list = new ListNBT();
		
		if (tile != null) {
			if (tile instanceof Container) {
				if (!((Container) tile).isEmpty()) {
					int size = ((Container) tile).getContainerSize();
					
					NonNullList<ItemStack> list_ = NonNullList.<ItemStack>withSize(size, ItemStack.EMPTY);
					
					for (int i = 0; i < size; i++) {
						list_.set(i, ((Container) tile).getItem(i));
					}
					
					ContainerHelper.saveAllItems(compound, list_);
					compound.putInt("size", size);
				}
			}
		
			if (tile instanceof IBlockEntitySided) {
				CompoundTag compound_tag = new CompoundTag();
				compound.put("sides", compound_tag);
				
				for (Direction c : Direction.values()) {
					compound_tag.putInt("index_" + c.get3DDataValue(), ((IBlockEntitySided) tile).getSideArray()[c.get3DDataValue()].getIndex());
				}
			}
			/**
			if (tile instanceof IEnergyHandler) {
				compound.setInteger("energy", ((IEnergyHandler) tile).getEnergyStored(Direction.DOWN));
			} */
			
			stack.getTag().put("nbt_data", compound);
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