package com.zeher.trzlib.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;

public class TRZTileEntityMachine extends TileEntityLockable implements TRZIMachine {

	public int stored;
	public int capacity;
	public int input_rate;

	public int getStored() {
		return this.stored;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public int getInputRate() {
		return this.input_rate;
	}
	
	public boolean hasStored(){
		return stored > 0;
	}
	
	public void addStored(int add){
		this.stored += add;
	}

	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return null;
	}

	@Override
	public String getGuiID() {
		return null;
	}

}
