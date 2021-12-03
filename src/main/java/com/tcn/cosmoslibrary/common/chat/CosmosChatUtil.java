package com.tcn.cosmoslibrary.common.chat;

import java.util.UUID;

import net.minecraft.network.chat.BaseComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class CosmosChatUtil {
	
	public static void sendClientPlayerMessage(Player playerIn, BaseComponent comp) {
		if (playerIn.level.isClientSide) {
			playerIn.sendMessage(comp, UUID.randomUUID());
		}
	}

	public static void sendServerPlayerMessage(Player playerIn, BaseComponent comp) {
		if (playerIn instanceof ServerPlayer) {
			playerIn.sendMessage(comp, UUID.randomUUID());
		}
		
		if (!playerIn.level.isClientSide) {
			//playerIn.sendMessage(comp, UUID.randomUUID());
		}
	}
	
	public static void sendPlayerMessageServer(ServerPlayer playerIn, BaseComponent comp) {
		playerIn.sendMessage(comp, UUID.randomUUID());
	}
}