package com.zeher.zeherlib.api.interfaces;

import net.minecraft.util.EnumFacing;

public abstract interface IRotatableTile {
	
	public abstract boolean canRotate();

	public abstract void rotate();

	public abstract EnumFacing getDirectionFacing();
}
