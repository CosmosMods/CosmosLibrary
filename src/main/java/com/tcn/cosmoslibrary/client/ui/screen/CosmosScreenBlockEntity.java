package com.tcn.cosmoslibrary.client.ui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tcn.cosmoslibrary.client.container.CosmosContainerMenuBlockEntity;
import com.tcn.cosmoslibrary.client.ui.lib.CosmosUISystem;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CosmosScreenBlockEntity<J extends CosmosContainerMenuBlockEntity> extends AbstractContainerScreen<J> {
	
	protected ResourceLocation TEXTURE;
	protected ResourceLocation DUAL_TEXTURE;
	
	private int[] screenCoords;
	
	private boolean hasDualScreen = false;
	private int[] dualScreenIndex;
	
	private boolean renderTitleLabel = true;
	private boolean renderInventoryLabel = true;

	public CosmosScreenBlockEntity(J containerIn, Inventory playerInventoryIn, Component titleIn) {
		super(containerIn, playerInventoryIn, titleIn);
	}
	
	@Override
	protected void init() {
		this.setScreenCoords(CosmosUISystem.getScreenCoords(this, this.imageWidth, this.imageHeight));
		super.init();
		this.addButtons();
	}
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(poseStack);
		super.render(poseStack, mouseX, mouseY, partialTicks);

		this.renderComponents(poseStack, mouseX, mouseY, partialTicks);
		this.renderComponentHoverEffect(poseStack, Style.EMPTY, mouseX, mouseY);
		this.renderTooltip(poseStack, mouseX, mouseY);
	}
	
	protected void renderComponents(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) { }

	@Override
	protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
		CosmosUISystem.renderStaticElement(this, poseStack, this.screenCoords, 0, 0, 0, 0, Mth.clamp(this.imageWidth, 0, 256), this.imageHeight, TEXTURE);
		
		if (this.hasDualScreen) {
			if(this.dualScreenIndex != null && this.DUAL_TEXTURE != null && this.DUAL_TEXTURE != null) {
				CosmosUISystem.renderStaticElement(this, poseStack, this.screenCoords, this.dualScreenIndex[0], this.dualScreenIndex[1], 0, 0, Mth.clamp(256 + this.dualScreenIndex[2], 0, 256), this.dualScreenIndex[3], DUAL_TEXTURE);
			}
		}
	}

	@Override
	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
		if (this.renderTitleLabel) {
			this.font.draw(poseStack, this.title, (float) this.titleLabelX, (float) this.titleLabelY, CosmosUISystem.DEFAULT_COLOUR_FONT_LIST);
		}
		
		if (this.renderInventoryLabel) {
			this.font.draw(poseStack, this.playerInventoryTitle, (float) this.inventoryLabelX, (float) this.inventoryLabelY, CosmosUISystem.DEFAULT_COLOUR_FONT_LIST);
		}
	}
	
	@Override
	public void renderComponentHoverEffect(PoseStack poseStack, Style style, int mouseX, int mouseY) { }
	
	protected void addButtons() { 
		this.clearWidgets();
	}
	
	protected void pushButton(Button button) { }
	
	protected void setImageDims(int widthIn, int heightIn) {
		this.imageWidth = widthIn;
		this.imageHeight = heightIn;
	}

	protected void setTexture(ResourceLocation textureIn) {
		this.TEXTURE = textureIn;
	}
	
	protected void setDualScreen() {
		this.hasDualScreen = true;
	}
	
	protected void setDualScreenIndex(int posX, int posY, int width, int height) {
		this.setDualScreen();
		this.dualScreenIndex = new int[] { posX, posY, width, height };
	}

	protected void setDual(ResourceLocation textureIn) {
		this.setDualScreen();
		this.DUAL_TEXTURE = textureIn;
	}
	
	protected void setNoTitleLabel() {
		this.renderTitleLabel = false;
	}
	
	protected void setTitleLabelDims(int posX, int posY) {
		this.titleLabelX = posX;
		this.titleLabelY = posY;
	}

	protected void setNoInventoryLabel() {
		this.renderInventoryLabel = false;
	}

	protected void setInventoryLabelDims(int posX, int posY) {
		this.inventoryLabelX = posX;
		this.inventoryLabelY = posY;
	}
	
	protected void setScreenCoords(int[] coordsIn) {
		this.screenCoords = coordsIn;
	}
	
	protected int[] getScreenCoords() {
		return this.screenCoords;
	}
	
	public BlockEntity getBlockEntity() {
		CosmosContainerMenuBlockEntity container = (CosmosContainerMenuBlockEntity) this.menu;
		Level level = container.getLevel();
		BlockPos pos = container.getBlockPos();
		
		return level.getBlockEntity(pos);
	}
	
}