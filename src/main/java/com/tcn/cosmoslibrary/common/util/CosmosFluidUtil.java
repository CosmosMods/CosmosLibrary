package com.tcn.cosmoslibrary.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class CosmosFluidUtil {
	
	public static ItemStack useItemSafely(ItemStack stack) {
		if (stack.getCount() == 1) {
			if (stack.getItem().hasContainerItem(stack))
				return stack.getItem().getContainerItem(stack);
			else
				return null;
		} else {
			stack.split(1);
			return stack;
		}
	}

	public static void dropStackInWorld(Level world, BlockPos pos, ItemStack stack) {
		if (!world.isClientSide && world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
			float f = 0.7F;
			double d0 = (double) (world.random.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			double d1 = (double) (world.random.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			double d2 = (double) (world.random.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			ItemEntity entityitem = new ItemEntity(world, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, stack);
			entityitem.setPickUpDelay(10); 
			world.addFreshEntity(entityitem);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getTankStackFromData(Block block) {
		ItemStack stack = new ItemStack(Item.byBlock(block));
		CompoundTag tag = new CompoundTag();
		stack.setTag(tag);
		return stack;
	}
}