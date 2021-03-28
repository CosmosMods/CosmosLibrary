package com.tcn.cosmoslibrary.common.comp;

import java.util.Arrays;
import java.util.Comparator;

import com.tcn.cosmoslibrary.common.comp.CosmosCompHelper.Value;

import net.minecraft.util.text.IFormattableTextComponent;

public enum CosmosColour {

	WHITE(0, "White", 16777215, Value.WHITE, 255, 255, 255, false),
	ORANGE(1, "Orange", 16755200, Value.ORANGE, 255, 170, 0, false),
	MAGENTA(2, "Magenta", 16733695, Value.MAGENTA, 255, 85, 255, false),
	LIGHT_BLUE(3, "Light Blue", 5592575, Value.LIGHT_BLUE, 85, 85, 255, false),
	YELLOW(4, "Yellow", 16777045, Value.YELLOW, 255, 255, 85, false),
	LIME(5, "Lime", 5635925, Value.BRIGHT_GREEN, 85, 255, 85, false),
	PINK(6, "Pink", 15961002, "", 243, 139, 170, false),
	GRAY(7, "Gray", 5592405, Value.GRAY, 85, 85, 85, true),
	LIGHT_GRAY(8, "Light Gray", 11184810, Value.LIGHT_GRAY, 170, 170, 170, false),
	CYAN(9, "Cyan", 43690, Value.CYAN, 0, 170, 170, false),
	PURPLE(10, "Purple", 11141290, Value.PURPLE, 170, 0, 170, true),
	BLUE(11, "Blue", 170, Value.BLUE, 0, 0, 170, true),
	BROWN(12, "Brown", 8606770, "", 131, 84, 50, true),
	GREEN(13, "Green", 43520, Value.GREEN, 0, 170, 0, false),
	RED(14, "Red", 11141120, Value.RED, 170, 0, 0, true),
	BLACK(15, "Black", 1579032, Value.BLACK, 24, 24, 24, true),
	POCKET_PURPLE(16, "Pocket Purple", 4134239, Value.PURPLE, 35, 12, 53, true),
	POCKET_PURPLE_LIGHT (17, "Pocket Purple Light", 6627993, Value.PURPLE, 101, 34, 154, true),
	POCKET_PURPLE_GUI (17, "Pocket Purple GUI", 10748079, Value.PURPLE, 164, 0, 175, false),
	LIGHT_RED(18, "Light Red", 16733525, Value.LIGHT_RED, 255, 85, 85, false);

	private final int index;
	private final String name;
	private final int decimal;
	private final String chat_colour;
	private final int[] RGB;
	private final boolean dark;

	private static final CosmosColour[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(CosmosColour::getIndex)).toArray((colorId) -> {
		return new CosmosColour[colorId];
	});

	CosmosColour(int indexIn, String nameIn, int decimalIn, String chatColourIn, int rIn, int gIn, int bIn, boolean isDarkIn) {
		this.index = indexIn;
		this.name = nameIn;
		this.decimal = decimalIn;
		this.chat_colour = chatColourIn;
		this.RGB = new int[] { rIn, gIn, bIn};
		this.dark = isDarkIn;
	}

	public String toString() {
		return this.name;
	}

	public int getIndex() {
		return this.index;
	}

	public String getName() {
		return this.name;
	}

	public int dec() {
		return this.decimal;
	}

	public String getChatColour() {
		return this.chat_colour;
	}
	
	public IFormattableTextComponent getColouredName() {
		return CosmosCompHelper.locComp(this, true, this.name);
	}

	public static CosmosColour fromIndex(int colorId) {
		if (colorId < 0 || colorId >= VALUES.length) {
			colorId = 0;
		}

		return VALUES[colorId];
	}
	
	public int[] getRGB() {
		return this.RGB;
	}

	public boolean isDark() {
		return this.dark;
	}
	
	public static CosmosColour col(int decimal) {
		for (CosmosColour colour : values()) {
			if (colour.dec() == decimal) {
				return colour;
			}
		}
		
		return WHITE;
	}
	
	public static int[] rgbIntArray(int decimal) {
		for (CosmosColour colour : values()) {
			if (colour.dec() == decimal) {
				return colour.getRGB();
			}
		}
		
		return new int[] {255, 255, 255};
	}
	
	public static float[] rgbFloatArray(int decimal) {
		for (CosmosColour colour : values()) {
			if (colour.dec() == decimal) {
				int[] RGB_ = colour.getRGB();
				
				return new float[] {RGB_[0] / 255.0F, RGB_[1] / 255.0F, RGB_[2] / 255.0F };				
			}
		}
		
		return new float[] {1, 1, 1};
	}
}