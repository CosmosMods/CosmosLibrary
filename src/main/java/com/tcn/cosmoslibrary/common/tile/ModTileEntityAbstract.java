package com.tcn.cosmoslibrary.common.tile;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

public abstract class ModTileEntityAbstract extends TileEntity {

    public ModTileEntityAbstract(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	@Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        load(this.getBlockState(), pkt.getTag());
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT tag = new CompoundNBT();
        save(tag);
        return new SUpdateTileEntityPacket(this.getBlockPos(), 0, tag);
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