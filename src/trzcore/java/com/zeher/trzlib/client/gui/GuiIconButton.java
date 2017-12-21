package com.zeher.trzlib.client.gui;

import com.zeher.trzlib.TRZLib;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiIconButton extends GuiButton {
	
	protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation(TRZLib.mod_id + ":" + "textures/gui/util/button/gui_buttons.png");
	public int width;
	public int height;
	
	public int xPosition;
	public int yPosition;
	
	public String displayString;
	public int type;
	public int id;
	
	public boolean enabled;
	public boolean visible;
	
	protected boolean hovered;
	
	public int packedFGColour;
	
	public int[] button_state_y = { 0, 23, 46, 69, 92, 115, 138, 161, 184 };
	public int[] button_state_x = { 0 , 23, 44, 67, 88, 111, 132, 155, 176, 199 };

	public GuiIconButton(int buttonId, int x, int y, String buttonText, int type) {
		this(buttonId, x, y, 20, 20, type);
	}

	public GuiIconButton(int buttonId, int x, int y, int widthIn, int heightIn, int type) {
		super(buttonId, x, y, widthIn, heightIn, "");
		this.width = widthIn;
		this.height = heightIn;
		this.enabled = true;
		this.visible = true;
		this.id = buttonId;
		this.xPosition = x;
		this.yPosition = y;
		this.width = widthIn;
		this.height = heightIn;
		this.displayString = "";
		this.type = type;
	}
	
	protected int getHoverState(boolean mouseOver) {
		int i = 1;

		if (!this.enabled) {
			i = 0;
		} else if (mouseOver) {
			i = 2;
		}
		return i;
	}
	
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			FontRenderer fontrenderer = mc.fontRendererObj;
			mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			
			this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
			
			int i = this.getHoverState(this.hovered);
			
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			
			if(type < 6){
				if(this.width == 20 && this.height == 20){
					this.drawTexturedModalRect(this.xPosition, this.yPosition, this.button_state_x[type * 2], this.button_state_y[i], this.width, this.height);
				}
				if(this.width == 18 && this.height == 18){
					this.drawTexturedModalRect(this.xPosition, this.yPosition, this.button_state_x[(type * 2) + 1], this.button_state_y[i], this.width, this.height);
				}
			}
			if(type >= 6){
				if(this.width == 20 && this.height == 20){
					this.drawTexturedModalRect(this.xPosition, this.yPosition, this.button_state_x[(type * 2) / type], this.button_state_y[i + 3], this.width, this.height);
				}
				if(this.width == 18 && this.height == 18){
					this.drawTexturedModalRect(this.xPosition, this.yPosition, this.button_state_x[((type * 2) / type) + 1], this.button_state_y[i + 3], this.width, this.height);
				}
			}
			if(type >= 11 && type < 16){
				if(this.width == 20 && this.height == 20){
					this.drawTexturedModalRect(this.xPosition, this.yPosition, this.button_state_x[(type * 2) / type], this.button_state_y[i + 6], this.width, this.height);
				}
				if(this.width == 18 && this.height == 18){
					this.drawTexturedModalRect(this.xPosition, this.yPosition, this.button_state_x[((type * 2) / type) + 1], this.button_state_y[i + 6], this.width, this.height);
				}
			}
			
			this.mouseDragged(mc, mouseX, mouseY);
			
			int j = 14737632;

			if (packedFGColour != 0) {
				j = packedFGColour;
			} else if (!this.enabled) {
				j = 10526880;
			} else if (this.hovered) {
				j = 16777120;
			}

			this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2,
					this.yPosition + (this.height - 8) / 2, j);
		}
	}

	protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
	}
	
	public void mouseReleased(int mouseX, int mouseY) {
	}
	
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition
				&& mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
	}
	
	public boolean isMouseOver() {
		return this.hovered;
	}

	public void drawButtonForegroundLayer(int mouseX, int mouseY) {
	}

	public void playPressSound(SoundHandler soundHandlerIn) {
		soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
	}

	public int getButtonWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
}