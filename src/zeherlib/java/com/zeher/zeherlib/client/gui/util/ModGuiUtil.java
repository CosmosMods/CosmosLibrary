package com.zeher.zeherlib.client.gui.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.api.util.TextUtil;
import com.zeher.zeherlib.client.container.GuiContainerElements;
import com.zeher.zeherlib.client.gui.button.GuiFluidButton;
import com.zeher.zeherlib.client.gui.element.GuiListElement;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.IFluidTank;

public class ModGuiUtil {
	
	public static final int DEFAULT_COLOUR_BACKGROUND = 4210752;
	public static final int DEFAULT_COLOUR_FONT_LIST = 16777215;
	
	public static class DRAW {
		public static void drawPowerBar(GuiContainer container, int[] screen_coords, int draw_x, int draw_y, int scaled, int[] bar_location, boolean has_stored) {
			container.mc.getTextureManager().bindTexture(Reference.GUI.RESOURCE.ENERGY_BAR_TEXTURE);
			
			if (has_stored) {
				container.drawTexturedModalRect(screen_coords[0] + draw_x, (screen_coords[1] + bar_location[2] + draw_y) - scaled, bar_location[0], bar_location[1] - scaled, bar_location[3], scaled);
			}
			
			container.mc.getTextureManager().deleteTexture(Reference.GUI.RESOURCE.ENERGY_BAR_TEXTURE);
		}
		
		public static void drawSlot(GuiContainer container, int[] screen_coords, int draw_x, int draw_y, int[] slot_location) {
			container.mc.getTextureManager().bindTexture(Reference.GUI.RESOURCE.SLOT_TEXTURES);
			
			container.drawTexturedModalRect(screen_coords[0] + draw_x, screen_coords[1] + draw_y, slot_location[0], slot_location[1], slot_location[2], slot_location[3]);
			
			container.mc.getTextureManager().deleteTexture(Reference.GUI.RESOURCE.SLOT_TEXTURES);
		}
		
		public static void drawBackground(GuiContainer container, int[] screen_coords, ResourceLocation texture) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			container.mc.getTextureManager().bindTexture(texture);
			
			container.drawTexturedModalRect(screen_coords[0], screen_coords[1], 0, 0, container.getXSize(), container.getYSize());
		}
		
		public static void drawFluidTank(GuiContainer container, int[] screen_coords, int draw_x, int draw_y, IFluidTank tank, int scaled) {
			if (tank.getFluidAmount() > 0) {
				TextureAtlasSprite fluid_texture = container.mc.getTextureMapBlocks().getTextureExtry(tank.getFluid().getFluid().getStill().toString());
				container.mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			
				if (!(fluid_texture == null)) {
					if(scaled <= 30){
						container.drawTexturedModalRect(screen_coords[0] + draw_x, screen_coords[1] + draw_y + 38 - scaled, fluid_texture, 16, scaled);
					} else if (scaled > 30 && scaled <= 45){
						container.drawTexturedModalRect(screen_coords[0] + draw_x, screen_coords[1] + draw_y + 38 - scaled, fluid_texture, 16, scaled / 2 + 1);
						container.drawTexturedModalRect(screen_coords[0] + draw_x, screen_coords[1] + draw_y + 38 - scaled/2, fluid_texture, 16, scaled / 2);
					} else if (scaled > 45){
						container.drawTexturedModalRect(screen_coords[0] + draw_x, screen_coords[1] + draw_y + 38 - scaled, fluid_texture, 16, scaled / 2);
						container.drawTexturedModalRect(screen_coords[0] + draw_x, screen_coords[1] + draw_y + 38 - scaled/2, fluid_texture, 16, scaled / 2);
					}
				}
			}
		}
		
		public static void drawHoveringTextPowerProducer(GuiContainer container, int[] screen_coords, int draw_x, int draw_y, int mouse_x, int mouse_y, int stored, int generation_rate, boolean producing) {
			if (IS_HOVERING.isHoveringPower(mouse_x, mouse_y, screen_coords[0] + draw_x, screen_coords[1] + draw_y)) {
				if (producing) {
					container.drawHoveringText(TEXT_LIST.generationText(stored, generation_rate), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
				} else {
					container.drawHoveringText(TEXT_LIST.storedTextNo(stored), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
				}
			}
		}
		
		public static void drawHoveringTextFluid(GuiContainer container, int[] screen_coords, int draw_x, int draw_y, int mouse_x, int mouse_y, IFluidTank tank) {
			if (IS_HOVERING.isHoveringFluid(mouse_x, mouse_y, screen_coords[0] + draw_x, screen_coords[1] + draw_y)) {
				if (tank.getFluidAmount() > 0) {
					container.drawHoveringText(TEXT_LIST.fluidText(tank.getFluid().getLocalizedName(), tank.getFluidAmount()), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
				} else {
					container.drawHoveringText(TEXT_LIST.fluidTextEmpty(), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
				}
			}
		}
		
		public static void drawHoveringTextEmptyFluidButton(GuiContainer container, int[] screen_coords, int draw_x, int draw_y, int mouse_x, int mouse_y, boolean has_fluid) {
			if (IS_HOVERING.isHoveringButtonStandard(mouse_x, mouse_y, screen_coords[0] + draw_x, screen_coords[1] + draw_y)) {
				if (has_fluid) {
					if (container.isShiftKeyDown()) {
						container.drawHoveringText(TEXT_LIST.emptyFluidTankDo(), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
					} else {
						container.drawHoveringText(TEXT_LIST.emptyFluidTank(), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
					}
				}
			}
		}
		
		public static void drawScaledElementUpNestled(GuiContainer container, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int height, int scaled) {
			container.drawTexturedModalRect(screen_coords[0] + draw_x, (screen_coords[1] + draw_y + height) - scaled, texture_x, (texture_y + height) - scaled, width, scaled);
		}
		
		public static void drawScaledElementDownNestled(GuiContainer container, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int scaled) {
			container.drawTexturedModalRect(screen_coords[0] + draw_x, screen_coords[1] + draw_y, texture_x, texture_y, width, scaled);
		}
		
		public static void drawScaledElementRightNestled(GuiContainer container, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int height, int scaled) {
			container.drawTexturedModalRect(screen_coords[0] + draw_x, screen_coords[1] + draw_y, texture_x, texture_y, scaled + 1, height);
		}
		
		public static void drawScaledElementUpExternal(GuiContainer container, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int height, int scaled, ResourceLocation texture) {
			container.mc.getTextureManager().bindTexture(texture);
			
			container.drawTexturedModalRect(screen_coords[0] + draw_x, (screen_coords[1] + draw_y + height) - scaled, texture_x, (texture_y + height) - scaled, width, scaled);
			
			container.mc.getTextureManager().deleteTexture(texture);
		}
		
		public static void drawScaledElementDownExternal(GuiContainer container, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int scaled, ResourceLocation texture) {
			container.mc.getTextureManager().bindTexture(texture);
			
			container.drawTexturedModalRect(screen_coords[0] + draw_x, screen_coords[1] + draw_y, texture_x, texture_y + scaled, width, scaled);
			
			container.mc.getTextureManager().deleteTexture(texture);
		}
		
		public static void drawButton(GuiContainer container, GuiButton button) {
			
		}
		
		public static GuiFluidButton createFluidButton(int id, int[] screen_coords, int draw_x, int draw_y, int size, boolean enabled) {
			GuiFluidButton button;
			if (enabled) {
				button = new GuiFluidButton(id, screen_coords[0] + draw_x, screen_coords[1] + draw_y, size, 1, false);
			} else {
				button = new GuiFluidButton(id, screen_coords[0] + draw_x, screen_coords[1] + draw_y, size, 4, true);
			}
			return button;
		}
		
		public static void drawStaticElement(GuiContainer container, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int height) {
			container.drawTexturedModalRect(screen_coords[0] + draw_x, screen_coords[1] + draw_y, texture_x, texture_y, width, height);
		}
		
		public static void drawStaticElementToggled(GuiContainer container, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int height, boolean enabled) {
			if (enabled) {
				container.drawTexturedModalRect(screen_coords[0] + draw_x, screen_coords[1] + draw_y, texture_x, texture_y, width, height);
			}
		}	
	}
	
	public static class ELEMENT {
		
		public static class SCROLL {
			public static final int[] TYPE_ONE = new int[] { 15, 0 };
			public static final int[] TYPE_TWO = new int[] { 15, 15 };
			
			public static class DRAW {
				public static void drawScrollElement(GuiContainerElements container, int[] screen_coords, int draw_x, int draw_y, int current_scroll, boolean toggled, int type) {
					
					container.mc.getTextureManager().bindTexture(Reference.GUI.RESOURCE.ELEMENT_MISC_TEXTURES);
					
					if (type == 1) {
						ModGuiUtil.DRAW.drawStaticElementToggled(container, screen_coords, draw_x, (draw_y + (current_scroll / 10)), TYPE_ONE[0], TYPE_ONE[1], 13, 15, toggled);
					} else if (type == 2) {
						ModGuiUtil.DRAW.drawStaticElementToggled(container, screen_coords, draw_x, (draw_y + (current_scroll / 10)), TYPE_TWO[0], TYPE_TWO[1], 13, 15, toggled);
					} else {
						
					}
					
					container.mc.getTextureManager().deleteTexture(Reference.GUI.RESOURCE.ELEMENT_MISC_TEXTURES);
				}
			}
		
		}
		
		public static class LIST {
			public static class DRAW {
				public static void drawListWithElementsSmall(GuiContainerElements container, FontRenderer fontRenderer, int[] screen_coords, int draw_x, int draw_y, int width, int index_from, ArrayList<String> list) {
					int height = 14;
					int spacing_y = height + 2;
					int index_from_clamped = MathHelper.clamp(index_from, 0, list.size());
					
					ArrayList<GuiListElement> new_list = new ArrayList<GuiListElement>();
					
					if (list.isEmpty()) {
						return;
					}
					
					for (int i = 0 + index_from_clamped; i < list.size(); i++) {
						String display_string = list.get(i);
						
						GuiListElement add = new GuiListElement(i, screen_coords[0] + draw_x, screen_coords[1] + draw_y + (spacing_y * i), width, height, display_string, DEFAULT_COLOUR_FONT_LIST);
						
						if (new_list.size() < 1) {
							new_list.add(add);
						} else {
							for (int x = 0; x < new_list.size(); x++) {
								String display = new_list.get(x).displayString;
								if(!(new_list.get(x).displayString.equals(display_string))) {
									new_list.add(add);
								}
							}
						}
					}
					
					container.setElementList(new_list);
				}
				
				
				public static void drawListWithElementsLarge(GuiContainerElements container, FontRenderer fontRenderer, int[] screen_coords, int draw_x, int draw_y, int width, ArrayList<String> list) {
					int height = 20;
					int spacing_y = height + 2;
					
					for (int i = 0; i < list.size(); i++) {
						String display_string = list.get(i);
						
						container.getElementList().add(new GuiListElement(i, screen_coords, draw_x, draw_y + (spacing_y * i), width, height, display_string, DEFAULT_COLOUR_FONT_LIST));
					}
				}
			}
		}
	}
	
	public static class FONT_LIST {
		public static class DRAW {
			public static void drawList(GuiContainer container, FontRenderer fontRenderer, int[] screen_coords, int draw_x, int draw_y, ArrayList<String> list) {
				int spacing_x = 5;
				int spacing_y = 4;
				int spacing_y_text = 10;
				
				for (int i = 0; i < list.size(); i++) {
					String display_string = list.get(i);
					
					fontRenderer.drawString(display_string, screen_coords[0] + draw_x + spacing_x, screen_coords[1] + draw_y + spacing_y + (spacing_y_text * i), DEFAULT_COLOUR_FONT_LIST);
				}
			}
			
			public static void drawList(GuiContainer container, FontRenderer fontRenderer, int[] screen_coords, int draw_x, int draw_y, int color, ArrayList<String> list) {
				int spacing_x = 5;
				int spacing_y = 8;
				
				for (int i = 0; i < list.size(); i++) {
					String display_string = list.get(i);
					
					fontRenderer.drawString(display_string, screen_coords[0] + draw_x + spacing_x, screen_coords[1] + draw_y + spacing_y + (spacing_y * i), DEFAULT_COLOUR_FONT_LIST);
				}
			}
		}
	}
	
	public static class FONT {
		
		public static class DRAW {
			public static void drawStringUnformatted(FontRenderer font, int[] screen_coords, int draw_x, int draw_y, String draw_text) {
				font.drawString(draw_text, screen_coords[0] + draw_x, screen_coords[1] + draw_y, DEFAULT_COLOUR_BACKGROUND);
			}
			
			public static void drawStringUnformatted(FontRenderer font, int[] screen_coords, int draw_x, int draw_y, String draw_text, int colour) {
				font.drawString(draw_text, screen_coords[0] + draw_x, screen_coords[1] + draw_y, colour);
			}
			
			public static void drawStringFormatted(FontRenderer font, int[] screen_coords, int draw_x, int draw_y, String draw_text) {
				font.drawString(I18n.format(draw_text), screen_coords[0] + draw_x, screen_coords[1] + draw_y, DEFAULT_COLOUR_BACKGROUND);
			}
			
			public static void drawStringFormatted(FontRenderer font, int[] screen_coords, int draw_x, int draw_y, String draw_text, int colour) {
				font.drawString(I18n.format(draw_text), screen_coords[0] + draw_x, screen_coords[1] + draw_y, colour);
			}
			
			public static void drawInventoryString(FontRenderer font, int[] screen_coords, int draw_x, int draw_y) {
				font.drawString(I18n.format("container.inventory"), screen_coords[0] + draw_x, screen_coords[1] + draw_y, DEFAULT_COLOUR_BACKGROUND);
			}
			
			public static void drawInventoryString(FontRenderer font, int[] screen_coords, int draw_x, int draw_y, int colour) {
				font.drawString(I18n.format("container.inventory"), screen_coords[0] + draw_x, screen_coords[1] + draw_y, colour);
			}
			
			public static void drawCustomString(IInventory inventory, FontRenderer font, int[] screen_coords, int draw_x, int draw_y) {
				String name = inventory.hasCustomName() ? inventory.getName() : I18n.format(inventory.getName());
				font.drawString(name, screen_coords[0] + draw_x, screen_coords[1] + draw_y, DEFAULT_COLOUR_BACKGROUND);
			}
			
			public static void drawCustomString(IInventory inventory, FontRenderer font, int[] screen_coords, int draw_x, int draw_y, int colour) {
				String name = inventory.hasCustomName() ? inventory.getName() : I18n.format(inventory.getName());
				font.drawString(name, screen_coords[0] + draw_x, screen_coords[1] + draw_y, colour);
			}
		}
	}
	
	public static class UTILITY {
		
	}
	
	public static class IS_HOVERING {
		public static boolean isHoveringPower(int mouse_x, int mouse_y, int x, int y) {
			if (mouse_x >= x && mouse_x <= x + 17) {
				if (mouse_y >= y && mouse_y <= y + 62) {
					return true;
				}
			}
			return false;
		}
		
		public static boolean isHoveringPowerSmall(int mouse_x, int mouse_y, int x, int y) {
			if (mouse_x >= x && mouse_x <= x + 17) {
				if (mouse_y >= y && mouse_y <= y + 40) {
					return true;
				}
			}
			return false;
		}
		
		public static boolean isHovering(int mouse_x, int mouse_y, int min_x, int max_x, int min_y, int max_y) {
			if (mouse_x >= min_x && mouse_x <= max_x) {
				if (mouse_y >= min_y && mouse_y <= max_y) {
					return true;
				}
			}
			return false;
		}
		
		public static boolean isHoveringFluid(int mouse_x, int mouse_y, int x, int y) {
			if (mouse_x >= x && mouse_x <= x + 16) {
				if (mouse_y >= y && mouse_y <= y + 38) {
					return true;
				}
			}
			return false;
		}
		
		public static boolean isHoveringButtonStandard(int mouse_x, int mouse_y, int x, int y) {
			if (mouse_x >= x && mouse_x <= x + 18) {
				if (mouse_y >= y && mouse_y <= y + 18) {
					return true;
				}
			}
			return false;
		}
		
		public static boolean isHoveringButton(int mouse_x, int mouse_y, int x, int y, int x_size, int y_size) {
			if (mouse_x >= x && mouse_x <= x + x_size) {
				if (mouse_y >= y && mouse_y <= y + y_size) {
					return true;
				}
			}
			return false;
		}
	}
	
	public static class TEXT_LIST {
		public static List storedTextRF(int stored, int speed) {
			String[] description = { TextUtil.GREEN + "Stored: " + TextUtil.LIGHT_GRAY + stored , TextUtil.RED + "Using: " + TextUtil.LIGHT_GRAY + speed + TextUtil.RED + " RF/t."};
			return Arrays.asList(description);
		}
		
		public static List storedTextNo(int stored) {
			String[] description = { TextUtil.GREEN + "Stored: " + TextUtil.LIGHT_GRAY + stored };
			return Arrays.asList(description);
		}
		
		public static List fluidText(String name, int amount) {
			String[] description = { TextUtil.TEAL + "Fluid: " + name , TextUtil.ORANGE + "Amount: " + amount + " mB"};
			return Arrays.asList(description);
		}
		
		public static List fluidTextEmpty() {
			String[] description = { TextUtil.TEAL + "Empty:" , TextUtil.ORANGE + "Amount: 0 mB" };
			return Arrays.asList(description);
		}
		
		public static List emptyFluidTankDo() {
			String[] description = { TextUtil.GREEN + "Empty above tank.", TextUtil.RED + "Warning: " + TextUtil.ORANGE + "Cannot be undone!"};
			return Arrays.asList(description);
		}
		
		public static List emptyFluidTank() {
			String[] description = { TextUtil.GREEN + "Shift click " + TextUtil.LIGHT_GRAY + "to empty above tank."};
			return Arrays.asList(description);
		}
		
		public static List modeChange(String colour, String mode) {
			String[] description = { TextUtil.GREEN + "Click to change machine mode.", TextUtil.LIGHT_GRAY + "Current mode: " + colour + mode + TextUtil.LIGHT_GRAY + "."};
			return Arrays.asList(description);
		}
		
		public static List generationText(int stored, int generation_rate) {
			String[] description = { TextUtil.GREEN + "Stored: " + TextUtil.LIGHT_GRAY + stored , TextUtil.LIGHT_RED + "Producing: " + TextUtil.LIGHT_GRAY + generation_rate + TextUtil.LIGHT_RED + " RF/t."};
			return Arrays.asList(description);
		}
	}
}