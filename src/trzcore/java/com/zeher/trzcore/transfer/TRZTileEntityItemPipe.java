package com.zeher.trzcore.transfer;

import java.util.Arrays;
import java.util.List;

import com.zeher.trzcore.api.TRZBlockPos;
import com.zeher.trzcore.api.connect.TRZItemPipeConnectionType;
import com.zeher.trzcore.api.connect.TRZPipeConnectionsList;
import com.zeher.trzcore.transfer.TRZTileEntityItemPipe;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TRZTileEntityItemPipe
  extends TileEntity {
  
  public EnumFacing side;
  private static List<Integer> _connectionWhitelist = Arrays.asList(new Integer[0]);
  private static List<Integer> _connectionBlackList;
  
  public int up;
  public int down;
  public int north;
  public int south;
  public int east;
  public int west;
  
  public void setSide(String str, int value)
  {
	  if(str == "up"){
		  this.up = value;
	  }
	  if(str == "down"){
		  this.down = value;
	  }
	  if(str == "north"){
		  this.north = value;
	  }
	  if(str == "south"){
		  this.south = value;
	  }
	  if(str == "east"){
		  this.east = value;
	  }
	  if(str == "west"){
		  this.west = value;
	  }
  }
  
  public int getSide(String str)
  {
	  if(str == "up"){
		  return this.up;
	  }
	  if(str == "down"){
		  return this.down;
	  }
	  if(str == "north"){
		  return this.north;
	  }
	  if(str == "south"){
		  return this.south;
	  }
	  if(str == "east"){
		  return this.east;
	  }
	  if(str == "west"){
		  return this.west;
	  }else {
	  return 0;
	  }
  }
  
  public TRZItemPipeConnectionType getItemConnectionState(EnumFacing side)
  {
    TRZBlockPos bp = new TRZBlockPos(this);
    bp.orientation = side;
    bp.moveForwards(1);
    
    TRZTileEntityItemPipe tile = (TRZTileEntityItemPipe)world.getTileEntity(pos);
    TileEntity tileOther = world.getTileEntity(bp.pos);
    
    IBlockState blockId = this.world.getBlockState(bp.pos);
    if (blockId == null) {
      return TRZItemPipeConnectionType.none;
    }
    if(blockId.getBlock() instanceof TRZBlockItemPipe){
	    if(tileOther != null && tileOther instanceof TRZTileEntityItemPipe){
	    	if(side.equals(EnumFacing.UP) && (this.up == 2 || this.up == 0)){
	    		if(((TRZTileEntityItemPipe)tileOther).getSide("down") == 1){
	    			return TRZItemPipeConnectionType.none;
	    		} else {
	    			return TRZItemPipeConnectionType.cableall;
	    		}
	    	}
	    	if(side.equals(EnumFacing.DOWN) && (this.down == 2 || this.down == 0)){
	    		if(((TRZTileEntityItemPipe)tileOther).getSide("up") == 1){
	    			return TRZItemPipeConnectionType.none;
	    		} else {
	    			return TRZItemPipeConnectionType.cableall;
	    		}
	    	}
	    	if(side.equals(EnumFacing.NORTH) && (this.north == 2 || this.north == 0)){
	    		if(((TRZTileEntityItemPipe)tileOther).getSide("south") == 1){
	    			return TRZItemPipeConnectionType.none;
	    		} else {
	    			return TRZItemPipeConnectionType.cableall;
	    		}
	    	}
	    	if(side.equals(EnumFacing.SOUTH) && (this.south == 2 || this.south == 0)){
	    		if(((TRZTileEntityItemPipe)tileOther).getSide("north") == 1){
	    			return TRZItemPipeConnectionType.none;
	    		} else {
	    			return TRZItemPipeConnectionType.cableall;
	    		}
	    	}
	    	if(side.equals(EnumFacing.EAST) && (this.east == 2 || this.east == 0)){
	    		if(((TRZTileEntityItemPipe)tileOther).getSide("west") == 1){
	    			return TRZItemPipeConnectionType.none;
	    		} else {
	    			return TRZItemPipeConnectionType.cableall;
	    		}
	    	}
	    	if(side.equals(EnumFacing.WEST) && (this.west == 2 || this.west == 0)){
	    		if(((TRZTileEntityItemPipe)tileOther).getSide("east") == 1){
	    			return TRZItemPipeConnectionType.none;
	    		} else {
	    			return TRZItemPipeConnectionType.cableall;
	    		}
	    	}
	    }
    }
    if(side.equals(EnumFacing.UP))
    {
    	if(this.up == 2){
    		return TRZItemPipeConnectionType.cableall;
    	}
    	if(this.up == 1){
    		return TRZItemPipeConnectionType.none;
    	}
    }
    if(side.equals(EnumFacing.DOWN))
    {
    	if(this.down == 2){
    		return TRZItemPipeConnectionType.cableall;
    	}
    	if(this.down == 1){
    		return TRZItemPipeConnectionType.none;
    	}
    }
    if(side.equals(EnumFacing.NORTH))
    {
    	if(this.north == 2){
    		return TRZItemPipeConnectionType.cableall;
    	}
    	if(this.north == 1){
    		return TRZItemPipeConnectionType.none;
    	}
    }
    if(side.equals(EnumFacing.SOUTH))
    {
    	if(this.south == 2){
    		return TRZItemPipeConnectionType.cableall;
    	}
    	if(this.south == 1){
    		return TRZItemPipeConnectionType.none;
    	}
    }
    if(side.equals(EnumFacing.EAST))
    {
    	if(this.east == 2){
    		return TRZItemPipeConnectionType.cableall;
    	}
    	if(this.east == 1){
    		return TRZItemPipeConnectionType.none;
    	}
    }
    if(side.equals(EnumFacing.WEST))
    {
    	if(this.west == 2){
    		return TRZItemPipeConnectionType.cableall;
    	}
    	if(this.west == 1){
    		return TRZItemPipeConnectionType.none;
    	}
    }
    if(TRZPipeConnectionsList.getItemConnectionBlocks(side, blockId, world, bp.pos))
    {
    	return TRZItemPipeConnectionType.cablesingle;
    }
	   return TRZItemPipeConnectionType.none;
}
 
  public void readFromNBT(NBTTagCompound compound)
  {
      this.up = compound.getInteger("up");
      super.readFromNBT(compound);
      
  }

  public NBTTagCompound writeToNBT(NBTTagCompound compound)
  {
      compound.setInteger("up", this.up);
      return super.writeToNBT(compound);
  }

}
