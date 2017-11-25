package com.zeher.trzcore.client.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TRZSlotMachineUpgrade extends Slot {
	private EntityPlayer thePlayer;
	public Item item;

	public TRZSlotMachineUpgrade(IInventory tile, int par3, int par4, int par5, Item par6) {
		super(tile, par3, par4, par5);
		this.item = par6;
	}

	public boolean isItemValid(ItemStack par1ItemStack) {
		if (par1ItemStack != null) {
			Item item = par1ItemStack.getItem();
			return (item != null) && (item == this.item);
		}
		return false;
	}
	
	public int getSlotStackLimit()
    {
        return 4;
    }
}
