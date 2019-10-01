package com.zeher.zeherlib.client.container;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.api.connection.EnumSide;
import com.zeher.zeherlib.api.util.GuiColours;
import com.zeher.zeherlib.client.gui.button.GuiEnergyButtonCustom;
import com.zeher.zeherlib.client.gui.button.GuiIconButton;
import com.zeher.zeherlib.client.gui.element.GuiListElement;
import com.zeher.zeherlib.client.gui.util.ModGuiUtil;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiContainerElements extends GuiContainer {
	
	public int current_scroll;
	private int max_scroll;
	
	private ArrayList<GuiListElement> element_list = new ArrayList<GuiListElement>();
	
	public GuiContainerElements(Container container) {
		super(container);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		for (int i = 0; i < this.element_list.size(); ++i) {
            ((GuiListElement)this.element_list.get(i)).drawElement(this.mc, mouseX, mouseY, partialTicks);
        }
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	protected void mouseClicked(int mouse_x, int mouse_y, int mouse_button) throws IOException {
		for (int i = 0; i < element_list.size(); i++) {
			GuiListElement element = element_list.get(i);
			if (element.isMouseOver()) {
				element.mousePressed(mc, mouse_x, mouse_y);
			}
		}
		super.mouseClicked(mouse_x, mouse_y, mouse_button);
	}
	
	protected void actionPerformed(GuiListElement button) throws IOException { }
	
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
        int i = Mouse.getEventDWheel();
        
    	this.current_scroll = (this.current_scroll - i);
    	this.current_scroll = MathHelper.clamp(this.current_scroll, 0, this.max_scroll);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
	}

	public <T extends GuiListElement> T addListElement(T element) {
		this.element_list.add(element);
		return element;
	}
	
	public ArrayList<GuiListElement> getElementList() {
		return this.element_list;
	}
	
	public void setElementList(ArrayList<GuiListElement> list) {
		this.element_list = list;
	}
}