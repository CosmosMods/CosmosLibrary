package com.tcn.cosmoslibrary.client.util;

import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tcn.cosmoslibrary.CosmosReference;
import com.tcn.cosmoslibrary.client.screen.widget.CosmosButtonWithType;
import com.tcn.cosmoslibrary.client.screen.widget.CosmosButtonWithType.TYPE;
import com.tcn.cosmoslibrary.common.comp.CosmosCompHelper;
import com.tcn.cosmoslibrary.common.comp.CosmosCompHelper.Value;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fmlclient.gui.GuiUtils;

/**
 * 
 * Flexible Collection of Helpful methods to simply the Creation of in-game guis.
 * 
 * @author TheCosmicNebula_
 * 
 */

@SuppressWarnings({ "removal", "static-access" })
@OnlyIn(Dist.CLIENT)
public class CosmosScreenHelper {
	
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
	
	public static final float[] NORMAL_COLOUR = new float[] { 1.0F, 1.0F, 1.0F, 1.0F };
	
	public static class INIT {
		public static int[] getScreenCoords(Screen screen, int imageWidth, int imageHeight) {
			return new int[] { ((screen.width - imageWidth) / 2), ((screen.height - imageHeight) / 2) };
		}
		
		public static int[] getScreenCoords(AbstractContainerScreen<?> screen, int imageWidth, int imageHeight) {
			return new int[] { ((screen.width - imageWidth) / 2), ((screen.height - imageHeight) / 2) };
		}
	}
	
	public static class DRAW {
		public static void renderItemStack(Screen screen, Font font, PoseStack matrixStack, ItemStack stack, int[] screen_coords, int x, int y, int mouseX, int mouseY, boolean withTooltip) {
			int renderX = screen_coords[0] + x;
			int renderY = screen_coords[1] + y;
			
			Minecraft.getInstance().getItemRenderer().renderGuiItem(stack, screen_coords[0] + x, screen_coords[1] + y);
			
			if (withTooltip) {
				if (mouseX > renderX && mouseX < renderX + 16) {
					if (mouseY > renderY && mouseY < renderY + 16) {
					    GuiUtils.preItemToolTip(stack);
						screen.renderComponentTooltip(matrixStack, getTooltipFromItem(screen, stack), mouseX, mouseY, font);
					    GuiUtils.postItemToolTip();
					}
				}
			}
		}
		
		public static void renderItemStack(Screen screen, Font font, PoseStack matrixStack, ItemStack stack, int count, int[] screen_coords, int x, int y, int mouseX, int mouseY, boolean withTooltip) {
			int renderX = screen_coords[0] + x;
			int renderY = screen_coords[1] + y;
			
			Minecraft.getInstance().getItemRenderer().renderGuiItem(stack, screen_coords[0] + x, screen_coords[1] + y);
			
			if (withTooltip) {
				if (mouseX > renderX && mouseX < renderX + 16) {
					if (mouseY > renderY && mouseY < renderY + 16) {
						GuiUtils.preItemToolTip(stack);
						screen.renderComponentTooltip(matrixStack, getTooltipFromItem(screen, stack), mouseX, mouseY, font);
					    GuiUtils.postItemToolTip();
					}
				}
			}
		}
		
		public static List<Component> getTooltipFromItem(Screen screen, ItemStack p_231151_1_) {
			Minecraft mc = screen.getMinecraft();
			return p_231151_1_.getTooltipLines(mc.player, mc.options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);
		}
		
		public static void drawPowerBar(Screen container, PoseStack stack, int[] screen_coords, int draw_x, int draw_y, int scaled, int[] bar_location, boolean has_stored) {
			bindForSetup(container, stack, CosmosReference.RESOURCE.BASE.GUI_ENERGY_BAR_LOC, NORMAL_COLOUR);
			
			if (has_stored) {
				container.blit(stack, screen_coords[0] + draw_x, (screen_coords[1] + bar_location[2] + draw_y) - scaled, bar_location[0], bar_location[1] - scaled, bar_location[3], scaled);
			}
		}
		
		public static void drawSlot(Screen container, PoseStack stack, int[] screen_coords, int draw_x, int draw_y, int[] slot_location) {
			bindForSetup(container, stack, CosmosReference.RESOURCE.BASE.GUI_SLOT_LOC, NORMAL_COLOUR);
			
			container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y, slot_location[0], slot_location[1], slot_location[2], slot_location[3]);
		}
		
		public static void drawBackground(Screen container, PoseStack stack, int[] screen_coords, ResourceLocation texture) {
			bindForSetup(container, stack, texture, NORMAL_COLOUR);
			
			container.blit(stack, screen_coords[0], screen_coords[1], 0, 0, container.width, container.height);
		}
		
		public static void drawBackground(Screen container, PoseStack stack, int[] screen_coords, int draw_x, int draw_y, ResourceLocation texture) {
			bindForSetup(container, stack, texture, NORMAL_COLOUR);
			
			container.blit(stack, screen_coords[0], screen_coords[1], draw_x, draw_y, container.width, container.height);
		}
		
		public static void bindForSetup(Screen container, PoseStack stack, ResourceLocation texture, float[] colour) {
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
		    RenderSystem.setShaderColor(colour[0], colour[1], colour[2], colour[3]);
			RenderSystem.setShaderTexture(0, texture);
			
		}
		
		public static void unbindForSetup(Screen container, PoseStack stack, ResourceLocation texture) {
			//container.getMinecraft().getTextureManager().release(texture);
			RenderSystem.deleteTexture(0);
		}

		@OnlyIn(Dist.CLIENT)
		public static void drawFluidTank(Screen container, PoseStack stack, int[] screen_coords, int draw_x, int draw_y, IFluidTank tank, int scaled) {

			if (tank.getFluidAmount() > 0) {
				ResourceLocation fluidStill = tank.getFluid().getFluid().getAttributes().getStillTexture();
				TextureAtlas texture = container.getMinecraft().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS);
				TextureAtlasSprite fluid_texture = texture.getSprite(fluidStill);
				
				texture.bind();
				int color = tank.getFluid().getFluid().getAttributes().getColor();
				
				float r = ((color >> 16) & 0xFF) / 255f; // red
			    float g = ((color >> 8) & 0xFF) / 255f; // green
			    float b = ((color >> 0) & 0xFF) / 255f; // blue
			    float a = ((color >> 24) & 0xFF) / 255f; // alpha
				
				RenderSystem.setShaderColor(r, g, b, a);
				
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
		
		public static void renderToolTipPowerProducer(Screen container, PoseStack stack, int[] screen_coords, int draw_x, int draw_y, int mouse_x, int mouse_y, int stored, int generation_rate, boolean producing) {
			if (IS_HOVERING.isHoveringPower(mouse_x, mouse_y, screen_coords[0] + draw_x, screen_coords[1] + draw_y)) {
				if (producing) {
					container.renderComponentTooltip(stack, TEXT_LIST.generationText(stored, generation_rate), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
				} else {
					container.renderComponentTooltip(stack, TEXT_LIST.storedTextNo(stored), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
				}
			}
		}
		
		public static void renderToolTipFluidLarge(Screen container, PoseStack stack, int[] screen_coords, int draw_x, int draw_y, int mouse_x, int mouse_y, IFluidTank tank) {
			if (IS_HOVERING.isHoveringFluidLarge(mouse_x, mouse_y, screen_coords[0] + draw_x, screen_coords[1] + draw_y)) {
				if (tank.getFluidAmount() > 0) {
					container.renderComponentTooltip(stack, TEXT_LIST.fluidText(tank.getFluid().getDisplayName().toString(), tank.getFluidAmount(), tank.getCapacity()), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
				} else {
					container.renderComponentTooltip(stack, TEXT_LIST.fluidTextEmpty(), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
				}
			}
		}
		
		public static void renderToolTipEmptyFluidButton(Screen container, PoseStack stack, int[] screen_coords, int draw_x, int draw_y, int mouse_x, int mouse_y, boolean has_fluid) {
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
		
		public static void drawScaledElementUpNestled(Screen container, PoseStack stack, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int height, int scaled) {
			container.blit(stack, screen_coords[0] + draw_x, (screen_coords[1] + draw_y + height) - scaled, texture_x, (texture_y + height) - scaled, width, scaled);
		}
		
		public static void drawScaledElementDownNestled(Screen container, PoseStack stack, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int scaled) {
			container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y, texture_x, texture_y, width, scaled);
		}
		
		public static void drawScaledElementRightNestled(Screen container, PoseStack stack, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int height, int scaled) {
			container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y, texture_x, texture_y, scaled + 1, height);
		}
		
		public static void drawScaledElementUpExternal(Screen container, PoseStack stack, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int height, int scaled, ResourceLocation texture) {
			bindForSetup(container, stack, texture, NORMAL_COLOUR);
			container.blit(stack, screen_coords[0] + draw_x, (screen_coords[1] + draw_y + height) - scaled, texture_x, (texture_y + height) - scaled, width, scaled);
		}
		
		public static void drawScaledElementDownExternal(Screen container, PoseStack stack, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int scaled, ResourceLocation texture) {
			bindForSetup(container, stack, texture, NORMAL_COLOUR);
			container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y, texture_x, texture_y + scaled, width, scaled);
			//RenderSystem.deleteTexture(0);
		}
		
		public static CosmosButtonWithType createBucketButton(int id, int[] screen_coords, int draw_x, int draw_y, int size, boolean enabled) {
			CosmosButtonWithType button;
			if (enabled) {
				button = new CosmosButtonWithType(TYPE.FLUID, screen_coords[0] + draw_x, screen_coords[1] + draw_y, size, true, false, 1, CosmosCompHelper.locComp(""), (x) -> {});
			} else {
				button = new CosmosButtonWithType(TYPE.FLUID, screen_coords[0] + draw_x, screen_coords[1] + draw_y, size, true, true, 4, CosmosCompHelper.locComp(""), (x) -> {});
			}
			return button;
		}
		
		public static void drawStaticElement(Screen container, PoseStack stack, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int height, ResourceLocation location) {
			bindForSetup(container, stack, location, NORMAL_COLOUR);
			container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y, texture_x, texture_y, width, height);
		}

		public static void drawStaticElement(Screen container, PoseStack stack, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int height, float[] colour, ResourceLocation location) {
			bindForSetup(container, stack, location, colour);
			container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y, texture_x, texture_y, width, height);
		}
		
		public static void drawStaticElement(Screen container, PoseStack stack, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int height) {
			container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y, texture_x, texture_y, width, height);
		}
		
		public static void drawStaticElementToggled(Screen container, PoseStack stack, int[] screen_coords, int draw_x, int draw_y, int texture_x, int texture_y, int width, int height, boolean enabled) {
			if (enabled) {
				container.blit(stack, screen_coords[0] + draw_x, screen_coords[1] + draw_y, texture_x, texture_y, width, height);
			}
		}	
	}

	public static class FONT {
		public static void drawString(PoseStack matrixStack, Font font, int[] screen_coords, int x, int y, boolean drawFrom, Component comp) {
			font.draw(matrixStack, comp, !drawFrom ? x : screen_coords[0] + x, !drawFrom ? y : screen_coords[1] + y, comp.getStyle().getColor().getValue());
		}

		public static void drawCenteredString(PoseStack matrixStack, Font font, int[] screen_coords, int xOffset, int yOffset, Component comp) {
			int x = (screen_coords[0] * 2) / 2;
			int y = (screen_coords[1] * 2) / 2 + 33;

			font.draw(matrixStack, comp, ((float)(x - font.width(comp) / 2) + xOffset), y + yOffset, comp.getStyle().getColor().getValue());
		}
		
		public static void drawWrappedStringBR(PoseStack matrixStack, Font font, int[] screen_coords, int xOffset, int yOffset, int length, Component comp) {
			int prevLines = 0;
			
			for (String str : comp.getString().split("<br>")) {
				int x = (screen_coords[0] * 2) / 2;
				int y = (screen_coords[1] * 2) / 2 + 33;

				font.draw(matrixStack, str, ((float)(x - font.width(str) / 2) + xOffset), y + yOffset + (font.lineHeight * prevLines), comp.getStyle().getColor().getValue());
				
				prevLines += (int) Math.ceil((float) (str.length() * 7) / (float) 204);
			}
		}

		public static void drawCenteredString(PoseStack matrixStack, Font font, int[] screen_coords, int xOffset, int yOffset, int colour, String string, boolean shadow) {
			int x = (screen_coords[0] * 2) / 2;
			int y = screen_coords[1] * 2 / 2 + 33;

			if (shadow) {
				font.drawShadow(matrixStack, string, ((float)(x - font.width(string) / 2) + xOffset), y + yOffset, colour);
			} else {
				font.draw(matrixStack, string, ((float)(x - font.width(string) / 2) + xOffset), y + yOffset, colour);
			}
		}
		
		public static void drawWrappedStringBR(PoseStack matrixStack, Font font, int[] screen_coords, int xOffset, int yOffset, int length, int colour, String string) {
			int prevLines = 0;
			
			for (String str : string.split("<br>")) {
				int x = (screen_coords[0] * 2) / 2;
				int y = (screen_coords[1] * 2) / 2 + 33;

				font.draw(matrixStack, str, ((float)(x - font.width(str) / 2) + xOffset), y + yOffset + (font.lineHeight * prevLines), colour);
				
				prevLines += (int) Math.ceil((float) (str.length() * 7) / (float) 204);
			}
		}

		public static void drawInventoryString(PoseStack stack, Font font, int[] screen_coords, int draw_x, int draw_y, boolean draw_from) {
			if (draw_from) {
				font.draw(stack, I18n.get("container.inventory"), screen_coords[0] + draw_x, screen_coords[1] + draw_y, DEFAULT_COLOUR_BACKGROUND);
			} else {
				font.draw(stack, I18n.get("container.inventory"), draw_x, draw_y, DEFAULT_COLOUR_BACKGROUND);
			}
		}
		
		public static void drawInventoryString(PoseStack stack, Font font, int[] screen_coords, int draw_x, int draw_y, int colour) {
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
		public static List<Component> storedTextRF(int stored, int speed) {
			TextComponent[] description = { 
					new TextComponent(Value.PURPLE + "Stored: " + Value.ORANGE + stored), 
					new TextComponent(Value.GREEN + "Using: " + Value.CYAN + speed + Value.GREEN + " RF/t.")};
			
			return Arrays.asList(description);
		}
		
		public static List<Component> storedTextNo(int stored) {
			TextComponent[] description = {
					new TextComponent(Value.PURPLE + "Stored: " + Value.ORANGE + stored)};
			
			return Arrays.asList(description);
		}
		
		public static List<Component> fluidText(String name, int amount, int capacity) {
			TextComponent[] description = {
					new TextComponent(Value.CYAN + "Fluid: " + name), 
					new TextComponent(Value.ORANGE + "Amount: " + amount + " / " + capacity + " mB")};
			
			return Arrays.asList(description);
		}
		
		public static List<Component> fluidTextEmpty() {
			TextComponent[] description = { 
					new TextComponent(Value.CYAN + "Empty:"), 
					new TextComponent(Value.ORANGE + "Amount: 0 mB")};
			
			return Arrays.asList(description);
		}
		
		public static List<Component> emptyFluidTankDo() {
			TextComponent[] description = { 
					new TextComponent(Value.GREEN + "Empty tank."), 
					new TextComponent(Value.RED + "Warning: " + Value.ORANGE + "Cannot be undone!")};
			
			return Arrays.asList(description);
		}

		public static List<Component> emptyFluidTank() {
			TextComponent[] description = {
					new TextComponent(Value.GREEN + "Shift click " + Value.LIGHT_GRAY + "to empty tank.")};
			
			return Arrays.asList(description);
		}
		
		public static List<Component> modeChange(String colour, String mode) {
			TextComponent[] description = { 
					new TextComponent(Value.GREEN + "Click to change mode."), 
					new TextComponent(Value.LIGHT_GRAY + "Current mode: " + colour + mode + Value.LIGHT_GRAY + ".")};
			
			return Arrays.asList(description);
		}
		
		public static List<Component> generationText(int stored, int generation_rate) {
			TextComponent[] description = { 
					new TextComponent(Value.PURPLE + "Stored: " + Value.ORANGE + stored), 
					new TextComponent(Value.RED + "Producing: " + Value.CYAN + generation_rate + Value.RED + " RF/t.")};
			
			return Arrays.asList(description);
		}
	}
}