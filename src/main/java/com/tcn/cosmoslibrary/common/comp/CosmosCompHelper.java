package com.tcn.cosmoslibrary.common.comp;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;
import com.tcn.cosmoslibrary.CosmosReference.RESOURCE.BASE;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;

public final class CosmosCompHelper {
	
	public final class Value {
		public static final String BLACK = (char) 167 + "0";
		public static final String BLUE = (char) 167 + "1";
		public static final String GREEN = (char) 167 + "2";
		public static final String CYAN = (char) 167 + "3";
		public static final String RED = (char) 167 + "4";
		public static final String PURPLE = (char) 167 + "5";
		public static final String ORANGE = (char) 167 + "6";
		public static final String LIGHT_GRAY = (char) 167 + "7";
		public static final String GRAY = (char) 167 + "8";
		public static final String LIGHT_BLUE = (char) 167 + "9";
		public static final String BRIGHT_GREEN = (char) 167 + "a";
		public static final String BRIGHT_BLUE = (char) 167 + "b";
		public static final String LIGHT_RED = (char) 167 + "c";
		public static final String MAGENTA = (char) 167 + "d";
		public static final String YELLOW = (char) 167 + "e";
		public static final String WHITE = (char) 167 + "f";
	
		public static final String OBFUSCATED = (char) 167 + "k";
		public static final String BOLD = (char) 167 + "l";
		public static final String STRIKETHROUGH = (char) 167 + "m";
		public static final String UNDERLINE = (char) 167 + "n";
		public static final String ITALIC = (char) 167 + "o";
		public static final String END = (char) 167 + "r";
	}
	
	public static boolean isAltKeyDown(Minecraft mc) {
		return (InputConstants.isKeyDown(mc.getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_ALT) || InputConstants.isKeyDown(mc.getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_ALT));
	}

	public static boolean isControlKeyDown(Minecraft mc) {
		return (InputConstants.isKeyDown(mc.getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_CONTROL) || InputConstants.isKeyDown(mc.getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_CONTROL));
	}

	public static boolean isShiftKeyDown(Minecraft mc) {
		return (InputConstants.isKeyDown(mc.getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT) || InputConstants.isKeyDown(mc.getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_SHIFT));
	}
	
	/*
	public static int getSplitStringHeight(Font fontRenderer, TextProperties input, int width) {
		List<FormattedCharSequence> stringRows = fontRenderer.split(input, width);
		return stringRows.size() * fontRenderer.lineHeight;
	}
	*/
	
	public static String camelCase(String input) {
		return input.substring(0, 1).toLowerCase() + input.substring(1);
	}

	public static String titleCase(String input) {
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}

	@OnlyIn(Dist.CLIENT)
	public static String locString(String key) {
		return I18n.get(key);
	}

	@OnlyIn(Dist.CLIENT)
	public static String locString(String colour, String key) {
		return colour + I18n.get(key) + Value.END;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static String locString(String pre, String key, String suff) {
		return pre + I18n.get(key) + suff + Value.END;
	}
	
	public static BaseComponent locComp(String key) {
		return new TranslatableComponent(key);
	}

	public static BaseComponent locComp(int colour, boolean bold, String key) {
		BaseComponent comp = new TranslatableComponent(key);
		
		comp.setStyle(Style.EMPTY.withBold(bold).withColor(TextColor.fromRgb(colour)));
		
		return comp;
	}

	public static BaseComponent locComp(CosmosColour colour, boolean bold, String key) {
		BaseComponent comp = new TranslatableComponent(key);
		
		comp.setStyle(Style.EMPTY.withBold(bold).withColor(TextColor.fromRgb(colour.dec())));
		
		return comp;
	}

	public static BaseComponent locComp(CosmosColour colour, boolean bold, boolean underline, String key) {
		BaseComponent comp = new TranslatableComponent(key);
		
		comp.setStyle(Style.EMPTY.withBold(bold).withUnderlined(underline).withColor(TextColor.fromRgb(colour.dec())));
		
		return comp;
	}
	
	public static BaseComponent locComp(CosmosColour colour, boolean bold, String pre, String key) {
		BaseComponent pre_comp = new TranslatableComponent(pre);
		BaseComponent comp = new TranslatableComponent(key);

		pre_comp.setStyle(Style.EMPTY.withBold(bold).withColor(TextColor.fromRgb(colour.dec())));
		comp.setStyle(Style.EMPTY.withBold(bold).withColor(TextColor.fromRgb(colour.dec())));
		
		return (BaseComponent) pre_comp.append(comp);
	}
	
	public static BaseComponent locComp(CosmosColour colour, boolean bold, String pre, String key, String suff) {
		BaseComponent pre_comp = new TranslatableComponent(pre);
		BaseComponent comp = new TranslatableComponent(key);
		BaseComponent suff_comp = new TranslatableComponent(suff);

		pre_comp.setStyle(Style.EMPTY.withBold(bold).withColor(TextColor.fromRgb(colour.dec())));
		comp.setStyle(Style.EMPTY.withBold(bold).withColor(TextColor.fromRgb(colour.dec())));
		
		return (BaseComponent) pre_comp.append(comp).append(suff_comp);
	}

	public static String getFluidName(FluidStack fluid) {
		return getFluidName(fluid.getFluid());
	}

	public static String getFluidName(Fluid fluid) {
		String fluidName = "";
		if (fluid.getAttributes().getTemperature() > 1000) {
			fluidName = fluidName + Value.RED;
		} else {
			fluidName = fluidName + Value.BLUE;
		}
		fluidName = fluidName + locString(fluid.getRegistryName().toString()) + Value.END;

		return fluidName;
	}

	public static String getScaledNumber(int number) {
		return getScaledNumber(number, 2);
	}

	public static String getScaledNumber(int number, int minDigits) {
		String numString = "";

		int numMod = 10 * minDigits;
		if (number > 100000 * numMod) {
			numString = numString + number / 1000000 + "M";
		} else if (number > 100 * numMod) {
			numString = numString + number / 1000 + "k";
		} else {
			numString = numString + number;
		}
		
		return numString;
	}

	public static BaseComponent shiftForMoreDetails() {
		return (BaseComponent) locComp(CosmosColour.WHITE, false, BASE.TOOLTIP_HOLD).append(locComp(CosmosColour.BLACK, false, " ")).append(locComp(CosmosColour.ORANGE, true, BASE.TOOLTIP_SHIFT)
				.append(locComp(CosmosColour.BLACK, false, " ")).append(locComp(CosmosColour.WHITE, false, BASE.TOOLTIP_MORE)));
		
	}
	
	public static BaseComponent shiftForLessDetails() {
		return (BaseComponent) locComp(CosmosColour.WHITE, false, BASE.TOOLTIP_RELEASE).append(locComp(CosmosColour.BLACK, false, " ")).append(locComp(CosmosColour.ORANGE, true, BASE.TOOLTIP_SHIFT)
				.append(locComp(CosmosColour.BLACK, false, " ")).append(locComp(CosmosColour.WHITE, false, BASE.TOOLTIP_LESS)));
	}
	
	public static BaseComponent ctrlForMoreDetails() {
		return (BaseComponent) locComp(CosmosColour.WHITE, false, BASE.TOOLTIP_HOLD).append(locComp(CosmosColour.BLACK, false, " ")).append(locComp(CosmosColour.LIGHT_GRAY, true, BASE.TOOLTIP_CTRL)
				.append(locComp(CosmosColour.BLACK, false, " ")).append(locComp(CosmosColour.WHITE, false, BASE.TOOLTIP_NBT)));
	}
	
	public static BaseComponent ctrlForLessDetails() {
		return (BaseComponent) locComp(CosmosColour.WHITE, false, BASE.TOOLTIP_RELEASE).append(locComp(CosmosColour.BLACK, false, " ")).append(locComp(CosmosColour.LIGHT_GRAY, true, BASE.TOOLTIP_CTRL)
				.append(locComp(CosmosColour.BLACK, false, " ")).append(locComp(CosmosColour.WHITE, false, BASE.TOOLTIP_NBT_LESS)));
	}

	public static BaseComponent altForEnergyDetails() {
		return (BaseComponent) locComp(CosmosColour.WHITE, false, BASE.TOOLTIP_HOLD).append(locComp(CosmosColour.BLACK, false, " ")).append(locComp(CosmosColour.RED, true, BASE.TOOLTIP_ALT)
				.append(locComp(CosmosColour.BLACK, false, " ")).append(locComp(CosmosColour.WHITE, false, BASE.TOOLTIP_ENERGY)));
	}
	
	public static BaseComponent altForLessDetails() {
		return (BaseComponent) locComp(CosmosColour.WHITE, false, BASE.TOOLTIP_RELEASE).append(locComp(CosmosColour.BLACK, false, " ")).append(locComp(CosmosColour.RED, true, BASE.TOOLTIP_ALT)
				.append(locComp(CosmosColour.BLACK, false, " ")).append(locComp(CosmosColour.WHITE, false, BASE.TOOLTIP_ENERGY_LESS)));
	}

	public static BaseComponent getTooltipInfo(String key) {
		return locComp(CosmosColour.LIGHT_GRAY, false, key);
	}

	public static BaseComponent getTooltipOne(String key) {
		return locComp(CosmosColour.CYAN, false, key);
	}

	public static BaseComponent getTooltipTwo(String key) {
		return locComp(CosmosColour.GREEN, false, key);
	}
	
	public static BaseComponent getTooltipThree(String key) {
		return locComp(CosmosColour.LIGHT_BLUE, false, key);
	}

	public static BaseComponent getTooltipFour(String key) {
		return locComp(CosmosColour.LIME, false, key);
	}
	
	public static BaseComponent getTooltipLimit(String key) {
		return locComp(CosmosColour.LIGHT_RED, false, key);
	}
	
	public static BaseComponent getErrorText(String key) {
		return locComp(CosmosColour.RED, false, key);
	}

	public static boolean displayShiftForDetail = true;
	public static boolean displayCtrlForDetail = true;
	public static boolean displayAltForDetail = true;
	
}