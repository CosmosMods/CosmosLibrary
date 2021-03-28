package com.tcn.cosmoslibrary.client.entity.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
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

	public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
		ItemStack itemstack = p_225628_4_.getItemBySlot(EquipmentSlotType.CHEST);
		if (shouldRender(itemstack, p_225628_4_)) {
			ResourceLocation resourcelocation;
			if (p_225628_4_ instanceof AbstractClientPlayerEntity) {
				AbstractClientPlayerEntity abstractclientplayerentity = (AbstractClientPlayerEntity) p_225628_4_;
				if (abstractclientplayerentity.isElytraLoaded() && abstractclientplayerentity.getElytraTextureLocation() != null) {
					resourcelocation = abstractclientplayerentity.getElytraTextureLocation();
				} else if (abstractclientplayerentity.isCapeLoaded() && abstractclientplayerentity.getCloakTextureLocation() != null && abstractclientplayerentity.isModelPartShown(PlayerModelPart.CAPE)) {
					resourcelocation = abstractclientplayerentity.getCloakTextureLocation();
				} else {
					resourcelocation = getElytraTexture(itemstack, p_225628_4_);
				}
			} else {
				resourcelocation = getElytraTexture(itemstack, p_225628_4_);
			}

			p_225628_1_.pushPose();
			p_225628_1_.translate(0.0D, 0.0D, 0.125D);
			this.getParentModel().copyPropertiesTo(this.elytraModel);
			this.elytraModel.setupAnim(p_225628_4_, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_);
			IVertexBuilder ivertexbuilder = ItemRenderer.getArmorFoilBuffer(p_225628_2_, RenderType.armorCutoutNoCull(resourcelocation), false, itemstack.hasFoil());
			this.elytraModel.renderToBuffer(p_225628_1_, ivertexbuilder, p_225628_3_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			p_225628_1_.popPose();
		}
	}

	public boolean shouldRender(ItemStack stack, T entity) {
		return stack.getItem() instanceof CosmosElytraArmourItem;
	}

	public ResourceLocation getElytraTexture(ItemStack stack, T entity) {
		return TEXTURE_ELYTRA;
	}
}
