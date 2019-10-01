package com.zeher.zeherlib.transfer;

import com.zeher.zeherlib.api.connection.ConnectionType;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public interface IFluidPipe {
	
	public FluidTank tank = null;
	public EnumFacing side = null;
	
	public int up = 0;
	public int down = 0;
	public int north = 0;
	public int south = 0;
	public int east = 0;
	public int west = 0;
	
	public int output_rate = 0;
	
	public EnumFacing last_from = null;

	public int getSide(EnumFacing facing);
	public int getSide(String str);
	
	public void setSide(EnumFacing facing, int value);
	public void setSide(String str, int value);
	
	public ConnectionType.PIPE.FLUID getFluidConnectionState(EnumFacing side);
	
	public int fill(FluidStack resource, boolean doFill, EnumFacing side_from);
	public int fill(FluidStack resource, boolean doFill);
	
	public FluidStack drain(FluidStack resource, boolean doDrain);
	public FluidStack drain(int maxDrain, boolean doDrain);
	
	public boolean canFill(EnumFacing from, Fluid fluid);
	public boolean canDrain(EnumFacing from, Fluid fluid);
	
	public FluidTankInfo[] getTankInfo(EnumFacing from);
	public IFluidTankProperties[] getTankProperties();
	
	public FluidTank getTank();
	
	public int getOutputRate();
	
	public boolean isFluidEmpty();
	
	public EnumFacing getLastFrom();
	
}
