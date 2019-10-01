package com.zeher.zeherlib.api.transfer;

import com.zeher.zeherlib.api.util.ModUtil;
import com.zeher.zeherlib.storage.IModFluidTank;
import com.zeher.zeherlib.transfer.IFluidPipe;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidTransfer {

	public static class OUTPUT {

		public static class TANK {

			public static void outputFluid(World world, BlockPos tile_pos) {
				TileEntity tile_orig = world.getTileEntity(tile_pos);

				IBlockState state = world.getBlockState(tile_pos);
				Block block = state.getBlock();

				if (tile_orig != null && tile_orig instanceof IModFluidTank) {
					for (EnumFacing c : EnumFacing.VALUES) {
						TileEntity tile_offset = world.getTileEntity(tile_pos.offset(c));
						FluidTank tank_orig = ((IModFluidTank) tile_orig).getTank();

						if (((IModFluidTank) tile_orig).getSide(c) == 2) {
							if (!((IModFluidTank) tile_orig).isFluidEmpty()) {
								if (tile_offset != null) {
									if (tile_offset instanceof IModFluidTank) {
										if (((IModFluidTank) tile_offset).getSide(c.getOpposite()) == 0) {
											if (((IModFluidTank) tile_offset).isFluidEmpty()) {
												FluidTank tank_offset = ((IModFluidTank) tile_offset).getTank();

												int fluid_amount = tank_offset.getFluidAmount();
												int fluid_capacity = tank_offset.getCapacity();
												int gap = fluid_capacity - fluid_amount;

												int fluid_rate = ((IModFluidTank) tile_offset).getOutputRate();

												if (fluid_amount < fluid_capacity) {
													if (gap >= fluid_rate) {
														FluidStack fluid_stack = new FluidStack(
																tank_orig.getFluid().getFluid(), fluid_rate);
														tank_offset.fill(fluid_stack, true);

														tank_orig.drain(fluid_rate, true);

													}
												}

											} else {
												Fluid fluid_offset = ((IModFluidTank) tile_offset).getTank().getFluid().getFluid();

												if (tank_orig.getFluid().getFluid().equals(fluid_offset)) {
													FluidTank tank_offset = ((IModFluidTank) tile_offset).getTank();

													int fluid_amount = tank_offset.getFluidAmount();
													int fluid_capacity = tank_offset.getCapacity();
													int gap = fluid_capacity - fluid_amount;

													int fluid_rate = ((IModFluidTank) tile_offset).getOutputRate();

													if (fluid_amount < fluid_capacity) {
														if (gap >= fluid_rate) {
															FluidStack fluid_stack = new FluidStack(tank_offset.getFluid().getFluid(), fluid_rate);
															tank_offset.fill(fluid_stack, true);

															tank_orig.drain(fluid_rate, true);

														}
													}
												}
											}
										}
									}

									if (tile_offset instanceof IFluidPipe) {
										if (((IFluidPipe) tile_offset).getLastFrom() != null) {
											if (((IFluidPipe) tile_offset).getSide(c.getOpposite()) == 0) {
												if (((IFluidPipe) tile_offset).isFluidEmpty()) {
													FluidTank tank_offset = ((IFluidPipe) tile_offset).getTank();

													int fluid_amount = tank_offset.getFluidAmount();
													int fluid_capacity = tank_offset.getCapacity();
													int gap = fluid_capacity - fluid_amount;

													int fluid_rate = ((IFluidPipe) tile_offset).getOutputRate();

													if (fluid_amount < fluid_capacity) {
														if (gap >= fluid_rate) {
															FluidStack fluid_stack = new FluidStack(tank_orig.getFluid().getFluid(), fluid_rate);
															((IFluidPipe) tile_offset).fill(fluid_stack, true, c);

															tank_orig.drain(fluid_rate, true);
														}
													}

												} else {
													Fluid fluid_offset = ((IFluidPipe) tile_offset).getTank()
															.getFluid().getFluid();

													if (tank_orig.getFluid().getFluid().equals(fluid_offset)) {
														FluidTank tank_offset = ((IFluidPipe) tile_offset).getTank();

														int fluid_amount = tank_offset.getFluidAmount();
														int fluid_capacity = tank_offset.getCapacity();
														int gap = fluid_capacity - fluid_amount;

														int fluid_rate = ((IFluidPipe) tile_offset).getOutputRate();

														if (fluid_amount < fluid_capacity) {
															if (gap >= fluid_rate) {
																FluidStack fluid_stack = new FluidStack(
																		tank_offset.getFluid().getFluid(), fluid_rate);
																((IFluidPipe) tile_offset).fill(fluid_stack, true,
																		c);

																tank_orig.drain(fluid_rate, true);
															}
														}
													}
												}
											}
										} else {
											if (((IFluidPipe) tile_offset).getSide(c.getOpposite()) == 0) {
												if (((IFluidPipe) tile_offset).isFluidEmpty()) {
													FluidTank tank_offset = ((IFluidPipe) tile_offset).getTank();

													int fluid_amount = tank_offset.getFluidAmount();
													int fluid_capacity = tank_offset.getCapacity();
													int gap = fluid_capacity - fluid_amount;

													int fluid_rate = ((IFluidPipe) tile_offset).getOutputRate();

													if (fluid_amount < fluid_capacity) {
														if (gap >= fluid_rate) {
															FluidStack fluid_stack = new FluidStack(
																	tank_orig.getFluid().getFluid(), fluid_rate);
															((IFluidPipe) tile_offset).fill(fluid_stack, true, c);

															tank_orig.drain(fluid_rate, true);
														}
													}

												} else {
													Fluid fluid_offset = ((IFluidPipe) tile_offset).getTank().getFluid().getFluid();

													if (tank_orig.getFluid().getFluid().equals(fluid_offset)) {
														FluidTank tank_offset = ((IFluidPipe) tile_offset).getTank();

														int fluid_amount = tank_offset.getFluidAmount();
														int fluid_capacity = tank_offset.getCapacity();
														int gap = fluid_capacity - fluid_amount;

														int fluid_rate = ((IFluidPipe) tile_offset).getOutputRate();

														if (fluid_amount < fluid_capacity) {
															if (gap >= fluid_rate) {
																FluidStack fluid_stack = new FluidStack(
																		tank_offset.getFluid().getFluid(), fluid_rate);
																((IFluidPipe) tile_offset).fill(fluid_stack, true, c);

																tank_orig.drain(fluid_rate, true);
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
				ModUtil.syncBlockAndRerender(world, tile_pos);
			}
		}

		public static class FLUIDPIPE {

			public static void outputFluid(World world, BlockPos tile_pos, EnumFacing last_from) {
				TileEntity tile_orig = world.getTileEntity(tile_pos);

				IBlockState state = world.getBlockState(tile_pos);
				Block block = state.getBlock();

				if (tile_orig != null && tile_orig instanceof IFluidPipe) {
					for (EnumFacing c : EnumFacing.VALUES) {
						TileEntity tile_offset = world.getTileEntity(tile_pos.offset(c));
						FluidTank tank_orig = ((IFluidPipe) tile_orig).getTank();

						int fluid_rate = ((IFluidPipe) tile_orig).getOutputRate();

						if (!((IFluidPipe) tile_orig).isFluidEmpty()) {
							if (tile_offset != null) {
								if (tile_offset instanceof IModFluidTank) {
									if (((IModFluidTank) tile_offset).getSide(c.getOpposite()) == 0) {
										if (((IModFluidTank) tile_offset).isFluidEmpty()) {
											FluidTank tank_offset = ((IModFluidTank) tile_offset).getTank();

											int fluid_amount = tank_offset.getFluidAmount();
											int fluid_capacity = tank_offset.getCapacity();
											int gap = fluid_capacity - fluid_amount;

											if (fluid_amount < fluid_capacity) {
												if (gap >= fluid_rate) {
													FluidStack fluid_stack = new FluidStack(tank_orig.getFluid().getFluid(), fluid_rate);
													tank_offset.fill(fluid_stack, true);

													tank_orig.drain(fluid_rate, true);
												}
											}

										} else {
											Fluid fluid_offset = ((IModFluidTank) tile_offset).getTank().getFluid().getFluid();

											if (tank_orig.getFluid().getFluid().equals(fluid_offset)) {
												FluidTank tank_offset = ((IModFluidTank) tile_offset).getTank();

												int fluid_amount = tank_offset.getFluidAmount();
												int fluid_capacity = tank_offset.getCapacity();
												int gap = fluid_capacity - fluid_amount;

												if (fluid_amount < fluid_capacity) {
													if (gap >= fluid_rate) {
														if (((IFluidPipe) tile_orig).getTank().getFluidAmount() == ((IFluidPipe) tile_orig).getTank().getCapacity() / 2 && ((IFluidPipe) tile_orig).getTank().getFluidAmount() > 0) {
															
														} else {
															FluidStack fluid_stack = new FluidStack(tank_offset.getFluid().getFluid(), fluid_rate);
															tank_offset.fill(fluid_stack, true);
	
															tank_orig.drain(fluid_rate, true);
														}
													}
												}
											}
										}
									}
								}

								if (tile_offset instanceof IFluidPipe) {
									if (!last_from.equals(c.getOpposite())) {
										if (((IFluidPipe) tile_offset).getSide(c.getOpposite()) == 0 || ((IFluidPipe) tile_offset).getSide(c.getOpposite()) == 2) {
											if (((IFluidPipe) tile_offset).isFluidEmpty()) {
												FluidTank tank_offset = ((IFluidPipe) tile_offset).getTank();

												int fluid_amount = tank_offset.getFluidAmount();
												int fluid_capacity = tank_offset.getCapacity();
												int gap = fluid_capacity - fluid_amount;

												if (fluid_amount < fluid_capacity) {
													if (gap >= fluid_rate) {
														FluidStack fluid_stack = new FluidStack(tank_orig.getFluid().getFluid(), fluid_rate);
														((IFluidPipe) tile_offset).fill(fluid_stack, true, c);

														tank_orig.drain(fluid_rate, true);
													}
												}

											} else {
												Fluid fluid_offset = ((IFluidPipe) tile_offset).getTank().getFluid().getFluid();

												if (tank_orig.getFluid().getFluid().equals(fluid_offset)) {
													FluidTank tank_offset = ((IFluidPipe) tile_offset).getTank();

													int fluid_amount = tank_offset.getFluidAmount();
													int fluid_capacity = tank_offset.getCapacity();
													int gap = fluid_capacity - fluid_amount;

													if (fluid_amount < fluid_capacity) {
														if (gap >= fluid_rate) {
															FluidStack fluid_stack = new FluidStack(tank_offset.getFluid().getFluid(), fluid_rate);
															((IFluidPipe) tile_offset).fill(fluid_stack, true, c);

															tank_orig.drain(fluid_rate, true);
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
				ModUtil.syncBlockAndRerender(world, tile_pos);
			}
		}
	}

}
