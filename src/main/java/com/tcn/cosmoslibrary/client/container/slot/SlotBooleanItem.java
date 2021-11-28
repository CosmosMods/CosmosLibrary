package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotBooleanItem extends Slot {

	public int limit;
	public boolean mayPlace;
	public boolean mayPickup;

	public SlotBooleanItem(IInventory tile, int index, int xPos, int yPos, boolean mayPlace, int limit) {
		super(tile, index, xPos, yPos);
		
		this.mayPlace = mayPlace;
		this.mayPickup = true;
		
		this.limit = limit;
	}
	
	public SlotBooleanItem(IInventory tile, int index, int xPos, int yPos, boolean mayPlace, boolean mayPickup, int limit) {
		super(tile, index, xPos, yPos);

		this.mayPlace = mayPlace;
		this.mayPickup = mayPickup;
		
		this.limit = limit;
	}

	@Override
	public boolean mayPlace(ItemStack par1ItemStack) {
		return this.mayPlace;
	}

	@Override
	public boolean mayPickup(PlayerEntity playerIn) {
		return this.mayPickup;
	}

	@Override
	public int getMaxStackSize() {
		return this.limit;
	}
}