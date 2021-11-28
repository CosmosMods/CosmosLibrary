package com.tcn.cosmoslibrary.client.renderer.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.tcn.cosmoslibrary.CosmosLibrary;
import com.tcn.cosmoslibrary.energy.interfaces.ICosmosEnergyItem;

import net.minecraft.block.Block;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;

@OnlyIn(Dist.CLIENT)
public class CosmosEnergyItemItemStackRenderer extends ItemStackTileEntityRenderer {

	@Override
	@OnlyIn(Dist.CLIENT)
	public void renderByItem(ItemStack stackIn, ItemCameraTransforms.TransformType transformIn, MatrixStack matrixStack, IRenderTypeBuffer typeBuffer, int combinedLight, int combinedOverlay) {
		Item item = stackIn.getItem();
		ResourceLocation itemLocation = item.getRegistryName();
		Minecraft mc = Minecraft.getInstance();
		ItemRenderer renderer = mc.getItemRenderer();
		ModelManager manager = mc.getModelManager();
		
		if (item instanceof ICosmosEnergyItem) {
			ICosmosEnergyItem energyItem = (ICosmosEnergyItem) item;
			boolean flag = transformIn == ItemCameraTransforms.TransformType.GUI;

			matrixStack.pushPose();
			if (flag) {
				IBakedModel itemModel = manager.getModel(new ResourceLocation(itemLocation.getNamespace(), "item/" + itemLocation.getPath() + "_item"));
				
				int ref = Math.round((float) energyItem.getScaledEnergy(stackIn, 13.0F));
				IBakedModel barModelOverlay = manager.getModel(new ResourceLocation(CosmosLibrary.MOD_ID, "item/energy_bar_" + Integer.toString(ref)));

				matrixStack.translate(0.5F, 0.5F, 0.5F);
				this.render(renderer, stackIn, transformIn, false, matrixStack, typeBuffer, combinedLight, combinedOverlay, itemModel, true);

				matrixStack.pushPose();
				matrixStack.translate(0.0F, -0.1F, 0.1F);
				if (energyItem.doesDisplayEnergyBar(stackIn)) {
					this.render(renderer, stackIn, transformIn, false, matrixStack, typeBuffer, combinedLight, combinedOverlay, barModelOverlay, false);
				}
				matrixStack.popPose();
				
			} else {
				IBakedModel model = mc.getModelManager().getModel(new ResourceLocation(itemLocation.getNamespace(), "item/" + itemLocation.getPath() + "_item"));
				
				boolean flag1;
				
				if (transformIn != ItemCameraTransforms.TransformType.GUI && !transformIn.firstPerson() && stackIn.getItem() instanceof BlockItem) {
					Block block = ((BlockItem) stackIn.getItem()).getBlock();
					flag1 = !(block instanceof BreakableBlock) && !(block instanceof StainedGlassPaneBlock);
				} else {
					flag1 = true;
				}

				if (model.isLayered()) {
					ForgeHooksClient.drawItemLayered(renderer, model, stackIn, matrixStack, typeBuffer, combinedLight, combinedOverlay, flag1);
				} else {
					RenderType rendertype = RenderTypeLookup.getRenderType(stackIn, flag1);
					IVertexBuilder ivertexbuilder;

					if (flag1) {
						ivertexbuilder = ItemRenderer.getFoilBufferDirect(typeBuffer, rendertype, true, stackIn.hasFoil());
					} else {
						ivertexbuilder = ItemRenderer.getFoilBuffer(typeBuffer, rendertype, true, stackIn.hasFoil());
					}

					renderer.renderModelLists(model, stackIn, combinedLight, combinedOverlay, matrixStack, ivertexbuilder);
				}
				
				matrixStack.popPose();
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void render(ItemRenderer renderer, ItemStack stackIn, ItemCameraTransforms.TransformType transformIn, boolean p_229111_3_, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, IBakedModel modelIn, boolean renderFoil) {
		if (!stackIn.isEmpty()) {
			matrixStackIn.pushPose();
			boolean flag = transformIn == ItemCameraTransforms.TransformType.GUI || transformIn == ItemCameraTransforms.TransformType.GROUND || transformIn == ItemCameraTransforms.TransformType.FIXED;
			
			modelIn = ForgeHooksClient.handleCameraTransforms(matrixStackIn, modelIn, transformIn, p_229111_3_);
			matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
			
			if ((stackIn.getItem() != Items.TRIDENT || flag)) {
				boolean flag1;
				if (transformIn != ItemCameraTransforms.TransformType.GUI && !transformIn.firstPerson() && stackIn.getItem() instanceof BlockItem) {
					Block block = ((BlockItem) stackIn.getItem()).getBlock();
					flag1 = !(block instanceof BreakableBlock) && !(block instanceof StainedGlassPaneBlock);
				} else {
					flag1 = true;
				}
				
				if (modelIn.isLayered()) {
					ForgeHooksClient.drawItemLayered(renderer, modelIn, stackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, flag1);
				} else {
					RenderType rendertype = RenderTypeLookup.getRenderType(stackIn, flag1);
					IVertexBuilder ivertexbuilder;
					
					if (flag1) {
						if (renderFoil) {
							ivertexbuilder = ItemRenderer.getFoilBufferDirect(bufferIn, rendertype, true, stackIn.hasFoil());
						} else {
							ivertexbuilder = ItemRenderer.getFoilBufferDirect(bufferIn, rendertype, true, false);
						}
					} else {
						if (renderFoil) {
							ivertexbuilder = ItemRenderer.getFoilBuffer(bufferIn, rendertype, true, stackIn.hasFoil());
						} else {
							ivertexbuilder = ItemRenderer.getFoilBuffer(bufferIn, rendertype, true, false);
						}
					}
					
					
					renderer.renderModelLists(modelIn, stackIn, combinedLightIn, combinedOverlayIn, matrixStackIn, ivertexbuilder);
				}
			}

			matrixStackIn.popPose();
		}
	}
}
