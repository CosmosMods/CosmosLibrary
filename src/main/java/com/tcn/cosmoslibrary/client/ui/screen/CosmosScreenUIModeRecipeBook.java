package com.tcn.cosmoslibrary.client.ui.screen;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tcn.cosmoslibrary.actual.NetworkManagerCosmos;
import com.tcn.cosmoslibrary.actual.network.PacketUIHelp;
import com.tcn.cosmoslibrary.actual.network.PacketUIMode;
import com.tcn.cosmoslibrary.client.container.CosmosContainerRecipeBookBlockEntity;
import com.tcn.cosmoslibrary.client.ui.lib.CosmosUISystem;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosButtonUIHelp;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosButtonUIMode;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosUIHelpElement;
import com.tcn.cosmoslibrary.common.enums.EnumUIHelp;
import com.tcn.cosmoslibrary.common.enums.EnumUIMode;
import com.tcn.cosmoslibrary.common.interfaces.blockentity.IBlockEntityUIMode;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CosmosScreenUIModeRecipeBook<A extends Container, J extends CosmosContainerRecipeBookBlockEntity<A>> extends AbstractContainerScreen<J> {

	protected ResourceLocation TEXTURE_LIGHT;
	protected ResourceLocation TEXTURE_DARK;

	protected ResourceLocation DUAL_TEXTURE_LIGHT;
	protected ResourceLocation DUAL_TEXTURE_DARK;
	
	protected CosmosButtonUIMode uiModeButton; private int[] uiModeButtonIndex;
	protected CosmosButtonUIHelp uiHelpButton; private int[] uiHelpButtonIndex;
	
	protected List<CosmosUIHelpElement> uiHelpElements = Lists.newArrayList();
	
	private int[] screenCoords;
	
	private boolean hasDualScreen = false;
	private int[] dualScreenIndex;
	
	private boolean renderTitleLabel = true;
	private boolean renderInventoryLabel = true;
	
	private boolean hasUIHelp = false;
	private boolean hasUIHelpElementDeadzone = false;
	private int[] uiHelpElementDeadzone;
	private int uiHelpTitleYOffset = 0;

	public CosmosScreenUIModeRecipeBook(J containerIn, Inventory playerInventoryIn, Component titleIn) {
		super(containerIn, playerInventoryIn, titleIn);
	}
	
	@Override
	protected void init() {
		this.setScreenCoords(CosmosUISystem.getScreenCoords(this, this.imageWidth, this.imageHeight));
		super.init();
		this.addButtons();
		this.addUIHelpElements();
	}
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(poseStack);
		super.render(poseStack, mouseX, mouseY, partialTicks);

		this.renderComponents(poseStack, mouseX, mouseY, partialTicks);
		this.renderComponentHoverEffect(poseStack, Style.EMPTY, mouseX, mouseY);
		this.renderTooltip(poseStack, mouseX, mouseY);
	}
	
	public void renderComponents(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		this.addButtons();
		this.addUIHelpElements();
		this.renderUIHelpElements(poseStack, mouseX, mouseY, partialTicks);
	}

	@Override
	protected void renderBg(PoseStack poseStack, float mouseX, int mouseY, int partialTicks) {
		BlockEntity entity = this.getBlockEntity();
		
		if (entity instanceof IBlockEntityUIMode) {
			IBlockEntityUIMode entityMode = (IBlockEntityUIMode) entity;
			float[] colour = new float[] { 1.0F, 1.0F, 1.0F, 1.0F };

			if (entityMode.getUIHelp().equals(EnumUIHelp.SHOWN)) {
				colour = new float[] { 0.5F, 0.5F, 0.5F, 1.0F };
			}
			
			CosmosUISystem.renderStaticElementWithUIMode(this, poseStack, this.screenCoords, 0, 0, 0, 0, Mth.clamp(this.imageWidth, 0, 256), this.imageHeight, colour, entityMode, new ResourceLocation[] { TEXTURE_LIGHT, TEXTURE_DARK });
			
			if (this.hasDualScreen) {
				if(this.dualScreenIndex != null && this.DUAL_TEXTURE_LIGHT != null && this.DUAL_TEXTURE_LIGHT != null) {
					CosmosUISystem.renderStaticElementWithUIMode(this, poseStack, this.screenCoords, this.dualScreenIndex[0], this.dualScreenIndex[1], 0, 0, 
							Mth.clamp(256 + this.dualScreenIndex[2], 0, 256), this.dualScreenIndex[3], colour, entityMode, new ResourceLocation[] { DUAL_TEXTURE_LIGHT, DUAL_TEXTURE_DARK });
				}
			}
		}
	}

	@Override
	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
		BlockEntity entity = this.getBlockEntity();
		
		if (entity instanceof IBlockEntityUIMode) {
			IBlockEntityUIMode blockEntity = (IBlockEntityUIMode) entity;
			
			if (this.renderTitleLabel) {
				this.font.draw(poseStack, this.title, (float) this.titleLabelX, (float) this.titleLabelY, blockEntity.getUIMode().equals(EnumUIMode.DARK) ? CosmosUISystem.DEFAULT_COLOUR_FONT_LIST : ComponentColour.BLACK.dec());
			}
			
			if (this.renderInventoryLabel) {
				this.font.draw(poseStack, this.playerInventoryTitle, (float) this.inventoryLabelX, (float) this.inventoryLabelY, blockEntity.getUIMode().equals(EnumUIMode.DARK) ? CosmosUISystem.DEFAULT_COLOUR_FONT_LIST : ComponentColour.BLACK.dec());
			}
		}
	}
	
	@Override
	protected void renderTooltip(PoseStack poseStack, int mouseX, int mouseY) {
		if (this.getHasUIHelpShow()) {
			if (this.hasUIHelpElementDeadzone) {
				if (!(mouseX > (this.getScreenCoords()[0] + this.uiHelpElementDeadzone[0]) && mouseX < (this.getScreenCoords()[0] + this.uiHelpElementDeadzone[2]) 
						&& mouseY > (this.getScreenCoords()[1] + this.uiHelpElementDeadzone[1]) && mouseY < (this.getScreenCoords()[1] + this.uiHelpElementDeadzone[3]))) {
					super.renderTooltip(poseStack, mouseX, mouseY);
				}
			} else {
				super.renderTooltip(poseStack, mouseX, mouseY);
			}
		} else {
			super.renderTooltip(poseStack, mouseX, mouseY);
		}
	}
	
	@Override
	public void renderComponentHoverEffect(PoseStack poseStack, Style style, int mouseX, int mouseY) {
		BlockEntity entity = this.getBlockEntity();
		
		if (entity instanceof IBlockEntityUIMode) {
			IBlockEntityUIMode blockEntity = (IBlockEntityUIMode) entity;
			
			if (blockEntity.getUIHelp().equals(EnumUIHelp.HIDDEN)) {
				this.renderStandardHoverEffect(poseStack, style, mouseX, mouseY);
			} else {
				this.renderHelpElementHoverEffect(poseStack, mouseX, mouseY);
			}
			
			if (this.uiModeButton.isMouseOver(mouseX, mouseY)) {
				Component[] comp = new Component[] { 
					ComponentHelper.locComp(ComponentColour.WHITE, false, "cosmoslibrary.gui.ui_mode.info"),
					ComponentHelper.locComp(ComponentColour.GRAY, false, "cosmoslibrary.gui.ui_mode.value").append(blockEntity.getUIMode().getColouredComp())
				};
				
				this.renderComponentTooltip(poseStack, Arrays.asList(comp), mouseX, mouseY);
			}
			
			if (this.getHasUIHelp()) {
				if (this.uiHelpButton.isMouseOver(mouseX, mouseY)) {
					Component[] comp = new Component[] { 
						ComponentHelper.locComp(ComponentColour.WHITE, false, "cosmoslibrary.gui.ui_help.info"),
						ComponentHelper.locComp(ComponentColour.GRAY, false, "cosmoslibrary.gui.ui_help.value").append(blockEntity.getUIHelp().getColouredComp())
					};
					
					this.renderComponentTooltip(poseStack, Arrays.asList(comp), mouseX, mouseY);
				}
			}
		}
	}
	
	protected void renderStandardHoverEffect(PoseStack poseStack, Style style, int mouseX, int mouseY) { }
	
	protected void renderHelpElementHoverEffect(PoseStack poseStack, int mouseX, int mouseY) {
		BlockEntity entity = this.getBlockEntity();
		
		if (entity instanceof IBlockEntityUIMode) {
			IBlockEntityUIMode blockEntity = (IBlockEntityUIMode) entity;
			
			if (this.getHasUIHelpShow()) {
				if (blockEntity.getUIHelp().equals(EnumUIHelp.SHOWN)) {
					for (CosmosUIHelpElement element : this.uiHelpElements) {
						if (element.isMouseOver(mouseX, mouseY)) {
							RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);	
							
							this.renderComponentTooltip(poseStack, element.getHoverElement(), mouseX, mouseY);
						}
					}
					
					RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
					
					Component title = ComponentHelper.locComp(ComponentColour.GREEN, false, "cosmoslibrary.gui_help_title");
					//this.font.draw(poseStack, title, ((this.getScreenCoords()[0] * 2) / 2) + (this.imageWidth / 2) - (this.font.width(title) / 2), this.getScreenCoords()[1] - 8, CosmosColour.WHITE.dec());
					this.renderComponentTooltip(poseStack, Arrays.asList(title), ((this.getScreenCoords()[0] * 2) / 2) + (this.imageWidth / 2) - (this.font.width(title) / 2) - 13, this.getScreenCoords()[1] - 2 + this.uiHelpTitleYOffset);
				}
			}
		}
	}
	
	protected void renderUIHelpElements(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		if (this.getHasUIHelpShow()) {
			for (CosmosUIHelpElement element : this.uiHelpElements) {
				element.render(poseStack, mouseX, mouseY, partialTicks);
			}
			
			for (Widget widget : this.renderables) {
				if (!(widget instanceof CosmosUIHelpElement) && !(widget instanceof CosmosButtonUIHelp) && !(widget instanceof CosmosButtonUIMode)) {
					if (widget instanceof Button) {
						Button button = (Button) widget;
						
						button.active = false;
					}
				}
			}
		}
	}
	
	protected void addButtons() {
		this.clearWidgets();
		int[] screen_coords = CosmosUISystem.getScreenCoords(this, this.imageWidth, this.imageHeight);
		
		BlockEntity entity = this.getBlockEntity();
		
		if (entity instanceof IBlockEntityUIMode) {
			IBlockEntityUIMode blockEntity = (IBlockEntityUIMode) entity;
			
			this.addUIModeButton(blockEntity, screen_coords, uiModeButtonIndex, (button) -> { this.pushButton(this.uiModeButton); });
			
			if (this.getHasUIHelp()) {
				this.addUIHelpButton(blockEntity, screen_coords, uiHelpButtonIndex, (button) -> { this.pushButton(this.uiHelpButton); });
			}
		}
	}
	
	protected void pushButton(Button button) {
		BlockEntity entity = this.getBlockEntity();
		
		if (entity instanceof IBlockEntityUIMode) {
			IBlockEntityUIMode blockEntity = (IBlockEntityUIMode) entity;
			
			if (button.equals(this.uiModeButton)) {				
				NetworkManagerCosmos.sendToServer(new PacketUIMode(this.menu));
				blockEntity.cycleUIMode();
			}
			
			else if (button.equals(this.uiHelpButton)) {				
				NetworkManagerCosmos.sendToServer(new PacketUIHelp(this.menu));
				blockEntity.cycleUIHelp();
			}
		}
	}
	
	protected void addUIModeButton(IBlockEntityUIMode entityIn, int[] screen_coords, int[] indexIn, Button.OnPress pressAction) {
		this.uiModeButton = this.addRenderableWidget(new CosmosButtonUIMode(entityIn.getUIMode(), screen_coords[0] + indexIn[0], screen_coords[1] + indexIn[1], true, true, ComponentHelper.locComp(""), pressAction));
	}

	protected void addUIHelpButton(IBlockEntityUIMode entityIn, int[] screen_coords, int[] indexIn, Button.OnPress pressAction) {
		this.uiHelpButton = this.addRenderableWidget(new CosmosButtonUIHelp(entityIn.getUIHelp(), screen_coords[0] + indexIn[0], screen_coords[1] + indexIn[1], true, true, ComponentHelper.locComp(""), pressAction));
	}
	
	protected void addUIHelpElements() { 
		this.clearUIHelpElementList();
	}

	protected void addRenderableUIHelpElement(int[] screenCoords, int xIn, int yIn, int widthIn, int heightIn, Component... descIn) {
		this.addRenderableUIHelpElement(screenCoords, xIn, yIn, widthIn, heightIn, true, descIn);
	}
	
	protected void addRenderableUIHelpElement(int[] screenCoords, int xIn, int yIn, int widthIn, int heightIn, boolean isVisible, Component... descIn) {
		this.addUIHelpElement(new CosmosUIHelpElement(screenCoords[0] + xIn, screenCoords[1] + yIn, widthIn, heightIn, descIn).setVisible(isVisible));
	}
	
	private CosmosUIHelpElement addUIHelpElement(CosmosUIHelpElement elementIn) {
		this.uiHelpElements.add(elementIn);
		//this.renderables.add(elementIn);
		return elementIn;
	}
	
	protected void clearUIHelpElementList() {
		this.uiHelpElements.clear();
	}
	
	protected boolean getHasUIHelp() {
		return this.hasUIHelp;
	}
	
	protected boolean getHasUIHelpShow() {
		BlockEntity entity = this.getBlockEntity();
		
		if (entity instanceof IBlockEntityUIMode) {
			IBlockEntityUIMode blockEntity = (IBlockEntityUIMode) entity;
			
			return this.hasUIHelp && blockEntity.getUIHelp().equals(EnumUIHelp.SHOWN);
		}
		return false;
	}
	
	protected void setDark(ResourceLocation textureIn) {
		this.TEXTURE_DARK = textureIn;
	}

	protected void setLight(ResourceLocation textureIn) {
		this.TEXTURE_LIGHT = textureIn;
	}
	
	protected void setUIModeButtonIndex(int posX, int posY) {
		this.uiModeButtonIndex = new int[] { posX, posY };
	}
	
	protected void setHasUIHelp() {
		this.hasUIHelp = true;
	}
	
	protected void setUIHelpButtonIndex(int posX, int posY) {
		this.setHasUIHelp();
		this.uiHelpButtonIndex = new int[] { posX, posY };
	}

	protected void setHasUIElementDeadzone() {
		this.hasUIHelpElementDeadzone = true;
	}

	protected void setUIHelpElementDeadzone(int minX, int minY, int maxX, int maxY) {
		this.setHasUIElementDeadzone();
		this.uiHelpElementDeadzone = new int[] { minX, minY, maxX, maxY };
	}
	
	protected void setUIHelpTitleOffset(int yOffset) {
		this.uiHelpTitleYOffset = yOffset;
	}
	
	protected void setImageDims(int widthIn, int heightIn) {
		this.imageWidth = widthIn;
		this.imageHeight = heightIn;
	}
	
	protected void setTitleLabelDims(int posX, int posY) {
		this.titleLabelX = posX;
		this.titleLabelY = posY;
	}
	
	protected void setInventoryLabelDims(int posX, int posY) {
		this.inventoryLabelX = posX;
		this.inventoryLabelY = posY;
	}
	
	protected void setScreenCoords(int[] coordsIn) {
		this.screenCoords = coordsIn;
	}
	
	protected void setDualScreen() {
		this.hasDualScreen = true;
	}
	
	protected void setDualScreenIndex(int posX, int posY, int width, int height) {
		this.setDualScreen();
		this.dualScreenIndex = new int[] { posX, posY, width, height };
	}

	protected void setDualDark(ResourceLocation textureIn) {
		this.setDualScreen();
		this.DUAL_TEXTURE_DARK = textureIn;
	}

	protected void setDualLight(ResourceLocation textureIn) {
		this.setDualScreen();
		this.DUAL_TEXTURE_LIGHT = textureIn;
	}

	protected void setNoTitleLabel() {
		this.renderTitleLabel = false;
	}
	
	protected void setNoInventoryLabel() {
		this.renderInventoryLabel = false;
	}
	
	protected int[] getScreenCoords() {
		return this.screenCoords;
	}
	
	public BlockEntity getBlockEntity() {
		CosmosContainerRecipeBookBlockEntity<A> container = (CosmosContainerRecipeBookBlockEntity<A>) this.menu;
		Level level = container.getLevel();
		BlockPos pos = container.getBlockPos();
		
		return level.getBlockEntity(pos);
	}
	
}