package com.tcn.cosmoslibrary.common.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class CosmosItemBypassInfo extends CosmosItemInfo {
	
	public CosmosItemBypassInfo(Item.Properties properties, String info, String shift_desc_one, String shift_desc_two) {
		super(properties, info, shift_desc_one, shift_desc_two);
	}
	
	public CosmosItemBypassInfo(Item.Properties properties, String info, String shift_desc_one, String shift_desc_two, int max_stack) {
		super(properties.stacksTo(max_stack), info, shift_desc_one, shift_desc_two);
	}
	
	@Override
	public boolean canAttackBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		return false;
	}
	
	@Override
	public boolean doesSneakBypassUse(ItemStack stack, IWorldReader world, BlockPos pos, PlayerEntity player) {
		return true;
	}
	
	@Override
	public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
		return ActionResultType.PASS;
	}
}