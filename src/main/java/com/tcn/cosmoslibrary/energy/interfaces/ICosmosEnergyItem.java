package com.tcn.cosmoslibrary.energy.interfaces;

import net.minecraft.item.ItemStack;

/**
 * An interface that allows the addition of Energy to Items.
 * 
 * @author TheCosmicNebula
 *
 */
public interface ICosmosEnergyItem {

	int maxEnergyStored = 0;
	int maxExtract = 0;
	int maxReceive = 0;
	int maxUse = 0;
	boolean doesExtract = true;
	boolean doesCharge = true;
	boolean doesDisplayEnergyInTooltip = true;
	boolean doesDisplayEnergyBar = true;

	public int getMaxEnergyStored(ItemStack stackIn);
	public int getMaxExtract(ItemStack stackIn);
	public int getMaxReceive(ItemStack stackIn); 
	public int getMaxUse(ItemStack stackIn);
	public boolean doesExtract(ItemStack stackIn);
	public boolean doesCharge(ItemStack stackIn);
	public boolean doesDisplayEnergyInTooltip(ItemStack stackIn);
	public boolean doesDisplayEnergyBar(ItemStack stackIn);
	
	default boolean hasEnergy(ItemStack stackIn) {
		return this.getEnergy(stackIn) > 0;
	}

	default int getEnergy(ItemStack stackIn) {
		return !stackIn.hasTag() ? 0 : stackIn.getTag().getInt("energy");
	}
	
	default int setEnergy(ItemStack stackIn, int energy) {
		stackIn.getOrCreateTag().putInt("energy", Math.max(0, energy));
		return energy;
	}
	
	public boolean canReceiveEnergy(ItemStack stackIn);

	default boolean canExtractEnergy(ItemStack stackIn) {
		return this.getEnergy(stackIn) > 0;
	}

	public double getScaledEnergy(ItemStack stackIn, int scaleIn);
	public double getScaledEnergy(ItemStack stackIn, float scaleIn);
	
	public int receiveEnergy(ItemStack stackIn, int energy, boolean simulate);
	public int extractEnergy(ItemStack stackIn, int energy, boolean simulate);

}