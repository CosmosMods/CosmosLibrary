package com.zeher.trzlib;

import com.zeher.trzlib.api.TRZIProxy;
import com.zeher.trzlib.network.proxy.TRZCommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;


@Mod(modid = TRZLib.mod_id, name = TRZLib.mod_name, version = TRZLib.mod_version, dependencies = TRZDependencies.FORGE_DEP)
public class TRZLib {

	public static final String mod_id = "trzlib";
	public static final String mod_name = "TRZLib";
	public static final String mod_version = "2.0.5r";
	public static final String mod_version_max = "2.1.0r";
	public static final String mod_dependencies = TRZDependencies.FORGE_DEP;
	
	public static final String version_group = "required-after:" + mod_id + "@[" + mod_version + "," + mod_version_max + "];";

	@Instance(mod_id)
	public static TRZLib instance;
	
	@SidedProxy(clientSide = "com.zeher.trzlib.network.proxy.TRZClientProxy", serverSide = "com.zeher.trzlib.network.proxy.TRZCommonProxy")
	public static TRZCommonProxy common_proxy;
	public static TRZIProxy iproxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		common_proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		common_proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		common_proxy.postInit(event);
	}
	
	@EventHandler
	public void onServerAboutToStart(FMLServerAboutToStartEvent event) {
		common_proxy.serverAboutToStart(event);
	}
	
	@EventHandler
	public void onServerStarting(FMLServerStartingEvent event) {
		common_proxy.serverStarting(event);
	}
	
	@EventHandler
	public void onServerStarted(FMLServerStartedEvent event) {
		common_proxy.serverStarted(event);
	}
	
	@EventHandler
	public void onServerStopping(FMLServerStoppingEvent event) {
		common_proxy.serverStopping(event);
	}
}
