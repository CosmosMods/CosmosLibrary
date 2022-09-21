package com.tcn.cosmoslibrary.client.ui.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tcn.cosmoslibrary.CosmosReference;
import com.tcn.cosmoslibrary.CosmosReference.RESOURCE.INFO;
import com.tcn.cosmoslibrary.client.ui.lib.CosmosUISystem;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CosmosColourButton extends Button {
	
	private ComponentColour colour = ComponentColour.EMPTY;
	
	protected int width;
	protected int height;
	public int x;
	public int y;
	protected boolean isHovered;
	public boolean active = true;
	public boolean visible = true;

	public CosmosColourButton(ComponentColour startingColour, int x, int y, int size, boolean enabled, boolean visible, Component title, Button.OnPress pressedAction) {
		super(x, y, size, size, title, pressedAction);
		
		this.colour = startingColour;
		
		this.x = x;
		this.y = y;
		this.width = size;
		this.height = size;

		this.active = enabled;
		this.visible = visible;
	}

	public CosmosColourButton(ComponentColour startingColourIn, int x, int y, boolean enabled, boolean visible, Component title, Button.OnPress pressedAction) {
		this(startingColourIn, x, y, 20, enabled, visible, title, pressedAction);
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			this.renderButton(poseStack, mouseX, mouseY, partialTicks);
		}
	}
	
	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		if (!this.active) {
			return false;
		}
		
		return super.isMouseOver(mouseX, mouseY);
	}

	@Override
	public void onPress() {
		if (this.visible && this.active) {
			this.onPress.onPress(this);
		}
	}

	@Override
	public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		CosmosUISystem.setTextureWithColourAlpha(poseStack, CosmosReference.RESOURCE.BASE.BUTTON_COLOUR, new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
		
		this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		int i = this.getHoverState(this.isHovered);

		if (this.width == 20 && this.height == 20) {
			this.blit(poseStack, this.x, this.y, INFO.BUTTON_STATE_X[0], INFO.BUTTON_STATE_Y[i], this.width, this.height);
		} else if (this.width == 18 && this.height == 18) {
			this.blit(poseStack, this.x, this.y, INFO.BUTTON_STATE_X_SMALL[0], INFO.BUTTON_STATE_Y_SMALL[i], this.width, this.height);
		}
		
		if (!this.colour.equals(ComponentColour.EMPTY)) {
			CosmosUISystem.setTextureColour(this.colour);
			
			if (this.width == 20 && this.height == 20) {
				this.blit(poseStack, this.x, this.y, INFO.BUTTON_STATE_X[1], INFO.BUTTON_STATE_Y[i], this.width, this.height);
			} else if (this.width == 18 && this.height == 18) {
				this.blit(poseStack, this.x, this.y, INFO.BUTTON_STATE_X_SMALL[1], INFO.BUTTON_STATE_Y_SMALL[i], this.width, this.height);
			}
		}
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
	
	public ComponentColour getColour() {
		return this.colour;
	}
}