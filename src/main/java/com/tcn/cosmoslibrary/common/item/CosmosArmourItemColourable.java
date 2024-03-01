package com.tcn.cosmoslibrary.common.item;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;

public class CosmosArmourItemColourable extends ArmorItem implements Vanishable {
	
	public CosmosArmourItemColourable(ArmorMaterial materialIn, Type typeIn, Item.Properties builderIn) {
		super(materialIn, typeIn, builderIn);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		EquipmentSlot equipmentslottype = Mob.getEquipmentSlotForItem(itemstack);
		ItemStack itemstack1 = playerIn.getItemBySlot(equipmentslottype);

		if (itemstack1.isEmpty()) {
			playerIn.setItemSlot(equipmentslottype, itemstack.copy());
			if (!levelIn.isClientSide()) {
				playerIn.awardStat(Stats.ITEM_USED.get(this));
			}
			itemstack.setCount(0);
			return InteractionResultHolder.sidedSuccess(itemstack, levelIn.isClientSide());
		} else {
			return InteractionResultHolder.fail(itemstack);
		}
	}
}