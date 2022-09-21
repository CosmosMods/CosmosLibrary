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
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.registries.DeferredRegister;
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

	@OnlyIn(Dist.CLIENT)
	public static void registerSpecialModels(String modId, String... modelPaths) {
		for (String modelPath : modelPaths) {
			ForgeModelBakery.addSpecialModel(new ResourceLocation(modId, modelPath));
		}
	}

	@Deprecated(since = "1.18.1")
	public static <T extends IForgeRegistryEntry<T>> T setupString(final String modId, final String name, final T entry) {
		return setupLow(entry, new ResourceLocation(modId, name));
	}

	@Deprecated(since = "1.18.1")
	public static <T extends IForgeRegistryEntry<T>> T setupResource(final ResourceLocation name, final T entry) {
		return setupLow(entry, name);
	}
	
	@Deprecated(since = "1.18.1")
	public static <T extends IForgeRegistryEntry<T>> T setupLow(final T entry, final ResourceLocation registryName) {
		entry.setRegistryName(registryName);
		return entry;
	}

	@Deprecated(since = "1.18.1")
	public static void registerBlock(DeferredRegister<Block> BLOCKS, String name, Block block) {
		BLOCKS.register(name, () -> block);
	}

	@Deprecated(since = "1.18.1")
	public static void registerBlock(DeferredRegister<Block> BLOCKS, DeferredRegister<Item> ITEMS, String name, Block block, CreativeModeTab group) {
		BLOCKS.register(name, () -> block);
		ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(group)));
	}

	@Deprecated(since = "1.18.1")
	public static void registerBlockAndItem(DeferredRegister<Block> REGISTER, DeferredRegister<Item> ITEMS, String name, Block block, BlockItem item) {
		REGISTER.register(name, () -> block);
		ITEMS.register(name, () -> item);
	}

	@Deprecated(since = "1.18.1")
	public static void registerItem(DeferredRegister<Item> ITEMS, String name, Item item) {
		ITEMS.register(name, () -> item);
	}

	@Deprecated(since = "1.18.1")
	public static void registerItem(DeferredRegister<Item> ITEMS, ResourceLocation name, BlockItem item) {
		ITEMS.register(name.getPath(), () -> item);
	}
}