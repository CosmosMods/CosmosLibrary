package com.tcn.cosmoslibrary;

import com.tcn.cosmoslibrary.common.runtime.CosmosConsoleManager;
import com.tcn.cosmoslibrary.runtime.ModBusSubscriberCosmos;
import com.tcn.cosmoslibrary.runtime.NetworkManagerCosmos;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author TheCosmicNebula_
 */
@Mod("cosmoslibrary")
public final class CosmosLibrary {

	public static final String MOD_ID = "cosmoslibrary";
	public static final String MOD_ID_WITH = "cosmoslibrary:";
	public static final CosmosConsoleManager CONSOLE = new CosmosConsoleManager(CosmosLibrary.MOD_ID, false, true);

    public CosmosLibrary() {
    	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    	
    	ModBusSubscriberCosmos.register(bus);
    	
    	bus.addListener(this::commonSetup);
    }
	
	public void commonSetup(FMLCommonSetupEvent event){
		NetworkManagerCosmos.registerPackets();

		CONSOLE.startup("CosmosLibrary Common Setup complete.");
	}
}