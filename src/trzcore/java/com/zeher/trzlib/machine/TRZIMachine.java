package com.zeher.trzlib.machine;

public interface TRZIMachine {
	
	public int stored = 0;
	public int capacity = 0;
	public int input_rate = 0;

	public int getStored();
	public int getCapacity();
	public int getInputRate();
	
	public boolean hasStored();
	public void addStored(int add);

}
