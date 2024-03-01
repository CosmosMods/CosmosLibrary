package com.tcn.cosmoslibrary.core.teleport;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.server.ServerLifecycleHooks;

public class CosmosTeleportCore {
	
	public static void shiftPlayerToDimension(Player playerIn, CosmosTeleporter teleporterIn, @Nullable Holder<SoundEvent> soundIn, float volume) {
		if (playerIn instanceof ServerPlayer) {
			ServerPlayer server_player = (ServerPlayer) playerIn;
			ResourceKey<Level> dimension_key = teleporterIn.getDimensionKey();
			
			if (dimension_key != null) {
				MinecraftServer Mserver = ServerLifecycleHooks.getCurrentServer();
				ServerLevel server_world = Mserver.getLevel(dimension_key);
				BlockPos target_pos = teleporterIn.getTargetPos();
				
				if (server_world != null) {
					if (target_pos != null) {
						double[] position = teleporterIn.getTargetPosA();
						
						server_player.changeDimension(server_world, teleporterIn);

						if (!teleporterIn.playVanillaSound() && soundIn != null) {
							server_player.connection.send(new ClientboundSoundPacket(soundIn, SoundSource.AMBIENT, position[0], position[1], position[2], volume, 1, 0));
						}
					} else {
						server_player.changeDimension(server_world, teleporterIn);

						if (!teleporterIn.playVanillaSound() && soundIn != null) {
							server_player.connection.send(new ClientboundSoundPacket(soundIn, SoundSource.AMBIENT, server_player.getX(), server_player.getY(), server_player.getZ(), volume, 1, 0));
						}
					}
				}
			}
		}
	}
}