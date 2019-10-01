package com.zeher.zeherlib.machine;

public interface IMachine {
	
	public int stored = 0;
	public int capacity = 0;
	public int input_rate = 0;
	public int cook_speed = 0;
	//public int cook_time = 0;

	public int getStored();
	public int getCapacity();
	public int getInputRate();
	public int getCookSpeed();
	//public int getCookTime();
	
	public boolean hasStored();
	public void addStored(int add);
	
	public int getCookTime(int value);

}
