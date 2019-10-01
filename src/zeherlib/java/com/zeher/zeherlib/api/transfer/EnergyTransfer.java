package com.zeher.zeherlib.api.transfer;

import com.zeher.zeherlib.api.connection.EnumSide;
import com.zeher.zeherlib.machine.BlockFluidMachine;
import com.zeher.zeherlib.machine.BlockMachine;
import com.zeher.zeherlib.machine.IFluidMachine;
import com.zeher.zeherlib.machine.IMachine;
import com.zeher.zeherlib.production.BlockFluidProducer;
import com.zeher.zeherlib.production.BlockProducer;
import com.zeher.zeherlib.production.IFluidProducer;
import com.zeher.zeherlib.production.IProducer;
import com.zeher.zeherlib.storage.IStorage;
import com.zeher.zeherlib.transfer.IEnergyPipe;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnergyTransfer {
	
	public static class OUTPUT {
		
		public static class PRODUCER {
			
			public static void outputEnergy(World world, BlockPos tile_pos) {
				TileEntity tile_orig = world.getTileEntity(tile_pos);
	
				IBlockState state = world.getBlockState(tile_pos);
				Block block = state.getBlock();
				EnumFacing facing = state.getValue(((BlockProducer) block).FACING);
				
				if (tile_orig != null && tile_orig instanceof IProducer) {
					for (EnumFacing c : EnumFacing.VALUES) {
						if (facing.equals(EnumFacing.NORTH) || facing.equals(EnumFacing.SOUTH)) {
							if (c.equals(EnumFacing.NORTH) || c.equals(EnumFacing.SOUTH)) {
								
								TileEntity tile_offset = world.getTileEntity(tile_pos.offset(c));
								
								if (tile_offset != null) {
									if (tile_offset instanceof IEnergyPipe) {
										if (((IEnergyPipe) tile_offset).getSide(c.getOpposite()).equals(EnumSide.INPUT_FROM)) {
											int pipe_stored = ((IEnergyPipe) tile_offset).getStored();
											int pipe_capacity = ((IEnergyPipe) tile_offset).getCapacity();
											int pipe_transfer_rate = ((IEnergyPipe) tile_offset).getTransferRate();
											int pipe_gap_stored = pipe_capacity - pipe_stored;
											
											if (pipe_stored <= pipe_capacity) {
												if (pipe_transfer_rate <= ((IProducer) tile_orig).getGenerationRate()) {
													if (pipe_gap_stored >= pipe_transfer_rate) {
														((IProducer) tile_orig).minusStored(pipe_transfer_rate);
														((IEnergyPipe) tile_offset).addStored(pipe_transfer_rate, c);
													} else if (pipe_gap_stored < pipe_transfer_rate) {
														((IProducer) tile_orig).minusStored(pipe_gap_stored);
														((IEnergyPipe) tile_offset).addStored(pipe_gap_stored, c);
													}
												} else if (pipe_transfer_rate > ((IProducer) tile_orig).getGenerationRate()) {
													if (pipe_gap_stored >= ((IProducer) tile_orig).getGenerationRate()) {
														((IProducer) tile_orig).minusStored(((IProducer) tile_orig).getGenerationRate());
														((IEnergyPipe) tile_offset).addStored(((IProducer) tile_orig).getGenerationRate(), c);
													} else if (pipe_gap_stored < ((IProducer) tile_orig).getGenerationRate()) {
														((IProducer) tile_orig).minusStored(pipe_gap_stored);
														((IEnergyPipe) tile_offset).addStored(pipe_gap_stored, c);
													}
												}
											}
										}
									} else if (tile_offset instanceof IMachine) {
										IBlockState state_offset = world.getBlockState(tile_pos.offset(c));
										BlockMachine block_offset = (BlockMachine) state_offset.getBlock();
										EnumFacing facing_offset = state_offset.getValue(block_offset.FACING);
										
										if (facing_offset.equals(c.getOpposite())) {
											
										} else {
											int machine_stored = ((IMachine) tile_offset).getStored();
											int machine_capacity = ((IMachine) tile_offset).getCapacity();
											int machine_input_rate = ((IMachine) tile_offset).getInputRate();
											int machine_gap_stored = machine_capacity - machine_stored;
		
											if (machine_stored <= machine_capacity) {
												if (machine_gap_stored >= machine_input_rate) {
													((IProducer) tile_orig).minusStored(machine_input_rate);
													((IMachine) tile_offset).addStored(machine_input_rate);
												} else if (machine_gap_stored < machine_input_rate) {
													((IProducer) tile_orig).minusStored(machine_gap_stored);
													((IMachine) tile_offset).addStored(machine_gap_stored);
												}
											}
										}
									}  else if (tile_offset instanceof IFluidMachine) {
										IBlockState state_offset = world.getBlockState(tile_pos.offset(c));
										BlockFluidMachine block_offset = (BlockFluidMachine) state_offset.getBlock();
										EnumFacing facing_offset = state_offset.getValue(block_offset.FACING);
										
										if (facing_offset.equals(c.getOpposite())) {
											
										} else {
											int machine_stored = ((IFluidMachine) tile_offset).getStored();
											int machine_capacity = ((IFluidMachine) tile_offset).getCapacity();
											int machine_input_rate = ((IFluidMachine) tile_offset).getInputRate();
											int machine_gap_stored = machine_capacity - machine_stored;
		
											if (machine_stored <= machine_capacity) {
												if (machine_gap_stored >= machine_input_rate) {
													((IProducer) tile_orig).minusStored(machine_input_rate);
													((IFluidMachine) tile_offset).addStored(machine_input_rate);
												} else if (machine_gap_stored < machine_input_rate) {
													((IProducer) tile_orig).minusStored(machine_gap_stored);
													((IFluidMachine) tile_offset).addStored(machine_gap_stored);
												}
											}
										}
									} else if (tile_offset instanceof IStorage) {
										EnumSide side_mode = ((IStorage) tile_offset).getSide(c);
										
										if (side_mode.equals(EnumSide.OUTPUT_TO)) {
											int capacitor_stored = ((IStorage) tile_offset).getStored();
											int capacitor_capacity = ((IStorage) tile_offset).getCapacity();
											int capacitor_input_rate = ((IStorage) tile_offset).getInputRate();
											int gap_stored = capacitor_capacity - capacitor_stored;
	
											if (capacitor_stored <= capacitor_capacity) {
												if (((IProducer) tile_orig).getGenerationRate() < capacitor_input_rate) {
													if (gap_stored >= ((IProducer) tile_orig).getGenerationRate()) {
														((IProducer) tile_orig).minusStored(((IProducer) tile_orig).getGenerationRate());
														((IStorage) tile_offset).addStored(((IProducer) tile_orig).getGenerationRate());
													} else if (gap_stored < ((IProducer) tile_orig).getGenerationRate()) {
														((IProducer) tile_orig).minusStored(gap_stored);
														((IStorage) tile_offset).addStored(gap_stored);
													}
												} else {
													if (gap_stored >= capacitor_input_rate) {
														((IProducer) tile_orig).minusStored(capacitor_input_rate);
														((IStorage) tile_offset).addStored(capacitor_input_rate);
													} else if (gap_stored < capacitor_input_rate) {
														((IProducer) tile_orig).minusStored(gap_stored);
														((IStorage) tile_offset).addStored(gap_stored);
													}
												}
											}
										}
									}
								}
							}
						} else if (facing.equals(EnumFacing.WEST) || facing.equals(EnumFacing.EAST)) {
							if (c.equals(EnumFacing.WEST) || c.equals(EnumFacing.EAST)) {
	
								TileEntity tile_offset = world.getTileEntity(tile_pos.offset(c));
								
								if (tile_offset != null) {
									if (tile_offset instanceof IEnergyPipe) {
										if (((IEnergyPipe) tile_offset).getSide(c.getOpposite()).equals(EnumSide.INPUT_FROM)) {
											int pipe_stored = ((IEnergyPipe) tile_offset).getStored();
											int pipe_capacity = ((IEnergyPipe) tile_offset).getCapacity();
											int pipe_transfer_rate = ((IEnergyPipe) tile_offset).getTransferRate();
											int pipe_gap_stored = pipe_capacity - pipe_stored;
											
											if (pipe_stored <= pipe_capacity) {
												if (pipe_transfer_rate <= ((IProducer) tile_orig).getGenerationRate()) {
													if (pipe_gap_stored >= pipe_transfer_rate) {
														((IProducer) tile_orig).minusStored(pipe_transfer_rate);
														((IEnergyPipe) tile_offset).addStored(pipe_transfer_rate, c);
													} else if (pipe_gap_stored < pipe_transfer_rate) {
														((IProducer) tile_orig).minusStored(pipe_gap_stored);
														((IEnergyPipe) tile_offset).addStored(pipe_gap_stored, c);
													}
												} else if (pipe_transfer_rate > ((IProducer) tile_orig).getGenerationRate()) {
													if (pipe_gap_stored >= ((IProducer) tile_orig).getGenerationRate()) {
														((IProducer) tile_orig).minusStored(((IProducer) tile_orig).getGenerationRate());
														((IEnergyPipe) tile_offset).addStored(((IProducer) tile_orig).getGenerationRate(), c);
													} else if (pipe_gap_stored < ((IProducer) tile_orig).getGenerationRate()) {
														((IProducer) tile_orig).minusStored(pipe_gap_stored);
														((IEnergyPipe) tile_offset).addStored(pipe_gap_stored, c);
													}
												}
											}
										}
									} else if (tile_offset instanceof IMachine) {
										IBlockState state_offset = world.getBlockState(tile_pos.offset(c));
										BlockMachine block_offset = (BlockMachine) state_offset.getBlock();
										EnumFacing facing_offset = state_offset.getValue(block_offset.FACING);
										
										if (facing_offset.equals(c.getOpposite())) {
											
										} else {
											int machine_stored = ((IMachine) tile_offset).getStored();
											int machine_capacity = ((IMachine) tile_offset).getCapacity();
											int machine_input_rate = ((IMachine) tile_offset).getInputRate();
											int machine_gap_stored = machine_capacity - machine_stored;
		
											if (machine_stored <= machine_capacity) {
												if (machine_gap_stored >= machine_input_rate) {
													((IProducer) tile_orig).minusStored(machine_input_rate);
													((IMachine) tile_offset).addStored(machine_input_rate);
												} else if (machine_gap_stored < machine_input_rate) {
													((IProducer) tile_orig).minusStored(machine_gap_stored);
													((IMachine) tile_offset).addStored(machine_gap_stored);
												}
											}
										}
									} else if (tile_offset instanceof IStorage) {
										EnumSide side_mode = ((IStorage) tile_offset).getSide(c);
										
										if (side_mode.equals(EnumSide.OUTPUT_TO)) {
											int capacitor_stored = ((IStorage) tile_offset).getStored();
											int capacitor_capacity = ((IStorage) tile_offset).getCapacity();
											int capacitor_input_rate = ((IStorage) tile_offset).getInputRate();
											int gap_stored = capacitor_capacity - capacitor_stored;
	
											if (capacitor_stored <= capacitor_capacity) {
												if (((IProducer) tile_orig).getGenerationRate() < capacitor_input_rate) {
													if (gap_stored >= ((IProducer) tile_orig).getGenerationRate()) {
														((IProducer) tile_orig).minusStored(((IProducer) tile_orig).getGenerationRate());
														((IStorage) tile_offset).addStored(((IProducer) tile_orig).getGenerationRate());
													} else if (gap_stored < ((IProducer) tile_orig).getGenerationRate()) {
														((IProducer) tile_orig).minusStored(gap_stored);
														((IStorage) tile_offset).addStored(gap_stored);
													}
												} else {
													if (gap_stored >= capacitor_input_rate) {
														((IProducer) tile_orig).minusStored(capacitor_input_rate);
														((IStorage) tile_offset).addStored(capacitor_input_rate);
													} else if (gap_stored < capacitor_input_rate) {
														((IProducer) tile_orig).minusStored(gap_stored);
														((IStorage) tile_offset).addStored(gap_stored);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		public static class FLUIDPRODUCER {
			
			public static void outputEnergy(World world, BlockPos tile_pos) {
				TileEntity tile_orig = world.getTileEntity(tile_pos);
	
				IBlockState state = world.getBlockState(tile_pos);
				Block block = state.getBlock();
				EnumFacing facing = state.getValue(((BlockFluidProducer) block).FACING);
				
				if (tile_orig != null && tile_orig instanceof IFluidProducer) {
					for (EnumFacing c : EnumFacing.VALUES) {
						if (facing.equals(EnumFacing.NORTH) || facing.equals(EnumFacing.SOUTH)) {
							if (c.equals(EnumFacing.NORTH) || c.equals(EnumFacing.SOUTH)) {
								
								TileEntity tile_offset = world.getTileEntity(tile_pos.offset(c));
								
								if (tile_offset != null) {
									if (tile_offset instanceof IEnergyPipe) {
										if (((IEnergyPipe) tile_offset).getSide(c.getOpposite()).equals(EnumSide.INPUT_FROM)) {
											int pipe_stored = ((IEnergyPipe) tile_offset).getStored();
											int pipe_capacity = ((IEnergyPipe) tile_offset).getCapacity();
											int pipe_transfer_rate = ((IEnergyPipe) tile_offset).getTransferRate();
											int pipe_gap_stored = pipe_capacity - pipe_stored;
											
											if (pipe_stored <= pipe_capacity) {
												if (pipe_transfer_rate <= ((IFluidProducer) tile_orig).getGenerationRate()) {
													if (pipe_gap_stored >= pipe_transfer_rate) {
														((IFluidProducer) tile_orig).minusStored(pipe_transfer_rate);
														((IEnergyPipe) tile_offset).addStored(pipe_transfer_rate, c);
													} else if (pipe_gap_stored < pipe_transfer_rate) {
														((IFluidProducer) tile_orig).minusStored(pipe_gap_stored);
														((IEnergyPipe) tile_offset).addStored(pipe_gap_stored, c);
													}
												} else if (pipe_transfer_rate > ((IFluidProducer) tile_orig).getGenerationRate()) {
													if (pipe_gap_stored >= ((IFluidProducer) tile_orig).getGenerationRate()) {
														((IFluidProducer) tile_orig).minusStored(((IFluidProducer) tile_orig).getGenerationRate());
														((IEnergyPipe) tile_offset).addStored(((IFluidProducer) tile_orig).getGenerationRate(), c);
													} else if (pipe_gap_stored < ((IFluidProducer) tile_orig).getGenerationRate()) {
														((IFluidProducer) tile_orig).minusStored(pipe_gap_stored);
														((IEnergyPipe) tile_offset).addStored(pipe_gap_stored, c);
													}
												}
											}
										}
									} else if (tile_offset instanceof IMachine) {
										IBlockState state_offset = world.getBlockState(tile_pos.offset(c));
										BlockMachine block_offset = (BlockMachine) state_offset.getBlock();
										EnumFacing facing_offset = state_offset.getValue(block_offset.FACING);
										
										if (facing_offset.equals(c.getOpposite())) {
											
										} else {
											int machine_stored = ((IMachine) tile_offset).getStored();
											int machine_capacity = ((IMachine) tile_offset).getCapacity();
											int machine_input_rate = ((IMachine) tile_offset).getInputRate();
											int machine_gap_stored = machine_capacity - machine_stored;
		
											if (machine_stored <= machine_capacity) {
												if (machine_gap_stored >= machine_input_rate) {
													((IFluidProducer) tile_orig).minusStored(machine_input_rate);
													((IMachine) tile_offset).addStored(machine_input_rate);
												} else if (machine_gap_stored < machine_input_rate) {
													((IFluidProducer) tile_orig).minusStored(machine_gap_stored);
													((IMachine) tile_offset).addStored(machine_gap_stored);
												}
											}
										}
									} else if (tile_offset instanceof IFluidMachine) {
										IBlockState state_offset = world.getBlockState(tile_pos.offset(c));
										BlockFluidMachine block_offset = (BlockFluidMachine) state_offset.getBlock();
										EnumFacing facing_offset = state_offset.getValue(block_offset.FACING);
										
										if (facing_offset.equals(c.getOpposite())) {
											
										} else {
											int machine_stored = ((IFluidMachine) tile_offset).getStored();
											int machine_capacity = ((IFluidMachine) tile_offset).getCapacity();
											int machine_input_rate = ((IFluidMachine) tile_offset).getInputRate();
											int machine_gap_stored = machine_capacity - machine_stored;
		
											if (machine_stored <= machine_capacity) {
												if (machine_gap_stored >= machine_input_rate) {
													((IFluidProducer) tile_orig).minusStored(machine_input_rate);
													((IFluidMachine) tile_offset).addStored(machine_input_rate);
												} else if (machine_gap_stored < machine_input_rate) {
													((IFluidProducer) tile_orig).minusStored(machine_gap_stored);
													((IFluidMachine) tile_offset).addStored(machine_gap_stored);
												}
											}
										}
									} else if (tile_offset instanceof IStorage) {
										EnumSide side_mode = ((IStorage) tile_offset).getSide(c);
										
										if (side_mode.equals(EnumSide.OUTPUT_TO)) {
											int capacitor_stored = ((IStorage) tile_offset).getStored();
											int capacitor_capacity = ((IStorage) tile_offset).getCapacity();
											int capacitor_input_rate = ((IStorage) tile_offset).getInputRate();
											int gap_stored = capacitor_capacity - capacitor_stored;
	
											if (capacitor_stored <= capacitor_capacity) {
												if (((IFluidProducer) tile_orig).getGenerationRate() < capacitor_input_rate) {
													if (gap_stored >= ((IFluidProducer) tile_orig).getGenerationRate()) {
														((IFluidProducer) tile_orig).minusStored(((IFluidProducer) tile_orig).getGenerationRate());
														((IStorage) tile_offset).addStored(((IFluidProducer) tile_orig).getGenerationRate());
													} else if (gap_stored < ((IFluidProducer) tile_orig).getGenerationRate()) {
														((IFluidProducer) tile_orig).minusStored(gap_stored);
														((IStorage) tile_offset).addStored(gap_stored);
													}
												} else {
													if (gap_stored >= capacitor_input_rate) {
														((IFluidProducer) tile_orig).minusStored(capacitor_input_rate);
														((IStorage) tile_offset).addStored(capacitor_input_rate);
													} else if (gap_stored < capacitor_input_rate) {
														((IFluidProducer) tile_orig).minusStored(gap_stored);
														((IStorage) tile_offset).addStored(gap_stored);
													}
												}
											}
										}
									}
								}
							}
						} else if (facing.equals(EnumFacing.WEST) || facing.equals(EnumFacing.EAST)) {
							if (c.equals(EnumFacing.WEST) || c.equals(EnumFacing.EAST)) {
	
								TileEntity tile_offset = world.getTileEntity(tile_pos.offset(c));
								
								if (tile_offset != null) {
									if (tile_offset instanceof IEnergyPipe) {
										if (((IEnergyPipe) tile_offset).getSide(c.getOpposite()).equals(EnumSide.INPUT_FROM)) {
											int pipe_stored = ((IEnergyPipe) tile_offset).getStored();
											int pipe_capacity = ((IEnergyPipe) tile_offset).getCapacity();
											int pipe_transfer_rate = ((IEnergyPipe) tile_offset).getTransferRate();
											int pipe_gap_stored = pipe_capacity - pipe_stored;
											
											if (pipe_stored <= pipe_capacity) {
												if (pipe_transfer_rate <= ((IFluidProducer) tile_orig).getGenerationRate()) {
													if (pipe_gap_stored >= pipe_transfer_rate) {
														((IFluidProducer) tile_orig).minusStored(pipe_transfer_rate);
														((IEnergyPipe) tile_offset).addStored(pipe_transfer_rate, c);
													} else if (pipe_gap_stored < pipe_transfer_rate) {
														((IFluidProducer) tile_orig).minusStored(pipe_gap_stored);
														((IEnergyPipe) tile_offset).addStored(pipe_gap_stored, c);
													}
												} else if (pipe_transfer_rate > ((IFluidProducer) tile_orig).getGenerationRate()) {
													if (pipe_gap_stored >= ((IFluidProducer) tile_orig).getGenerationRate()) {
														((IFluidProducer) tile_orig).minusStored(((IFluidProducer) tile_orig).getGenerationRate());
														((IEnergyPipe) tile_offset).addStored(((IFluidProducer) tile_orig).getGenerationRate(), c);
													} else if (pipe_gap_stored < ((IFluidProducer) tile_orig).getGenerationRate()) {
														((IFluidProducer) tile_orig).minusStored(pipe_gap_stored);
														((IEnergyPipe) tile_offset).addStored(pipe_gap_stored, c);
													}
												}
											}
										}
									} else if (tile_offset instanceof IMachine) {
										IBlockState state_offset = world.getBlockState(tile_pos.offset(c));
										BlockMachine block_offset = (BlockMachine) state_offset.getBlock();
										EnumFacing facing_offset = state_offset.getValue(block_offset.FACING);
										
										if (facing_offset.equals(c.getOpposite())) {
											
										} else if (c.equals(EnumFacing.DOWN)) {
											
										} else {
											int machine_stored = ((IMachine) tile_offset).getStored();
											int machine_capacity = ((IMachine) tile_offset).getCapacity();
											int machine_input_rate = ((IMachine) tile_offset).getInputRate();
											int machine_gap_stored = machine_capacity - machine_stored;
		
											if (machine_stored <= machine_capacity) {
												if (machine_gap_stored >= machine_input_rate) {
													((IFluidProducer) tile_orig).minusStored(machine_input_rate);
													((IMachine) tile_offset).addStored(machine_input_rate);
												} else if (machine_gap_stored < machine_input_rate) {
													((IFluidProducer) tile_orig).minusStored(machine_gap_stored);
													((IMachine) tile_offset).addStored(machine_gap_stored);
												}
											}
										}
									} else if (tile_offset instanceof IFluidMachine) {
										IBlockState state_offset = world.getBlockState(tile_pos.offset(c));
										BlockFluidMachine block_offset = (BlockFluidMachine) state_offset.getBlock();
										EnumFacing facing_offset = state_offset.getValue(block_offset.FACING);
										
										if (facing_offset.equals(c.getOpposite())) {
											
										} else if (c.equals(EnumFacing.DOWN)) {
											
										} else {
											int machine_stored = ((IFluidMachine) tile_offset).getStored();
											int machine_capacity = ((IFluidMachine) tile_offset).getCapacity();
											int machine_input_rate = ((IFluidMachine) tile_offset).getInputRate();
											int machine_gap_stored = machine_capacity - machine_stored;
		
											if (machine_stored <= machine_capacity) {
												if (machine_gap_stored >= machine_input_rate) {
													((IFluidProducer) tile_orig).minusStored(machine_input_rate);
													((IFluidMachine) tile_offset).addStored(machine_input_rate);
												} else if (machine_gap_stored < machine_input_rate) {
													((IFluidProducer) tile_orig).minusStored(machine_gap_stored);
													((IFluidMachine) tile_offset).addStored(machine_gap_stored);
												}
											}
										}
									} else if (tile_offset instanceof IStorage) {
										EnumSide side_mode = ((IStorage) tile_offset).getSide(c);
										
										if (side_mode.equals(EnumSide.OUTPUT_TO)) {
											int capacitor_stored = ((IStorage) tile_offset).getStored();
											int capacitor_capacity = ((IStorage) tile_offset).getCapacity();
											int capacitor_input_rate = ((IStorage) tile_offset).getInputRate();
											int gap_stored = capacitor_capacity - capacitor_stored;
	
											if (capacitor_stored <= capacitor_capacity) {
												if (((IFluidProducer) tile_orig).getGenerationRate() < capacitor_input_rate) {
													if (gap_stored >= ((IFluidProducer) tile_orig).getGenerationRate()) {
														((IFluidProducer) tile_orig).minusStored(((IFluidProducer) tile_orig).getGenerationRate());
														((IStorage) tile_offset).addStored(((IFluidProducer) tile_orig).getGenerationRate());
													} else if (gap_stored < ((IFluidProducer) tile_orig).getGenerationRate()) {
														((IFluidProducer) tile_orig).minusStored(gap_stored);
														((IStorage) tile_offset).addStored(gap_stored);
													}
												} else {
													if (gap_stored >= capacitor_input_rate) {
														((IFluidProducer) tile_orig).minusStored(capacitor_input_rate);
														((IStorage) tile_offset).addStored(capacitor_input_rate);
													} else if (gap_stored < capacitor_input_rate) {
														((IFluidProducer) tile_orig).minusStored(gap_stored);
														((IStorage) tile_offset).addStored(gap_stored);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		public static class STORAGE {
			public static void outputEnergy(World world, BlockPos tile_pos) {
				TileEntity tile_orig = world.getTileEntity(tile_pos);				
				
				if (tile_orig instanceof IStorage) {
					for (EnumFacing c : EnumFacing.VALUES) {
						if (((IStorage) tile_orig).getSide(c).equals(EnumSide.OUTPUT_TO)) {
							TileEntity tile_offset = world.getTileEntity(tile_pos.offset(c));
							
							if (tile_offset != null) {
								if (tile_offset instanceof IEnergyPipe) {
									if (((IEnergyPipe)tile_offset).getSide(c.getOpposite()).equals(EnumSide.INPUT_FROM)) {
										int pipe_stored = ((IEnergyPipe) tile_offset).getStored();
										int pipe_capacity = ((IEnergyPipe) tile_offset).getCapacity();
										int pipe_transfer_rate = ((IEnergyPipe) tile_offset).getTransferRate();
										int gap_stored = pipe_capacity - pipe_stored;
										
										if (pipe_stored <= pipe_capacity) {
											if (pipe_transfer_rate <= ((IStorage) tile_orig).getOutputRate()) {
												if (gap_stored >= pipe_transfer_rate) {
													((IStorage) tile_orig).minusStored(pipe_transfer_rate);
													((IEnergyPipe) tile_offset).addStored(pipe_transfer_rate, c);
												} else if (gap_stored < pipe_transfer_rate) {
													((IStorage) tile_orig).minusStored(gap_stored);
													((IEnergyPipe) tile_offset).addStored(gap_stored, c);
												}
											} else if (pipe_transfer_rate > ((IStorage) tile_orig).getOutputRate()) {
												if (gap_stored >= ((IStorage) tile_orig).getOutputRate()) {
													((IStorage) tile_orig).minusStored(((IStorage) tile_orig).getOutputRate());
													((IEnergyPipe) tile_offset).addStored(((IStorage) tile_orig).getOutputRate(), c);
												} else if (gap_stored < ((IStorage) tile_orig).getOutputRate()) {
													((IStorage) tile_orig).minusStored(gap_stored);
													((IEnergyPipe) tile_offset).addStored(gap_stored, c);
												}
											}
										}
									}
								} else if (tile_offset instanceof IMachine) {
									int machine_stored = ((IMachine) tile_offset).getStored();
									int machine_capacity = ((IMachine) tile_offset).getCapacity();
									int machine_input_rate = ((IMachine) tile_offset).getInputRate();
									int gap_stored = machine_capacity - machine_stored;
	
									if (machine_stored <= machine_capacity) {
										if (gap_stored >= machine_input_rate) {
											((IStorage) tile_orig).minusStored(machine_input_rate);
											((IMachine) tile_offset).addStored(machine_input_rate);
										} else if (gap_stored < machine_input_rate) {
											((IStorage) tile_orig).minusStored(gap_stored);
											((IMachine) tile_offset).addStored(gap_stored);
										}
									}
								} else if (tile_offset instanceof IStorage) {
									EnumSide side_mode = ((IStorage) tile_offset).getSide(c);
									if (side_mode.equals(EnumSide.OUTPUT_TO)) {
										int capacitor_stored = ((IStorage) tile_offset).getStored();
										int capacitor_capacity = ((IStorage) tile_offset).getCapacity();
										int capacitor_input_rate = ((IStorage) tile_offset).getInputRate();
										int gap_stored = capacitor_capacity - capacitor_stored;
	
										if (capacitor_stored <= capacitor_capacity) {
											if (((IStorage) tile_orig).getOutputRate() < capacitor_input_rate) {
												if (gap_stored >= ((IStorage) tile_orig).getOutputRate()) {
													((IStorage) tile_orig).minusStored(((IStorage) tile_orig).getOutputRate());
													((IStorage) tile_offset).addStored(((IStorage) tile_orig).getOutputRate());
												} else if (gap_stored < ((IStorage) tile_orig).getOutputRate()) {
													((IStorage) tile_orig).minusStored(gap_stored);
													((IStorage) tile_offset).addStored(gap_stored);
												}
											} else {
												if (gap_stored >= capacitor_input_rate) {
													((IStorage) tile_orig).minusStored(capacitor_input_rate);
													((IStorage) tile_offset).addStored(capacitor_input_rate);
												} else if (gap_stored < capacitor_input_rate) {
													((IStorage) tile_orig).minusStored(gap_stored);
													((IStorage) tile_offset).addStored(gap_stored);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		public static class ENERGYPIPE {
			public static void outputEnergy(World world, BlockPos tile_pos, EnumFacing last_from) {
				TileEntity tile_orig = world.getTileEntity(tile_pos);
				
				if (tile_orig != null && tile_orig instanceof IEnergyPipe) {
					if (last_from != null) {
						for (EnumFacing c : EnumFacing.VALUES) {
							
							if (((IEnergyPipe) tile_orig).getSide(c).equals(EnumSide.OUTPUT_TO)) {
								TileEntity tile_offset = world.getTileEntity(tile_pos.offset(c));
								
								if (tile_offset != null) {
									if (tile_offset instanceof IEnergyPipe) {
										if (!last_from.equals(c.getOpposite())) {
											if (((IEnergyPipe) tile_offset).getSide(c.getOpposite()).equals(EnumSide.OUTPUT_TO)) {
												int pipe_stored = ((IEnergyPipe) tile_offset).getStored();
												int pipe_capacity = ((IEnergyPipe) tile_offset).getCapacity();
												int pipe_transfer_rate = ((IEnergyPipe) tile_offset).getTransferRate();
												int gap_stored = pipe_capacity - pipe_stored;
												
												if (pipe_stored <= pipe_capacity) {
													if (pipe_transfer_rate <= ((IEnergyPipe)tile_orig).getTransferRate()) {
														if (gap_stored >= pipe_transfer_rate) {
															((IEnergyPipe)tile_orig).minusStored(pipe_transfer_rate);
															((IEnergyPipe) tile_offset).addStored(pipe_transfer_rate, c);
														} else if (gap_stored < pipe_transfer_rate) {
															((IEnergyPipe)tile_orig).minusStored(gap_stored);
															((IEnergyPipe) tile_offset).addStored(gap_stored, c);
														}
													} else if (pipe_transfer_rate > ((IEnergyPipe)tile_orig).getTransferRate()) {
														if (gap_stored >= ((IEnergyPipe)tile_orig).getTransferRate()) {
															((IEnergyPipe)tile_orig).minusStored(((IEnergyPipe)tile_orig).getTransferRate());
															((IEnergyPipe) tile_offset).addStored(((IEnergyPipe)tile_orig).getTransferRate(), c);
														} else if (gap_stored < ((IEnergyPipe)tile_orig).getTransferRate()) {
															((IEnergyPipe)tile_orig).minusStored(gap_stored);
															((IEnergyPipe) tile_offset).addStored(gap_stored, c);
														}
													}
												}
											}
										}
									} else if (tile_offset instanceof IMachine) {
										IBlockState state_offset = world.getBlockState(tile_pos.offset(c));
										BlockMachine block_offset = (BlockMachine) state_offset.getBlock();
										EnumFacing facing_offset = state_offset.getValue(block_offset.FACING);
										
										if (facing_offset.equals(c.getOpposite())) {
											
										} else if (c.equals(EnumFacing.DOWN)) {
											
										} else {
											int machine_stored = ((IMachine) tile_offset).getStored();
											int machine_capacity = ((IMachine) tile_offset).getCapacity();
											int machine_input_rate = ((IMachine) tile_offset).getInputRate();
											int machine_gap_stored = machine_capacity - machine_stored;
		
											if (machine_stored <= machine_capacity) {
												if (machine_gap_stored >= machine_input_rate) {
													((IEnergyPipe) tile_orig).minusStored(machine_input_rate);
													((IMachine) tile_offset).addStored(machine_input_rate);
												} else if (machine_gap_stored < machine_input_rate) {
													((IEnergyPipe) tile_orig).minusStored(machine_gap_stored);
													((IMachine) tile_offset).addStored(machine_gap_stored);
												}
											}
										}
									} else if (tile_offset instanceof IFluidMachine) {
										IBlockState state_offset = world.getBlockState(tile_pos.offset(c));
										BlockFluidMachine block_offset = (BlockFluidMachine) state_offset.getBlock();
										EnumFacing facing_offset = state_offset.getValue(block_offset.FACING);
										
										if (facing_offset.equals(c.getOpposite())) {
											
										} else if (c.equals(EnumFacing.DOWN)) {
											
										} else {
											int machine_stored = ((IFluidMachine) tile_offset).getStored();
											int machine_capacity = ((IFluidMachine) tile_offset).getCapacity();
											int machine_input_rate = ((IFluidMachine) tile_offset).getInputRate();
											int machine_gap_stored = machine_capacity - machine_stored;
		
											if (machine_stored <= machine_capacity) {
												if (machine_gap_stored >= machine_input_rate) {
													((IEnergyPipe) tile_orig).minusStored(machine_input_rate);
													((IFluidMachine) tile_offset).addStored(machine_input_rate);
												} else if (machine_gap_stored < machine_input_rate) {
													((IEnergyPipe) tile_orig).minusStored(machine_gap_stored);
													((IFluidMachine) tile_offset).addStored(machine_gap_stored);
												}
											}
										}
									} else if (tile_offset instanceof IStorage) {
										EnumSide side_mode = ((IStorage) tile_offset).getSide(c);
										if (side_mode.equals(EnumSide.OUTPUT_TO)) {
											int capacitor_stored = ((IStorage) tile_offset).getStored();
											int capacitor_capacity = ((IStorage) tile_offset).getCapacity();
											int capacitor_input_rate = ((IStorage) tile_offset).getInputRate();
											int gap_stored = capacitor_capacity - capacitor_stored;
											
											if (capacitor_stored < capacitor_capacity) {
												
												if (((IEnergyPipe)tile_orig).getTransferRate() < capacitor_input_rate) {
													if (gap_stored >= ((IEnergyPipe)tile_orig).getTransferRate()) {
														((IEnergyPipe)tile_orig).minusStored(((IEnergyPipe)tile_orig).getTransferRate());
														((IStorage) tile_offset).addStored(((IEnergyPipe)tile_orig).getTransferRate());
													} else if (gap_stored < ((IEnergyPipe)tile_orig).getTransferRate()) {
														((IEnergyPipe)tile_orig).minusStored(gap_stored);
														((IStorage) tile_offset).addStored(gap_stored);
													}
												} else {
													if (gap_stored >= capacitor_input_rate) {
														((IEnergyPipe)tile_orig).minusStored(capacitor_input_rate);
														((IStorage) tile_offset).addStored(capacitor_input_rate);
													} else if (gap_stored < capacitor_input_rate) {
														((IEnergyPipe)tile_orig).minusStored(gap_stored);
														((IStorage) tile_offset).addStored(gap_stored);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
