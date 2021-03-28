package com.tcn.cosmoslibrary.common.enums;

import com.tcn.cosmoslibrary.common.comp.CosmosCompHelper;
import com.tcn.cosmoslibrary.common.comp.CosmosColour;

import net.minecraft.util.text.ITextComponent;

public enum EnumGeneralAllowState {

	ALLOWED(0, "allowed", "cosmoslibrary.enum.allow_state.allowed", true, CosmosColour.GREEN),
	BLOCKED(1, "blocked", "cosmoslibrary.enum.allow_state.blocked", false, CosmosColour.RED);
	
	private int index;
	private String name;
	private String localized_name;
	private boolean value;
	private CosmosColour colour;
	
	EnumGeneralAllowState(int indexIn, String nameIn, String localizedNameIn, boolean valueIn, CosmosColour colourIn) {
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
	
	public ITextComponent getColouredComp() {
		return CosmosCompHelper.locComp(this.colour, true, this.localized_name);
	}

	public boolean getValue() {
		return this.value;
	}

	public CosmosColour getColour() {
		return this.colour;
	}
	
	public static EnumGeneralAllowState getOpposite(EnumGeneralAllowState state) {
		if (state.equals(BLOCKED)) {
			return ALLOWED;
		} else {
			return BLOCKED;
		}
	}
	
	public static EnumGeneralAllowState getStateFromIndex(int index) {
		switch(index) {
			case 0:
				return ALLOWED;
			case 1:
				return BLOCKED;
			default:
				return ALLOWED;
		}
	}
	
	public static EnumGeneralAllowState getStateFromValue(boolean value) {
		if (value) {
			return ALLOWED;
		} else {
			return BLOCKED;
		}
	}
}
