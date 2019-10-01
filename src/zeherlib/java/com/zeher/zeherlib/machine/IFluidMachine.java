package com.zeher.zeherlib.machine;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public interface IFluidMachine {
	public int stored = 0;
	public int capacity = 0;
	public int input_rate = 0;
	public int cook_speed = 0;
	
	public boolean needsUpdate = false;
	public int updateTimer = 0;
	
	public FluidTank tank = new FluidTank(0);

	public int getStored();
	public int getCapacity();
	public int getInputRate();
	public int getCookSpeed();
	
	public boolean hasStored();
	public void addStored(int add);
	
	public int fill(FluidStack resource, boolean doDrain);
	public FluidStack drain(FluidStack resource, boolean doDrain);
	public FluidStack drain(int maxDrain, boolean doDrain);
	
	public boolean canFill(EnumFacing from, Fluid fluid);
	public boolean canDrain(EnumFacing from, Fluid fluid);
	
	public FluidTankInfo[] getTankInfo(EnumFacing from);
	public IFluidTankProperties[] getTankProperties();
	public FluidTank getTank();
}
