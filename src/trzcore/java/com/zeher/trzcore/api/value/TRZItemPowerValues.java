package com.zeher.trzcore.api.value;

import com.zeher.trzcore.storage.item.TRZItemStorage;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TRZItemPowerValues {
	
	public static int getItemPower(Item item) {
		if (item.equals(Items.REDSTONE)) {
			return 5;
		}
		if (item instanceof TRZItemStorage) {
			return 1000;
		}
		return 0;
	}

	public static int getGeneratorItemPower(ItemStack item) {
		if (item.isEmpty()) {
			return 0;
		}
		Item i = item.getItem();
		if (i == Items.COAL) {
			return 100;
		}
		if (i == Item.getItemFromBlock(Blocks.COAL_BLOCK)) {
			return 1000;
		}
		if (i == Item.getItemFromBlock(Blocks.LOG) || i == Item.getItemFromBlock(Blocks.LOG2)) {
			return 250;
		}
		if (i == Item.getItemFromBlock(Blocks.PLANKS)) {
			return 50;
		}
		if (i == Items.STICK) {
			return 25;
		}
		return 0;
	}

	public static int getEnergyToolDamage() {
		return 1;
	}

}
