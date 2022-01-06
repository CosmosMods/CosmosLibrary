package com.tcn.cosmoslibrary.client.ui.screen;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tcn.cosmoslibrary.CosmosReference.RESOURCE.BASE;
import com.tcn.cosmoslibrary.client.container.CosmosContainerMenuBlockEntity;
import com.tcn.cosmoslibrary.client.ui.lib.CosmosUISystem;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosListWidget;
import com.tcn.cosmoslibrary.common.enums.EnumUIMode;
import com.tcn.cosmoslibrary.common.interfaces.blockentity.IBlockEntityUIMode;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CosmosScreenUIModeList<J extends CosmosContainerMenuBlockEntity> extends CosmosScreenUIMode<J> {

	protected ResourceLocation WIDGET_TEXTURE;
	protected ArrayList<CosmosListWidget> widgetList = new ArrayList<CosmosListWidget>();
	protected ArrayList<String> fromList = new ArrayList<String>();

	private boolean scrollEnabled = false;
	private int currentScroll;
	
	private int[] listIndex;
	private int[] scrollElementIndex;
	
	public CosmosScreenUIModeList(J containerIn, Inventory playerInventoryIn, Component titleIn) {
		super(containerIn, playerInventoryIn, titleIn);
	}
	
	@Override
	protected void init() {
		super.init();
	}
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		super.render(poseStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void renderBg(PoseStack poseStack, float mouseX, int mouseY, int partialTicks) {
		super.renderBg(poseStack, mouseX, mouseY, partialTicks);
	}
	
	protected void updateWidgetList() {
		this.renderSmallWidgetList();
	}
	
	@Override
	public void renderComponents(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		this.renderScrollElement(poseStack);
		this.updateWidgetList();
		this.renderWidgetList(poseStack, mouseX, mouseY, partialTicks);
		super.renderComponents(poseStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		for (int i = 0; i < widgetList.size(); i++) {
			CosmosListWidget widget = this.getListWidget(i);
			
			if (widget.isMouseOver()) {
				widget.mousePressed(this.minecraft, mouseX, mouseY);
				this.selectWidget(i);
			} else {
				if (mouseX < this.listIndex[0] && mouseX > this.listIndex[0] + this.listIndex[2]) {
					if (mouseY < this.listIndex[1] && mouseY > this.listIndex[1] + this.listIndex[3]) {
						//this.deselectWidget(i);//widget.deselect();
					}
				}
			}
		}
		
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double direction) {
		if (this.scrollEnabled()) {
			int maxScroll = this.widgetList.size() - this.widgetCount();
			this.currentScroll += -direction;
			
			if (this.currentScroll >= 0) {
				if (this.currentScroll <= maxScroll) {
					
					if (direction == -1.0F) {
						for (int i = 0; i < this.widgetList.size(); i++) {
							CosmosListWidget widget = this.widgetList.get(i);
							
							widget.setPositionToLastWidget(this.listIndex[5]);
						}
					} else {
						for (int i = 0; i < this.widgetList.size(); i++) {
							CosmosListWidget widget = this.widgetList.get(i);
							
							widget.setPositionToNextWidget(this.listIndex[5]);
						}
					}
					
					return true;
				} else {
					this.currentScroll = maxScroll;
					return false;
				}
			} else {
				this.currentScroll = 0;
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public void resize(Minecraft mc, int width, int height) {
		this.clearWidgetList();
		super.resize(mc, width, height);
	}
	
	protected void setListDims(int x, int y, int width, int height, int widgetHeightIn, int widgetSpacingIn) {
		this.listIndex = new int[] { x, y, width, height, widgetHeightIn, widgetSpacingIn};
	}
	
	protected void setScrollElementDims(int x, int y) {
		this.scrollEnabled = true;
		this.scrollElementIndex = new int[] { x, y };
	}

	protected void setWidgetTexture(ResourceLocation textureIn) {
		this.WIDGET_TEXTURE = textureIn;
	}
	
	protected void updateFromList(ArrayList<String> fromListIn) {
		this.fromList = fromListIn;
	}
	
	public CosmosListWidget addListWidget(CosmosListWidget widget) {
		this.widgetList.add(widget);
		return widget;
	}
	
	protected CosmosListWidget getListWidget(int index) {
		return this.widgetList.get(index);
	}

	protected void setWidgetList(ArrayList<CosmosListWidget> list) {
		this.widgetList = list;
	}
	
	protected ArrayList<CosmosListWidget> getWidgetList() {
		return this.widgetList;
	}
	
	protected void selectWidget(int index) {
		for (int i = 0; i < this.widgetList.size(); i++) {
			this.widgetList.get(i).deselect();
		}
		
		this.widgetList.get(index).setSelectedState(true);
	}

	protected void deselectWidget(int index) {
		this.widgetList.get(index).setSelectedState(false);
	}
	
	public int getSelectedWidgetIndex() {
		for (int i = 0; i < this.widgetList.size(); i++) {
			if (this.getListWidget(i).getSelected()) {
				return i;
			}
		}
		
		return 0;
	}
	
	public int widgetCount() {
		return (int) Math.floor(this.listIndex[3] / (this.listIndex[4]));
	}
	
	public boolean scrollEnabled() {
		if (this.widgetList.size() > this.widgetCount()) {
			return true;
		} else {
			return false;
		}
	}

	protected void clearWidgetList() {
		this.widgetList.clear();
	}
	
	private void renderWidgetList(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		for (int i = 0; i < this.widgetList.size(); i++) {
			CosmosListWidget widget = this.getListWidget(i);
			
			if ((widget.getYPos() + widget.getHeight()) <= this.listIndex[2]) {
				if (this.scrollEnabled()) {
					int j = i + this.currentScroll;
					
					if (!(j > this.widgetList.size()) && j < this.widgetList.size()) {
						CosmosListWidget firstWidget = this.widgetList.get(j);

						firstWidget.renderWidget(poseStack, this.font, this.getScreenCoords(), mouseX, mouseY, j, this.listIndex[2]);
					}
				} else {
					widget.renderWidget(poseStack, this.font, this.getScreenCoords(), mouseX, mouseY, i, this.listIndex[2]);
				}
			}
		}
	}
	
	protected void renderSmallWidgetList() {
		int spacing_y = this.listIndex[4] + this.listIndex[5];
		
		ArrayList<CosmosListWidget> new_list = new ArrayList<CosmosListWidget>();
		
		if (this.fromList.isEmpty()) {
			return;
		} else {
			for (int j = 0; j < this.fromList.size(); j++) {
				String string = this.fromList.get(j);
				
				if (string != null) {
					for (int i = 0; i < this.getWidgetList().size(); i++) {
						String test_string = this.getWidgetList().get(i).getDisplayString();
						if (string.equals(test_string)) {
							return;
						}
					}
				}
			}
		}
		
		for (int i = 0; i < this.fromList.size(); i++) {
			String display_string = this.fromList.get(i);
			CosmosListWidget widget = new CosmosListWidget(this.listIndex[0], this.listIndex[1] + (spacing_y * i), this.listIndex[2], this.listIndex[4], this.WIDGET_TEXTURE, display_string, ComponentColour.WHITE);
			
			if (new_list.size() < 1) {
				new_list.add(widget);
			} else {
				if (!(new_list.contains(widget))) {
					new_list.add(widget);
				}
			}
		}
		
		this.setWidgetList(new_list);
	}
	
	protected void renderScrollElement(PoseStack poseStack) {
		int[] scrollType = new int[] { 15, 0, 15 };
		
		CosmosUISystem.setTextureWithColour(poseStack, BASE.GUI_ELEMENT_MISC_LOC, new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
		
		if (this.widgetList.size() > 0) {
			int div;
			
			if (this.widgetList.size() == this.widgetCount()) {
				div = 1;
			} else {
				div = this.widgetList.size() - this.widgetCount();
			}
			
			int increment = (this.listIndex[3] - 12) / div;
			
			int posX = this.scrollElementIndex[0];
			int posY = Mth.clamp((this.scrollElementIndex[1] + (this.currentScroll * increment)), 0, (this.listIndex[1] + this.listIndex[3] - 1) - this.listIndex[4]);

			BlockEntity entity = this.getBlockEntity();
			
			if (entity instanceof IBlockEntityUIMode) {
				IBlockEntityUIMode blockEntity = (IBlockEntityUIMode) entity;
				
				int type = blockEntity.getUIMode().equals(EnumUIMode.DARK) ? 0 : 1;
				
				CosmosUISystem.renderStaticElementToggled(this, poseStack, this.getScreenCoords(), posX, this.scrollEnabled ? posY : this.scrollElementIndex[1], scrollType[0], scrollType[type + 1], 13, 15, true);
			}
		}
	}
}