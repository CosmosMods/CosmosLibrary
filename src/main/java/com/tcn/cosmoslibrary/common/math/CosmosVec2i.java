package com.tcn.cosmoslibrary.common.math;

import java.util.stream.IntStream;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;

import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@Immutable
public class CosmosVec2i implements Comparable<CosmosVec2i> {
   public static final Codec<CosmosVec2i> CODEC = Codec.INT_STREAM.comapFlatMap((stream) -> {
      return Util.fixedSize(stream, 3).map((componentArraz) -> {
         return new CosmosVec2i(componentArraz[0], componentArraz[1]);
      });
   }, (vector) -> {
      return IntStream.of(vector.getX(), vector.getZ());
   });
   /** An immutable vector with zero as all coordinates. */
   public static final CosmosVec2i NULL_VECTOR = new CosmosVec2i(0, 0);
   private int x;
   private int z;

   public CosmosVec2i(int xIn, int zIn) {
      this.x = xIn;
      this.z = zIn;
   }

   public CosmosVec2i(double xIn, double zIn) {
      this(Mth.floor(xIn), Mth.floor(zIn));
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof CosmosVec2i)) {
         return false;
      } else {
         CosmosVec2i vector3i = (CosmosVec2i)p_equals_1_;
         if (this.getX() != vector3i.getX()) {
            return false;
         } else if (this.getZ() != vector3i.getZ()) {
            return false;
         } else if (this.getX() == vector3i.getX()) {
        	 return true;
         } else {
        	 return this.getZ() == vector3i.getZ();
         }
      }
   }

   public int hashCode() {
      return (this.getZ() * 31) * 31 + this.getX();
   }

   public int compareTo(CosmosVec2i p_compareTo_1_) {
      if (this.getZ() == p_compareTo_1_.getZ()) {
         return this.getZ() == p_compareTo_1_.getZ() ? this.getX() - p_compareTo_1_.getX() : this.getZ() - p_compareTo_1_.getZ();
      } else {
         return this.getZ() - p_compareTo_1_.getZ();
      }
   }

   /**
    * Gets the X coordinate.
    */
   public int getX() {
      return this.x;
   }

   /**
    * Gets the Z coordinate.
    */
   public int getZ() {
      return this.z;
   }
   
   /**
    * Sets the X coordinate.
    */
   protected void setX(int xIn) {
      this.x = xIn;
   }

   protected void setZ(int zIn) {
      this.z = zIn;
   }
   
   /**
    * Offset this BlockPos 1 block up
    */
   public CosmosVec2i up() {
      return this.up(1);
   }

   /**
    * Offset this BlockPos n blocks up
    */
   public CosmosVec2i up(int n) {
      return this.offset(Direction.UP, n);
   }

   /**
    * Offset this BlockPos 1 block down
    */
   public CosmosVec2i down() {
      return this.down(1);
   }

   /**
    * Offset this BlockPos n blocks down
    */
   public CosmosVec2i down(int n) {
      return this.offset(Direction.DOWN, n);
   }

   /**
    * Offsets this BlockPos n blocks in the given direction
    */
   public CosmosVec2i offset(Direction facing, int n) {
      return n == 0 ? this : new CosmosVec2i(this.getX() + facing.getStepX() * n, this.getZ() + facing.getStepZ() * n);
   }

   /**
    * Calculate the cross product of this and the given Vector
    */
   public CosmosVec2i crossProduct(CosmosVec2i vec) {
	   return null;
      //return new Vector2i(this.getZ() * vec.getZ() - this.getZ() * vec.getZ(), this.getZ() * vec.getX() - this.getX() * vec.getZ(), this.getX() * vec.getZ() - this.getZ() * vec.getX());
   }

   public boolean withinDistance(CosmosVec2i vector, double distance) {
      return this.distanceSq((double)vector.getX(), (double)vector.getZ(), false) < distance * distance;
   }

   public boolean withinDistance(Position position, double distance) {
      return this.distanceSq(position.x(), position.z(), true) < distance * distance;
   }

   /**
    * Calculate squared distance to the given Vector
    */
   public double distanceSq(CosmosVec2i to) {
      return this.distanceSq((double)to.getX(), (double)to.getZ(), true);
   }

   public double distanceSq(Position position, boolean useCenter) {
      return this.distanceSq(position.x(), position.z(), useCenter);
   }

   public double distanceSq(double x, double z, boolean useCenter) {
      double d0 = useCenter ? 0.5D : 0.0D;
      double d1 = (double)this.getX() + d0 - x;
      double d2 = (double)this.getZ() + d0 - z;
      return d1 * d1 + d2 * d2;
   }

   public int manhattanDistance(CosmosVec2i vector) {
      float f = (float)Math.abs(vector.getX() - this.getX());
      float f1 = (float)Math.abs(vector.getZ() - this.getZ());
      return (int)(f + f1);
   }
   
   public String toString() {
      return MoreObjects.toStringHelper(this).add("x", this.getX()).add("z", this.getZ()).toString();
   }

   @OnlyIn(Dist.CLIENT)
   public String getCoordinatesAsString() {
      return "" + this.getX() + ", " + this.getZ();
   }
}
