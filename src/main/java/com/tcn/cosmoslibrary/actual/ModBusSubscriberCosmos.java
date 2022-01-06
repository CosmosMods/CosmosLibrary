package com.tcn.cosmoslibrary.actual;

import com.tcn.cosmoslibrary.CosmosLibrary;
import com.tcn.cosmoslibrary.common.item.CosmosItemTool;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod.EventBusSubscriber(modid = CosmosLibrary.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModBusSubscriberCosmos {
	
	public static final Item COSMOS_WRENCH = new CosmosItemTool(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_TOOLS));
	
	@SubscribeEvent
	public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
		//event.getRegistry().registerAll(CosmosRuntimeHelper.setupString(CosmosLibrary.MOD_ID, "cosmos_wrench", COSMOS_WRENCH));
	}

}