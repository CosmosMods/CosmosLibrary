package com.tcn.cosmoslibrary.client.util;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tcn.cosmoslibrary.CosmosReference;
import com.tcn.cosmoslibrary.client.widget.FluidWidget;
import com.tcn.cosmoslibrary.common.comp.CosmosCompHelper;
import com.tcn.cosmoslibrary.common.comp.CosmosCompHelper.Value;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fluids.IFluidTank;

/**
 * Heavily used to simplify the creation and usage of GUIs.
 */
public class ScreenUtil {
	
	public static final int DEFAULT_COLOUR_BACKGROUND = 4210752;
	public static final int DEFAULT_COLOUR_FONT_LIST = 16777215;
	
	public static final int BLACK = 0x000000;
	public static final int WHITE = 0xFFFFFF;
	public static final int LIGHT_BLUE = 0x5882FA;
	public static final int BLUE = 0x0000FF;
	public static final int LIGHT_GREY = 0xA4A4A4;
	public static final int GREY = 0x424242;
	public static final int GREEN = 0x00FF00;
	public static final int DARK_GREEN = 0x0B610B;
	public static final int RED = 0xFF0000;
	public static final int YELLOW = 0xFFFF00;
	public static final int ORANGE = 0xFF8000;
	public static final int CYAN = 0x01A9DB;
	public static final int MAGENTA = 0xDF01D7;
	public static final int PURPLE = 0x8904B1;
	public static final int PINK = 0xFE2EC8;
	public static final int BROWN = 0x61210B;
	
	public static class DRAW {
		public static void drawPowerBar(Screen container, MatrixStack stack, int[] screen_coords, int draw_x, int draw_y, int scaled, int[] bar_location, boolean has_stored) {
			container.getMinecraft().getTextureManager().bind(CosmosReference.RESOURCE.BASE.GUI_ENERGY_BAR_LOC);
			
			if (has_stored) {
				container.blit(stack, screen_coords[0] + draw_x, (screen_coords[1] + bar_location[2] + draw_y) - scaled, bar_location[0], bar_location[1] - scaled, bar_location[3], scaled);
			}
		}
		
		public static void drawSlot(Screen container, MatrixStack stack, int[] screen_coords, int draw_x, int draw_y, int[] slot_location) {
			container.getMinecraft().getTextureManager().bind(CosmosReference.RESOURCE.BASE.GUI_SLOT_LOC);
			
			container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y, slot_location[0], slot_location[1], slot_location[2], slot_location[3]);
		}
		
		public static void drawBackground(Screen container, MatrixStack stack, int[] screen_coords, ResourceLocation texture) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			container.getMinecraft().getTextureManager().bind(texture);
			
			container.blit(stack, screen_coords[0], screen_coords[1], 0, 0, container.width, container.height);
		}
		
		public static void drawBackground(Screen container, MatrixStack stack, int[] screen_coords, int draw_x, int draw_y, ResourceLocation texture) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			container.getMinecraft().getTextureManager().bind(texture);
			
			container.blit(stack, screen_coords[0], screen_coords[1], draw_x, draw_y, container.width, container.height);
		}
		
		public static void bind(Screen container, MatrixStack stack, ResourceLocation texture) {
			container.getMinecraft().getTextureManager().bind(texture);
		}
		
		public static void unbind(Screen container, MatrixStack stack, ResourceLocation texture) {
			container.getMinecraft().getTextureManager().release(texture);
		}
		
		@SuppressWarnings({ "static-access", "deprecation" })
		public static void drawFluidTank(Screen container, MatrixStack stack, int[] screen_coords, int draw_x, int draw_y, IFluidTank tank, int scaled) {

			if (tank.getFluidAmount() > 0) {
				ResourceLocation fluidStill = tank.getFluid().getFluid().getAttributes().getStillTexture();
				AtlasTexture texture = container.getMinecraft().getModelManager().getAtlas(PlayerContainer.BLOCK_ATLAS);
				TextureAtlasSprite fluid_texture = texture.getSprite(fluidStill);
				
				texture.bind();
				int color = tank.getFluid().getFluid().getAttributes().getColor();
				
				float r = ((color >> 16) & 0xFF) / 255f; // red
			    float g = ((color >> 8) & 0xFF) / 255f; // green
			    float b = ((color >> 0) & 0xFF) / 255f; // blue
			    float a = ((color >> 24) & 0xFF) / 255f; // alpha
				
				RenderSystem.color4f(r, g, b, a);
				
				if (!(fluid_texture == null)) {
					if(scaled <= 30){
						container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y + 38 - scaled ,0, 16, scaled,  fluid_texture);
					} else if (scaled > 30 && scaled <= 45){
						container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y + 38 - scaled, 0, 16, scaled / 2 + 1, fluid_texture);
						container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y + 38 - scaled / 2, 0, 16, scaled / 2, fluid_texture);
					} else if (scaled > 45){
						container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y + 38 - scaled, 0, 16, scaled / 2, fluid_texture);
						container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y + 38 - scaled / 2, 0, 16, scaled / 2, fluid_texture);
					}
				}
			}
		}
		
		public static void renderToolTipPowerProducer(Screen container, MatrixStack stack, int[] screen_coords, int draw_x, int draw_y, int mouse_x, int mouse_y, int stored, int generation_rate, boolean producing) {
			if (IS_HOVERING.isHoveringPower(mouse_x, mouse_y, screen_coords[0] + draw_x, screen_coords[1] + draw_y)) {
				if (producing) {
					container.renderComponentTooltip(stack, TEXT_LIST.generationText(stored, generation_rate), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
				} else {
					container.renderComponentTooltip(stack, TEXT_LIST.storedTextNo(stored), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
				}
			}
		}
		
		public static void renderToolTipFluidLarge(Screen container, MatrixStack stack, int[] screen_coords, int draw_x, int draw_y, int mouse_x, int mouse_y, IFluidTank tank) {
			if (IS_HOVERING.isHoveringFluidLarge(mouse_x, mouse_y, screen_coords[0] + draw_x, screen_coords[1] + draw_y)) {
				if (tank.getFluidAmount() > 0) {
					container.renderComponentTooltip(stack, TEXT_LIST.fluidText(tank.getFluid().getDisplayName().toString(), tank.getFluidAmount(), tank.getCapacity()), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
				} else {
					container.renderComponentTooltip(stack, TEXT_LIST.fluidTextEmpty(), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
				}
			}
		}
		
		public static void renderToolTipEmptyFluidButton(Screen container, MatrixStack stack, int[] screen_coords, int draw_x, int draw_y, int mouse_x, int mouse_y, boolean has_fluid) {
			if (IS_HOVERING.isHoveringButtonStandard(mouse_x, mouse_y, screen_coords[0] + draw_x, screen_coords[1] + draw_y)) {
				if (has_fluid) {
					if (CosmosCompHelper.isShiftKeyDown(container.getMinecraft())) {
						container.renderComponentTooltip(stack, TEXT_LIST.emptyFluidTankDo(), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
					} else {
						container.renderComponentTooltip(stack, TEXT_LIST.emptyFluidTank(), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
					}
				}
			}
		}
		
		public static void drawScaledElementUpNestled(Screen container, MatrixStack stack, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int height, int scaled) {
			container.blit(stack, screen_coords[0] + draw_x, (screen_coords[1] + draw_y + height) - scaled, texture_x, (texture_y + height) - scaled, width, scaled);
		}
		
		public static void drawScaledElementDownNestled(Screen container, MatrixStack stack, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int scaled) {
			container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y, texture_x, texture_y, width, scaled);
		}
		
		public static void drawScaledElementRightNestled(Screen container, MatrixStack stack, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int height, int scaled) {
			container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y, texture_x, texture_y, scaled + 1, height);
		}
		
		public static void drawScaledElementUpExternal(Screen container, MatrixStack stack, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int height, int scaled, ResourceLocation texture) {
			container.getMinecraft().getTextureManager().bind(texture);
			
			container.blit(stack, screen_coords[0] + draw_x, (screen_coords[1] + draw_y + height) - scaled, texture_x, (texture_y + height) - scaled, width, scaled);
			
			container.getMinecraft().getTextureManager().release(texture);
		}
		
		public static void drawScaledElementDownExternal(Screen container, MatrixStack stack, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int scaled, ResourceLocation texture) {
			container.getMinecraft().getTextureManager().bind(texture);
			
			container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y, texture_x, texture_y + scaled, width, scaled);
			
			container.getMinecraft().getTextureManager().release(texture);
		}
		
		public static FluidWidget createBucketButton(int id, int[] screen_coords, int draw_x, int draw_y, int size, boolean enabled) {
			FluidWidget button;
			if (enabled) {
				button = new FluidWidget(screen_coords[0] + draw_x, screen_coords[1] + draw_y, size, 1, false);
			} else {
				button = new FluidWidget(screen_coords[0] + draw_x, screen_coords[1] + draw_y, size, 4, true);
			}
			return button;
		}
		
		public static void drawStaticElement(Screen container, MatrixStack stack, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int height, ResourceLocation location) {
			bind(container, stack, location);
			container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y, texture_x, texture_y, width, height);
			//unbind(container, stack, location);
		}
		
		public static void drawStaticElement(Screen container, MatrixStack stack, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int height) {
			container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y, texture_x, texture_y, width, height);
		}
		
		public static void drawStaticElementToggled(Screen container, MatrixStack stack, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int height, boolean enabled) {
			if (enabled) {
				container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y, texture_x, texture_y, width, height);
			}
		}	
	}

	public static class FONT {
		public static void drawString(MatrixStack stack, FontRenderer font, int[] screen_coords, int draw_x, int draw_y, String draw_text, boolean formatted, boolean draw_from) {
			if (formatted) {
				if (draw_from) {
					font.draw(stack, I18n.get(draw_text), screen_coords[0] + draw_x, screen_coords[1] + draw_y, DEFAULT_COLOUR_BACKGROUND);
				} else {
					font.draw(stack, I18n.get(draw_text), draw_x, draw_y, DEFAULT_COLOUR_BACKGROUND);
				}
			} else {
				if (draw_from) {
					font.draw(stack, draw_text, screen_coords[0] + draw_x, screen_coords[1] + draw_y, DEFAULT_COLOUR_BACKGROUND);
				} else {
					font.draw(stack, draw_text, draw_x, draw_y, DEFAULT_COLOUR_BACKGROUND);
				}
			}
		}
		
		public static void drawString(MatrixStack stack, FontRenderer font, int[] screen_coords, int draw_x, int draw_y, String draw_text, boolean formatted, boolean draw_from, int colour) {
			if (formatted) {
				if (draw_from) {
					font.draw(stack, I18n.get(draw_text), screen_coords[0] + draw_x, screen_coords[1] + draw_y, colour);
				} else {
					font.draw(stack, I18n.get(draw_text), draw_x, draw_y, colour);
				}
			} else {
				if (draw_from) {
					font.draw(stack, draw_text, screen_coords[0] + draw_x, screen_coords[1] + draw_y, colour);
				} else {
					font.draw(stack, draw_text, draw_x, draw_y, colour);
				}
			}
		}
		
		public static void drawString(MatrixStack stack, FontRenderer font, int[] screen_coords, int draw_x, int draw_y, String draw_text, int colour, boolean shadow) {
			if (shadow) {
				font.drawShadow(stack, draw_text, screen_coords[0] + draw_x, screen_coords[1] + draw_y, colour);
			} else {
				font.draw(stack, draw_text, screen_coords[0] + draw_x, screen_coords[1] + draw_y, colour);
			}
		}
		
		public static void drawInventoryString(MatrixStack stack, FontRenderer font, int[] screen_coords, int draw_x, int draw_y, boolean draw_from) {
			if (draw_from) {
				font.draw(stack, I18n.get("container.inventory"), screen_coords[0] + draw_x, screen_coords[1] + draw_y, DEFAULT_COLOUR_BACKGROUND);
			} else {
				font.draw(stack, I18n.get("container.inventory"), draw_x, draw_y, DEFAULT_COLOUR_BACKGROUND);
			}
		}
		
		public static void drawInventoryString(MatrixStack stack, FontRenderer font, int[] screen_coords, int draw_x, int draw_y, int colour) {
			font.draw(stack, I18n.get("container.inventory"), screen_coords[0] + draw_x, screen_coords[1] + draw_y, colour);
		}
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
		
		public static boolean isHoveringFluidLarge(int mouse_x, int mouse_y, int x, int y) {
			if (mouse_x >= x - 1 && mouse_x <= x + 16) {
				if (mouse_y >= y && mouse_y <= y + 57) {
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
		public static List<ITextComponent> storedTextRF(int stored, int speed) {
			StringTextComponent[] description = { 
					new StringTextComponent(Value.PURPLE + "Stored: " + Value.ORANGE + stored), 
					new StringTextComponent(Value.GREEN + "Using: " + Value.CYAN + speed + Value.GREEN + " RF/t.")};
			
			return Arrays.asList(description);
		}
		
		public static List<ITextComponent> storedTextNo(int stored) {
			StringTextComponent[] description = {
					new StringTextComponent(Value.PURPLE + "Stored: " + Value.ORANGE + stored)};
			
			return Arrays.asList(description);
		}
		
		public static List<ITextComponent> fluidText(String name, int amount, int capacity) {
			StringTextComponent[] description = {
					new StringTextComponent(Value.CYAN + "Fluid: " + name), 
					new StringTextComponent(Value.ORANGE + "Amount: " + amount + " / " + capacity + " mB")};
			
			return Arrays.asList(description);
		}
		
		public static List<ITextComponent> fluidTextEmpty() {
			StringTextComponent[] description = { 
					new StringTextComponent(Value.CYAN + "Empty:"), 
					new StringTextComponent(Value.ORANGE + "Amount: 0 mB")};
			
			return Arrays.asList(description);
		}
		
		public static List<ITextComponent> emptyFluidTankDo() {
			StringTextComponent[] description = { 
					new StringTextComponent(Value.GREEN + "Empty tank."), 
					new StringTextComponent(Value.RED + "Warning: " + Value.ORANGE + "Cannot be undone!")};
			
			return Arrays.asList(description);
		}

		public static List<ITextComponent> emptyFluidTank() {
			StringTextComponent[] description = {
					new StringTextComponent(Value.GREEN + "Shift click " + Value.LIGHT_GRAY + "to empty tank.")};
			
			return Arrays.asList(description);
		}
		
		public static List<ITextComponent> modeChange(String colour, String mode) {
			StringTextComponent[] description = { 
					new StringTextComponent(Value.GREEN + "Click to change mode."), 
					new StringTextComponent(Value.LIGHT_GRAY + "Current mode: " + colour + mode + Value.LIGHT_GRAY + ".")};
			
			return Arrays.asList(description);
		}
		
		public static List<ITextComponent> generationText(int stored, int generation_rate) {
			StringTextComponent[] description = { 
					new StringTextComponent(Value.PURPLE + "Stored: " + Value.ORANGE + stored), 
					new StringTextComponent(Value.RED + "Producing: " + Value.CYAN + generation_rate + Value.RED + " RF/t.")};
			
			return Arrays.asList(description);
		}
	}
}