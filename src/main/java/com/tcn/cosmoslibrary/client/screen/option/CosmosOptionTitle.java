package com.tcn.cosmoslibrary.client.screen.option;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tcn.cosmoslibrary.common.comp.CosmosColour;
import com.tcn.cosmoslibrary.common.comp.CosmosCompHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CosmosOptionTitle extends Option {
	
	private final BaseComponent customCaption;

	public CosmosOptionTitle(CosmosColour colour, boolean bold, String caption) {
		super("");
		
		this.customCaption = CosmosCompHelper.locComp(colour, bold, caption);
	}

	public AbstractWidget createButton(Options options, int xIn, int yIn, int width) {
		return new BlankTileButton(xIn, yIn, width, 16, this.getCaption(), false);
	}

	@Override
	public BaseComponent getCaption() {
		return this.customCaption;
	}
	
	@OnlyIn(Dist.CLIENT)
	public class BlankTileButton extends Button {
		
		public boolean renderBg;
		
		public BlankTileButton(int x, int y, int width, int height, BaseComponent title, boolean renderBg) {
			super(x, y, width, height, title, (button) -> {});
			
			this.renderBg = renderBg;
		}

		public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			this.renderButtonA(matrixStack, mouseX, mouseY, partialTicks);
		}

		public void renderToolTip(PoseStack matrixStack, int mouseX, int mouseY) {
			this.onTooltip.onTooltip(this, matrixStack, mouseX, mouseY);
		}
		
		public void renderButtonA(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			Minecraft minecraft = Minecraft.getInstance();
			Font fontrenderer = minecraft.font;
			
			if (this.renderBg) {
				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
				int i = this.getYImage(this.isHoveredOrFocused());
				
				RenderSystem.enableBlend();
				RenderSystem.defaultBlendFunc();
				RenderSystem.enableDepthTest();
				
				this.blit(matrixStack, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
				this.blit(matrixStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
				this.renderBg(matrixStack, minecraft, mouseX, mouseY);
			}
			
			int j = getFGColor();
			drawCenteredString(matrixStack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
		}

	}

}