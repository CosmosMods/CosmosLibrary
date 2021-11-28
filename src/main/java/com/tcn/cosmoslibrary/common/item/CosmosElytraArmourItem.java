package com.tcn.cosmoslibrary.common.item;

import net.minecraft.enchantment.IArmorVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class CosmosElytraArmourItem extends CosmosColourableArmourItem implements IArmorVanishable {
	
	private static boolean damageable;

	public CosmosElytraArmourItem(IArmorMaterial materialIn, EquipmentSlotType slot, Item.Properties builderIn, boolean damageableIn) {
		super(materialIn, slot, builderIn);
	}

	public static boolean isFlyEnabled(ItemStack stackIn) {
		if (damageable) {
			return stackIn.getDamageValue() < stackIn.getMaxDamage() - 1;
		}

		return true;
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

	@Override
	public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
		return isFlyEnabled(stack);
	}

	@Override
	public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
		if (damageable) {
			if (!entity.level.isClientSide && (flightTicks + 1) % 20 == 0) {
				stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(EquipmentSlotType.CHEST));
			}
			return true;
		}
		return true;
	}

}