package com.zeher.trzlib.fluid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class TRZFluid extends Fluid {

	public TRZFluid(String fluidName, ResourceLocation still, ResourceLocation flowing) {
		super(fluidName, still, flowing);
		this.setUnlocalizedName(fluidName);
	}

}
