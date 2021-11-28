package com.tcn.cosmoslibrary.client.renderer.model;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;

@SuppressWarnings("deprecation")
public class CustomModelClass implements IBakedModel {

	private final IBakedModel internal;

	public CustomModelClass(IBakedModel internal) {
		this.internal = internal;
	}

	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand) {
		return this.internal.getQuads(state, side, rand);
	}

	@Override
	public boolean useAmbientOcclusion() {
		return this.internal.useAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return this.internal.isGui3d();
	}

	public IBakedModel getInternal() {
		return this.internal;
	}

	@Override
	public boolean isCustomRenderer() {
		return true;
	}

	@Override
	public TextureAtlasSprite getParticleIcon() {
		return this.internal.getParticleIcon();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return this.internal.getOverrides();
	}

	@Override
	public ItemCameraTransforms getTransforms() {
		//You can use a field on your TileEntityItemStackRenderer to store this TransformType for use in renderByItem, this method is always called before it.
		return this.internal.getTransforms();
	}

	@Override
	public boolean usesBlockLight() {
		return false;
	}
}