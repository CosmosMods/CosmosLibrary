package com.zeher.trzcore.client.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TRZSlotFake extends Slot {

	public TRZSlotFake(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	
	public boolean canBeHovered() {
		return true;
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer playerIn)
    {
        return false;
    }
}
