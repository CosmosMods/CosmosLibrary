package com.tcn.cosmoslibrary;

import com.tcn.cosmoslibrary.actual.CosmosConsoleManager;
import com.tcn.cosmoslibrary.actual.CosmosConsoleManager.LEVEL;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author TheRealZeher
 */
@Mod("cosmoslibrary")
public final class CosmosLibrary {

	public static final String MOD_ID = "cosmoslibrary";
    
    public CosmosLibrary() {
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
    }
	
	public void commonSetup(FMLCommonSetupEvent event){
		CosmosConsoleManager.message(LEVEL.STARTUP, "[FMLCommonSetupEvent] PreInit...");
	}
	
	@SubscribeEvent
    public void onServerAboutToStart(FMLServerAboutToStartEvent event) {
		CosmosConsoleManager.message(LEVEL.STARTUP, "[FMLServerAboutToStartEvent] Server about to start...");
    }
	
	@SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
		CosmosConsoleManager.message(LEVEL.STARTUP, "[FMLServerStartingEvent] Server starting...");
    }
	
	@SubscribeEvent
    public void onServerStarted(FMLServerStartedEvent event) {
		CosmosConsoleManager.message(LEVEL.STARTUP, "[FMLServerStartedEvent] Server started...");
    }
	
	@SubscribeEvent
    public void onServerStopping(FMLServerStoppingEvent event) {
		CosmosConsoleManager.message(LEVEL.SHUTDOWN, "[FMLServerStoppingEvent] Server stopping...");
    }
}