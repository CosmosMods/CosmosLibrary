package com.zeher.trzcore.production;

public interface TRZIProducer {
	
	public int stored = 0;
	public int capacity = 0;
	public int output_rate = 0;
	public int generation_rate = 0;
	
	public int getStored();
	public int getCapacity();
	public int getOutputRate();
	public int getGenerationRate();
	
	public boolean hasStored();

}
