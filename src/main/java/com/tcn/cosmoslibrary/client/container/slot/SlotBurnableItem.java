package com.tcn.cosmoslibrary.client.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

public class SlotBurnableItem extends Slot {

	public SlotBurnableItem(Container containerIn, int indexIn, int xPos, int yPos) {
		super(containerIn, indexIn, xPos, yPos);
	}

	@Override
	public boolean mayPlace(ItemStack stackIn) {
		return ForgeHooks.getBurnTime(stackIn, null) > 0;
	}
}