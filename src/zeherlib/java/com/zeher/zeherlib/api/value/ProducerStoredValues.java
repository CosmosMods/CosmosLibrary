package com.zeher.zeherlib.api.value;

public class ProducerStoredValues {

	public static int poweredStored(int i) {
		if (i == 0) {
			return 50000;
		} else if (i == 1) {
			return 54000;
		} else if (i == 2) {
			return 58000;
		} else if (i == 3) {
			return 64000;
		} else if (i == 4) {
			return 68000;
		} else {
			return 50000;
		}
	}

	public static int energizedStored(int i) {
		if (i == 0) {
			return 80000;
		} else if (i == 1) {
			return 100000;
		} else if (i == 2) {
			return 120000;
		} else if (i == 3) {
			return 140000;
		} else if (i == 4) {
			return 160000;
		} else {
			return 80000;
		}
	}
}
