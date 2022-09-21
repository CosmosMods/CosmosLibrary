package com.tcn.cosmoslibrary.common.runtime;

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
import net.minecraftforge.client.event.ModelEvent;

@SuppressWarnings({ "removal", "deprecation" })
public class CosmosRuntimeHelper {
	
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
	public static void registerSpecialModels(ModelEvent.RegisterAdditional eventIn, String modId, String... modelPaths) {
		for (String modelPath : modelPaths) {
			eventIn.register(new ResourceLocation(modId, modelPath));
		}
	}
}