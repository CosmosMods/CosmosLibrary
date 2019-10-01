package com.zeher.zeherlib.api.value;

public class EnergyOutputValues {

	public static int generatorOutput(int i) {
		if (i == 1) {
			return 1000;
		} else if (i == 2) {
			return 10000;
		} else if (i == 3) {
			return 50000;
		} else if (i == 4) {
			return 250000;
		} else
			return 0;
	}

	public static int generatorItemOutput(int i) {
		if (i == 1) {
			return 1;
		} else if (i == 2) {
			return 10;
		} else if (i == 3) {
			return 50;
		} else if (i == 4) {
			return 25;
		} else
			return 0;
	}

	public static int generatorAdvOutput(int i) {
		if (i == 1) {
			return 500000;
		} else if (i == 2) {
			return 1000000;
		} else if (i == 3) {
			return 1250000;
		} else if (i == 4) {
			return 1500000;
		} else
			return 0;
	}

	public static int chargerOutput(int i) {
		if (i == 1) {
			return 1000;
		} else if (i == 2) {
			return 3000;
		} else if (i == 3) {
			return 8000;
		} else if (i == 4) {
			return 10000;
		} else
			return 0;
	}

	public static int chargerItemOutput(int i) {
		if (i == 1) {
			return 1;
		} else if (i == 2) {
			return 3;
		} else if (i == 3) {
			return 8;
		} else if (i == 4) {
			return 10;
		} else
			return 0;
	}
}
