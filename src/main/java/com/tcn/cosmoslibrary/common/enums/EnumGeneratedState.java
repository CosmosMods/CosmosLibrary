package com.tcn.cosmoslibrary.common.enums;

import com.tcn.cosmoslibrary.common.comp.CosmosColour;
import com.tcn.cosmoslibrary.common.comp.CosmosCompHelper;

import net.minecraft.network.chat.BaseComponent;

public enum EnumGeneratedState {
	
	UNGENERATED(0, "ungenerated", "cosmoslibrary.enum.generated.false", false, CosmosColour.RED), 
	GENERATED(1, "generated", "cosmoslibrary.enum.generated.true", true, CosmosColour.GREEN);
	
	private int index;
	private String name;
	private String localized_name;
	private boolean value;
	private CosmosColour colour;
	
	EnumGeneratedState(int indexIn, String nameIn, String localizedNameIn, boolean valueIn, CosmosColour colourIn) {
		this.index = indexIn;
		this.name = nameIn;
		this.localized_name = localizedNameIn;
		this.value = valueIn;
		this.colour = colourIn;
	}

	public int getIndex() {
		return this.index;
	}

	public String getName() {
		return this.name;
	}
	
	public String getUnlocalizedName() {
		return this.localized_name;
	}
	
	public BaseComponent getColouredComp() {
		return CosmosCompHelper.locComp(this.colour, true, this.localized_name);
	}

	public boolean getValue() {
		return this.value;
	}

	public CosmosColour getColour() {
		return this.colour;
	}
	
	public static EnumGeneratedState getOpposite(EnumGeneratedState state) {
		if (state.equals(UNGENERATED)) {
			return GENERATED;
		} else {
			return UNGENERATED;
		}
	}
	
	public static EnumGeneratedState getStateFromIndex(int index) {
		switch(index) {
			case 0:
				return UNGENERATED;
			case 1:
				return GENERATED;
			default:
				return UNGENERATED;
		}
	}
	
	public static EnumGeneratedState getStateFromValue(boolean value) {
		if (value) {
			return GENERATED;
		} else {
			return UNGENERATED;
		}
	}
}