package com.zeher.trzcore.transfer;

import java.util.Arrays;
import java.util.List;

import com.zeher.trzcore.api.TRZBlockPos;
import com.zeher.trzcore.api.connect.TRZEnergyPipeConnectionType;
import com.zeher.trzcore.api.connect.TRZPipeConnectionsList;
import com.zeher.trzcore.transfer.TRZTileEntityEnergyPipe;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TRZTileEntityEnergyPipe extends TileEntity implements TRZIEnergyPipe {

	public int stored;
	public int capacity;
	public int transfer_rate;
	
	public EnumFacing last_from;
	
	public int up;
	public int down;
	public int north;
	public int south;
	public int east;
	public int west;

	public int getStored() {
		return this.stored;
	}
	
	public EnumFacing getLastFrom(){
		return this.last_from;
	}

	public int getCapacity() {
		return this.capacity;
	}
	
	public int getTransferRate(){
		return this.transfer_rate;
	}
	
	public void addStored(int add){
		this.stored += add;
		this.markDirty();
	}
	
	public void addStored(int add, EnumFacing side_from){
		this.stored += add;
		this.markDirty();
	}

	public void setPower(int stored) {
		boolean mustUpdate = stored != this.stored;
		this.stored = stored;
		if (mustUpdate) {
		}
	}

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

	public int getPowerConnectionState(EnumFacing side, int x, int z, int y) {
		return 0;
	}

	public TRZEnergyPipeConnectionType getEnergyConnectionState(EnumFacing side) {
		TRZBlockPos bp = new TRZBlockPos(this);
		bp.orientation = side;
		bp.moveForwards(1);

		TRZTileEntityEnergyPipe tile = (TRZTileEntityEnergyPipe) world.getTileEntity(pos);
		TileEntity tileOther = world.getTileEntity(bp.pos);

		IBlockState blockId = this.world.getBlockState(bp.pos);
		if (blockId == null) {
			return TRZEnergyPipeConnectionType.none;
		}
		if (blockId.getBlock() instanceof TRZBlockEnergyPipe) {
			if (tileOther != null && tileOther instanceof TRZTileEntityEnergyPipe) {
				if (side.equals(EnumFacing.UP) && (this.up == 2 || this.up == 0)) {
					if (((TRZTileEntityEnergyPipe) tileOther).getSide("down") == 1) {
						return TRZEnergyPipeConnectionType.none;
					} else {
						return TRZEnergyPipeConnectionType.cableall;
					}
				}
				if (side.equals(EnumFacing.DOWN) && (this.down == 2 || this.down == 0)) {
					if (((TRZTileEntityEnergyPipe) tileOther).getSide("up") == 1) {
						return TRZEnergyPipeConnectionType.none;
					} else {
						return TRZEnergyPipeConnectionType.cableall;
					}
				}
				if (side.equals(EnumFacing.NORTH) && (this.north == 2 || this.north == 0)) {
					if (((TRZTileEntityEnergyPipe) tileOther).getSide("south") == 1) {
						return TRZEnergyPipeConnectionType.none;
					} else {
						return TRZEnergyPipeConnectionType.cableall;
					}
				}
				if (side.equals(EnumFacing.SOUTH) && (this.south == 2 || this.south == 0)) {
					if (((TRZTileEntityEnergyPipe) tileOther).getSide("north") == 1) {
						return TRZEnergyPipeConnectionType.none;
					} else {
						return TRZEnergyPipeConnectionType.cableall;
					}
				}
				if (side.equals(EnumFacing.EAST) && (this.east == 2 || this.east == 0)) {
					if (((TRZTileEntityEnergyPipe) tileOther).getSide("west") == 1) {
						return TRZEnergyPipeConnectionType.none;
					} else {
						return TRZEnergyPipeConnectionType.cableall;
					}
				}
				if (side.equals(EnumFacing.WEST) && (this.west == 2 || this.west == 0)) {
					if (((TRZTileEntityEnergyPipe) tileOther).getSide("east") == 1) {
						return TRZEnergyPipeConnectionType.none;
					} else {
						return TRZEnergyPipeConnectionType.cableall;
					}
				}
			}
		}
		if (side.equals(EnumFacing.UP)) {
			if (this.up == 2) {
				return TRZEnergyPipeConnectionType.cableall;
			}
			if (this.up == 1) {
				return TRZEnergyPipeConnectionType.none;
			}
		}
		if (side.equals(EnumFacing.DOWN)) {
			if (this.down == 2) {
				return TRZEnergyPipeConnectionType.cableall;
			}
			if (this.down == 1) {
				return TRZEnergyPipeConnectionType.none;
			}
		}
		if (side.equals(EnumFacing.NORTH)) {
			if (this.north == 2) {
				return TRZEnergyPipeConnectionType.cableall;
			}
			if (this.north == 1) {
				return TRZEnergyPipeConnectionType.none;
			}
		}
		if (side.equals(EnumFacing.SOUTH)) {
			if (this.south == 2) {
				return TRZEnergyPipeConnectionType.cableall;
			}
			if (this.south == 1) {
				return TRZEnergyPipeConnectionType.none;
			}
		}
		if (side.equals(EnumFacing.EAST)) {
			if (this.east == 2) {
				return TRZEnergyPipeConnectionType.cableall;
			}
			if (this.east == 1) {
				return TRZEnergyPipeConnectionType.none;
			}
		}
		if (side.equals(EnumFacing.WEST)) {
			if (this.west == 2) {
				return TRZEnergyPipeConnectionType.cableall;
			}
			if (this.west == 1) {
				return TRZEnergyPipeConnectionType.none;
			}
		}
		if (TRZPipeConnectionsList.getEnergyConnectionBlocks(side, blockId, world, bp.pos)) {
			return TRZEnergyPipeConnectionType.cablesingle;
		}
		return TRZEnergyPipeConnectionType.none;
	}

	public void readFromNBT(NBTTagCompound compound) {
		this.up = compound.getInteger("up");
		super.readFromNBT(compound);

	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("up", this.up);
		return super.writeToNBT(compound);
	}

}
