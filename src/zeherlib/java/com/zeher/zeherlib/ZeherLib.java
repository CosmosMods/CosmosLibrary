package com.zeher.zeherlib;

import com.zeher.zeherlib.api.interfaces.IProxy;
import com.zeher.zeherlib.network.proxy.ProxyCommon;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = ZeherLib.MOD_ID, name = ZeherLib.MOD_NAME, version = ZeherLib.MOD_VERSION, dependencies = Reference.DEPENDENCY.FORGE_DEP)
public class ZeherLib {

	public static final String MOD_ID = "zeherlib";
	public static final String MOD_NAME = "ZeherLib";
	public static final String MOD_VERSION = "4.1.24";
	public static final String MOD_VERSION_MAX = "4.2.0";
	public static final String MOD_DEPENDENCIES = Reference.DEPENDENCY.FORGE_DEP;
	
	public static final String VERSION_GROUP = "required-after:" + MOD_ID + "@[" + MOD_VERSION + "," + MOD_VERSION_MAX + "];";
	
	
	@Instance(MOD_ID)
	public static ZeherLib INSTANCE;
	
	@SidedProxy(clientSide = "com.zeher.zeherlib.network.proxy.ProxyClient", serverSide = "com.zeher.zeherlib.network.proxy.ProxyCommon")
	public static ProxyCommon COMMON_PROXY;
	public static IProxy IPROXY;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		COMMON_PROXY.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		COMMON_PROXY.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		COMMON_PROXY.postInit();
	}
	
}
