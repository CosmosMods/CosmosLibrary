package com.zeher.zeherlib.storage;

import com.zeher.zeherlib.api.connection.EnumSide;

import net.minecraft.util.EnumFacing;

public interface IStorage {
	
	public int stored = 0;
	public int capacity = 0;
	public int input_rate = 0;
	public int output_rate = 0;

	public EnumSide[] side_array = new EnumSide[] { EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO };

	public int getStored();
	public int getCapacity();
	
	public int getInputRate();
	public int getOutputRate();
	
	public boolean hasStored();
	public void addStored(int add);

	public EnumSide getSide(EnumFacing facing);
	
	public void setSide(EnumFacing facing, EnumSide value);
	
	public void cycleside(EnumFacing facing);
	
	public void minusStored(int value);
	
	public EnumSide[] getSideArray();
	public void setSideArray(EnumSide[] array_from);
	
}
