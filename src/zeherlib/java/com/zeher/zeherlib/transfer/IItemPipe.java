package com.zeher.zeherlib.transfer;

import com.zeher.zeherlib.api.connection.ConnectionType;

import net.minecraft.util.EnumFacing;

public interface IItemPipe {

	public EnumFacing last_from = null;
	
	public int up = 0;
	public int down = 0;
	public int north = 0;
	public int south = 0;
	public int east = 0;
	public int west = 0;
	

	public int getSide(EnumFacing facing);
	public int getSide(String str);
	
	public void setSide(EnumFacing facing, int value);
	public void setSide(String str, int value);
	
	public ConnectionType.PIPE.ITEM getItemConnectionState(EnumFacing side);

}
