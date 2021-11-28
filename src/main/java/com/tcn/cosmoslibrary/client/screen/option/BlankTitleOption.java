package com.tcn.cosmoslibrary.client.screen.option;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tcn.cosmoslibrary.common.comp.CosmosColour;
import com.tcn.cosmoslibrary.common.comp.CosmosCompHelper;

import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlankTitleOption extends AbstractOption {
	
	private final IFormattableTextComponent customCaption;

	public BlankTitleOption(CosmosColour colour, boolean bold, String caption) {
		super("");
		
		this.customCaption = CosmosCompHelper.locComp(colour, bold, caption);
	}

	public Widget createButton(GameSettings options, int xIn, int yIn, int width) {
		return new BlankTileButton(xIn, yIn, width, 16, this.getCaption(), false);
	}

	@Override
	public IFormattableTextComponent getCaption() {
		return this.customCaption;
	}
	
	@OnlyIn(Dist.CLIENT)
	public class BlankTileButton extends Button {
		
		public boolean renderBg;
		
		public BlankTileButton(int x, int y, int width, int height, ITextComponent title, boolean renderBg) {
			super(x, y, width, height, title, (button) -> {});
			
			this.renderBg = renderBg;
		}

		public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			this.renderButtonA(matrixStack, mouseX, mouseY, partialTicks);
		}

		public void renderToolTip(MatrixStack matrixStack, int mouseX, int mouseY) {
			this.onTooltip.onTooltip(this, matrixStack, mouseX, mouseY);
		}

		@SuppressWarnings("deprecation")
		public void renderButtonA(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			Minecraft minecraft = Minecraft.getInstance();
			FontRenderer fontrenderer = minecraft.font;
			
			if (this.renderBg) {
				minecraft.getTextureManager().bind(WIDGETS_LOCATION);
				RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
				int i = this.getYImage(this.isHovered());
				
				RenderSystem.enableBlend();
				RenderSystem.defaultBlendFunc();
				RenderSystem.enableDepthTest();
				
				this.blit(matrixStack, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
				this.blit(matrixStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
				this.renderBg(matrixStack, minecraft, mouseX, mouseY);
			}
			
			int j = getFGColor();
			drawCenteredString(matrixStack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
		}

	}
}