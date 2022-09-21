package com.tcn.cosmoslibrary.runtime;

import com.tcn.cosmoslibrary.CosmosLibrary;
import com.tcn.cosmoslibrary.energy.item.CosmosEnergyShield;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod.EventBusSubscriber(modid = CosmosLibrary.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
public class EventManagerCosmos {

	@SubscribeEvent
	public static void onLivingAttackEvent(LivingAttackEvent event) {
		float damage = event.getAmount();
		
		if (!(event.getEntityLiving() instanceof Player)) {
			return;
		}
		
		Player player = (Player) event.getEntityLiving();
		ItemStack stack = player.getUseItem();
		
		if (damage > 0.0F && !stack.isEmpty() && stack.getItem() instanceof CosmosEnergyShield && player.isUsingItem()) {
			CosmosEnergyShield shieldItem = (CosmosEnergyShield) stack.getItem();
			
			shieldItem.damageItem(stack, 0, player, (playerX) -> {  });
		}
	}
}