package com.zeher.zeherlib.api.connection;

public class ConnectionType {

	public static class PIPE {
		
		public enum ENERGY {
			NONE, SHORT, LONG, ALL, CABLE, SUPPORT, INPUT, OUTPUT, LONG_INPUT, LONG_OUTPUT;
		}
		
		public enum ITEM {
			NONE, SHORT, LONG, ALL, CABLE, SUPPORT;
		}
		
		public enum FLUID {
			NONE, SHORT, LONG, ALL, CABLE, SUPPORT;
		}
	}
}
