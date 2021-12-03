package com.tcn.cosmoslibrary.actual;

import com.google.common.base.Preconditions;
import com.tcn.cosmoslibrary.CosmosLibrary;
import com.tcn.cosmoslibrary.common.item.CosmosItemTool;
import com.tcn.cosmoslibrary.energy.interfaces.ICosmosEnergyItem;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mod.EventBusSubscriber(modid = CosmosLibrary.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModBusSubscriberCosmos {
	
	public static final Item COSMOS_WRENCH = new CosmosItemTool(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_TOOLS));
	
	@SubscribeEvent
	public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
		//event.getRegistry().registerAll(setupString("cosmos_wrench", COSMOS_WRENCH));
	}
	
	public static <T extends IForgeRegistryEntry<T>> T setupString(final String name, final T entry) {
		return setupLow(entry, new ResourceLocation(CosmosLibrary.MOD_ID, name));
	}
	
	public static <T extends IForgeRegistryEntry<T>> T setupResource(final ResourceLocation name, final T entry) {
		return setupLow(entry, name);
	}

	public static <T extends IForgeRegistryEntry<T>> T setupLow(final T entry, final ResourceLocation registryName) {
		entry.setRegistryName(registryName);
		CosmosLibrary.CONSOLE.debug("Object Registered: " + registryName);
		return entry;
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onModelRegistryEvent(ModelRegistryEvent event) {
		for (int i = 0; i < 14; i++) {
			ModelLoader.addSpecialModel(new ResourceLocation(CosmosLibrary.MOD_ID, "item/energy_bar_" + i));
		}
		
		for (final Item item : ForgeRegistries.ITEMS.getValues()) {
			final ResourceLocation blockRegistryName = item.getRegistryName();
			Preconditions.checkNotNull(blockRegistryName, "Registry Name of Block \"" + item + "\" of class \"" + item.getClass().getName() + "\"is null! This is not allowed!");

			if (item instanceof ICosmosEnergyItem) {
				ResourceLocation registryName = item.getRegistryName();
				String namespace = registryName.getNamespace();
				String path = registryName.getPath();
				
				String modelPath = "item/" + path + "_item";
				
				ModelLoader.addSpecialModel(new ResourceLocation(namespace, modelPath));
				CosmosLibrary.CONSOLE.info("Special Model Registered [" + item + ", " + modelPath + " ]");
			}
		}

		CosmosLibrary.CONSOLE.info("Model Registry complete...");
	}
}
