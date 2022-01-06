package com.tcn.cosmoslibrary.client.ui.screen.widget;

import javax.annotation.Nullable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tcn.cosmoslibrary.CosmosReference;
import com.tcn.cosmoslibrary.client.ui.lib.CosmosUISystem;
import com.tcn.cosmoslibrary.client.ui.lib.CosmosUISystem.FONT;
import com.tcn.cosmoslibrary.client.ui.lib.CosmosUISystem.IS_HOVERING;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class CosmosListWidget extends GuiComponent {

	private ResourceLocation TEXTURE;
	private int width;
	private int height;
	private int xPosition;
	private int yPosition;
	private String displayComponent;
	private ComponentColour displayColour;
	
	private boolean hovered;
	private boolean selected;
	
	public CosmosListWidget() { }
	
	public CosmosListWidget(int posX, int posY, int widthIn, int heightIn, @Nullable ResourceLocation textureIn, String displayComponentIn, ComponentColour displayColourIn) {

		this.xPosition = posX;
		this.yPosition = posY;
		
		this.width = widthIn;
		this.height = heightIn;
		this.displayComponent = displayComponentIn;
		this.displayColour = displayColourIn;
		
		this.TEXTURE = textureIn;
	}
	
	public CosmosListWidget(int[] screen_coords, int posX, int posY, int widthIn, int heightIn, @Nullable ResourceLocation textureIn, String displayComponentIn, ComponentColour displayColourIn) {
		
		this.xPosition = screen_coords[0] + posX;
		this.yPosition = screen_coords[1] + posY;
		
		this.width = widthIn;
		this.height = heightIn;
		
		this.displayComponent = displayComponentIn;
		this.displayColour = displayColourIn;
		
		this.TEXTURE = textureIn;
	}

	protected int getHoverState(boolean mouseOver) {
		int i = 0;

		if (mouseOver) {
			i = 1;
		}
		
		return i;
	}
	
	public void renderWidget(PoseStack stack, Font font_renderer, int[] screen_coords, int mouseX, int mouseY, int indexIn, int boxMaxY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);

		if (this.TEXTURE != null) {
			RenderSystem.setShaderTexture(0, TEXTURE);
		} else {
			RenderSystem.setShaderTexture(0, CosmosReference.RESOURCE.BASE.GUI_ELEMENT_MISC_LOC);
		}
		
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		this.hovered = IS_HOVERING.isHovering(mouseX, mouseY, screen_coords[0] + this.xPosition, screen_coords[0] + this.xPosition + this.width, screen_coords[1] + this.yPosition, screen_coords[1] + this.yPosition + this.height);
		int hovered = this.getHoverState(this.hovered);

		CosmosUISystem.enableAlpha();
		
		if (this.yPosition + this.height < boxMaxY) {
			if (this.height == 20) {
				if (indexIn == 0) {
					this.blit(stack, screen_coords[0] + this.xPosition, screen_coords[1] + this.yPosition, 56, 42 + (hovered * 20), this.width / 2, this.height);
					this.blit(stack, screen_coords[0] + this.xPosition + screen_coords[1] + this.width / 2, this.yPosition, 256 - this.width / 2, 42 + (hovered * 20), this.width / 2, this.height);
				} else {
					this.blit(stack, screen_coords[0] + this.xPosition, screen_coords[1] + this.yPosition, 56, this.selected ? 20 : hovered * 20, this.width / 2, this.height);
					this.blit(stack, screen_coords[0] + this.xPosition + this.width / 2, screen_coords[1] + this.yPosition, 256 - this.width / 2, this.selected ? 20 : hovered * 20, this.width / 2, this.height);
				}
				
			} else if (this.height == 14) {
				if (indexIn == 0) {
					this.blit(stack, screen_coords[0] + this.xPosition, screen_coords[1] + this.yPosition, 56, 114 + (hovered * 14), this.width / 2, this.height);
					this.blit(stack, screen_coords[0] + this.xPosition + this.width / 2, screen_coords[1] + this.yPosition, 256 - this.width / 2, 114 + (hovered * 14), this.width / 2, this.height);
				} else {
					this.blit(stack, screen_coords[0] + this.xPosition, screen_coords[1] + this.yPosition, 56, this.selected ? 84 + 14 : 84 + (hovered * 14), this.width / 2, this.height);
					this.blit(stack, screen_coords[0] + this.xPosition + this.width / 2, screen_coords[1] + this.yPosition, 256 - this.width / 2, this.selected ? 84 + 14 : 84 + (hovered * 14), this.width / 2, this.height);
				}
			}
			
			if (indexIn == 0) {
				FONT.drawCenteredString(stack, font_renderer, screen_coords, this.xPosition + this.width / 2, this.yPosition - 30, ComponentHelper.locComp(hovered == 0 ? this.getDisplayColour() : ComponentColour.BLACK, false, this.getDisplayString()));//.append(ComponentHelper.locComp(hovered == 0 ? this.getDisplayColour() : ComponentColour.BLACK, false, " [Owner]")));
			} else {
				FONT.drawCenteredString(stack, font_renderer, screen_coords, this.xPosition + this.width / 2, this.yPosition - 30, ComponentHelper.locComp(hovered == 0 ? this.getDisplayColour() : ComponentColour.BLACK, false, this.getDisplayString()));//, "  " + font_renderer.width(this.getDisplayString()) + "  " + this.width));
			}
		}
	}

	public void setPositionToLastWidget(int spacing) {
		this.yPosition -= (this.height + spacing);
	}
	
	public void setPositionToNextWidget(int spacing) {
		this.yPosition += (this.height + spacing);
	}
	
	public void setPosition(int x, int y) {
		this.xPosition = x;
		this.yPosition = y;
	}
	
	public boolean mousePressed(Minecraft mc, double mouseX, double mouseY) {
		if (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height) {
			this.switchSelected();
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isMouseOver() {
		return this.hovered;
	}
	
	public void switchSelected() {
		this.selected = !(this.selected);
	}
	
	public boolean getSelected() {
		return this.selected;
	}
	
	public void deselect() {
		this.selected = false;
	}
	
	public void setSelectedState(boolean set) {
		this.selected = set;
	}

	public String getDisplayString() {
		return displayComponent;
	}
	
	public ComponentColour getDisplayColour() {
		return this.displayColour;
	}
	
	public int getXPos() {
		return this.xPosition;
	}
	
	public int getYPos() {
		return this.yPosition;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
}