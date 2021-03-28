package com.tcn.cosmoslibrary.common.util;

import com.tcn.cosmoslibrary.common.interfaces.item.IWrench;
import com.tcn.cosmoslibrary.common.item.CosmosItemWrench;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CosmosUtil {
	
	public static final void syncBlockAndRerender(World world, BlockPos pos) {
		if (world == null || pos == null)
			return;

		BlockState state = world.getBlockState(pos);

		world.markAndNotifyBlock(pos, null, state, state, 2, 0);
	}
	
	public static boolean holdingWrench(PlayerEntity playerIn) {
		if (playerIn.inventory.getSelected().isEmpty()) {
			return false;
		}
		
		Item currentItem = playerIn.inventory.getSelected().getItem();
		
		if (currentItem instanceof CosmosItemWrench) {
			return ((CosmosItemWrench) currentItem).isActive(playerIn.inventory.getSelected());
		} else if (currentItem instanceof IWrench) {
			return true;
		}

		return false;
	}
	
	public static void setToAir(World worldIn, BlockPos pos) {
		worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
	}
	
	public static boolean handEmpty(PlayerEntity playerIn) {
		return playerIn.inventory.getSelected().isEmpty();
	}

	public static boolean handItem(PlayerEntity playerIn, Item item) {
		if (playerIn.inventory.getSelected().isEmpty()) {
			return false;
		}
		
		Item currentItem = playerIn.inventory.getSelected().getItem();
		if (currentItem.equals(item)) {
			return true;
		}
		return false;
	}
	
	public static ItemStack getStack(PlayerEntity playerIn) {
		return playerIn.inventory.getSelected();
	}
	
	public static Item getStackItem(PlayerEntity playerIn) {
		return playerIn.inventory.getSelected().getItem();
	}
	
	public static void addItem(PlayerEntity playerIn, Item item, int count) {
		playerIn.inventory.add(new ItemStack(item, count));
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