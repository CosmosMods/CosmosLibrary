package com.tcn.cosmoslibrary.client.ui.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tcn.cosmoslibrary.CosmosReference;
import com.tcn.cosmoslibrary.client.ui.lib.CosmosUISystem;
import com.tcn.cosmoslibrary.common.enums.EnumUIHelp;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * 
 * @author TheCosmicNebula_
 * 
 */
@OnlyIn(Dist.CLIENT)
public class CosmosButtonUIHelp extends Button {
	private int[] index = new int[] { 0, 12, 24, 36 };
	
	private final ResourceLocation TEXTURE = CosmosReference.RESOURCE.BASE.BUTTON_UI;
	private EnumUIHelp mode;
	protected int width;
	protected int height;
	public int x;
	public int y;
	protected boolean isHovered;
	public boolean active = true;
	public boolean visible = true;

	public CosmosButtonUIHelp(EnumUIHelp modeIn, int x, int y, boolean enabled, boolean visible, Component title, Button.OnPress pressedAction) {
		this(modeIn, x, y, 12, 17, enabled, visible, title, pressedAction);
	}

	public CosmosButtonUIHelp(EnumUIHelp modeIn, int x, int y, int width, int height, boolean enabled, boolean visible, Component title, Button.OnPress pressedAction) {
		super(x, y, width, height, title, pressedAction);
		
		this.mode = modeIn;
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.active = enabled;
		this.visible = visible;
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
		if (this.active) {
			this.onPress.onPress(this);
		}
	}

	@Override
	public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		if (this.mode != null) {
			CosmosUISystem.setTextureWithColourAlpha(poseStack, TEXTURE, new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
			
			this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			int i = this.getHoverState(this.isHovered);
			
			if (this.mode.equals(EnumUIHelp.HIDDEN)) {
				this.blit(poseStack, this.x, this.y, index[i], 20, this.width, this.height);
			} else {
				this.blit(poseStack, this.x, this.y, index[i + 2], 20, this.width, this.height);
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