package com.tcn.cosmoslibrary.runtime;

import com.tcn.cosmoslibrary.CosmosLibrary;
import com.tcn.cosmoslibrary.runtime.network.ICosmosPacket;
import com.tcn.cosmoslibrary.runtime.network.PacketUIHelp;
import com.tcn.cosmoslibrary.runtime.network.PacketUILock;
import com.tcn.cosmoslibrary.runtime.network.PacketUIMode;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkManagerCosmos {

	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
		new ResourceLocation(CosmosLibrary.MOD_ID, "network"), 
		() -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals
	);
	
	public static void registerPackets() {
		INSTANCE.registerMessage(0, PacketUIMode.class, PacketUIMode::encode, PacketUIMode::new, PacketUIMode::handle);
		INSTANCE.registerMessage(1, PacketUIHelp.class, PacketUIHelp::encode, PacketUIHelp::new, PacketUIHelp::handle);
		INSTANCE.registerMessage(2, PacketUILock.class, PacketUILock::encode, PacketUILock::new, PacketUILock::handle);
		
		CosmosLibrary.CONSOLE.startup("Cosmos Network Setup complete.");
	}
	
	public static void sendToServer(ICosmosPacket message) {
        INSTANCE.sendToServer(message);
    }
}