package com.tcn.cosmoslibrary.client.renderer.item;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.tcn.cosmoslibrary.client.renderer.model.CosmosShieldModel;
import com.tcn.cosmoslibrary.energy.item.CosmosEnergyShield;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("deprecation")
public class CosmosShieldBEWLR extends BlockEntityWithoutLevelRenderer {
	
	private final CosmosShieldModel shieldModel = new CosmosShieldModel();
	private Material normalMat, noPatternMat;
	
	public CosmosShieldBEWLR(ResourceLocation normalIn, ResourceLocation noPatternIn) {
		super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
		
		this.normalMat = new Material(TextureAtlas.LOCATION_BLOCKS, normalIn);
		this.noPatternMat = new Material(TextureAtlas.LOCATION_BLOCKS, noPatternIn);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void renderByItem(ItemStack stackIn, ItemTransforms.TransformType transformIn, PoseStack poseStack, MultiBufferSource typeBuffer, int combinedLight, int combinedOverlay) {
		Item item = stackIn.getItem();
		
		if (item instanceof CosmosEnergyShield) {
			boolean flag = BlockItem.getBlockEntityData(stackIn) != null;
			poseStack.pushPose();
			poseStack.scale(1.0F, -1.0F, -1.0F);
			Material material = flag ? this.normalMat : this.noPatternMat;
			
			VertexConsumer vertexconsumer = material.sprite().wrap(ItemRenderer.getFoilBufferDirect(typeBuffer, this.shieldModel.renderType(material.atlasLocation()), true, stackIn.hasFoil()));
			
			this.shieldModel.handle().render(poseStack, vertexconsumer, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
			if (flag) {
				List<Pair<BannerPattern, DyeColor>> list = BannerBlockEntity.createPatterns(ShieldItem.getColor(stackIn), BannerBlockEntity.getItemPatterns(stackIn));
				BannerRenderer.renderPatterns(poseStack, typeBuffer, combinedLight, combinedOverlay, this.shieldModel.plate(), material, false, list, stackIn.hasFoil());
			} else {
				this.shieldModel.plate().render(poseStack, vertexconsumer, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
			}
			poseStack.popPose();
		}
	}
}