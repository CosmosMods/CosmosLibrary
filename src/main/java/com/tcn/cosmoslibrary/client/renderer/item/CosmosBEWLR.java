package com.tcn.cosmoslibrary.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tcn.cosmoslibrary.CosmosLibrary;
import com.tcn.cosmoslibrary.energy.interfaces.ICosmosEnergyItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;

@OnlyIn(Dist.CLIENT)
public class CosmosBEWLR extends BlockEntityWithoutLevelRenderer {
	
	public final static BlockEntityWithoutLevelRenderer INSTANCE = new CosmosBEWLR();

	public CosmosBEWLR() {
		super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void renderByItem(ItemStack stackIn, ItemTransforms.TransformType transformIn, PoseStack matrixStack, MultiBufferSource typeBuffer, int combinedLight, int combinedOverlay) {
		Item item = stackIn.getItem();
		ResourceLocation itemLocation = item.getRegistryName();
		Minecraft mc = Minecraft.getInstance();
		ItemRenderer renderer = mc.getItemRenderer();
		ModelManager manager = mc.getModelManager();
		
		if (item instanceof ICosmosEnergyItem) {
			ICosmosEnergyItem energyItem = (ICosmosEnergyItem) item;
			boolean flag = transformIn == ItemTransforms.TransformType.GUI;

			matrixStack.pushPose();
			if (flag) {
				BakedModel itemModel = manager.getModel(new ResourceLocation(itemLocation.getNamespace(), "item/" + itemLocation.getPath() + "_item"));
				
				int ref = Math.round((float) energyItem.getScaledEnergy(stackIn, 13.0F));
				BakedModel barModelOverlay = manager.getModel(new ResourceLocation(CosmosLibrary.MOD_ID, "item/energy_bar_" + Integer.toString(ref)));

				matrixStack.translate(0.5F, 0.5F, 0.5F);
				this.render(renderer, stackIn, transformIn, false, matrixStack, typeBuffer, combinedLight, combinedOverlay, itemModel, true);

				matrixStack.pushPose();
				matrixStack.translate(0.0F, -0.1F, 0.1F);
				if (energyItem.doesDisplayEnergyBar(stackIn)) {
					this.render(renderer, stackIn, transformIn, false, matrixStack, typeBuffer, combinedLight, combinedOverlay, barModelOverlay, false);
				}
				matrixStack.popPose();
				
			} else {
				BakedModel model = mc.getModelManager().getModel(new ResourceLocation(itemLocation.getNamespace(), "item/" + itemLocation.getPath() + "_item"));
				
				boolean flag1;
				
				if (transformIn != ItemTransforms.TransformType.GUI && !transformIn.firstPerson() && stackIn.getItem() instanceof BlockItem) {
					Block block = ((BlockItem) stackIn.getItem()).getBlock();
					flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
				} else {
					flag1 = true;
				}

				if (model.isLayered()) {
					ForgeHooksClient.drawItemLayered(renderer, model, stackIn, matrixStack, typeBuffer, combinedLight, combinedOverlay, flag1);
				} else {
					RenderType rendertype = ItemBlockRenderTypes.getRenderType(stackIn, flag1);
					VertexConsumer ivertexbuilder;

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
	public void render(ItemRenderer renderer, ItemStack stackIn, ItemTransforms.TransformType transformIn, boolean p_229111_3_, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, BakedModel modelIn, boolean renderFoil) {
		if (!stackIn.isEmpty()) {
			matrixStackIn.pushPose();
			boolean flag = transformIn == ItemTransforms.TransformType.GUI || transformIn == ItemTransforms.TransformType.GROUND || transformIn == ItemTransforms.TransformType.FIXED;
			
			modelIn = ForgeHooksClient.handleCameraTransforms(matrixStackIn, modelIn, transformIn, p_229111_3_);
			matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
			
			if ((stackIn.getItem() != Items.TRIDENT || flag)) {
				boolean flag1;
				if (transformIn != ItemTransforms.TransformType.GUI && !transformIn.firstPerson() && stackIn.getItem() instanceof BlockItem) {
					Block block = ((BlockItem) stackIn.getItem()).getBlock();
					flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
				} else {
					flag1 = true;
				}
				
				if (modelIn.isLayered()) {
					ForgeHooksClient.drawItemLayered(renderer, modelIn, stackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, flag1);
				} else {
					RenderType rendertype = ItemBlockRenderTypes.getRenderType(stackIn, flag1);
					VertexConsumer ivertexbuilder;
					
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
