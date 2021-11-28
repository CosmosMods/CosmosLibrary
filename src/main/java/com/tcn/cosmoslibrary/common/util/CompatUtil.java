package com.tcn.cosmoslibrary.common.util;

import javax.annotation.Nonnull;

import com.tcn.cosmoslibrary.common.interfaces.tile.ISidedTile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * Collection of useful utilities connected to AZRF.
 */
public class CompatUtil {
	
	/**
	 * Used to generate an {@link ItemStack} from a block to produce a {@link ItemBlock} with the required NBT data.
	 * @param {@link World} [given world the block is in]
	 * @param {@link BlockPos} [position of the given block]
	 */
	@SuppressWarnings({ "deprecation" })
	public static void generateStack(World world, BlockPos pos) {
		TileEntity tile = world.getBlockEntity(pos);
		BlockState state = world.getBlockState(pos);
		Block block = world.getBlockState(pos).getBlock();
		
		ItemStack stack = new ItemStack(block);
		CompoundNBT compound = new CompoundNBT();
		stack.setTag(new CompoundNBT());
		//ListNBT list = new ListNBT();
		
		if (tile != null) {
			if (tile instanceof IInventory) {
				if (!((IInventory) tile).isEmpty()) {
					int size = ((IInventory) tile).getContainerSize();
					
					NonNullList<ItemStack> list_ = NonNullList.<ItemStack>withSize(size, ItemStack.EMPTY);
					
					for (int i = 0; i < size; i++) {
						list_.set(i, ((IInventory) tile).getItem(i));
					}
					
					ItemStackHelper.saveAllItems(compound, list_);
					compound.putInt("size", size);
				}
			}
		
			if (tile instanceof ISidedTile) {
				CompoundNBT compound_tag = new CompoundNBT();
				compound.put("sides", compound_tag);
				
				for (Direction c : Direction.values()) {
					compound_tag.putInt("index_" + c.get3DDataValue(), ((ISidedTile) tile).getSideArray()[c.get3DDataValue()].getIndex());
				}
			}
			/**
			if (tile instanceof IEnergyHandler) {
				compound.setInteger("energy", ((IEnergyHandler) tile).getEnergyStored(Direction.DOWN));
			} */
			
			stack.getTag().put("nbt_data", compound);
		}
		
		block.spawnAfterBreak(state, (ServerWorld) world, pos, stack);
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
	
	public static void spawnStack(ItemStack itemStack, World world, double d, double e, double f, int delayBeforePickup) {
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