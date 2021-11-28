package com.tcn.cosmoslibrary.client.entity.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.tcn.cosmoslibrary.common.comp.CosmosColour;
import com.tcn.cosmoslibrary.common.item.CosmosElytraArmourItem;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.ElytraModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CosmosElytraLayerStand<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
	private static ResourceLocation TEXTURE_ELYTRA;
	private final ElytraModel<T> elytraModel = new ElytraModel<>();

	public CosmosElytraLayerStand(IEntityRenderer<T, M> rendererIn, ResourceLocation location) {
		super(rendererIn);

		TEXTURE_ELYTRA = location;
	}

	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stackIn = entityLivingBaseIn.getItemBySlot(EquipmentSlotType.CHEST);
		
		if (shouldRender(stackIn, entityLivingBaseIn)) {
			ResourceLocation resourcelocation;
			
			if (entityLivingBaseIn instanceof AbstractClientPlayerEntity) {
				AbstractClientPlayerEntity abstractclientplayerentity = (AbstractClientPlayerEntity) entityLivingBaseIn;
				if (abstractclientplayerentity.isElytraLoaded() && abstractclientplayerentity.getElytraTextureLocation() != null) {
					resourcelocation = abstractclientplayerentity.getElytraTextureLocation();
				} else if (abstractclientplayerentity.isCapeLoaded() && abstractclientplayerentity.getCloakTextureLocation() != null && abstractclientplayerentity.isModelPartShown(PlayerModelPart.CAPE)) {
					resourcelocation = abstractclientplayerentity.getCloakTextureLocation();
				} else {
					resourcelocation = getElytraTexture(stackIn, entityLivingBaseIn);
				}
			} else {
				resourcelocation = getElytraTexture(stackIn, entityLivingBaseIn);
			}

			float[] rgb = CosmosColour.rgbFloatArray(CosmosColour.LIGHT_GRAY);
			
			if (stackIn.hasTag()) {
				CompoundNBT stackTag = stackIn.getTag();
				
				if (stackTag.contains("nbt_data")) {
					CompoundNBT nbtData = stackTag.getCompound("nbt_data");
					
					if (nbtData.contains("wing_colour")) {
						int wingColour = nbtData.getInt("wing_colour");
						
						rgb = CosmosColour.rgbFloatArray(CosmosColour.fromIndex(wingColour));
					}
				}
			}

			matrixStackIn.pushPose();
			matrixStackIn.translate(0.0D, 0.0D, 0.125D);
			
			this.getParentModel().copyPropertiesTo(this.elytraModel);
			
			this.elytraModel.setupAnim(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			
			IVertexBuilder ivertexbuilder = ItemRenderer.getArmorFoilBuffer(bufferIn, RenderType.armorCutoutNoCull(resourcelocation), false, stackIn.hasFoil());
			
			this.elytraModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, rgb[0], rgb[1], rgb[2], 1.0F);
			
			matrixStackIn.popPose();
		}
	}

	public boolean shouldRender(ItemStack stack, T entity) {
		return stack.getItem() instanceof CosmosElytraArmourItem;
	}

	public ResourceLocation getElytraTexture(ItemStack stack, T entity) {
		return TEXTURE_ELYTRA;
	}
}
