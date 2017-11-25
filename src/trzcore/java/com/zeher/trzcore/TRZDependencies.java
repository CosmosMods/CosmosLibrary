package com.zeher.trzcore;

public class TRZDependencies {
	
	private TRZDependencies() {}

	private static final String FORGE_BUILD = "2386";
	public static final String FORGE_REQ = "13.20.0." + FORGE_BUILD;
	public static final String FORGE_REQ_MAX = "13.21.0";
	
	public static final String FORGE_DEP = "required-after:" + "forge" +  "@[" + FORGE_REQ + "," + FORGE_REQ_MAX + "];";
	
	public static final String DOWN_URL = "";
	
}