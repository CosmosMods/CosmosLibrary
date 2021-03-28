package com.tcn.cosmoslibrary.common.chat;

import java.util.UUID;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.IFormattableTextComponent;

public class CosmosChatUtil {
	
	public static void sendClientPlayerMessage(PlayerEntity playerIn, IFormattableTextComponent comp) {
		if (playerIn.level.isClientSide) {
			playerIn.sendMessage(comp, UUID.randomUUID());
		}
	}

	public static void sendServerPlayerMessage(PlayerEntity playerIn, IFormattableTextComponent comp) {
		if (!playerIn.level.isClientSide) {
			playerIn.sendMessage(comp, UUID.randomUUID());
		}
	}
	
	public static void sendPlayerMessageServer(ServerPlayerEntity playerIn, IFormattableTextComponent comp) {
		playerIn.sendMessage(comp, UUID.randomUUID());
	}
}
