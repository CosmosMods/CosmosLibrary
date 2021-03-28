package com.tcn.cosmoslibrary.common.enums;

import com.tcn.cosmoslibrary.common.comp.CosmosCompHelper;
import com.tcn.cosmoslibrary.common.comp.CosmosColour;

import net.minecraft.util.text.ITextComponent;

public enum EnumLockState {

	LOCKED(0, "locked", "cosmoslibrary.enum.lock_state.locked", true, CosmosColour.RED),
	UNLOCKED(1, "unlocked", "cosmoslibrary.enum.lock_state.unlocked", false, CosmosColour.GREEN),
	UNKNOWN(2, "unknown", "cosmoslibrary.enum.lock_state.unknown", false, CosmosColour.GRAY);
	
	private int index;
	private String name;
	private String localized_name;
	private boolean value;
	private CosmosColour colour;
	
	EnumLockState(int indexIn, String nameIn, String localizedNameIn, boolean valueIn, CosmosColour colourIn) {
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
	
	public static EnumLockState getOpposite(EnumLockState state) {
		if (state.equals(LOCKED)) {
			return UNLOCKED;
		} else {
			return LOCKED;
		}
	}
	
	public static EnumLockState getStateFromIndex(int index) {
		switch(index) {
			case 0:
				return LOCKED;
			case 1:
				return UNLOCKED;
			default:
				return UNKNOWN;
		}
	}
	
	public static EnumLockState getStateFromValue(boolean value) {
		if (value) {
			return LOCKED;
		} else {
			return UNLOCKED;
		}
	}
}
