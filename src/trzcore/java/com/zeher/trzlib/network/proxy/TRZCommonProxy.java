package com.zeher.trzlib.network.proxy;

import com.zeher.trzlib.api.TRZIProxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

public class TRZCommonProxy implements TRZIProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {}

	@Override
	public void init(FMLInitializationEvent event) {}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}

	@Override
	public void serverStarting(FMLServerStartingEvent event) {}

	@Override
	public void serverStarted(FMLServerStartedEvent event) {}

	@Override
	public void serverStopping(FMLServerStoppingEvent event) {}

	@Override
	public void serverAboutToStart(FMLServerAboutToStartEvent event) {}

}
