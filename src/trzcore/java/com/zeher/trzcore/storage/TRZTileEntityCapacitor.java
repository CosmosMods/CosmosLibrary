package com.zeher.trzcore.storage;

import net.minecraft.tileentity.TileEntity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TRZTileEntityCapacitor extends TileEntity implements TRZICapacitor {

	public int stored;
	public int capacity;
	public int input_rate;
	public int output_rate;
	
	public int up;
	public int down;
	public int north;
	public int south;
	public int east;
	public int west;

	@Override
	public int getStored() {
		return this.stored;
	}

	@Override
	public int getCapacity() {
		return this.capacity;
	}
	
	@Override
	public int getInputRate() {
		return this.input_rate;
	}

	@Override
	public int getOutputRate() {
		return this.output_rate;
	}
	
	public boolean hasStored(){
		return this.stored > 0;
	}
	
	@Override
	public void addStored(int add) {
		this.stored += add;
	}
	
	public int getSide(String str) {
		if (str == "up") {
			return this.up;
		}
		if (str == "down") {
			return this.down;
		}
		if (str == "north") {
			return this.north;
		}
		if (str == "south") {
			return this.south;
		}
		if (str == "east") {
			return this.east;
		}
		if (str == "west") {
			return this.west;
		}
		return 0;
	}

}
