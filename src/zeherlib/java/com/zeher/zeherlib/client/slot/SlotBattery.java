package com.zeher.zeherlib.client.slot;

import com.zeher.zeherlib.storage.item.ItemStorage;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBattery extends Slot {

	public SlotBattery(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}


	public boolean isItemValid(ItemStack stack) {
		if (stack.getItem() instanceof ItemStorage) {
			return true;
		}
		
		return false;
	}
}
