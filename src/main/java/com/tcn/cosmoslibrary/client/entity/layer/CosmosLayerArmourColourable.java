package com.tcn.cosmoslibrary.client.entity.layer;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tcn.cosmoslibrary.common.item.CosmosArmourItemColourable;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unused")
public class CosmosLayerArmourColourable<E extends LivingEntity, M extends HumanoidModel<E>, A extends HumanoidModel<E>> extends RenderLayer<E, M> {
	
	private enum TYPE {
		BASE,
		OVERLAY,
		ALPHA;
	}
	
	private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = Maps.newHashMap();
	
	private final A innerModel;
	private final A outerModel;

	public CosmosLayerArmourColourable(RenderLayerParent<E, M> entityRenderer, A innerModelIn, A outerModelIn) {
		super(entityRenderer);
		this.innerModel = innerModelIn;
		this.outerModel = outerModelIn;
	}

	@Override
	public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, E entityLivingBaseIn, float limbSwingIn, float limbSwingAmountIn, float partialTicks, float ageInTicks, float netHeadYawIn, float headPitchIn) {
		this.renderArmorPiece(matrixStackIn, bufferIn, entityLivingBaseIn, EquipmentSlot.CHEST, packedLightIn, this.getArmorModel(EquipmentSlot.CHEST) );
		this.renderArmorPiece(matrixStackIn, bufferIn, entityLivingBaseIn, EquipmentSlot.LEGS,  packedLightIn, this.getArmorModel(EquipmentSlot.LEGS) );
		this.renderArmorPiece(matrixStackIn, bufferIn, entityLivingBaseIn, EquipmentSlot.FEET,  packedLightIn, this.getArmorModel(EquipmentSlot.FEET) );
		this.renderArmorPiece(matrixStackIn, bufferIn, entityLivingBaseIn, EquipmentSlot.HEAD,  packedLightIn, this.getArmorModel(EquipmentSlot.HEAD) );
	}

	private void renderArmorPiece(PoseStack matrixStackIn, MultiBufferSource bufferIn, E livingEntityIn, EquipmentSlot slotTypeIn, int packedLightIn, A modelIn) {
		ItemStack stackIn = livingEntityIn.getItemBySlot(slotTypeIn);
		
		if (stackIn.getItem() instanceof CosmosArmourItemColourable) {
			CosmosArmourItemColourable armoritem = (CosmosArmourItemColourable) stackIn.getItem();

			if (armoritem.getSlot() == slotTypeIn) {
				this.getParentModel().copyPropertiesTo(modelIn);
				Model model = this.getArmorModelHook(livingEntityIn, stackIn, slotTypeIn, modelIn);
				this.setPartVisibility(modelIn, slotTypeIn);
				
				boolean flag = this.usesInnerModel(slotTypeIn);
				boolean flag1 = stackIn.hasFoil();

				float[] rgb = ComponentColour.rgbFloatArray(ComponentColour.POCKET_PURPLE_LIGHT);
				
				if (stackIn.hasTag()) {
					CompoundTag stackTag = stackIn.getTag();
					
					if (stackTag.contains("nbt_data")) {
						CompoundTag nbtData = stackTag.getCompound("nbt_data");
						
						if (nbtData.contains("colour")) {
							int colour = nbtData.getInt("colour");
							
							float[] colArray = ComponentColour.rgbFloatArray(colour);
							
							this.renderModel(matrixStackIn, bufferIn, packedLightIn, flag1, model, colArray[0], colArray[1], colArray[2], 1.0F, false, this.getArmorResource(livingEntityIn, stackIn, slotTypeIn, TYPE.BASE, null));
						}
					} else {
						this.renderModel(matrixStackIn, bufferIn, packedLightIn, flag1, model, rgb[0], rgb[1], rgb[2], 1.0F, false, this.getArmorResource(livingEntityIn, stackIn, slotTypeIn, TYPE.BASE, null));
					}
				} else {
					this.renderModel(matrixStackIn, bufferIn, packedLightIn, flag1, model, rgb[0], rgb[1], rgb[2], 1.0F, false, this.getArmorResource(livingEntityIn, stackIn, slotTypeIn, TYPE.BASE, null));
				}

				this.renderModel(matrixStackIn, bufferIn, packedLightIn, flag1, model, 1.0F, 1.0F, 1.0F, 1.0F, false, this.getArmorResource(livingEntityIn, stackIn, slotTypeIn, TYPE.OVERLAY, null));
				
				this.renderModel(matrixStackIn, bufferIn, packedLightIn, flag1, model, 1.0F, 1.0F, 1.0F, 1.0F, false, this.getArmorResource(livingEntityIn, stackIn, slotTypeIn, TYPE.ALPHA, null));
			}
		}
	}

	@SuppressWarnings("incomplete-switch")
	protected void setPartVisibility(A modelIn, EquipmentSlot slotIn) {
		modelIn.setAllVisible(false);
		switch (slotIn) {
		case HEAD:
			modelIn.head.visible = true;
			modelIn.hat.visible = true;
			break;
		case CHEST:
			modelIn.body.visible = true;
			modelIn.rightArm.visible = true;
			modelIn.leftArm.visible = true;
			break;
		case LEGS:
			modelIn.body.visible = true;
			modelIn.rightLeg.visible = true;
			modelIn.leftLeg.visible = true;
			break;
		case FEET:
			modelIn.rightLeg.visible = true;
			modelIn.leftLeg.visible = true;
		}

	}

	private void renderModel(PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn, boolean foilIn, Model modelIn, float r, float g, float b, float a, boolean alphaLayer, ResourceLocation armorResource) {
		if (alphaLayer) {
			RenderType type = RenderType.entityTranslucent(armorResource);
			
			VertexConsumer ivertexbuilder = ItemRenderer.getArmorFoilBuffer(bufferIn, type, false, foilIn);
			modelIn.renderToBuffer(poseStack, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, r, g, b, a);
		} else {
			RenderType type = RenderType.armorCutoutNoCull(armorResource);
			
			VertexConsumer ivertexbuilder = ItemRenderer.getArmorFoilBuffer(bufferIn, type, false, false);
			modelIn.renderToBuffer(poseStack, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, r, g, b, a);
		}
	}

	private A getArmorModel(EquipmentSlot slotIn) {
		return (A) (this.usesInnerModel(slotIn) ? this.innerModel : this.outerModel);
	}

	private boolean usesInnerModel(EquipmentSlot slotIn) {
		return slotIn == EquipmentSlot.LEGS;
	}
	
	protected Model getArmorModelHook(E entity, ItemStack itemStack, EquipmentSlot slot, A model) {
		return ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);
	}
	
	public ResourceLocation getArmorResource(Entity entity, ItemStack stack, EquipmentSlot slot, TYPE typeIn, @Nullable String type) {
		Item item = stack.getItem();
		
		if (item instanceof CosmosArmourItemColourable) {
			CosmosArmourItemColourable armour = (CosmosArmourItemColourable) stack.getItem();
			
			if (typeIn.equals(TYPE.ALPHA)) {
				String texture = "";
				String domain = "minecraft";
				texture = armour.getMaterial().getName() + "_alpha";
				
				int idx = texture.indexOf(':');
				
				if (idx != -1) {
					domain = texture.substring(0, idx);
					texture = texture.substring(idx + 1);
				}
				
				String s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (this.usesInnerModel(slot) ? 2 : 1), type == null ? "" : String.format("_%s", type));
		
				s1 = ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
				ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(s1);
		
				if (resourcelocation == null) {
					resourcelocation = new ResourceLocation(s1);
					ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
				}
		
				return resourcelocation;
			} else if (typeIn.equals(TYPE.OVERLAY)) {
				String texture = "";
				String domain = "minecraft";
				texture = armour.getMaterial().getName() + "_overlay";
				
				int idx = texture.indexOf(':');
				
				if (idx != -1) {
					domain = texture.substring(0, idx);
					texture = texture.substring(idx + 1);
				}
				
				String s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (this.usesInnerModel(slot) ? 2 : 1), type == null ? "" : String.format("_%s", type));
		
				s1 = ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
				ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(s1);
		
				if (resourcelocation == null) {
					resourcelocation = new ResourceLocation(s1);
					ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
				}
		
				return resourcelocation;
			} else if (typeIn.equals(TYPE.BASE)) {
				String texture = "";
				String domain = "minecraft";
				texture = armour.getMaterial().getName();
				
				int idx = texture.indexOf(':');
				
				if (idx != -1) {
					domain = texture.substring(0, idx);
					texture = texture.substring(idx + 1);
				}
				
				String s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (this.usesInnerModel(slot) ? 2 : 1), type == null ? "" : String.format("_%s", type));
		
				s1 = ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
				ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(s1);
		
				if (resourcelocation == null) {
					resourcelocation = new ResourceLocation(s1);
					ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
				}
		
				return resourcelocation;
			} else {
				return new ResourceLocation("", "");
			}
		} else {
			return new ResourceLocation("", "");
		}
	}
}