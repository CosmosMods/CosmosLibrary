package com.tcn.cosmoslibrary.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ACosmosBlockEntity extends BlockEntity {

    public ACosmosBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos posIn, BlockState stateIn) {
		super(tileEntityTypeIn, posIn, stateIn);
	}

	@Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void fireEvent(int id, int process) {
        level.blockEvent(this.getBlockPos(), this.getBlockState().getBlock(), id, process);
    }

    public BlockPos getCoordSet() {
        return this.getBlockPos();
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + getCoordSet();
    }
}