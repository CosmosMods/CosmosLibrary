package com.tcn.cosmoslibrary.client.entity.layer;

import com.tcn.cosmoslibrary.common.item.CosmosElytraArmourItem;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CosmosElytraLayer extends ElytraLayer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

    private ResourceLocation TEXTURE_ELYTRA;
    
	public CosmosElytraLayer(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> rendererIn, ResourceLocation location) {
		super(rendererIn);
		
		this.TEXTURE_ELYTRA = location;
	}

    @Override
    public boolean shouldRender(ItemStack stack, AbstractClientPlayerEntity entity) {
        return stack.getItem() instanceof CosmosElytraArmourItem;
    }

    @Override
    public ResourceLocation getElytraTexture(ItemStack stack, AbstractClientPlayerEntity entity) {
        return TEXTURE_ELYTRA;
    }
}