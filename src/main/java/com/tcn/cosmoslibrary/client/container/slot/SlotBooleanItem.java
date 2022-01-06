package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotBooleanItem extends Slot {

	public int limit;
	public boolean mayPlace;
	public boolean mayPickup;

	public SlotBooleanItem(Container containerIn, int index, int xPos, int yPos, boolean mayPlace, int limit) {
		super(containerIn, index, xPos, yPos);
		
		this.mayPlace = mayPlace;
		this.mayPickup = true;
		
		this.limit = limit;
	}
	
	public SlotBooleanItem(Container containerIn, int index, int xPos, int yPos, boolean mayPlace, boolean mayPickup, int limit) {
		super(containerIn, index, xPos, yPos);

		this.mayPlace = mayPlace;
		this.mayPickup = mayPickup;
		
		this.limit = limit;
	}

	@Override
	public boolean mayPlace(ItemStack par1ItemStack) {
		return this.mayPlace;
	}

	@Override
	public boolean mayPickup(Player playerIn) {
		return this.mayPickup;
	}

	@Override
	public int getMaxStackSize() {
		return this.limit;
	}
}