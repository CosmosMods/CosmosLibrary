package com.zeher.trzcore.storage;

public interface TRZICapacitor {
	
	public int stored = 0;
	public int capacity = 0;
	public int input_rate = 0;
	public int output_rate = 0;
	
	public int getStored();
	public int getCapacity();
	
	public int getInputRate();
	public int getOutputRate();
	
	public boolean hasStored();
	public void addStored(int add);
	
}
