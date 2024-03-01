package com.tcn.cosmoslibrary.client.ui.screen.widget;

import java.util.function.Supplier;

import com.tcn.cosmoslibrary.CosmosReference;
import com.tcn.cosmoslibrary.client.ui.lib.CosmosUISystem;
import com.tcn.cosmoslibrary.common.enums.EnumUILock;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * 
 * @author TheCosmicNebula_
 * 
 */
@OnlyIn(Dist.CLIENT)
public class CosmosButtonUILock extends Button {
	private int[] index = new int[] { 0, 12, 24, 36 };
	
	private final ResourceLocation TEXTURE = CosmosReference.RESOURCE.BASE.BUTTON_UI;
	private EnumUILock mode;
	protected int width;
	protected int height;
	public int x;
	public int y;
	protected boolean isHovered;
	public boolean active = true;
	public boolean visible = true;

	public CosmosButtonUILock(EnumUILock modeIn, int x, int y, boolean enabled, boolean visible, Component title, Button.OnPress pressedAction) {
		this(modeIn, x, y, 12, 12, enabled, visible, title, pressedAction, new Button.CreateNarration() {
			
			@Override
			public MutableComponent createNarrationMessage(Supplier<MutableComponent> p_253695_) {
				return ComponentHelper.empty();
			}
		});
	}

	public CosmosButtonUILock(EnumUILock modeIn, int x, int y, int width, int height, boolean enabled, boolean visible, Component title, Button.OnPress pressedAction, Button.CreateNarration narration) {
		super(x, y, width, height, title, pressedAction, narration);
		
		this.mode = modeIn;
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.active = enabled;
		this.visible = visible;
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			this.renderWidget(graphics, mouseX, mouseY, partialTicks);
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
	public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		if (this.mode != null) {
			CosmosUISystem.setTextureWithColourAlpha(graphics.pose(), TEXTURE, new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
			
			this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			int i = this.getHoverState(this.isHovered);
			
			if (this.mode.equals(EnumUILock.PRIVATE)) {
				graphics.blit(TEXTURE, this.x, this.y, index[i], 45, this.width, this.height);
			} else {
				graphics.blit(TEXTURE, this.x, this.y, index[i + 2], 45, this.width, this.height);
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