package com.tcn.cosmoslibrary.client.screen;

import java.io.IOException;
import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tcn.cosmoslibrary.CosmosReference;
import com.tcn.cosmoslibrary.client.screen.widget.CosmosWidgetList;
import com.tcn.cosmoslibrary.client.util.CosmosScreenHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class CosmosScreenWithList<J extends AbstractContainerMenu> extends AbstractContainerScreen<J> {
	
	protected ArrayList<CosmosWidgetList> widget_list_array = new ArrayList<CosmosWidgetList>();
	private int[] scroll_information;
	public int current_scroll;
	
	private int widgetHeight;
	private int widgetSpacing;
	
	private ResourceLocation skin;
	
	public CosmosScreenWithList(J container, Inventory player_inventory, Component title, int[] scroll_info, ResourceLocation skinIn, int widgetHeight, int widgetSpacing) {
		super(container, player_inventory, title);
		
		this.scroll_information = scroll_info;
		this.skin = skinIn;
		
		this.widgetHeight = widgetHeight;
		this.widgetSpacing = widgetSpacing;
	}
	
	@Override
	protected void init() {
		super.init();
	}
	
	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
		int[] screen_coords = new int[] { ((this.width - this.imageWidth) / 2), (this.height - this.imageHeight) / 2 };
		super.render(stack, mouseX, mouseY, partialTicks);
		
		for (int i = 0; i < this.widget_list_array.size(); ++i) {
			CosmosWidgetList widget = this.widget_list_array.get(i);
			
			if (this.scroll_information[1] > 0) {
				if ((widget.yPosition + widget.height) <= this.scroll_information[1]) {
					if (this.scrollEnabled()) {
						int j = i + this.current_scroll;
						
						if (!(j > this.widget_list_array.size()) && j < this.widget_list_array.size()) {
							CosmosWidgetList firstWidget = this.widget_list_array.get(j);
							
							firstWidget.drawElement(stack, this.font, screen_coords, mouseX, mouseY, j, this.scroll_information[1]);
						}
					} else {
						widget.drawElement(stack, this.font, screen_coords, mouseX, mouseY, i, this.scroll_information[1]);
					}
				}
			}
        }	

		this.renderComponentHoverEffect(stack, Style.EMPTY, mouseX, mouseY);
		this.renderTooltip(stack, mouseX, mouseY);
	}
	
	@Override
	public boolean mouseClicked(double mouse_x, double mouse_y, int mouse_button) {
		for (int i = 0; i < widget_list_array.size(); i++) {
			CosmosWidgetList widget = widget_list_array.get(i);
			
			if (widget.isMouseOver()) {
				widget.mousePressed(this.minecraft, (int) mouse_x, (int) mouse_y);
				this.selected(i);
			} else {
				//element.deselect();
			}
		}
		return super.mouseClicked(mouse_x, mouse_y, mouse_button);
	}
	
	protected void actionPerformed(CosmosWidgetList widget) throws IOException { }
	
	public <T extends CosmosWidgetList> T addListWidget(T widget) {
		this.widget_list_array.add(widget);
		return widget;
	}
	
	public ArrayList<CosmosWidgetList> getWidgetList() {
		return this.widget_list_array;
	}
	
	public void setElementList(ArrayList<CosmosWidgetList> list) {
		this.widget_list_array = list;
	}
	
	public void selected(int index) {
		for (int i = 0; i < this.widget_list_array.size(); i++) {
			CosmosWidgetList widget = this.widget_list_array.get(i);
			widget.deselect();
		}
		
		CosmosWidgetList widget_focused = this.widget_list_array.get(index);
		widget_focused.setSelectedState(true);
	}
	
	public int getCurrentlySelectedInt() {
		for (int i = 0; i < this.widget_list_array.size(); i++) {
			CosmosWidgetList widget = this.widget_list_array.get(i);
			
			if (widget.getSelected()) {
				return i;
			}
		}
		return 0;
	}
	
	public int numOnScreen() {
		return (int) Math.floor(scroll_information[0] / (this.widgetHeight));
	}
	
	public boolean scrollEnabled() {
		if (this.widget_list_array.size() > this.numOnScreen()) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void resize(Minecraft mc, int width, int height) {
		this.widget_list_array.clear();
		
		super.resize(mc, width, height);
	}

	@Override
	protected void renderBg(PoseStack matrixStack, float partialTicks, int posX, int posY) { }
	
	@Override
	public void renderComponentHoverEffect(PoseStack matrixStack, Style style, int mouseX, int mouseY) {
		super.renderComponentHoverEffect(matrixStack, style, mouseX, mouseY);
	}
	
	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) { 
		super.renderLabels(matrixStack, mouseX, mouseY);
	}
	
	public void drawListWithElementsSmall(Font fontRenderer, int drawX, int drawY, int widgetWidth, ArrayList<String> list) {
		int spacing_y = this.widgetHeight + this.widgetSpacing;
		
		ArrayList<CosmosWidgetList> new_list = new ArrayList<CosmosWidgetList>();
		
		if (list.isEmpty()) {
			return;
		} else {
			for (int j = 0; j < list.size(); j++) {
				String string = list.get(j);
				
				if (string != null) {
					for (int o = 0; o < this.getWidgetList().size(); o++) {
						String test_string = this.getWidgetList().get(o).displayString;
						if (string.equals(test_string)) {
							return;
						}
					}
				}
			}
		}
		
		for (int i = 0; i < list.size(); i++) {
			String display_string = list.get(i);
			CosmosWidgetList widget = new CosmosWidgetList(i, drawX, drawY + (spacing_y * i), widgetWidth, this.widgetHeight, this.skin, display_string, CosmosScreenHelper.DEFAULT_COLOUR_FONT_LIST);
			
			if (new_list.size() < 1) {
				new_list.add(widget);
			} else {
				if (!(new_list.contains(widget))) {
					new_list.add(widget);
				}
			}
		}
		
		this.setElementList(new_list);
	}
	
	public void drawScrollElement(PoseStack stack, int[] screen_coords, int drawX, int drawY, boolean toggled, int type, boolean enabled) {
		int[] SCROLL_TYPE_ONE = new int[] { 15, 0 };
		int[] SCROLL_TYPE_TWO = new int[] { 15, 15 };
		
		this.getMinecraft().getTextureManager().bindForSetup(CosmosReference.RESOURCE.BASE.GUI_ELEMENT_MISC_LOC);
		
		if (this.widget_list_array.size() > 0) {
			int div;
			
			if (this.widget_list_array.size() == this.numOnScreen()) {
				div = 1;
			} else {
				div = this.widget_list_array.size() - this.numOnScreen();
			}
			
			int increment = (this.scroll_information[0] - 12) / div;
			
			if (type == 1) {
				if (enabled) {
					CosmosScreenHelper.DRAW.drawStaticElementToggled(this, stack, screen_coords, drawX, Mth.clamp((drawY + (this.current_scroll * increment)), 0, this.scroll_information[2] - this.widgetHeight), SCROLL_TYPE_ONE[0], SCROLL_TYPE_ONE[1], 13, 15, toggled);
				} else {
					CosmosScreenHelper.DRAW.drawStaticElementToggled(this, stack, screen_coords, drawX, drawY, SCROLL_TYPE_ONE[0], SCROLL_TYPE_ONE[1], 13, 15, toggled);
				}
			} else if (type == 2) {
				if (enabled) {
					CosmosScreenHelper.DRAW.drawStaticElementToggled(this, stack, screen_coords, drawX, Mth.clamp((drawY + (this.current_scroll * increment)), 0, this.scroll_information[2] - this.widgetHeight), SCROLL_TYPE_TWO[0], SCROLL_TYPE_TWO[1], 13, 15, toggled);
				} else {
					CosmosScreenHelper.DRAW.drawStaticElementToggled(this, stack, screen_coords, drawX, drawY, SCROLL_TYPE_TWO[0], SCROLL_TYPE_TWO[1], 13, 15, toggled);
				}
			}
			
			this.getMinecraft().getTextureManager().release(CosmosReference.RESOURCE.BASE.GUI_ELEMENT_MISC_LOC);
		}
	}
	
	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double direction) {
		if (this.scrollEnabled()) {
			int maxScroll = this.widget_list_array.size() - this.numOnScreen();
			this.current_scroll += -direction;
			
			if (this.current_scroll >= 0) {
				if (this.current_scroll <= maxScroll) {
					
					if (direction == -1.0F) {
						for (int i = 0; i < this.widget_list_array.size(); i++) {
							CosmosWidgetList widget = this.widget_list_array.get(i);
							
							widget.setToLastElementPosition(this.widgetSpacing);
						}
					} else {
						for (int i = 0; i < this.widget_list_array.size(); i++) {
							CosmosWidgetList widget = this.widget_list_array.get(i);
							
							widget.setToNextElementPostion(this.widgetSpacing);
						}
					}
					
					return true;
				} else {
					this.current_scroll = maxScroll;
					return false;
				}
			} else {
				this.current_scroll = 0;
				return false;
			}
		} else {
			return false;
		}
	}
}