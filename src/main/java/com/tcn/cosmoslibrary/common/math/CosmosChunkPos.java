package com.tcn.cosmoslibrary.common.math;

import java.util.Random;

import javax.annotation.concurrent.Immutable;

import com.google.common.collect.AbstractIterator;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Rotation;

@Immutable
public class CosmosChunkPos extends CosmosVec2i {
	
	public static final CosmosChunkPos ZERO = new CosmosChunkPos(0, 0);
	private static final int NUM_X_BITS = 1 + Mth.log2(Mth.smallestEncompassingPowerOfTwo(30000000));
	private static final int NUM_Z_BITS = NUM_X_BITS;
	private static final int NUM_Y_BITS = 64 - NUM_X_BITS - NUM_Z_BITS;
	private static final long X_MASK = (1L << NUM_X_BITS) - 1L;
	private static final long Z_MASK = (1L << NUM_Z_BITS) - 1L;
	private static final int INVERSE_START_BITS_Z = NUM_Y_BITS;
	private static final int INVERSE_START_BITS_X = NUM_Y_BITS + NUM_Z_BITS;

	public CosmosChunkPos(int x, int y) {
		super(x, y);
	}

	public CosmosChunkPos(double x, double y) {
		super(x, y);
	}

	public static CosmosChunkPos convertTo(BlockPos posIn) {
		if (posIn != null) {
			return new CosmosChunkPos(posIn.getX(), posIn.getZ());
		} else {
			return ZERO;
		}
	}

	public static BlockPos convertFrom(CosmosChunkPos posIn) {
		if (posIn != null) {
			return new BlockPos(posIn.getX(), 0, posIn.getZ());
		} else {
			return BlockPos.ZERO;
		}
	}

	public static CosmosChunkPos scaleToChunkPos(BlockPos posIn) {
		if (posIn != null) {
			return new CosmosChunkPos(posIn.getX() >> 4, posIn.getZ() >> 4);
		} else {
			return ZERO;
		}
	}

	public static BlockPos scaleFromChunkPos(CosmosChunkPos posIn) {
		if (posIn != null) {
			return new BlockPos(posIn.getX() * 16, 0, posIn.getZ() * 16);
		} else {
			return BlockPos.ZERO;
		}
	}
	
	public CosmosChunkPos(Position position) {
		this(position.x(), position.z());
	}

	public CosmosChunkPos(CosmosVec2i source) {
		this(source.getX(), source.getZ());
	}

	public static long offset(long pos, Direction direction) {
		return offset(pos, direction.getStepX(), direction.getStepZ());
	}

	public static long offset(long pos, int dx, int dz) {
		return pack(unpackX(pos) + dx, unpackZ(pos) + dz);
	}

	public static int unpackX(long packedPos) {
		return (int) (packedPos << 64 - INVERSE_START_BITS_X - NUM_X_BITS >> 64 - NUM_X_BITS);
	}

	public static int unpackZ(long packedPos) {
		return (int) (packedPos << 64 - INVERSE_START_BITS_Z - NUM_Z_BITS >> 64 - NUM_Z_BITS);
	}

	public static CosmosChunkPos fromLong(long packedPos) {
		return new CosmosChunkPos(unpackX(packedPos), unpackZ(packedPos));
	}

	public long toLong() {
		return pack(this.getX(), this.getZ());
	}

	public static long pack(int x, int z) {
		long i = 0L;
		i = i | ((long) x & X_MASK) << INVERSE_START_BITS_X;
		return i | ((long) z & Z_MASK) << INVERSE_START_BITS_Z;
	}

	public static long atSectionBottomY(long packedPos) {
		return packedPos & -16L;
	}
	
	public CosmosChunkPos add(double x, double z) {
		return x == 0.0D && z == 0.0D ? this : new CosmosChunkPos((double) this.getX() + x, (double) this.getZ() + z);
	}
	
	public CosmosChunkPos add(int x, int y, int z) {
		return x == 0 && y == 0 && z == 0 ? this : new CosmosChunkPos(this.getX() + x, this.getZ() + z);
	}
	
	public CosmosChunkPos add(CosmosVec2i vec) {
		return this.add(vec.getX(), vec.getZ());
	}
	
	public CosmosChunkPos subtract(CosmosVec2i vec) {
		return this.add(-vec.getX(), -vec.getZ());
	}
	
	@Override
	public CosmosChunkPos up() {
		return this.offset(Direction.UP);
	}
	
	@Override
	public CosmosChunkPos up(int n) {
		return this.offset(Direction.UP, n);
	}
	
	@Override
	public CosmosChunkPos down() {
		return this.offset(Direction.DOWN);
	}
	
	@Override
	public CosmosChunkPos down(int n) {
		return this.offset(Direction.DOWN, n);
	}
	
	public CosmosChunkPos north() {
		return this.offset(Direction.NORTH);
	}
	
	public CosmosChunkPos north(int n) {
		return this.offset(Direction.NORTH, n);
	}
	
	public CosmosChunkPos south() {
		return this.offset(Direction.SOUTH);
	}
	
	public CosmosChunkPos south(int n) {
		return this.offset(Direction.SOUTH, n);
	}
	
	public CosmosChunkPos west() {
		return this.offset(Direction.WEST);
	}
	
	public CosmosChunkPos west(int n) {
		return this.offset(Direction.WEST, n);
	}
	
	public CosmosChunkPos east() {
		return this.offset(Direction.EAST);
	}
	
	public CosmosChunkPos east(int n) {
		return this.offset(Direction.EAST, n);
	}
	
	public CosmosChunkPos offset(Direction facing) {
		return new CosmosChunkPos(this.getX() + facing.getStepX(), this.getZ() + facing.getStepZ());
	}
	
	@Override
	public CosmosChunkPos offset(Direction facing, int n) {
		return n == 0 ? this
				: new CosmosChunkPos(this.getX() + facing.getStepX() * n, this.getZ() + facing.getStepZ() * n);
	}

	public CosmosChunkPos rotate(Rotation rotationIn) {
		switch (rotationIn) {
		case NONE:
		default:
			return this;
		case CLOCKWISE_90:
			return new CosmosChunkPos(-this.getZ(), this.getX());
		case CLOCKWISE_180:
			return new CosmosChunkPos(-this.getX(), -this.getZ());
		case COUNTERCLOCKWISE_90:
			return new CosmosChunkPos(this.getZ(), -this.getX());
		}
	}
	
	public CosmosChunkPos toImmutable() {
		return this;
	}

	public CosmosChunkPos.Mutable toMutable() {
		return new CosmosChunkPos.Mutable(this.getX(), this.getZ());
	}

	public static Iterable<CosmosChunkPos> getRandomPositions(Random rand, int amount, int minX, int minZ, int maxX,
			int maxZ) {
		int i = maxX - minX + 1;
		int k = maxZ - minZ + 1;
		return () -> {
			return new AbstractIterator<CosmosChunkPos>() {
				final CosmosChunkPos.Mutable pos = new CosmosChunkPos.Mutable();
				int remainingAmount = amount;

				@Override
				protected CosmosChunkPos computeNext() {
					if (this.remainingAmount <= 0) {
						return this.endOfData();
					} else {
						CosmosChunkPos blockpos = this.pos.setPos(minX + rand.nextInt(i), minZ + rand.nextInt(k));
						--this.remainingAmount;
						return blockpos;
					}
				}
			};
		};
	}

	public static class Mutable extends CosmosChunkPos {
		public Mutable() {
			this(0, 0);
		}

		public Mutable(int x_, int z_) {
			super(x_, z_);
		}

		public Mutable(double x, double z) {
			this(Mth.floor(x), Mth.floor(z));
		}
		
		@Override
		public CosmosChunkPos add(double x, double z) {
			return super.add(x, z).toImmutable();
		}
		
		public CosmosChunkPos add(int x, int z) {
			return super.add(x, z).toImmutable();
		}
		
		@Override
		public CosmosChunkPos offset(Direction facing, int n) {
			return super.offset(facing, n).toImmutable();
		}
		
		@Override
		public CosmosChunkPos rotate(Rotation rotationIn) {
			return super.rotate(rotationIn).toImmutable();
		}
		
		public CosmosChunkPos.Mutable setPos(int xIn, int zIn) {
			this.setX(xIn);
			this.setZ(zIn);
			return this;
		}
		
		public CosmosChunkPos.Mutable setPos(double xIn, double zIn) {
			return this.setPos(Mth.floor(xIn), Mth.floor(zIn));
		}

		public CosmosChunkPos.Mutable setPos(CosmosVec2i vec) {
			return this.setPos(vec.getX(), vec.getZ());
		}

		public CosmosChunkPos.Mutable setPos(long packedPos) {
			return this.setPos(unpackX(packedPos), unpackZ(packedPos));
		}

		/*
		public ChunkPos.Mutable setPos(AxisRotation rotation, int x, int y, int z) {
			return this.setPos(rotation..getCoordinate(x, y, z, Direction.Axis.X),
					rotation.getCoordinate(x, y, z, Direction.Axis.Z));
		}*/

		public CosmosChunkPos.Mutable setAndMove(CosmosVec2i pos, Direction direction) {
			return this.setPos(pos.getX() + direction.getStepX(), pos.getZ() + direction.getStepZ());
		}

		public CosmosChunkPos.Mutable setAndOffset(CosmosVec2i pos, int offsetX, int offsetY, int offsetZ) {
			return this.setPos(pos.getX() + offsetX, pos.getZ() + offsetZ);
		}

		public CosmosChunkPos.Mutable move(Direction facing) {
			return this.move(facing, 1);
		}

		public CosmosChunkPos.Mutable move(Direction facing, int n) {
			return this.setPos(this.getX() + facing.getStepX() * n, this.getZ() + facing.getStepZ() * n);
		}

		public CosmosChunkPos.Mutable move(int xIn, int yIn, int zIn) {
			return this.setPos(this.getX() + xIn, this.getZ() + zIn);
		}

		public CosmosChunkPos.Mutable func_243531_h(CosmosVec2i p_243531_1_) {
			return this.setPos(this.getX() + p_243531_1_.getX(), this.getZ() + p_243531_1_.getZ());
		}

		public CosmosChunkPos.Mutable clampAxisCoordinate(Direction.Axis axis, int min, int max) {
			switch (axis) {
			case X:
				return this.setPos(Mth.clamp(this.getX(), min, max), this.getZ());
			case Z:
				return this.setPos(this.getX(), Mth.clamp(this.getZ(), min, max));
			default:
				throw new IllegalStateException("Unable to clamp axis " + axis);
			}
		}

		@Override
		public void setX(int xIn) {
			super.setX(xIn);
		}

		@Override
		public void setZ(int zIn) {
			super.setZ(zIn);
		}

		@Override
		public CosmosChunkPos toImmutable() {
			return new CosmosChunkPos(this);
		}
	}
}