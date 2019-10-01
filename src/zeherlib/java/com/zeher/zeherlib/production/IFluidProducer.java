package com.zeher.zeherlib.production;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public interface IFluidProducer {
	public int stored = 0;
	public int capacity = 0;
	public int output_rate = 0;
	public int generation_rate = 0;
	
	public int getStored();
	public int getCapacity();
	public int getOutputRate();
	public int getGenerationRate();
	
	public boolean hasStored();
	
	public int fill(FluidStack resource, boolean doDrain);
	public FluidStack drain(FluidStack resource, boolean doDrain);
	public FluidStack drain(int maxDrain, boolean doDrain);
	
	public boolean canFill(EnumFacing from, Fluid fluid);
	public boolean canDrain(EnumFacing from, Fluid fluid);
	
	public FluidTankInfo[] getTankInfo(EnumFacing from);
	public IFluidTankProperties[] getTankProperties();
	public FluidTank getTank();
	
	public void minusStored(Integer value);
}
