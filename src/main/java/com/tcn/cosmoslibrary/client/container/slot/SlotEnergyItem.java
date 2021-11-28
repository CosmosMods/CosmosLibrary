package com.tcn.cosmoslibrary.client.container.slot;

import com.tcn.cosmoslibrary.energy.interfaces.ICosmosEnergyItem;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotEnergyItem extends Slot {
	
	public SlotEnergyItem(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}


	public boolean isItemValid(ItemStack stack) {
		if (stack.getItem() instanceof ICosmosEnergyItem) {
			return true;
		}
		
		return false;
	}
	
}
