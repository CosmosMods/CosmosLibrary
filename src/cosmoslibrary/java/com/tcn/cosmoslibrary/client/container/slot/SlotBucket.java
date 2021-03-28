package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidUtil;

public class SlotBucket extends Slot {

	public SlotBucket(IInventory tile, int par3, int par4, int par5) {
		super(tile, par3, par4, par5);
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		if (stack != null) {
			if (FluidUtil.getFluidContained(stack).isPresent()) {
				return true;
			} else if (stack.getItem() instanceof BucketItem) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	@Override
	public int getMaxStackSize() {
		return 16;
	}
}