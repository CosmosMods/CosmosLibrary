package com.tcn.cosmoslibrary.impl.colour;

import java.util.Arrays;
import java.util.Comparator;

public enum EnumMinecraftColour {

	WHITE(0, "White", 16777215, ChatColour.WHITE), 
	ORANGE(1, "Orange", 16755200, ChatColour.ORANGE),
	MAGENTA(2, "Magenta", 16733695, ChatColour.MAGENTA), 
	LIGHT_BLUE(3, "Light_blue", 5592575, ChatColour.LIGHT_BLUE),
	YELLOW(4, "Yellow", 16777045, ChatColour.YELLOW), 
	LIME(5, "Lime", 5635925, ChatColour.BRIGHT_GREEN),
	PINK(6, "Pink", 15961002, ""),
	GRAY(7, "Gray", 5592405, ChatColour.GRAY), 
	LIGHT_GRAY(8, "Light Gray", 11184810, ChatColour.LIGHT_GRAY),
	CYAN(9, "Cyan", 43690, ChatColour.CYAN), 
	PURPLE(10, "Purple", 11141290, ChatColour.PURPLE), 
	BLUE(11, "Blue", 170, ChatColour.BLUE),
	BROWN(12, "Brown", 8606770, ""), 
	GREEN(13, "Green", 43520, ChatColour.GREEN), 
	RED(14, "Red", 16733535, ChatColour.RED),
	BLACK(15, "Black", 0, ChatColour.BLACK), 
	POCKET_PURPLE(16, "Pocket Purple", 2296885, ChatColour.PURPLE),
	POCKET_PURPLE_LIGHT (17, "Pocket Purple Light", 6627993, ChatColour.PURPLE);

	private final int index;
	private final String name;
	private final int decimal;
	private final String chat_colour;

	private static final EnumMinecraftColour[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(EnumMinecraftColour::getIndex)).toArray((colorId) -> {
		return new EnumMinecraftColour[colorId];
	});

	EnumMinecraftColour(int index, String name, int decimal, String chatColourIn) {
		this.index = index;
		this.name = name;
		this.decimal = decimal;
		this.chat_colour = chatColourIn;
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

	public int getDecimal() {
		return this.decimal;
	}

	public String getChatColour() {
		return this.chat_colour;
	}
	
	public String getColouredName() {
		return this.chat_colour + this.name;
	}

	public static EnumMinecraftColour byIndex(int colorId) {
		if (colorId < 0 || colorId >= VALUES.length) {
			colorId = 0;
		}

		return VALUES[colorId];
	}
}