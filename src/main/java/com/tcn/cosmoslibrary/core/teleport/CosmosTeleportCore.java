package com.tcn.cosmoslibrary.core.teleport;

import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class CosmosTeleportCore {
	
	public static void shiftPlayerToDimension(PlayerEntity playerIn, CosmosTeleporter teleporterIn, @Nullable SoundEvent soundIn, float volume) {
		if (playerIn instanceof ServerPlayerEntity) {
			ServerPlayerEntity server_player = (ServerPlayerEntity) playerIn;
			RegistryKey<World> dimension_key = teleporterIn.getDimensionKey();
			
			if (dimension_key != null) {
				MinecraftServer Mserver = ServerLifecycleHooks.getCurrentServer();
				ServerWorld server_world = Mserver.getLevel(dimension_key);
				BlockPos target_pos = teleporterIn.getTargetPos();
				
				if (server_world != null) {
					if (target_pos != null) {
						double[] position = teleporterIn.getTargetPosA();
						
						server_player.changeDimension(server_world, teleporterIn);

						if (!teleporterIn.playVanillaSound() && soundIn != null) {
							server_player.connection.send(new SPlaySoundEffectPacket(soundIn, SoundCategory.AMBIENT, position[0], position[1], position[2], volume, 1));
						}
					} else {
						server_player.changeDimension(server_world, teleporterIn);

						if (!teleporterIn.playVanillaSound() && soundIn != null) {
							server_player.connection.send(new SPlaySoundEffectPacket(soundIn, SoundCategory.AMBIENT, server_player.getX(), server_player.getY(), server_player.getZ(), volume, 1));
						}
					}
				}
			}
		}
	}
}