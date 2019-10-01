package com.zeher.zeherlib.api.value;

import com.zeher.zeherlib.storage.item.ItemStorage;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemPowerValues {

	public static int getItemPower(Item item) {
		if (item instanceof ItemStorage) {
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
		} else if (i == Item.getItemFromBlock(Blocks.COAL_BLOCK)) {
			return 1000;
		} else if (i == Item.getItemFromBlock(Blocks.LOG) || i == Item.getItemFromBlock(Blocks.LOG2)) {
			return 250;
		} else if (i == Item.getItemFromBlock(Blocks.PLANKS)) {
			return 50;
		} else if (i == Items.STICK) {
			return 25;
		}
		return 0;
	}

	public static int getEnergyToolDamage() {
		return 1;
	}

}
