package com.tcn.cosmoslibrary;

import com.tcn.cosmoslibrary.actual.NetworkManagerCosmos;
import com.tcn.cosmoslibrary.common.runtime.CosmosConsoleManager;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author TheCosmicNebula_
 */
@Mod("cosmoslibrary")
public final class CosmosLibrary {

	public static final String MOD_ID = "cosmoslibrary";
	public static final CosmosConsoleManager CONSOLE = new CosmosConsoleManager(CosmosLibrary.MOD_ID, false, true);

    public CosmosLibrary() {
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
    }
	
	public void commonSetup(FMLCommonSetupEvent event){
		NetworkManagerCosmos.registerPackets();

		CONSOLE.startup("CosmosLibrary Common Setup complete.");
	}
}