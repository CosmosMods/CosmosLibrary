package com.zeher.trzlib.client.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TRZSlotNoAccess extends Slot {
	private EntityPlayer thePlayer;
	public int limit;

	public TRZSlotNoAccess(IInventory tile, int par3, int par4, int par5, int limit) {
		super(tile, par3, par4, par5);
		this.limit = limit;
	}

	public boolean isItemValid(ItemStack par1ItemStack) {
		return false;
	}
	
	public int getSlotStackLimit()
    {
        return this.limit;
    }
	
}
