package com.zeher.trzcore;

import com.zeher.trzcore.api.TRZIProxy;
import com.zeher.trzcore.network.proxy.TRZCommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.SidedProxy;


@Mod(modid = TRZCore.mod_id, name = TRZCore.mod_name, version = TRZCore.mod_version, dependencies = TRZDependencies.FORGE_DEP)
public class TRZCore {

	public static final String mod_id = "trzcore";
	public static final String mod_name = "TRZ Core";
	public static final String mod_version = "1.5.1-mc-1.11.2-r";
	public static final String mod_version_max = "1.5.9-mc-1.11.2-r";
	public static final String mod_dependencies = TRZDependencies.FORGE_DEP;
	
	public static final String version_group = "required-after:" + mod_id + "@[" + mod_version + "," + mod_version_max + "];";

	@Instance(mod_id)
	public static TRZCore instance;
	
	@SidedProxy(clientSide = "com.zeher.trzcore.network.proxy.TRZClientProxy", serverSide = "com.zeher.trzcore.network.proxy.TRZCommonProxy")
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
