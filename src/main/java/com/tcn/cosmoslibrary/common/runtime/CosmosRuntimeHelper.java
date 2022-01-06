package com.tcn.cosmoslibrary.common.runtime;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class CosmosRuntimeHelper {
	
	@OnlyIn(Dist.CLIENT)
	public static void registerKeyBindings(KeyMapping... bindings) {
		for (KeyMapping bind : bindings) {
			ClientRegistry.registerKeyBinding(bind);
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void setRenderLayers(RenderType renderType, Block... blocks) {
		for (Block block : blocks) {
			ItemBlockRenderTypes.setRenderLayer(block, renderType);
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void registerItemColours(ItemColor colour, ItemLike... items) {
		ItemColors itemColours = Minecraft.getInstance().getItemColors();
		
		for (ItemLike item : items) {
			itemColours.register(colour, item);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerBlockColours(BlockColor colour, Block... blocks) {
		BlockColors blockColours = Minecraft.getInstance().getBlockColors();
		
		for (Block block : blocks) {
			blockColours.register(colour, block);
		}
	}
	
	public static <T extends IForgeRegistryEntry<T>> T setupString(final String modId, final String name, final T entry) {
		return setupLow(entry, new ResourceLocation(modId, name));
	}
	
	public static <T extends IForgeRegistryEntry<T>> T setupResource(final ResourceLocation name, final T entry) {
		return setupLow(entry, name);
	}

	public static <T extends IForgeRegistryEntry<T>> T setupLow(final T entry, final ResourceLocation registryName) {
		entry.setRegistryName(registryName);
		return entry;
	}
}
