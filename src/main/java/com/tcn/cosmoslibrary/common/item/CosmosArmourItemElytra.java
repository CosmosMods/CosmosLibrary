package com.tcn.cosmoslibrary.common.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;

@SuppressWarnings("resource")
public class CosmosArmourItemElytra extends CosmosArmourItemColourable implements Vanishable {
	
	private boolean damageable;

	public CosmosArmourItemElytra(ArmorMaterial materialIn, Type typeIn, Item.Properties builderIn, boolean damageableIn) {
		super(materialIn, typeIn, builderIn);
	}

	public boolean isFlyEnabled(ItemStack stackIn) {
		if (this.damageable) {
			return stackIn.getDamageValue() < stackIn.getMaxDamage() - 1;
		}

		return true;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerEntityIn, InteractionHand handIn) {
		ItemStack itemstack = playerEntityIn.getItemInHand(handIn);
		EquipmentSlot equipmentslottype = Mob.getEquipmentSlotForItem(itemstack);
		ItemStack itemstack1 = playerEntityIn.getItemBySlot(equipmentslottype);

		if (itemstack1.isEmpty()) {
			playerEntityIn.setItemSlot(equipmentslottype, itemstack.copy());
			itemstack.setCount(0);
			return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
		} else {
			return InteractionResultHolder.fail(itemstack);
		}
	}

	@Override
	public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
		return isFlyEnabled(stack);
	}

	@Override
	public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
		if (damageable) {
			if (!entity.level().isClientSide && (flightTicks + 1) % 20 == 0) {
				stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(EquipmentSlot.CHEST));
			}
			return true;
		}
		return true;
	}
	
}