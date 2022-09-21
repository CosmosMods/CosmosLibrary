package com.tcn.cosmoslibrary.client.ui.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tcn.cosmoslibrary.CosmosReference;
import com.tcn.cosmoslibrary.client.ui.lib.CosmosUISystem;
import com.tcn.cosmoslibrary.common.enums.EnumUIMode;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author TheCosmicNebula_
 */
@OnlyIn(Dist.CLIENT)
public class CosmosButtonUIMode extends Button {
	
	private int[] index = new int[] { 0, 12, 24, 36 };
	
	private final ResourceLocation TEXTURE = CosmosReference.RESOURCE.BASE.BUTTON_UI; 
	private EnumUIMode buttonType;
	protected int width;
	protected int height;
	public int x;
	public int y;
	protected boolean isHovered;
	public boolean active = true;
	public boolean visible = true;

	public CosmosButtonUIMode(EnumUIMode typeIn, int x, int y, boolean enabled, boolean visible, Component title, Button.OnPress pressedAction) {
		this(typeIn, x, y, 12, enabled, visible, title, pressedAction);
	}

	public CosmosButtonUIMode(EnumUIMode typeIn, int x, int y, int size, boolean enabled, boolean visible, Component title, Button.OnPress pressedAction) {
		super(x, y, size, size, title, pressedAction);
		this.buttonType = typeIn;
		
		this.x = x;
		this.y = y;
		this.width = size;
		this.height = size;

		this.active = enabled;
		this.visible = visible;
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) { 
		if (this.visible) {
			this.renderButton(matrixStack, mouseX, mouseY, partialTicks);
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
		if (this.active) {
			this.onPress.onPress(this);
		}
	}

	@Override
	public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		if (this.buttonType != null) {
			CosmosUISystem.setTextureWithColourAlpha(matrixStack, TEXTURE, new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
			
			this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			int i = this.getHoverState(this.isHovered);

		    if (this.buttonType.equals(EnumUIMode.DARK)) {
		    	this.blit(matrixStack, this.x, this.y, index[i], 0, this.width, this.height);
		    } else {
		    	this.blit(matrixStack, this.x, this.y, index[i + 2], 0, this.width, this.height);
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
}