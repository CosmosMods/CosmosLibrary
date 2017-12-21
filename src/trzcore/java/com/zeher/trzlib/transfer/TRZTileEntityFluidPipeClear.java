package com.zeher.trzlib.transfer;

import java.util.Arrays;
import java.util.List;

import com.zeher.trzlib.api.TRZBlockPos;
import com.zeher.trzlib.api.connect.TRZFluidPipeConnectionType;
import com.zeher.trzlib.api.connect.TRZPipeConnectionsList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TRZTileEntityFluidPipeClear extends TRZTileEntityFluidPipe implements IFluidHandler, ITickable {

	public FluidTank tank = new FluidTank(0);
	public EnumFacing side;
	private static List<Integer> _connectionWhitelist = Arrays.asList(new Integer[0]);
	private static List<Integer> _connectionBlackList;

	public int up;
	public int down;
	public int north;
	public int south;
	public int east;
	public int west;

	public void setSide(String str, int value) {
		if (str == "up") {
			this.up = value;
		}
		if (str == "down") {
			this.down = value;
		}
		if (str == "north") {
			this.north = value;
		}
		if (str == "south") {
			this.south = value;
		}
		if (str == "east") {
			this.east = value;
		}
		if (str == "west") {
			this.west = value;
		}
	}

	public int getSide(String str) {
		if (str == "up") {
			return this.up;
		}
		if (str == "down") {
			return this.down;
		}
		if (str == "north") {
			return this.north;
		}
		if (str == "south") {
			return this.south;
		}
		if (str == "east") {
			return this.east;
		}
		if (str == "west") {
			return this.west;
		} else {
			return 0;
		}
	}

	public TRZFluidPipeConnectionType getFluidConnectionState(EnumFacing side) {
		TRZBlockPos bp = new TRZBlockPos(this);
		bp.orientation = side;
		bp.moveForwards(1);

		TRZTileEntityFluidPipeClear tile = (TRZTileEntityFluidPipeClear) world.getTileEntity(pos);
		TileEntity tileOther = world.getTileEntity(bp.pos);

		IBlockState blockId = this.world.getBlockState(bp.pos);
		if (blockId == null) {
			return TRZFluidPipeConnectionType.none;
		}
		if (blockId.getBlock() instanceof TRZBlockFluidPipe) {
			if (tileOther != null && tileOther instanceof TRZTileEntityFluidPipeClear) {
				if (side.equals(EnumFacing.UP) && (this.up == 2 || this.up == 0)) {
					if (((TRZTileEntityFluidPipeClear) tileOther).getSide("down") == 1) {
						return TRZFluidPipeConnectionType.none;
					} else {
						return TRZFluidPipeConnectionType.cableall;
					}
				}
				if (side.equals(EnumFacing.DOWN) && (this.down == 2 || this.down == 0)) {
					if (((TRZTileEntityFluidPipeClear) tileOther).getSide("up") == 1) {
						return TRZFluidPipeConnectionType.none;
					} else {
						return TRZFluidPipeConnectionType.cableall;
					}
				}
				if (side.equals(EnumFacing.NORTH) && (this.north == 2 || this.north == 0)) {
					if (((TRZTileEntityFluidPipeClear) tileOther).getSide("south") == 1) {
						return TRZFluidPipeConnectionType.none;
					} else {
						return TRZFluidPipeConnectionType.cableall;
					}
				}
				if (side.equals(EnumFacing.SOUTH) && (this.south == 2 || this.south == 0)) {
					if (((TRZTileEntityFluidPipeClear) tileOther).getSide("north") == 1) {
						return TRZFluidPipeConnectionType.none;
					} else {
						return TRZFluidPipeConnectionType.cableall;
					}
				}
				if (side.equals(EnumFacing.EAST) && (this.east == 2 || this.east == 0)) {
					if (((TRZTileEntityFluidPipeClear) tileOther).getSide("west") == 1) {
						return TRZFluidPipeConnectionType.none;
					} else {
						return TRZFluidPipeConnectionType.cableall;
					}
				}
				if (side.equals(EnumFacing.WEST) && (this.west == 2 || this.west == 0)) {
					if (((TRZTileEntityFluidPipeClear) tileOther).getSide("east") == 1) {
						return TRZFluidPipeConnectionType.none;
					} else {
						return TRZFluidPipeConnectionType.cableall;
					}
				}
			}
		}
		if (side.equals(EnumFacing.UP)) {
			if (this.up == 2) {
				return TRZFluidPipeConnectionType.cableall;
			}
			if (this.up == 1) {
				return TRZFluidPipeConnectionType.none;
			}
		}
		if (side.equals(EnumFacing.DOWN)) {
			if (this.down == 2) {
				return TRZFluidPipeConnectionType.cableall;
			}
			if (this.down == 1) {
				return TRZFluidPipeConnectionType.none;
			}
		}
		if (side.equals(EnumFacing.NORTH)) {
			if (this.north == 2) {
				return TRZFluidPipeConnectionType.cableall;
			}
			if (this.north == 1) {
				return TRZFluidPipeConnectionType.none;
			}
		}
		if (side.equals(EnumFacing.SOUTH)) {
			if (this.south == 2) {
				return TRZFluidPipeConnectionType.cableall;
			}
			if (this.south == 1) {
				return TRZFluidPipeConnectionType.none;
			}
		}
		if (side.equals(EnumFacing.EAST)) {
			if (this.east == 2) {
				return TRZFluidPipeConnectionType.cableall;
			}
			if (this.east == 1) {
				return TRZFluidPipeConnectionType.none;
			}
		}
		if (side.equals(EnumFacing.WEST)) {
			if (this.west == 2) {
				return TRZFluidPipeConnectionType.cableall;
			}
			if (this.west == 1) {
				return TRZFluidPipeConnectionType.none;
			}
		}
		if (TRZPipeConnectionsList.getFluidConnectionBlocks(side, blockId, world, bp.pos)) {
			return TRZFluidPipeConnectionType.cablesingle;
		}
		return TRZFluidPipeConnectionType.none;
	}

	public void readFromNBT(NBTTagCompound compound) {
		this.up = compound.getInteger("up");
		super.readFromNBT(compound);

	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("up", this.up);
		return super.writeToNBT(compound);
	}

	public int fill(FluidStack resource, boolean doFill) {
		return this.tank.fill(resource, doFill);
	}

	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return this.tank.drain(resource.amount, doDrain);
	}

	public FluidStack drain(int maxDrain, boolean doDrain) {
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
	public void update() {
	}
}
