package com.tcn.cosmoslibrary.client.widget;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.tcn.cosmoslibrary.CosmosReference;
import com.tcn.cosmoslibrary.client.util.ScreenUtil.IS_HOVERING;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FocusableGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.util.ResourceLocation;

public class ScreenListWidget extends FocusableGui {
	
	public int width;
	public int height;
	public int xPosition;
	public int yPosition;
	public int id;
	public int text_colour;
	public String displayString;
	
	public ResourceLocation skin;
	
	protected boolean hovered;
	protected boolean selected;
	
	public ScreenListWidget() { }
	
	public ScreenListWidget(int elementId, int draw_x, int draw_y, int widthIn, int heightIn, @Nullable ResourceLocation skin, String list_test, int text_colour) {
		this.width = widthIn;
		this.height = heightIn;
		this.id = elementId;
		this.xPosition = draw_x;
		this.yPosition = draw_y;
		this.width = widthIn;
		this.height = heightIn;
		this.displayString = list_test;
		this.text_colour = text_colour;
		
		this.skin = skin;
	}
	
	public ScreenListWidget(int elementId, int[] screen_coords, int draw_x, int draw_y, int widthIn, int heightIn, @Nullable ResourceLocation skin, String list_test, int text_colour) {
		this.width = widthIn;
		this.height = heightIn;
		this.id = elementId;
		this.xPosition = screen_coords[0] + draw_x;
		this.yPosition = screen_coords[1] + draw_y;
		this.width = widthIn;
		this.height = heightIn;
		this.displayString = list_test;
		this.text_colour = text_colour;
		
		this.skin = skin;
	}

	protected int getHoverState(boolean mouseOver) {
		int i = 0;

		if (mouseOver) {
			i = 1;
		}
		
		return i;
	}
	
	public void drawElement(MatrixStack stack, FontRenderer font_renderer, int[] screen_coords, int mouseX, int mouseY, int index, int boxMaxY) {
		Minecraft minecraft = Minecraft.getInstance();
		
		if (this.skin != null) {
			minecraft.getTextureManager().bind(skin);
		} else {
			minecraft.getTextureManager().bind(CosmosReference.RESOURCE.BASE.GUI_ELEMENT_MISC_LOC);
		}
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		this.hovered = IS_HOVERING.isHovering(mouseX, mouseY, screen_coords[0] + this.xPosition, screen_coords[0] + this.xPosition + this.width, screen_coords[1] + this.yPosition, screen_coords[1] + this.yPosition + this.height);
		
		int i = this.getHoverState(this.hovered);
		
		//this.drawTexturedModalRect(this.xPosition + mouseX, this.yPosition + mouseY, 56, 0 + 1 * 20, this.width / 2, this.height);
		
		GlStateManager._enableBlend();
		GlStateManager._blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.value, GlStateManager.SourceFactor.ONE.value, GlStateManager.DestFactor.ZERO.value);
		GlStateManager._blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.value);
		//GlStateManager._disableLighting();
		
		if (this.yPosition + this.height < boxMaxY) {
			if (this.height == 20) {
				if (this.selected) {
					this.blit(stack, screen_coords[0] + this.xPosition, screen_coords[1] + this.yPosition, 56, 0 + 1 * 20, this.width / 2, this.height);
					this.blit(stack, screen_coords[0] + this.xPosition + screen_coords[1] + this.width / 2, this.yPosition, 256 - this.width / 2, 0 + 1 * 20, this.width / 2, this.height);
				} else {
					this.blit(stack, screen_coords[0] + this.xPosition, screen_coords[1] + this.yPosition, 56, 0 + i * 20, this.width / 2, this.height);
					this.blit(stack, screen_coords[0] + this.xPosition + this.width / 2, screen_coords[1] + this.yPosition, 256 - this.width / 2, 0 + i * 20, this.width / 2, this.height);
				}
			} else if (this.height == 14) {
				if (this.selected) {
					this.blit(stack, screen_coords[0] + this.xPosition, screen_coords[1] + this.yPosition, 56, 42 + 1 * 14, this.width / 2, this.height);
					this.blit(stack, screen_coords[0] + this.xPosition + this.width / 2, screen_coords[1] + this.yPosition, 256 - this.width / 2, 42 + 1 * 14, this.width / 2, this.height);
				} else {
					this.blit(stack, screen_coords[0] + this.xPosition, screen_coords[1] + this.yPosition, 56, 42 + i * 14, this.width / 2, this.height);
					this.blit(stack, screen_coords[0] + this.xPosition + this.width / 2, screen_coords[1] + this.yPosition, 256 - this.width / 2, 42 + i * 14, this.width / 2, this.height);
				}
			}
			
			if (index == 0) {
				drawCenteredString(stack, font_renderer, this.displayString + " [Owner]", screen_coords[0] + this.xPosition + this.width / 2, screen_coords[1] + this.yPosition + (this.height - 8) / 2, this.text_colour);
			} else {
				drawCenteredString(stack, font_renderer, this.displayString, screen_coords[0] + this.xPosition + this.width / 2, screen_coords[1] + this.yPosition + (this.height - 8) / 2, this.text_colour);
			}
		}
		
		//GlStateManager._enableLighting();
	}

	public void setToLastElementPosition(int spacing) {
		this.yPosition -= (this.height + spacing);
	}
	
	public void setToNextElementPostion(int spacing) {
		this.yPosition += (this.height + spacing);
	}
	
	public void setPosition(int x, int y) {
		this.xPosition = x;
		this.yPosition = y;
	}
	
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
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

	@Override
	public List<? extends IGuiEventListener> children() {
		return null;
	}
}