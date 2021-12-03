package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlotUpgrade extends Slot {

	public Item item;

	public SlotUpgrade(Container tile, int index, int x, int y, Item upgrade) {
		super(tile, index, x, y);

		item = upgrade;
	}

	@Override
	public boolean mayPlace(ItemStack par1ItemStack) {
		if (par1ItemStack != null) {
			Item item = par1ItemStack.getItem();

			return item != null && item.equals(this.item);
		}
		return false;
	}

	@Override
	public int getMaxStackSize() {
		return 4;
	}
}
