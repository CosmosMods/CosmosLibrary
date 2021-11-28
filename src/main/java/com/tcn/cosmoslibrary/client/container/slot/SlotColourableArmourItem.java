package com.tcn.cosmoslibrary.client.container.slot;

import com.tcn.cosmoslibrary.common.item.CosmosColourableArmourItem;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotColourableArmourItem extends Slot {

	public int limit;

	public SlotColourableArmourItem(IInventory tile, int par3, int par4, int par5, int limit) {
		super(tile, par3, par4, par5);
		
		this.limit = limit;
	}

	@Override
	public boolean mayPlace(ItemStack par1ItemStack) {
		if (par1ItemStack != null) {
			Item item = par1ItemStack.getItem();
			
			if (item != null) {
				if (item instanceof CosmosColourableArmourItem) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		
		return false;
	}

	@Override
	public int getMaxStackSize() {
		return this.limit;
	}
}