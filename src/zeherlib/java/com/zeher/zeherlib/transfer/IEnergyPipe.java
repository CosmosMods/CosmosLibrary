package com.zeher.zeherlib.transfer;

import com.zeher.zeherlib.api.connection.ConnectionType;
import com.zeher.zeherlib.api.connection.EnumSide;

import net.minecraft.util.EnumFacing;

public interface IEnergyPipe {
	
	public int stored = 0;
	public int capacity = 0;
	public int transfer_rate = 0;
	
	public EnumFacing last_from = null;
	
	/**
	 * The order is D-U-N-S-W-E.
	 */
	
	public int getStored();
	public int getCapacity();
	public int getTransferRate();
	
	public void addStored(int add);
	public void addStored(int add, EnumFacing side_from);
	
	public void minusStored(int value);
	
	public EnumSide getSide(EnumFacing facing);
	
	public void setSide(EnumFacing facing, EnumSide value);
	
	public void cycleside(EnumFacing facing);
	
	public ConnectionType.PIPE.ENERGY getPipeConnectionState(EnumFacing side);
	
	public EnumFacing getLastFrom();
	
	public EnumSide[] getSideArray();
	public void setSideArray(EnumSide[] array_from);
}
