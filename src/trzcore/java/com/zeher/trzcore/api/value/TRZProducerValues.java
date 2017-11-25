package com.zeher.trzcore.api.value;

public class TRZProducerValues {
	
	public TRZProducerValues(){}

	public static int poweredDrain() {
		return 40;
	}

	public static int energizedDrain() {
		return 120;
	}

	public static int poweredBasicStored(int i) {
		if(i == 0){
			return 12000;
		}
		if(i == 1){
			return 16000;
		}
		if(i == 2){
			return 20000;
		}
		if(i == 3){
			return 24000;
		}
		if(i == 4){
			return 28000;
		}
		
		return 12000;
	}

	public static int poweredAdvancedStored(int i) {
		if(i == 0){
			return 28000;
		}
		if(i == 1){
			return 32000;
		}
		if(i == 2){
			return 36000;
		}
		if(i == 3){
			return 40000;
		}
		if(i == 4){
			return 44000;
		}
		
		return 28000;
	}
	
	public static int poweredEnderStored(int i) {
		if(i == 0){
			return 50000;
		}
		if(i == 1){
			return 54000;
		}
		if(i == 2){
			return 58000;
		}
		if(i == 3){
			return 64000;
		}
		if(i == 4){
			return 68000;
		}
		
		return 50000;
	}
	
	public static int energizedBasicStored(int i) {
		if(i == 0){
			return 80000;
		}
		if(i == 1){
			return 100000;
		}
		if(i == 2){
			return 120000;
		}
		if(i == 3){
			return 140000;
		}
		if(i == 4){
			return 160000;
		}
		
		return 80000;
	}
	
	public static int energizedEnderStored(int i) {
		if(i == 0){
			return 320000;
		}
		if(i == 1){
			return 340000;
		}
		if(i == 2){
			return 360000;
		}
		if(i == 3){
			return 380000;
		}
		if(i == 4){
			return 400000;
		}
		
		return 320000;
	}
	
	public static int basicOutputRate(){
		return 60;
	}
	
	public static int enderOutputRate(){
		return 120;
	}
}
