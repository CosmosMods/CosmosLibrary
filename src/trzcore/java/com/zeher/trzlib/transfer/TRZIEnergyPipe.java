package com.zeher.trzlib.transfer;

import net.minecraft.util.EnumFacing;

public interface TRZIEnergyPipe {
	
	public int stored = 0;
	public int capacity = 0;
	public int transfer_rate = 0;
	
	public EnumFacing last_from = null;

	public int getStored();
	public int getCapacity();
	public int getTransferRate();
	
	public void addStored(int add);
	public void addStored(int add, EnumFacing side_from);
	
}
