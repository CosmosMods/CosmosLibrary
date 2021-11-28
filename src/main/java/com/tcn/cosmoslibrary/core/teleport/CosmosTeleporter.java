package com.tcn.cosmoslibrary.core.teleport;

import java.util.function.Function;

import com.tcn.cosmoslibrary.common.math.CosmosMathUtil;

import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

public class CosmosTeleporter implements ITeleporter {

	private RegistryKey<World> dimension_key;
	private BlockPos target_pos;
	private float target_yaw;
	private float target_pitch;
	private boolean playVanillaSound;
	private boolean sendMessage;
	private boolean safeSpawn;

	public CosmosTeleporter(RegistryKey<World> dimensionKeyIn, BlockPos targetPosIn, float targetYawIn, float targetPitchIn, boolean playVanillaSoundIn, boolean sendMessageIn, boolean safeSpawnIn) {
		this.dimension_key = dimensionKeyIn;
		this.target_pos = targetPosIn;
		this.target_yaw = targetYawIn;
		this.target_pitch = targetPitchIn;
		this.playVanillaSound = playVanillaSoundIn;
		this.sendMessage = sendMessageIn;
		this.safeSpawn = safeSpawnIn;
	}
	
	public BlockPos getTargetPos() {
		return this.target_pos;
	}
	
	public double[] getTargetPosA () {
		return new double[] { this.target_pos.getX(), this.target_pos.getY(), this.target_pos.getZ() };
	}

	public float getTargetYaw() {
		return this.target_yaw;
	}

	public float getTargetPitch() {
		return this.target_pitch;
	}
	
	public float[] getTargetRotation() {
		return new float[] { this.target_yaw, this.target_pitch };
	}

	public RegistryKey<World> getDimensionKey() {
		return this.dimension_key;
	}

	public static CosmosTeleporter createTeleporter(RegistryKey<World> dimensionKeyIn, BlockPos targetPosIn, float targetYawIn, float targetPitchIn, boolean playVanillaSoundIn, boolean sendMessageIn, boolean safeSpawnIn) {
		return new CosmosTeleporter(dimensionKeyIn, targetPosIn, targetYawIn, targetPitchIn, playVanillaSoundIn, sendMessageIn, safeSpawnIn);
	}
	
	public boolean playVanillaSound() {
		return this.playVanillaSound;
	}

	public boolean getSendMessage() {
		return this.sendMessage;
	}

	@Override
	public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
		return repositionEntity.apply(false);
    }
	
	@Override
    public boolean playTeleportSound(ServerPlayerEntity player, ServerWorld sourceWorld, ServerWorld destWorld) {
    	return this.playVanillaSound;
    }
	
    @Override
    public PortalInfo getPortalInfo(Entity entity, ServerWorld destWorld, Function<ServerWorld, PortalInfo> defaultPortalInfo) {
    	if (this.safeSpawn) {
    		EnumSafeTeleport spawnLocation = EnumSafeTeleport.getValidTeleportLocation(destWorld, this.target_pos);
    		
    		if (spawnLocation != EnumSafeTeleport.UNKNOWN) {
    			BlockPos resultPos = spawnLocation.toBlockPos();
    			BlockPos combinedPos = CosmosMathUtil.addBlockPos(this.target_pos, resultPos);
    			
    			return new PortalInfo(new Vector3d(combinedPos.getX() + 0.5F, combinedPos.getY(), combinedPos.getZ() + 0.5F), Vector3d.ZERO, this.getTargetYaw(), this.getTargetPitch());
    		}
    	}
    	
    	return new PortalInfo(new Vector3d(target_pos.getX() + 0.5F, target_pos.getY(), target_pos.getZ() + 0.5F), Vector3d.ZERO, this.getTargetYaw(), this.getTargetPitch());
    }
}