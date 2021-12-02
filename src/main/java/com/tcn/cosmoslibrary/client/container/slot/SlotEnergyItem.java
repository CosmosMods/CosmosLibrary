package com.tcn.cosmoslibrary.client.container.slot;

import com.tcn.cosmoslibrary.energy.interfaces.ICosmosEnergyItem;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotEnergyItem extends Slot {
	
	public SlotEnergyItem(Container inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}


	public boolean isItemValid(ItemStack stack) {
		if (stack.getItem() instanceof ICosmosEnergyItem) {
			return true;
		}
		
		return false;
	}
	
}
