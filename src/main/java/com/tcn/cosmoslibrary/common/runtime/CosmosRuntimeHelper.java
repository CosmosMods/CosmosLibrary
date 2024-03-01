package com.tcn.cosmoslibrary.common.runtime;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;

public class CosmosRuntimeHelper {
	
	@Deprecated
	@OnlyIn(Dist.CLIENT)
	public static void setRenderLayers(RenderType renderType, Block... blocks) {
		for (Block block : blocks) {
			ItemBlockRenderTypes.setRenderLayer(block, renderType);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerItemColours(RegisterColorHandlersEvent.Item event, ItemColor colour, ItemLike... items) {
		for (ItemLike item : items) {
			event.register(colour, item);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerBlockColours(RegisterColorHandlersEvent.Block event, BlockColor colour, Block... blocks) {
		for (Block block : blocks) {
			event.register(colour, block);
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void registerSpecialModels(ModelEvent.RegisterAdditional eventIn, String modId, String... modelPaths) {
		for (String modelPath : modelPaths) {
			eventIn.register(new ResourceLocation(modId, modelPath));
		}
	}
}