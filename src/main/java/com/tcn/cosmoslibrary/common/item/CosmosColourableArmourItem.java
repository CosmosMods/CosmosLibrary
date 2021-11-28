package com.tcn.cosmoslibrary.common.item;

import net.minecraft.enchantment.IArmorVanishable;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class CosmosColourableArmourItem extends ArmorItem implements IArmorVanishable {
	
	public CosmosColourableArmourItem(IArmorMaterial materialIn, EquipmentSlotType slot, Item.Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerEntityIn, Hand handIn) {
		ItemStack itemstack = playerEntityIn.getItemInHand(handIn);
		EquipmentSlotType equipmentslottype = MobEntity.getEquipmentSlotForItem(itemstack);
		ItemStack itemstack1 = playerEntityIn.getItemBySlot(equipmentslottype);

		if (itemstack1.isEmpty()) {
			playerEntityIn.setItemSlot(equipmentslottype, itemstack.copy());
			itemstack.setCount(0);
			return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide());
		} else {
			return ActionResult.fail(itemstack);
		}
	}
}