package com.tcn.cosmoslibrary.client.entity.layer;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.tcn.cosmoslibrary.common.comp.CosmosColour;
import com.tcn.cosmoslibrary.common.item.CosmosColourableArmourItem;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unused")
public class CosmosColourableArmourLayer<T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> extends LayerRenderer<T, M> {
	private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = Maps.newHashMap();
	private final A innerModel;
	private final A outerModel;

	public CosmosColourableArmourLayer(IEntityRenderer<T, M> entityRenderer, A p_i50936_2_, A p_i50936_3_) {
		super(entityRenderer);
		this.innerModel = p_i50936_2_;
		this.outerModel = p_i50936_3_;
	}

	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwingIn, float limbSwingAmountIn, float partialTicks, float ageInTicks, float netHeadYawIn, float headPitchIn) {
		this.renderArmorPiece(matrixStackIn, bufferIn, entityLivingBaseIn, EquipmentSlotType.CHEST, packedLightIn, this.getArmorModel(EquipmentSlotType.CHEST));
		this.renderArmorPiece(matrixStackIn, bufferIn, entityLivingBaseIn, EquipmentSlotType.LEGS, packedLightIn, this.getArmorModel(EquipmentSlotType.LEGS));
		this.renderArmorPiece(matrixStackIn, bufferIn, entityLivingBaseIn, EquipmentSlotType.FEET, packedLightIn, this.getArmorModel(EquipmentSlotType.FEET));
		this.renderArmorPiece(matrixStackIn, bufferIn, entityLivingBaseIn, EquipmentSlotType.HEAD, packedLightIn, this.getArmorModel(EquipmentSlotType.HEAD));
	}

	private void renderArmorPiece(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, T livingEntityIn, EquipmentSlotType slotTypeIn, int packedLightIn, A modelIn) {
		ItemStack stackIn = livingEntityIn.getItemBySlot(slotTypeIn);
		
		if (stackIn.getItem() instanceof CosmosColourableArmourItem) {
			CosmosColourableArmourItem armoritem = (CosmosColourableArmourItem) stackIn.getItem();

			if (armoritem.getSlot() == slotTypeIn) {
				modelIn = getArmorModelHook(livingEntityIn, stackIn, slotTypeIn, modelIn);
				this.getParentModel().copyPropertiesTo(modelIn);
				this.setPartVisibility(modelIn, slotTypeIn);
				boolean flag = this.usesInnerModel(slotTypeIn);

				boolean flag1 = stackIn.hasFoil();

				float[] rgb = CosmosColour.rgbFloatArray(CosmosColour.POCKET_PURPLE_LIGHT);
				
				if (stackIn.hasTag()) {
					CompoundNBT stackTag = stackIn.getTag();
					
					if (stackTag.contains("nbt_data")) {
						CompoundNBT nbtData = stackTag.getCompound("nbt_data");
						
						if (nbtData.contains("colour")) {
							int colour = nbtData.getInt("colour");
							
							float[] colArray = CosmosColour.rgbFloatArray(colour);
							
							this.renderModel(matrixStackIn, bufferIn, packedLightIn, flag1, modelIn, colArray[0], colArray[1], colArray[2], this.getArmorResource(livingEntityIn, stackIn, slotTypeIn, null, false));
						}
					} else {
						this.renderModel(matrixStackIn, bufferIn, packedLightIn, flag1, modelIn, rgb[0], rgb[1], rgb[2], this.getArmorResource(livingEntityIn, stackIn, slotTypeIn, null, false));
					}
				} else {
					this.renderModel(matrixStackIn, bufferIn, packedLightIn, flag1, modelIn, rgb[0], rgb[1], rgb[2], this.getArmorResource(livingEntityIn, stackIn, slotTypeIn, null, false));
				}

				this.renderModel(matrixStackIn, bufferIn, packedLightIn, flag1, modelIn, 1.0F, 1.0F, 1.0F, this.getArmorResource(livingEntityIn, stackIn, slotTypeIn, null, true));
			}
		}
	}

	@SuppressWarnings("incomplete-switch")
	protected void setPartVisibility(A p_188359_1_, EquipmentSlotType p_188359_2_) {
		p_188359_1_.setAllVisible(false);
		switch (p_188359_2_) {
		case HEAD:
			p_188359_1_.head.visible = true;
			p_188359_1_.hat.visible = true;
			break;
		case CHEST:
			p_188359_1_.body.visible = true;
			p_188359_1_.rightArm.visible = true;
			p_188359_1_.leftArm.visible = true;
			break;
		case LEGS:
			p_188359_1_.body.visible = true;
			p_188359_1_.rightLeg.visible = true;
			p_188359_1_.leftLeg.visible = true;
			break;
		case FEET:
			p_188359_1_.rightLeg.visible = true;
			p_188359_1_.leftLeg.visible = true;
		}

	}

	private void renderModel(MatrixStack p_241738_1_, IRenderTypeBuffer p_241738_2_, int p_241738_3_, boolean p_241738_5_, A p_241738_6_, float p_241738_8_, float p_241738_9_, float p_241738_10_, ResourceLocation armorResource) {
		IVertexBuilder ivertexbuilder = ItemRenderer.getArmorFoilBuffer(p_241738_2_, RenderType.armorCutoutNoCull(armorResource), false, p_241738_5_);
		p_241738_6_.renderToBuffer(p_241738_1_, ivertexbuilder, p_241738_3_, OverlayTexture.NO_OVERLAY, p_241738_8_, p_241738_9_, p_241738_10_, 1.0F);
	}

	private A getArmorModel(EquipmentSlotType p_241736_1_) {
		return (A) (this.usesInnerModel(p_241736_1_) ? this.innerModel : this.outerModel);
	}

	private boolean usesInnerModel(EquipmentSlotType p_188363_1_) {
		return p_188363_1_ == EquipmentSlotType.LEGS;
	}
	
	protected A getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlotType slot, A model) {
		return ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);
	}
	
	public ResourceLocation getArmorResource(Entity entity, ItemStack stack, EquipmentSlotType slot, @Nullable String type, boolean overlay) {
		CosmosColourableArmourItem item = (CosmosColourableArmourItem) stack.getItem();
		
		String texture = "";
		
		if (!overlay) {
			texture = item.getMaterial().getName();
		} else {
			texture = item.getMaterial().getName() + "_overlay";
		}
		
		String domain = "minecraft";
		
		int idx = texture.indexOf(':');
		
		if (idx != -1) {
			domain = texture.substring(0, idx);
			texture = texture.substring(idx + 1);
		}
		
		String s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (usesInnerModel(slot) ? 2 : 1), type == null ? "" : String.format("_%s", type));

		s1 = ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
		ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(s1);

		if (resourcelocation == null) {
			resourcelocation = new ResourceLocation(s1);
			ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
		}

		return resourcelocation;
	}
}