package com.tcn.cosmoslibrary.client.ui.screen.widget;

import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tcn.cosmoslibrary.CosmosReference;
import com.tcn.cosmoslibrary.client.ui.lib.CosmosUISystem;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class CosmosUIHelpElement extends AbstractWidget {
	
	private Component[] desc;
	
	public boolean visible;
	
	public CosmosUIHelpElement(int xIn, int yIn, int widthIn, int heightIn, Component... descIn) {
		super(xIn, yIn, widthIn, heightIn, descIn[0]);
		
		this.desc = descIn;
	}

	@Override
	public void updateNarration(NarrationElementOutput p_169152_) { }
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		//if (this.visible) {
			this.renderElement(poseStack, mouseX, mouseY, partialTicks);
		//}
	}

	private void renderElement(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		this.setHovered(mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height);
		
		if (this.isHovered) {
			CosmosUISystem.setTextureWithColourAlpha(poseStack, CosmosReference.RESOURCE.BASE.UI_LIGHTEN_HIGHLIGHT, new float[] { 1.0F, 1.0F, 1.0F, 0.6F });
			this.blit(poseStack, this.x, this.y, 0, 0, this.width, this.height);

			CosmosUISystem.setTextureColour(0.0F, 1.0F, 0.0F, 1.0F);
			this.blit(poseStack, this.x, this.y, 0, 0, this.width, 1);
			this.blit(poseStack, this.x, this.y + this.height - 1, 0, 0, this.width, 1);
			this.blit(poseStack, this.x, this.y + 1, 0, 0, 1, this.height - 2);
			this.blit(poseStack, this.x + this.width - 1, this.y + 1, 0, 0, 1, this.height - 2);
		} else {
			CosmosUISystem.setTextureWithColourAlpha(poseStack, CosmosReference.RESOURCE.BASE.UI_LIGHTEN, new float[] { 1.0F, 1.0F, 1.0F, 0.4F });
			this.blit(poseStack, this.x, this.y, 0, 0, this.width, this.height);
			
			CosmosUISystem.setTextureColour(0.0F, 0.8F, 0.0F, 1.0F);
			this.blit(poseStack, this.x, this.y, 0, 0, this.width, 1);
			this.blit(poseStack, this.x, this.y + this.height - 1, 0, 0, this.width, 1);
			this.blit(poseStack, this.x, this.y + 1, 0, 0, 1, this.height - 2);
			this.blit(poseStack, this.x + this.width - 1, this.y + 1, 0, 0, 1, this.height - 2);
		}
	}
	
	public List<Component> getHoverElement() {
		return Arrays.asList(this.desc);
	}
		
	public CosmosUIHelpElement setVisible() {
		this.visible = true;
		return this;
	}

	public CosmosUIHelpElement setVisible(boolean valueIn) {
		this.visible = valueIn;
		return this;
	}
	
	public CosmosUIHelpElement setHidden() {
		this.visible = false;
		return this;
	}

	public CosmosUIHelpElement setHidden(boolean valueIn) {
		this.visible = valueIn;
		return this;
	}
	
	protected void setHovered(boolean valueIn) {
		this.isHovered = valueIn;
	}
	
	protected int getHoverState(boolean mouseOver) {
		int i = 0;

		if (!this.active) {
			i = 2;
		} else if (mouseOver) {
			i = 1;
		}
		return i;
	}
}
