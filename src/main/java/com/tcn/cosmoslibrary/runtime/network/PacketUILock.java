package com.tcn.cosmoslibrary.runtime.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.tcn.cosmoslibrary.CosmosLibrary;
import com.tcn.cosmoslibrary.client.container.CosmosContainerMenuBlockEntity;
import com.tcn.cosmoslibrary.client.container.CosmosContainerRecipeBookBlockEntity;
import com.tcn.cosmoslibrary.common.interfaces.blockentity.IBlockEntityUIMode;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class PacketUILock implements ICosmosPacket {
	
	private BlockPos pos;
	private ResourceKey<Level> dimension;
	private UUID uuid;
	
	public PacketUILock(FriendlyByteBuf buf) {
		this.pos = buf.readBlockPos();
		ResourceLocation location = buf.readResourceLocation();
		this.dimension = ResourceKey.create(Registries.DIMENSION, location);
		this.uuid = buf.readUUID();
	}
	
	public PacketUILock(CosmosContainerMenuBlockEntity containerIn) {
		this.pos = containerIn.getBlockPos();
		this.dimension = containerIn.getLevel().dimension();
		this.uuid = containerIn.getPlayer().getUUID();
	}
	
	public PacketUILock(CosmosContainerRecipeBookBlockEntity<? extends Container> containerIn) {
		this.pos = containerIn.getBlockPos();
		this.dimension = containerIn.getLevel().dimension();
		this.uuid = containerIn.getPlayer().getUUID();
	}
	
	public static void encode(PacketUILock packet, FriendlyByteBuf buf) {
		buf.writeBlockPos(packet.pos);
		buf.writeResourceLocation(packet.dimension.location());
		buf.writeUUID(packet.uuid);
	}
	
	public static void handle(final PacketUILock packet, Supplier<NetworkEvent.Context> context) {
		NetworkEvent.Context ctx = context.get();
		
		ctx.enqueueWork(() -> {
			MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
			ServerLevel world = server.getLevel(packet.dimension);
			BlockEntity entity = world.getBlockEntity(packet.pos);
			
			Player player = world.getPlayerByUUID(packet.uuid);
			
			if (entity instanceof IBlockEntityUIMode) {
				IBlockEntityUIMode blockEntity = (IBlockEntityUIMode) entity;
				
				if (blockEntity.checkIfOwner(player)) {
					blockEntity.cycleUILock();
				}
			} else {
				CosmosLibrary.CONSOLE.debugWarn("[Packet Delivery Failure] <uilock> Block Entity not equal to expected.");
			}
			
		});
		
		ctx.setPacketHandled(true);
	}
}
