package com.zeher.zeherlib.api.tesr;

import net.minecraft.util.IStringSerializable;

public enum EnumTESRColour implements IStringSerializable {
	
	RED("red", 255F/255F, 43F/255F, 39F/255F),
	GREEN("green", 54F/255F, 75F/255F, 24F/255F),
	BLUE("blue", 37F/255F, 49F/255F, 147F/255F),
    
	ORANGE("orange", 255F/255F, 102F/255F, 0F/255F),
	
	LIGHT_GREEN("light_green", 54F/255F, 255F/255F, 24F/255F),
    DARK_GREEN("dark_green", 16F/255F, 65F/255F, 53F/255F),
    LIGHT_BLUE("light_blue", 99F/255F, 135F/255F, 210F/255F),
    
    BLACK("black", 0.1F, 0.1F, 0.1F),
    WHITE("white", 0.9F, 0.9F, 0.9F);

    public final String name;
    public final float[] colour;

    EnumTESRColour(String name, float... conversionColorParticles){
        this.name = name;
        this.colour = conversionColorParticles;
    }

	@Override
	public String getName() {
		return this.name();
	}
    
}
