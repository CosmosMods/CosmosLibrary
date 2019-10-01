package com.zeher.zeherlib.api.connection;

import com.zeher.zeherlib.machine.BlockFluidMachine;
import com.zeher.zeherlib.machine.BlockMachine;
import com.zeher.zeherlib.machine.IFluidMachine;
import com.zeher.zeherlib.machine.IMachine;
import com.zeher.zeherlib.production.BlockFluidProducer;
import com.zeher.zeherlib.production.BlockProducer;
import com.zeher.zeherlib.production.IFluidProducer;
import com.zeher.zeherlib.production.IProducer;
import com.zeher.zeherlib.storage.ModBlockFluidTank;
import com.zeher.zeherlib.storage.BlockItemStorage;
import com.zeher.zeherlib.storage.BlockStorage;
import com.zeher.zeherlib.storage.IModFluidTank;
import com.zeher.zeherlib.storage.IStorage;
import com.zeher.zeherlib.transfer.BlockEnergyPipe;
import com.zeher.zeherlib.transfer.BlockFluidPipe;
import com.zeher.zeherlib.transfer.BlockItemPipe;
import com.zeher.zeherlib.transfer.IEnergyPipe;
import com.zeher.zeherlib.transfer.IFluidPipe;
import com.zeher.zeherlib.transfer.IItemPipe;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

public class Connection {

	public static class PIPE {
		public static class BLOCK {
	
			public static boolean getEnergyConnectionsAccess(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
				IBlockState blockId = worldIn.getBlockState(pos);
				
				if (blockId.getBlock() instanceof BlockMachine || blockId.getBlock() instanceof BlockEnergyPipe || blockId.getBlock() instanceof BlockFluidMachine || blockId.getBlock() instanceof BlockStorage) {
					return true;
				} else if (blockId.getBlock() instanceof BlockProducer) {
					BlockProducer block_ = (BlockProducer) blockId.getBlock();
					EnumFacing facing = blockId.getValue(block_.FACING);
					
					if(facing.equals(EnumFacing.NORTH) || facing.equals(EnumFacing.SOUTH)) {
						if (side.equals(EnumFacing.EAST) || side.equals(EnumFacing.WEST)) {
							return false;
						} else if (side.equals(EnumFacing.UP) || side.equals(EnumFacing.DOWN)) {
							return false;
						} else {
							return true;
						}
					} else if(facing.equals(EnumFacing.EAST) || facing.equals(EnumFacing.WEST)) {
						if (side.equals(EnumFacing.NORTH) || side.equals(EnumFacing.SOUTH)) {
							return false;
						} else if (side.equals(EnumFacing.UP) || side.equals(EnumFacing.DOWN)) {
							return false;
						} else {
							return true;
						}
					}
				} else if (blockId.getBlock() instanceof BlockFluidProducer) {
					BlockFluidProducer block_ = (BlockFluidProducer) blockId.getBlock();
					EnumFacing facing = blockId.getValue(block_.FACING);
					
					if(facing.equals(EnumFacing.NORTH) || facing.equals(EnumFacing.SOUTH)) {
						if (side.equals(EnumFacing.EAST) || side.equals(EnumFacing.WEST)) {
							return false;
						} else if (side.equals(EnumFacing.UP) || side.equals(EnumFacing.DOWN)) {
							return false;
						} else {
							return true;
						}
					} else if(facing.equals(EnumFacing.EAST) || facing.equals(EnumFacing.WEST)) {
						if (side.equals(EnumFacing.NORTH) || side.equals(EnumFacing.SOUTH)) {
							return false;
						} else if (side.equals(EnumFacing.UP) || side.equals(EnumFacing.DOWN)) {
							return false;
						} else {
							return true;
						}
					}
				}
				return false;
			}
	
			public static boolean canEnergyConnectTo(TileEntity tile_offset, EnumFacing facing, IBlockState state) {
				if (tile_offset != null) {
					if (tile_offset instanceof IEnergyPipe) {
						if (((IEnergyPipe) tile_offset).getSide(facing.getOpposite()).equals(EnumSide.NONE)) {
							return false;
						} else {
							return true;
						}
					} else if (tile_offset instanceof IStorage) {
						if (((IStorage) tile_offset).getSide(facing.getOpposite()).equals(EnumSide.OUTPUT_TO) || ((IStorage) tile_offset).getSide(facing.getOpposite()).equals(EnumSide.INPUT_FROM)) {
							return false;
						} else {
							return true;
						}
					} else if (tile_offset instanceof IProducer) {
						BlockProducer block_ = (BlockProducer) state.getBlock();
						EnumFacing facing_ = state.getValue(block_.FACING);
						
						if(facing_.equals(EnumFacing.NORTH) || facing_.equals(EnumFacing.SOUTH)) {
							if (facing.equals(EnumFacing.EAST) || facing.equals(EnumFacing.WEST)) {
								return false;
							} else if (facing.equals(EnumFacing.UP) || facing.equals(EnumFacing.DOWN)) {
								return false;
							} else {
								return true;
							}
						} else if(facing_.equals(EnumFacing.EAST) || facing_.equals(EnumFacing.WEST)) {
							if (facing.equals(EnumFacing.NORTH) || facing.equals(EnumFacing.SOUTH)) {
								return false;
							} else if (facing.equals(EnumFacing.UP) || facing.equals(EnumFacing.DOWN)) {
								return false;
							} else {
								return true;
							}
						}
					} else if (tile_offset instanceof IFluidProducer) {
							
						BlockFluidProducer block_ = (BlockFluidProducer) state.getBlock();
						EnumFacing facing_ = state.getValue(block_.FACING);
						
						if(facing_.equals(EnumFacing.NORTH) || facing_.equals(EnumFacing.SOUTH)) {
							if (facing.equals(EnumFacing.EAST) || facing.equals(EnumFacing.WEST)) {
								return false;
							} else if (facing.equals(EnumFacing.UP) || facing.equals(EnumFacing.DOWN)) {
								return false;
							} else {
								return true;
							}
						} else if(facing_.equals(EnumFacing.EAST) || facing_.equals(EnumFacing.WEST)) {
							if (facing.equals(EnumFacing.NORTH) || facing.equals(EnumFacing.SOUTH)) {
								return false;
							} else if (facing.equals(EnumFacing.UP) || facing.equals(EnumFacing.DOWN)) {
								return false;
							} else {
								return true;
							}
						}
					} else if (tile_offset instanceof IMachine) {
						BlockMachine block_ = (BlockMachine) state.getBlock();
						EnumFacing facing_ = state.getValue(block_.FACING);
						
						if (facing_.equals(facing.getOpposite())) {
							return false;
						} else if (facing.equals(EnumFacing.DOWN)) {
							return false;
						} else {
							return true;
						}
					} else if (tile_offset instanceof IFluidMachine) {
						BlockFluidMachine block_ = (BlockFluidMachine) state.getBlock();
						EnumFacing facing_ = state.getValue(block_.FACING);
						
						if (facing_.equals(facing.getOpposite())) {
							return false;
						} else if (facing.equals(EnumFacing.DOWN)) {
							return false;
						} else {
							return true;
						}
					}
				}
				return false;
			}
	
			public static boolean getItemConnectionsState(EnumFacing side, IBlockState blockId, World world, BlockPos pos) {
				if (blockId.getBlock() instanceof BlockItemPipe 
						|| blockId.getBlock() instanceof BlockMachine
						|| blockId.getBlock() instanceof BlockProducer
						|| blockId.getBlock() instanceof BlockItemStorage) {
					return true;
				}
				return false;
			}
	
			public static boolean getItemConnectionsAccess(IBlockAccess par1IBlockAccess, BlockPos pos, EnumFacing side) {
				IBlockState blockId = par1IBlockAccess.getBlockState(pos);
				if (blockId.getBlock() instanceof BlockItemPipe 
						|| blockId.getBlock() instanceof BlockMachine
						|| blockId.getBlock() instanceof BlockProducer
						|| blockId.getBlock() instanceof BlockItemStorage) {
					return true;
				}
				return false;
			}
	
			public static boolean getFluidConnectionsState(EnumFacing side, IBlockState blockId, World world, BlockPos pos) {
				if (blockId.getBlock() instanceof BlockFluidPipe || blockId.getBlock() instanceof ModBlockFluidTank
						|| blockId.getBlock() instanceof BlockFluidMachine
						|| blockId.getBlock() instanceof BlockFluidProducer) {
					return true;
				}
				return false;
			}
	
			public static boolean getFluidConnectionsAccess(IBlockAccess par1IBlockAccess, BlockPos pos, EnumFacing side) {
				IBlockState blockId = par1IBlockAccess.getBlockState(pos);
				if (blockId.getBlock() instanceof BlockFluidPipe 
						|| blockId.getBlock() instanceof ModBlockFluidTank
						|| blockId.getBlock() instanceof BlockFluidMachine
						|| blockId.getBlock() instanceof BlockFluidProducer) {
					return true;
				}
				return false;
			}
		}
		
		public static class TILEENTITY {
			public static ConnectionType.PIPE.ENERGY getEnergyConnections(EnumFacing facing, TileEntity tile_offset, IBlockState state_offset, EnumSide side) {
				Block block = state_offset.getBlock();
				
				if (block != null) {
					if (tile_offset != null) {
						if (tile_offset instanceof IEnergyPipe && block instanceof BlockEnergyPipe) {
							if (side.equals(EnumSide.OUTPUT_TO)) {
								if (((IEnergyPipe) tile_offset).getSide(facing.getOpposite()).equals(EnumSide.OUTPUT_TO)) {
									return ConnectionType.PIPE.ENERGY.ALL;
								} else {
									return ConnectionType.PIPE.ENERGY.NONE;
								}
							}
						}
						
						if (tile_offset instanceof IStorage && block instanceof BlockStorage) {
							if (((IStorage) tile_offset).getSide(facing.getOpposite()).equals(EnumSide.OUTPUT_TO) || ((IStorage) tile_offset).getSide(facing.getOpposite()).equals(EnumSide.INPUT_FROM)) {
								if (side.equals(EnumSide.INPUT_FROM)) {
									return ConnectionType.PIPE.ENERGY.LONG_OUTPUT;
								} else if (side.equals(EnumSide.OUTPUT_TO)) {
									return ConnectionType.PIPE.ENERGY.LONG_INPUT;
								} else {
									return ConnectionType.PIPE.ENERGY.NONE;
								}
							}
						}
						
						if (tile_offset instanceof IMachine && block instanceof BlockMachine) {
							BlockMachine block_ = (BlockMachine) state_offset.getBlock();
							EnumFacing facing_ = state_offset.getValue(block_.FACING);
							
							if (facing_.equals(facing.getOpposite())) {
								return ConnectionType.PIPE.ENERGY.NONE;
							} else if (facing.equals(EnumFacing.DOWN)) {
								return ConnectionType.PIPE.ENERGY.NONE;
							} else if (side.equals(EnumSide.OUTPUT_TO)) {
								return ConnectionType.PIPE.ENERGY.INPUT;
							} else if (side.equals(EnumSide.INPUT_FROM)) {
								return ConnectionType.PIPE.ENERGY.OUTPUT;
							} else {
								return ConnectionType.PIPE.ENERGY.NONE;
							}
						}
						
						if (tile_offset instanceof IFluidMachine &&block instanceof BlockFluidMachine) {
							BlockFluidMachine block_ = (BlockFluidMachine) state_offset.getBlock();
							EnumFacing facing_ = state_offset.getValue(block_.FACING);
							
							if (facing_.equals(facing.getOpposite())) {
								return ConnectionType.PIPE.ENERGY.NONE;
							} else if (facing.equals(EnumFacing.DOWN)) {
								return ConnectionType.PIPE.ENERGY.NONE;
							} else if (side.equals(EnumSide.OUTPUT_TO)) {
								return ConnectionType.PIPE.ENERGY.INPUT;
							} else if (side.equals(EnumSide.INPUT_FROM)) {
								return ConnectionType.PIPE.ENERGY.OUTPUT;
							} else {
								return ConnectionType.PIPE.ENERGY.NONE;
							}
						}
						
						if (tile_offset instanceof IProducer && block instanceof BlockProducer) {
							BlockProducer block_ = (BlockProducer) state_offset.getBlock();
							EnumFacing facing_ = state_offset.getValue(block_.FACING);
							
							if(facing_.equals(EnumFacing.NORTH) || facing_.equals(EnumFacing.SOUTH)) {
								if (facing.equals(EnumFacing.EAST) || facing.equals(EnumFacing.WEST)) {
									return ConnectionType.PIPE.ENERGY.NONE;
								} else if (facing.equals(EnumFacing.UP) || facing.equals(EnumFacing.DOWN)) {
									return ConnectionType.PIPE.ENERGY.NONE;
								} else if (facing.equals(EnumFacing.DOWN)) {
									return ConnectionType.PIPE.ENERGY.NONE;
								} else if (side.equals(EnumSide.OUTPUT_TO)) {
									return ConnectionType.PIPE.ENERGY.INPUT;
								} else if (side.equals(EnumSide.INPUT_FROM)) {
									return ConnectionType.PIPE.ENERGY.OUTPUT;
								} else {
									return ConnectionType.PIPE.ENERGY.NONE;
								}
							} else if (facing_.equals(EnumFacing.EAST) || facing_.equals(EnumFacing.WEST)) {
								if (facing.equals(EnumFacing.NORTH) || facing.equals(EnumFacing.SOUTH)) {
									return ConnectionType.PIPE.ENERGY.NONE;
								} else if (facing.equals(EnumFacing.UP) || facing.equals(EnumFacing.DOWN)) {
									return ConnectionType.PIPE.ENERGY.NONE;
								} else if (facing.equals(EnumFacing.DOWN)) {
									return ConnectionType.PIPE.ENERGY.NONE;
								} else if (side.equals(EnumSide.OUTPUT_TO)) {
									return ConnectionType.PIPE.ENERGY.INPUT;
								} else if (side.equals(EnumSide.INPUT_FROM)) {
									return ConnectionType.PIPE.ENERGY.OUTPUT;
								} else {
									return ConnectionType.PIPE.ENERGY.NONE;
								}
							}
						}
						
						if (tile_offset instanceof IFluidProducer && block instanceof BlockFluidProducer) {
							BlockFluidProducer block_ = (BlockFluidProducer) state_offset.getBlock();
							EnumFacing facing_ = state_offset.getValue(block_.FACING);
							
							if(facing_.equals(EnumFacing.NORTH) || facing_.equals(EnumFacing.SOUTH)) {
								if (facing.equals(EnumFacing.EAST) || facing.equals(EnumFacing.WEST)) {
									return ConnectionType.PIPE.ENERGY.NONE;
								} else if (facing.equals(EnumFacing.UP) || facing.equals(EnumFacing.DOWN)) {
									return ConnectionType.PIPE.ENERGY.NONE;
								} else if (facing.equals(EnumFacing.UP) || facing.equals(EnumFacing.DOWN)) {
									return ConnectionType.PIPE.ENERGY.NONE;
								} else if (facing.equals(EnumFacing.DOWN)) {
									return ConnectionType.PIPE.ENERGY.NONE;
								} else if (side.equals(EnumSide.OUTPUT_TO)) {
									return ConnectionType.PIPE.ENERGY.INPUT;
								} else if (side.equals(EnumSide.INPUT_FROM)) {
									return ConnectionType.PIPE.ENERGY.OUTPUT;
								} else {
									return ConnectionType.PIPE.ENERGY.NONE;
								}
							} else if (facing_.equals(EnumFacing.EAST) || facing_.equals(EnumFacing.WEST)) {
								if (facing.equals(EnumFacing.NORTH) || facing.equals(EnumFacing.SOUTH)) {
									return ConnectionType.PIPE.ENERGY.NONE;
								} else if (facing.equals(EnumFacing.UP) || facing.equals(EnumFacing.DOWN)) {
									return ConnectionType.PIPE.ENERGY.NONE;
								} else if (facing.equals(EnumFacing.UP) || facing.equals(EnumFacing.DOWN)) {
									return ConnectionType.PIPE.ENERGY.NONE;
								} else if (facing.equals(EnumFacing.DOWN)) {
									return ConnectionType.PIPE.ENERGY.NONE;
								} else if (side.equals(EnumSide.OUTPUT_TO)) {
									return ConnectionType.PIPE.ENERGY.INPUT;
								} else if (side.equals(EnumSide.INPUT_FROM)) {
									return ConnectionType.PIPE.ENERGY.OUTPUT;
								} else {
									return ConnectionType.PIPE.ENERGY.NONE;
								}
							}
						}
					} else if (side.equals(EnumSide.INPUT_FROM)) {
						return ConnectionType.PIPE.ENERGY.OUTPUT;
					} else if (side.equals(EnumSide.OUTPUT_TO)) {
						return ConnectionType.PIPE.ENERGY.NONE;
					}
				} else if (side.equals(EnumSide.INPUT_FROM)) {
					return ConnectionType.PIPE.ENERGY.OUTPUT;
				} else if (side.equals(EnumSide.OUTPUT_TO)) {
					return ConnectionType.PIPE.ENERGY.NONE;
				}
				return ConnectionType.PIPE.ENERGY.NONE;
			}
			
			public static ConnectionType.PIPE.FLUID getFluidConnections(TileEntity tile, EnumFacing facing, TileEntity tile_offset, IBlockState state_offset, int side, FluidTank tank) {
				
				if (state_offset == null) {
					return ConnectionType.PIPE.FLUID.NONE;
				} else if (state_offset.getBlock() instanceof BlockFluidPipe) {
					if (tile_offset != null && tile_offset instanceof IFluidPipe) {
						if (side == 0 || side == 2) {
							if (((IFluidPipe) tile).getTank().getFluid() != null) {
								if (((IFluidPipe) tile_offset).getTank().getFluid() != null) {
									if (((IFluidPipe) tile_offset).getTank().getFluid().getFluid().getName().equals(((IFluidPipe) tile).getTank().getFluid().getFluid().getName())) {
										return ConnectionType.PIPE.FLUID.ALL;
									} else {
										return ConnectionType.PIPE.FLUID.NONE;
									}
								} else {
									return ConnectionType.PIPE.FLUID.NONE;
								}
							} else if (((IFluidPipe) tile_offset).getTank().getFluid() != null) {
								return ConnectionType.PIPE.FLUID.NONE;
							} else if (((IFluidPipe) tile_offset).getSide(facing.getOpposite()) == 0 || ((IFluidPipe) tile_offset).getSide(facing.getOpposite()) == 2) {
								return ConnectionType.PIPE.FLUID.ALL;
							} else {
								return ConnectionType.PIPE.FLUID.NONE;
							}
						}
					}
				} else if (state_offset.getBlock() instanceof ModBlockFluidTank) { 
					if (tile_offset != null && tile_offset instanceof IModFluidTank) {
						if (side == 0 || side == 2) {
							if (((IFluidPipe) tile).getTank().getFluid() != null) {
								if (((IModFluidTank) tile_offset).getTank().getFluid() != null) {
									if (((IModFluidTank) tile_offset).getTank().getFluid().getFluid().getName().equals(((IFluidPipe) tile).getTank().getFluid().getFluid().getName())) {
										return ConnectionType.PIPE.FLUID.ALL;
									} else {
										return ConnectionType.PIPE.FLUID.NONE;
									}
								} else {
									return ConnectionType.PIPE.FLUID.NONE;
								}
							} else if (((IModFluidTank) tile_offset).getTank().getFluid() != null) {
								return ConnectionType.PIPE.FLUID.NONE;
							} else if (((IModFluidTank) tile_offset).getSide(facing.getOpposite()) == 0 || ((IModFluidTank) tile_offset).getSide(facing.getOpposite()) == 2) {
								return ConnectionType.PIPE.FLUID.ALL;
							} else {
								return ConnectionType.PIPE.FLUID.NONE;
							}
						}
					}
				
				} else if (side == 1) {
					return ConnectionType.PIPE.FLUID.NONE;
				} else if (side == 2) {
					return ConnectionType.PIPE.FLUID.ALL;
				} else if (Connection.PIPE.BLOCK.getFluidConnectionsState(facing, state_offset, tile.getWorld(), tile.getPos())) {
					return ConnectionType.PIPE.FLUID.LONG;
				}
				
				return ConnectionType.PIPE.FLUID.NONE;
				/**
				else if (side == 2 || side == 0) {
					if (((TRZIFluidPipe) tile_offset).getSide(facing.getOpposite()) == 1) {
						return TRZPipeConnectionType.PIPE.FLUID.NONE;
					}
					
					if(tank.getFluidAmount() == 0 && ((TRZIFluidPipe)tile_offset).getTank().getFluid() != null){
						return TRZPipeConnectionType.PIPE.FLUID.ALL;
					}
					
					if (tank.getFluid() != null) {
						if (((TRZIFluidPipe) tile_offset).getTank().getFluid() != null) {
							if (((TRZIFluidPipe) tile_offset).getTank().getFluid().getFluid().getName().equals(tank.getFluid().getFluid().getName())) {
								return TRZPipeConnectionType.PIPE.FLUID.ALL;
							} else {
								return TRZPipeConnectionType.PIPE.FLUID.NONE;
							}
						} else {
							return TRZPipeConnectionType.PIPE.FLUID.NONE;
						}
					} else {
						return TRZPipeConnectionType.PIPE.FLUID.ALL;
					}
				}
				*/
			}
			
			public static ConnectionType.PIPE.ITEM getItemConnections(TileEntity tile, EnumFacing facing, TileEntity tile_offset, IBlockState state_offset, int side) {
				
				if (state_offset == null) {
					return ConnectionType.PIPE.ITEM.NONE;
				} else if (state_offset.getBlock() instanceof BlockItemPipe) {
					if (tile_offset != null && tile_offset instanceof IItemPipe) {
						if (((IItemPipe) tile_offset).getSide(facing.getOpposite()) == 0 || ((IItemPipe) tile_offset).getSide(facing.getOpposite()) == 2) {
							if (side == 2 || side == 0) {
								return ConnectionType.PIPE.ITEM.ALL;
							} else {
								return ConnectionType.PIPE.ITEM.NONE;
							}
						}
					}
				} else if (side == 1) {
					return ConnectionType.PIPE.ITEM.NONE;
				} else if (side == 2) {
					return ConnectionType.PIPE.ITEM.LONG;
				} else if (Connection.PIPE.BLOCK.getItemConnectionsState(facing, state_offset, tile.getWorld(), tile.getPos())) {
					return ConnectionType.PIPE.ITEM.LONG;
				}
				return ConnectionType.PIPE.ITEM.NONE;
			}
		}
	}
}
