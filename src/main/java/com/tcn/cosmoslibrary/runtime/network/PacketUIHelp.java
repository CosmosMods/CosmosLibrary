package com.tcn.cosmoslibrary.runtime.network;

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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class PacketUIHelp implements ICosmosPacket {
	
	private BlockPos pos;
	private ResourceKey<Level> dimension;
	
	public PacketUIHelp(FriendlyByteBuf buf) {
		this.pos = buf.readBlockPos();
		ResourceLocation location = buf.readResourceLocation();
		this.dimension = ResourceKey.create(Registries.DIMENSION, location);
	}
	
	public PacketUIHelp(CosmosContainerMenuBlockEntity containerIn) {
		this.pos = containerIn.getBlockPos();
		this.dimension = containerIn.getLevel().dimension();
	}
	
	public PacketUIHelp(CosmosContainerRecipeBookBlockEntity<? extends Container> containerIn) {
		this.pos = containerIn.getBlockPos();
		this.dimension = containerIn.getLevel().dimension();
	}
	
	public static void encode(PacketUIHelp packet, FriendlyByteBuf buf) {
		buf.writeBlockPos(packet.pos);
		buf.writeResourceLocation(packet.dimension.location());
	}
	
	public static void handle(final PacketUIHelp packet, Supplier<NetworkEvent.Context> context) {
		NetworkEvent.Context ctx = context.get();
		
		ctx.enqueueWork(() -> {
			MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
			ServerLevel world = server.getLevel(packet.dimension);
			BlockEntity entity = world.getBlockEntity(packet.pos);
			
			if (entity instanceof IBlockEntityUIMode) {
				IBlockEntityUIMode blockEntity = (IBlockEntityUIMode) entity;
			
				blockEntity.cycleUIHelp();
			} else {
				CosmosLibrary.CONSOLE.debugWarn("[Packet Delivery Failure] <uihelp> Block Entity not equal to expected.");
			}
			
		});
		
		ctx.setPacketHandled(true);
	}
}
