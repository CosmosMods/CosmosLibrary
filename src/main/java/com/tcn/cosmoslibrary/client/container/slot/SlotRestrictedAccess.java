package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotRestrictedAccess extends Slot {

	public int stackSize;
	public boolean mayPlace;
	public boolean mayPickup;

	public SlotRestrictedAccess(IInventory inventoryIn, int indexIn, int positionX, int positionY, boolean mayPlace, boolean mayPickup) {
		super(inventoryIn, indexIn, positionX, positionY);
		this.stackSize = 64;
		this.mayPlace = mayPlace;
		this.mayPickup = mayPickup;
	}
	
	public SlotRestrictedAccess(IInventory inventoryIn, int indexIn, int positionX, int positionY, int stackSizeIn, boolean mayPlace, boolean mayPickup) {
		super(inventoryIn, indexIn, positionX, positionY);
		this.stackSize = stackSizeIn;
		this.mayPlace = mayPlace;
		this.mayPickup = mayPickup;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return this.mayPlace;
	}

	@Override
	public int getMaxStackSize() {
		return this.stackSize;
	}
	
	@Override
	public boolean mayPickup(PlayerEntity playerIn) {
		return this.mayPickup;
	}
}