package com.zeher.trzcore.storage;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TRZTileEntityFluidTank extends TileEntity implements IFluidHandler, ITickable {

	public FluidTank tank;
	private boolean needsUpdate = false;
	private int updateTimer = 0;
	
	public int up;
	public int down;
	public int north;
	public int south;
	public int east;
	public int west;
	
	
	public int fill(FluidStack resource, boolean doFill) {
		needsUpdate = true;
		return this.tank.fill(resource, doFill);
	}

	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return this.tank.drain(resource.amount, doDrain);
	}

	public FluidStack drain(int maxDrain, boolean doDrain) {
		needsUpdate = true;
		return this.tank.drain(maxDrain, doDrain);
	}

	public boolean canFill(EnumFacing from, Fluid fluid) {
		return true;
	}

	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return true;
	}

	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		return new FluidTankInfo[] { this.tank.getInfo() };
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties() {
		return this.tank.getTankProperties();
	}
	
	public FluidTank getTank(){
		return this.tank;
	}

	@Override
	public void update() {}
}
