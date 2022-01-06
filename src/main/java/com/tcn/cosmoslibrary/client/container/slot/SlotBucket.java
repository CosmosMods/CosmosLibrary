package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidUtil;

public class SlotBucket extends Slot {

	public SlotBucket(Container containerIn, int par3, int par4, int par5) {
		super(containerIn, par3, par4, par5);
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