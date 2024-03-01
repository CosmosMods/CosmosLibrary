package com.tcn.cosmoslibrary.common.event;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class PortalEvent extends Event {
	
	private final Entity entity;
	private final BlockPos entityPos;
	private final BlockPos destPos;
	private final ResourceLocation destDimension;
	
	public PortalEvent(Entity entityIn, BlockPos entityPosIn, BlockPos destPosIn,  ResourceLocation destDimensionIn) {
		this.entity = entityIn;
		this.entityPos = entityPosIn;
		this.destPos = destPosIn;
		this.destDimension = destDimensionIn;	
	}

	public Entity getEntity() {
		return entity;
	}

	public BlockPos getEntityPos() {
		return entityPos;
	}

	public BlockPos getDestPos() {
		return destPos;
	}

	public ResourceLocation getDestDimension() {
		return destDimension;
	}

	/**
	 * Fired when an entity enters a Portal.
	 * Cancel the event to stop it happening;
	 *
	 */
	@Cancelable
	public static class PortalTravel extends PortalEvent {
		public PortalTravel(Entity entityIn, BlockPos entityPosIn, BlockPos destPosIn, ResourceLocation destDimensionIn) {
			super(entityIn, entityPosIn, destPosIn, destDimensionIn);
		}
	}
	
	/**
	 * Fired when an Player links a Container.
	 * Cancel the event to stop it happening;
	 *
	 */
	@Cancelable
	public static class LinkContainer extends PortalEvent {
		public LinkContainer(Entity entityIn, BlockPos entityPosIn, BlockPos destPosIn, ResourceLocation destDimensionIn) {
			super(entityIn, entityPosIn, destPosIn, destDimensionIn);
		}
	}
}