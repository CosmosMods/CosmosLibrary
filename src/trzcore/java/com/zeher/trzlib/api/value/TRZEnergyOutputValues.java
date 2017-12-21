package com.zeher.trzlib.api.value;

public class TRZEnergyOutputValues {

	public static int generatorOutput(int i)
	{
		if(i == 1){
			return 1000;
		}
		if(i == 2){
			return 10000;
		}
		if(i == 3){
			return 50000;
		}
		if(i == 4){
			return 250000;
		}
		else return 0;
	}
	
	public static int generatorItemOutput(int i){
		if(i == 1){
			return 1;
		}
		if(i == 2){
			return 10;
		}
		if(i == 3){
			return 50;
		}
		if(i == 4){
			return 25;
		}
		else return 0;
	}
	
	public static int generatorAdvOutput(int i)
	{
		if(i == 1){
			return 500000;
		}
		if(i == 2){
			return 1000000;
		}
		if(i == 3){
			return 1250000;
		}
		if(i == 4){
			return 1500000;
		}
		else return 0;
	}
	
	public static int chargerOutput(int i){
		if(i == 1){
			return 1000;
		}
		if(i == 2){
			return 3000;
		}
		if(i == 3){
			return 8000;
		}
		if(i == 4){
			return 10000;
		}
		else return 0;
	}
	
	public static int chargerItemOutput(int i){
		if(i == 1){
			return 1;
		}
		if(i == 2){
			return 3;
		}
		if(i == 3){
			return 8;
		}
		if(i == 4){
			return 10;
		}
		else return 0;
	}
}
