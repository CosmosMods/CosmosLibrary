package com.tcn.cosmoslibrary.common.interfaces.blockentity;

import com.tcn.cosmoslibrary.common.enums.EnumChannelSideState;

import net.minecraft.core.Direction;

/**
 * Collection of interfaces for use in the implementation of channels.
 * Not to be used on standard blocks.
 */
public class IBlockEntityChannel { 

	/**
	 * This is an extension of {@link IBlockEntityChannelSided} for use on [energy] channels.
	 * This should not be used on standard blocks.
	 */
	public interface Energy extends IBlockEntityChannelSided {
		
		/**
		 * Returns a {@link EnumChannelSideState} for connections to other channels.
		 */
		public EnumChannelSideState getStateForConnection(Direction facing);
	}
	
	/**
	 * This is an extension of {@link IBlockEntityChannelSided} for use on [fluid] channels.
	 * This should not be used on standard blocks.
	 */
	public interface Fluid extends IBlockEntityChannelSided {
		
		/**
		 * Returns a {@link EnumChannelSideState} for connections to other channels.
		 */
		public EnumChannelSideState getStateForConnection(Direction facing);
	}
	
	/**
	 * This is an extension of {@link IBlockEntityChannelSided} for use on [item] channels.
	 * This should not be used on standard blocks.
	 */
	public interface Item extends IBlockEntityChannelSided {
		
		/**
		 * Returns a {@link EnumChannelSideState} for connections to other channels.
		 */
		public EnumChannelSideState getStateForConnection(Direction facing);
	}
}