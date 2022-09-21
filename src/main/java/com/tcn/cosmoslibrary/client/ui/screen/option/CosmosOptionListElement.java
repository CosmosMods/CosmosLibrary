package com.tcn.cosmoslibrary.client.ui.screen.option;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CosmosOptionListElement extends CosmosOptionInstance<String> {
	
	public Button.OnPress onPressFunction;
	private MutableComponent buttonText;
	
	public CosmosOptionListElement(MutableComponent captionIn, boolean hasExtraButton, MutableComponent buttonTextIn, Button.OnPress onPressFunctionIn) {
		super(captionIn, CosmosOptionInstance.noTooltip(), (component, value) -> { return ComponentHelper.empty(); }, new CosmosOptionInstance.Enum<String>(ImmutableList.of(""), Codec.STRING), "", "", (help) -> {  }, hasExtraButton, "");
		
		this.onPressFunction = onPressFunctionIn;
		this.buttonText = buttonTextIn;
	}

	@Override
	public AbstractWidget createButton(CosmosOptions options, int xPosIn, int yPosIn, int widthIn, int heightIn) {
		return new BlankTileButton(xPosIn, yPosIn, this.hasResetButton() ? widthIn - (heightIn / 2) - heightIn : widthIn, heightIn, this.caption, true, (button) -> {  });
	}
	
	@Override
	public AbstractWidget createResetButton(CosmosOptions options, int xPosIn, int yPosIn, int widthIn, int heightIn) {
		return new BlankTileButton(xPosIn + widthIn - heightIn, yPosIn, heightIn, heightIn, this.buttonText, true, this.onPressFunction);
	}
	
	@OnlyIn(Dist.CLIENT)
	public class BlankTileButton extends Button {
		
		public boolean doRenderBackground;
		
		public BlankTileButton(int xPosIn, int yPosIn, int widthIn, int heightIn, Component titleMessageIn, boolean doRenderBackgoundIn, Button.OnPress function) {
			super(xPosIn, yPosIn, widthIn, heightIn, titleMessageIn, function);
			
			this.doRenderBackground = doRenderBackgoundIn;
		}

		@Override
		public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
			Minecraft minecraft = Minecraft.getInstance();
			Font fontrenderer = minecraft.font;
			
			if (this.doRenderBackground) {
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

		@Override
		public void renderToolTip(PoseStack matrixStack, int mouseX, int mouseY) {
			this.onTooltip.onTooltip(this, matrixStack, mouseX, mouseY);
		}
	}
}