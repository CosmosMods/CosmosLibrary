package com.zeher.zeherlib.api.connection;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public enum EnumSide implements IStringSerializable {

	OUTPUT_TO(0, "output_to", "gui.button.side.output_to", 0x00FF00), 
	INPUT_FROM(1, "input_from", "gui.button.side.input_from", 0x2ECCFA),
	NONE(2, "none", "gui.button.side.none", 0xFF0000);
	
	private final int index;
	private final String name;
	private final String gui_name;
	private final int gui_colour;
	
	public static final EnumSide[] VALUES = new EnumSide[3];
	
	private EnumSide (int index_in, String name_in, String gui_name, int gui_colour) {
		this.index = index_in;
		this.name = name_in;
		this.gui_name = gui_name;
		this.gui_colour = gui_colour;
	}
	
	public EnumSide getNext() {
		switch (this) {
		case OUTPUT_TO:
			return INPUT_FROM;
		case INPUT_FROM:
			return NONE;
		case NONE:
			return OUTPUT_TO;
		default:
            throw new IllegalStateException("Unable to find next side of " + this);
		}
	}
	
	public static EnumSide[] getDefaultArray() {
		return new EnumSide[] { OUTPUT_TO, EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO };
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	public String getName2() {
		return this.name;
	}
	
	@Override
	public String toString() {
        return this.name;
    }
	
	public String getGuiName() {
		return I18n.format(gui_name).toString();
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public int getGuiColour() {
		return this.gui_colour;
	}
	
	public static EnumSide getSideFromIndex(int index_in) {
		switch (index_in) {
			case 0 :
				return OUTPUT_TO;
			case 1:
				return INPUT_FROM;
			case 2: 
				return NONE;
			default:
				throw new IllegalStateException("No Enum exists with that index: " + "[ " + index_in + " ]");
		}
	}
}
