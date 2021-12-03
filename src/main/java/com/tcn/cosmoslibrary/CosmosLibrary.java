package com.tcn.cosmoslibrary;

import com.tcn.cosmoslibrary.management.CosmosConsoleManager;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmlserverevents.FMLServerAboutToStartEvent;
import net.minecraftforge.fmlserverevents.FMLServerStartedEvent;
import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import net.minecraftforge.fmlserverevents.FMLServerStoppingEvent;

/**
 * @author TheRealZeher
 */
@Mod("cosmoslibrary")
public final class CosmosLibrary {

	public static final String MOD_ID = "cosmoslibrary";
	public static final CosmosConsoleManager CONSOLE = new CosmosConsoleManager(CosmosLibrary.MOD_ID, false, true);

    public CosmosLibrary() {
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
    }
	
	public void commonSetup(FMLCommonSetupEvent event){
		CONSOLE.startup("[FMLCommonSetupEvent] PreInit...");
	}
	
	@SubscribeEvent
    public void onServerAboutToStart(FMLServerAboutToStartEvent event) {
		CONSOLE.startup("[FMLServerAboutToStartEvent] Server about to start...");
    }
	
	@SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
		CONSOLE.startup("[FMLServerStartingEvent] Server starting...");
	}
	
	@SubscribeEvent
    public void onServerStarted(FMLServerStartedEvent event) {
		CONSOLE.startup("[FMLServerStartedEvent] Server started...");
    }
	
	@SubscribeEvent
    public void onServerStopping(FMLServerStoppingEvent event) {
		CONSOLE.startup("[FMLServerStoppingEvent] Server stopping...");
    }
}