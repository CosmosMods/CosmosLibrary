package com.zeher.trzcore.api;

import com.zeher.trzcore.tool.item.TRZIToolWrench;
import com.zeher.trzcore.tool.item.TRZIToolWrenchAdvanced;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class TRZUtil {

	public static final AxisAlignedBB[] BOUNDING_BOXES_FAT = new AxisAlignedBB[] {
			new AxisAlignedBB(0.3D, 0.3D, 0.3D, 0.7D, 0.7D, 0.7D), // BASE 0
			new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.7D, 0.7D), // DOWN 1

			new AxisAlignedBB(0.3D, 0.3D, 0.3D, 0.7D, 1.0D, 0.7D), // UP 2
			new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 1.0D, 0.7D), // UP-DOWN 3

			new AxisAlignedBB(0.3D, 0.3D, 0.0D, 0.7D, 0.7D, 0.7D), // NORTH 4
			new AxisAlignedBB(0.3D, 0.0D, 0.0D, 0.7D, 0.7D, 0.7D), // NORTH-DOWN
																	// 5
			new AxisAlignedBB(0.3D, 0.3D, 0.0D, 0.7D, 1.0D, 0.7D), // NORTH-UP 6
			new AxisAlignedBB(0.3D, 0.0D, 0.0D, 0.7D, 1.0D, 0.7D), // NORTH-UP-DOWN
																	// 7

			new AxisAlignedBB(0.3D, 0.3D, 0.3D, 0.7D, 0.7D, 1.0D), // SOUTH 8
			new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.7D, 1.0D), // SOUTH-DOWN
																	// 9
			new AxisAlignedBB(0.3D, 0.3D, 0.3D, 0.7D, 1.0D, 1.0D), // SOUTH-UP
																	// 10
			new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 1.0D, 1.0D), // SOUTH-UP-DOWN
																	// 11

			new AxisAlignedBB(0.3D, 0.3D, 0.0D, 0.7D, 0.7D, 1.0D), // NORTH-SOUTH
																	// 12
			new AxisAlignedBB(0.3D, 0.0D, 0.0D, 0.7D, 0.7D, 1.0D), // NORTH-SOUTH-DOWN
																	// 13
			new AxisAlignedBB(0.3D, 0.3D, 0.0D, 0.7D, 1.0D, 1.0D), // NORTH-SOUTH-UP
																	// 14
			new AxisAlignedBB(0.3D, 0.0D, 0.0D, 0.7D, 1.0D, 1.0D), // NORTH-SOUTH-UP-DOWN
																	// 15

			new AxisAlignedBB(0.0D, 0.3D, 0.3D, 0.7D, 0.7D, 0.7D), // WEST 16
			new AxisAlignedBB(0.0D, 0.0D, 0.3D, 0.7D, 0.7D, 0.7D), // WEST-DOWN
																	// 17
			new AxisAlignedBB(0.0D, 0.3D, 0.3D, 0.7D, 1.0D, 0.7D), // WEST-UP 18
			new AxisAlignedBB(0.0D, 0.0D, 0.3D, 0.7D, 1.0D, 0.7D), // WEST-UP-DOWN
																	// 19

			new AxisAlignedBB(0.0D, 0.3D, 0.0D, 0.7D, 0.7D, 0.7D), // NORTH-WEST
																	// 20
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.7D, 0.7D, 0.7D), // NORTH-WEST-DOWN
																	// 21
			new AxisAlignedBB(0.0D, 0.3D, 0.0D, 0.7D, 1.0D, 0.7D), // NORTH-WEST-UP
																	// 22
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.7D, 1.0D, 0.7D), // NORTH-WEST-UP-DOWN
																	// 23

			new AxisAlignedBB(0.0D, 0.3D, 0.3D, 0.7D, 0.7D, 1.0D), // SOUTH-WEST
																	// 24
			new AxisAlignedBB(0.0D, 0.0D, 0.3D, 0.7D, 0.7D, 1.0D), // SOUTH-WEST-DOWN
																	// 25
			new AxisAlignedBB(0.0D, 0.3D, 0.3D, 0.7D, 1.0D, 1.0D), // SOUTH-WEST-UP
																	// 26
			new AxisAlignedBB(0.0D, 0.0D, 0.3D, 0.7D, 1.0D, 1.0D), // SOUTH-WEST-UP-DOWN
																	// 27

			new AxisAlignedBB(0.0D, 0.3D, 0.0D, 0.7D, 0.7D, 1.0D), // NORTH-SOUTH-WEST
																	// 28
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.7D, 0.7D, 1.0D), // NORTH-SOUTH-WEST-DOWN
																	// 29
			new AxisAlignedBB(0.0D, 0.3D, 0.0D, 0.7D, 1.0D, 1.0D), // NORTH-SOUTH-WEST-UP
																	// 30
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.7D, 1.0D, 1.0D), // NORTH-SOUTH-WEST-UP-DOWN
																	// 31

			new AxisAlignedBB(0.3D, 0.3D, 0.3D, 1.0D, 0.7D, 0.7D), // EAST 32
			new AxisAlignedBB(0.3D, 0.0D, 0.3D, 1.0D, 0.7D, 0.7D), // EAST-DOWN
																	// 33
			new AxisAlignedBB(0.3D, 0.3D, 0.3D, 1.0D, 1.0D, 0.7D), // EAST-UP 34
			new AxisAlignedBB(0.3D, 0.0D, 0.3D, 1.0D, 1.0D, 0.7D), // EAST-UP-DOWN
																	// 35

			new AxisAlignedBB(0.3D, 0.3D, 0.0D, 1.0D, 0.7D, 0.7D), // NORTH-EAST
																	// 36
			new AxisAlignedBB(0.3D, 0.0D, 0.0D, 1.0D, 0.7D, 0.7D), // NORTH-EAST-DOWN
																	// 37
			new AxisAlignedBB(0.3D, 0.3D, 0.0D, 1.0D, 1.0D, 0.7D), // NORTH-EAST-UP
																	// 38
			new AxisAlignedBB(0.3D, 0.0D, 0.0D, 1.0D, 1.0D, 0.7D), // NORTH-EAST-UP-DOWN
																	// 39

			new AxisAlignedBB(0.3D, 0.3D, 0.3D, 1.0D, 0.7D, 1.0D), // SOUTH-EAST
																	// 40
			new AxisAlignedBB(0.3D, 0.0D, 0.3D, 1.0D, 0.7D, 1.0D), // SOUTH-EAST-DOWN
																	// 41
			new AxisAlignedBB(0.3D, 0.3D, 0.3D, 1.0D, 1.0D, 1.0D), // SOUTH-EAST-UP
																	// 42
			new AxisAlignedBB(0.3D, 0.0D, 0.3D, 1.0D, 1.0D, 1.0D), // SOUTH-EAST-UP-DOWN
																	// 43

			new AxisAlignedBB(0.3D, 0.3D, 0.0D, 1.0D, 0.7D, 1.0D), // NORTH-SOUTH-EAST
																	// 44
			new AxisAlignedBB(0.3D, 0.0D, 0.0D, 1.0D, 0.7D, 1.0D), // NORTH-SOUTH-EAST-DOWN
																	// 45
			new AxisAlignedBB(0.3D, 0.3D, 0.0D, 1.0D, 1.0D, 1.0D), // NORTH-SOUTH-EAST-UP
																	// 46
			new AxisAlignedBB(0.3D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), // NORTH-SOUTH-EAAT-UP-DOWN
																	// 47

			new AxisAlignedBB(0.0D, 0.3D, 0.3D, 1.0D, 0.7D, 0.7D), // EAST-WEST
																	// 48
			new AxisAlignedBB(0.0D, 0.0D, 0.3D, 1.0D, 0.7D, 0.7D), // EAST-WEST-DOWN
																	// 49
			new AxisAlignedBB(0.0D, 0.3D, 0.3D, 1.0D, 1.0D, 0.7D), // EAST-WEST-UP
																	// 50
			new AxisAlignedBB(0.0D, 0.0D, 0.3D, 1.0D, 1.0D, 0.7D), // EAST-WEST-UP=DOWN
																	// 51

			new AxisAlignedBB(0.0D, 0.3D, 0.0D, 1.0D, 0.7D, 0.7D), // EAST-WEST-NORTH
																	// 52
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.7D, 0.7D), // EAST-WEST-NORTH-DOWN
																	// 53
			new AxisAlignedBB(0.0D, 0.3D, 0.0D, 1.0D, 1.0D, 0.7D), // EAST-WEST-NORTH-UP
																	// 54
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.7D), // EAST-WEST-NORTH-UP-DOWN
																	// 55

			new AxisAlignedBB(0.0D, 0.3D, 0.3D, 1.0D, 0.7D, 1.0D), // SOUTH-EAST-WEST
																	// 56
			new AxisAlignedBB(0.0D, 0.0D, 0.3D, 1.0D, 0.7D, 1.0D), // SOUTH-EAST-WEST-DOWN
																	// 57
			new AxisAlignedBB(0.0D, 0.3D, 0.3D, 1.0D, 1.0D, 1.0D), // SOUTH-EAST-WEST-UP
																	// 58
			new AxisAlignedBB(0.0D, 0.0D, 0.3D, 1.0D, 1.0D, 1.0D), // SOUTH-EAST-WEST-UP-DOWN
																	// 59

			new AxisAlignedBB(0.0D, 0.3D, 0.0D, 1.0D, 0.7D, 1.0D), // SOUTH-NORTH-EAST-WEST
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.7D, 1.0D), // SOUTH-NORTH-EAST-WEST-DOWN
			new AxisAlignedBB(0.0D, 0.3D, 0.0D, 1.0D, 1.0D, 1.0D), // SOUTH-NORTH-EAST-WEST-UP
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D) }; // SOUTH-NORTH-EAST-WEST-UP-DOWN

	public static boolean isHoldingHammer(EntityPlayer player) {
		if (player.inventory.getCurrentItem() == null) {
			return false;
		}
		Item currentItem = player.inventory.getCurrentItem().getItem();
		if (currentItem instanceof TRZIToolWrenchAdvanced) {
			return ((TRZIToolWrenchAdvanced) currentItem).isActive(player.inventory.getCurrentItem());
		} else if (currentItem instanceof TRZIToolWrench) {
			return true;
		}

		return false;
	}

	public static boolean isHoldingItem(EntityPlayer player, Item item) {
		player.swingArm(EnumHand.MAIN_HAND);
		if (player.inventory.getCurrentItem() == null) {
			return false;
		}
		Item currentItem = player.inventory.getCurrentItem().getItem();
		if (currentItem == item) {
			return true;
		}

		return false;
	}

	public static int machinePowerDrain() {
		return 500;
	}

	public static int advMachinePowerDrain() {
		return 200;
	}
	
	
	public static int getSides(boolean down, boolean up, boolean north, boolean south, boolean west, boolean east){
		int side_connect = 0;
		if(down){
			side_connect = 1;
		}
		
		
		if(up){
			side_connect = 2;
		}
		if(up && down){
			side_connect = 3;
		}
		
		
		if(north){
			side_connect = 4;
		}
		if(north && down){
			side_connect = 5;
		}
		if(north && up){
			side_connect = 6;
		}
		if(north && up && down){
			side_connect = 7;
		}
		
		
		if(south){
			side_connect = 8;
		}
		if(south && down){
			side_connect = 9;
		}
		if(south && up){
			side_connect = 10;
		}
		if(south && up && down){
			side_connect = 11;
		}
		
		
		if(north && south){
			side_connect = 12;
		}
		if(north && south && down){
			side_connect = 13;
		}
		if(north && south && up){
			side_connect = 14;
		}
		if(north && south && up && down){
			side_connect = 15;
		}
		
		
		if(west){
			side_connect = 16;
		}
		if(west && down){
			side_connect = 17;
		}
		if(west && up){
			side_connect = 18;
		}
		if(west && up && down){
			side_connect = 19;
		}
		
		if(north && west){
			side_connect = 20;
		}
		if(north && west && down){
			side_connect = 21;
		}
		if(north && west && up){
			side_connect = 22;
		}
		if(north && west && up && down){
			side_connect = 23;
		}
		
		if(south && west){
			side_connect = 24;
		}
		if(south && west && down){
			side_connect = 25;
		}
		if(south && west && up){
			side_connect = 26;
		}
		if(south && west && up && down){
			side_connect = 27;
		}
		
		
		if(north && south && west){
			side_connect = 28;
		}
		if(north && south && west && down){
			side_connect = 29;
		}
		if(north && south && west && up){
			side_connect = 30;
		}
		if(north && south && west && up && down){
			side_connect = 31;
		}
		
		
		if(east){
			side_connect = 32;
		}
		if(east && down){
			side_connect = 33;
		}
		if(east && up){
			side_connect = 34;
		}
		if(east && up && down){
			side_connect = 35;
		}
		
		if(north && east){
			side_connect = 36;
		}
		if(north && east && down){
			side_connect = 37;
		}
		if(north && east && up){
			side_connect = 38;
		}
		if(north && east && up && down){
			side_connect = 39;
		}
		
		
		if(south && east){
			side_connect = 40;
		}
		if(south && east && down){
			side_connect = 41;
		}
		if(south && east && up){
			side_connect = 42;
		}
		if(south && east && up && down){
			side_connect = 43;
		}
		
		
		if(north && south && east){
			side_connect = 44;
		}
		if(north && south && east && down){
			side_connect = 45;
		}
		if(north && south && east && up){
			side_connect = 46;
		}
		if(north && south &&  east && up && down){
			side_connect = 47;
		}
		
		if(east && west){
			side_connect = 48;
		}
		if(east && west && down){
			side_connect = 49;
		}
		if(east && west && up){
			side_connect = 50;
		}
		if(east && west && up && down){
			side_connect = 51;
		}
		
		
		if(north && west && east){
			side_connect = 52;
		}
		if(north && west && east && down){
			side_connect = 53;
		}
		if(north && west && east && up){
			side_connect = 54;
		}
		if(north && west &&  east && up && down){
			side_connect = 55;
		}
		
		if(south && west && east){
			side_connect = 56;
		}
		if(south && west && east && down){
			side_connect = 57;
		}
		if(south && west && east && up){
			side_connect = 58;
		}
		if(south && west &&  east && up && down){
			side_connect = 59;
		}
		
		
		if(north && south && west &&  east){
			side_connect = 60;
		}
		if(north && south && west &&  east && down){
			side_connect = 61;
		}
		if(north && south && west &&  east && up){
			side_connect = 62;
		}
		if(north && south && west &&  east && up && down){
			side_connect = 63;
		}
		return side_connect;
	}
}
