package com.zeher.zeherlib.storage;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public interface IModFluidTank {
	
	public FluidTank tank = new FluidTank(0);
	
	public int up = 0;
	public int down = 0;
	public int north = 0;
	public int south = 0;
	public int east = 0;
	public int west = 0;
	
	public int output_rate = 0;
	
	public int fill(FluidStack resource, boolean doDrain);
	public FluidStack drain(FluidStack resource, boolean doDrain);
	public FluidStack drain(int maxDrain, boolean doDrain);
	
	public boolean canFill(EnumFacing from, Fluid fluid);
	public boolean canDrain(EnumFacing from, Fluid fluid);
	
	public FluidTankInfo[] getTankInfo(EnumFacing from);
	public IFluidTankProperties[] getTankProperties();
	public FluidTank getTank();
	
	public int getSide(String str);
	public int getSide(EnumFacing facing);
	
	public boolean isFluidEmpty();
	
	public int getOutputRate();
}
