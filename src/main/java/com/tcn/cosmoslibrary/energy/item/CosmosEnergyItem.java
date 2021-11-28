package com.tcn.cosmoslibrary.energy.item;

import java.util.List;

import javax.annotation.Nullable;

import com.tcn.cosmoslibrary.client.renderer.item.CosmosEnergyItemItemStackRenderer;
import com.tcn.cosmoslibrary.common.comp.CosmosColour;
import com.tcn.cosmoslibrary.common.comp.CosmosCompHelper;
import com.tcn.cosmoslibrary.common.comp.CosmosCompHelper.Value;
import com.tcn.cosmoslibrary.common.item.CosmosItem;
import com.tcn.cosmoslibrary.common.util.CosmosUtil;
import com.tcn.cosmoslibrary.energy.interfaces.ICosmosEnergyItem;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class CosmosEnergyItem extends CosmosItem implements ICosmosEnergyItem {
	private int maxEnergyStored;
	private int maxExtract;
	private int maxReceive;
	private int maxUse;
	private boolean doesExtract;
	private boolean doesCharge;
	private boolean doesDisplayEnergyInTooltip;
	private boolean doesDisplayEnergyBar;
	
	public CosmosEnergyItem(Item.Properties properties, CosmosEnergyItem.Properties energyProperties) {
		super(properties.setISTER(() -> CosmosEnergyItemItemStackRenderer::new));

		this.maxEnergyStored = energyProperties.maxEnergyStored;
		this.maxExtract = energyProperties.maxExtract;
		this.maxReceive = energyProperties.maxReceive;
		this.maxUse = energyProperties.maxUse;
		this.doesExtract = energyProperties.doesExtract;
		this.doesCharge = energyProperties.doesCharge;
		this.doesDisplayEnergyInTooltip = energyProperties.doesDisplayEnergyInTooltip;
		this.doesDisplayEnergyBar = energyProperties.doesDisplayEnergyBar;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		
		if (stack.hasTag()) {
			CompoundNBT stackTag = stack.getTag();
			tooltip.add(CosmosCompHelper.locComp(CosmosColour.GRAY, false, "cosmoslibrary.tooltip.energy_item.stored").append(CosmosCompHelper.locComp(Value.LIGHT_GRAY + "[ " + Value.RED + CosmosUtil.formatIntegerMillion(stackTag.getInt("energy")) + Value.LIGHT_GRAY + " / " + Value.RED + CosmosUtil.formatIntegerMillion(this.getMaxEnergyStored(stack)) + Value.LIGHT_GRAY + " ]")));
		}
	}
	
	@Override
	public int getMaxEnergyStored(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyItem) ? 0 : ((CosmosEnergyItem)item).maxEnergyStored;
	}

	@Override
	public int getMaxExtract(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyItem) ? 0 : ((CosmosEnergyItem)item).maxExtract;
	}

	@Override
	public int getMaxReceive(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyItem) ? 0 : ((CosmosEnergyItem)item).maxReceive;
	}

	@Override
	public int getMaxUse(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyItem) ? 0 : ((CosmosEnergyItem)item).maxUse;
	}

	@Override
	public boolean doesExtract(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyItem) ? false : ((CosmosEnergyItem)item).doesExtract;
	}

	@Override
	public boolean doesCharge(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyItem) ? false : ((CosmosEnergyItem)item).doesCharge;
	}

	@Override
	public boolean doesDisplayEnergyInTooltip(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyItem) ? false : ((CosmosEnergyItem)item).doesDisplayEnergyInTooltip;
	}

	@Override
	public boolean doesDisplayEnergyBar(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyItem) ? false : ((CosmosEnergyItem)item).doesDisplayEnergyBar;
	}

	@Override
	public boolean canReceiveEnergy(ItemStack stackIn) {
		return this.getEnergy(stackIn) < this.getMaxEnergyStored(stackIn);
	}

	@Override
	public double getScaledEnergy(ItemStack stackIn, int scaleIn) {
		Item item = stackIn.getItem();
		
		if (item instanceof ICosmosEnergyItem) {
			return (double) this.getEnergy(stackIn) * scaleIn / (double) this.getMaxEnergyStored(stackIn);
		}
		
		return 0;
	}

	@Override
	public double getScaledEnergy(ItemStack stackIn, float scaleIn) {
		Item item = stackIn.getItem();
		
		if (item instanceof ICosmosEnergyItem) {
			return (double) this.getEnergy(stackIn) * scaleIn / (double) this.getMaxEnergyStored(stackIn);
		}
		
		return 0;
	}

	@Override
	public int receiveEnergy(ItemStack stackIn, int energy, boolean simulate) {
		if (this.canReceiveEnergy(stackIn)) {
			if(this.doesCharge(stackIn)) {
				int storedReceived = Math.min(this.getMaxEnergyStored(stackIn) - this.getEnergy(stackIn), Math.min(this.getMaxReceive(stackIn), energy));
				
				if (!simulate) {
					this.setEnergy(stackIn, this.getEnergy(stackIn) + storedReceived);
				}
				
				return storedReceived;
			} 
		}
		
		return 0;
	}

	@Override
	public int extractEnergy(ItemStack stackIn, int energy, boolean simulate) {
		if (this.canExtractEnergy(stackIn)) {
			if (this.doesExtract(stackIn)) {
				int storedExtracted = Math.min(this.getEnergy(stackIn), Math.min(this.getMaxExtract(stackIn), energy));
				
				if (!simulate) {
					this.setEnergy(stackIn, this.getEnergy(stackIn) - storedExtracted);
				}
				
				return storedExtracted;
			}
		}
		
		return 0;
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}

	public static class Properties {
		public int maxEnergyStored = 0;
		public int maxExtract = 0;
		public int maxReceive = 0;
		public int maxUse = 0;
		public boolean doesExtract = true;
		public boolean doesCharge = true;
		public boolean doesDisplayEnergyInTooltip = true;
		public boolean doesDisplayEnergyBar = true;
		
		public CosmosEnergyItem.Properties maxEnergyStored(int valueIn) {
			this.maxEnergyStored = valueIn;
			return this;
		}
		
		public CosmosEnergyItem.Properties maxExtract(int valueIn) {
			this.maxExtract = valueIn;
			return this;
		}
		
		public CosmosEnergyItem.Properties maxReceive(int valueIn) {
			this.maxReceive = valueIn;
			return this;
		}
		
		public CosmosEnergyItem.Properties maxUse(int value) {
			this.maxUse = value;
			return this;
		}

		public CosmosEnergyItem.Properties maxIO(int valueIn) {
			this.maxExtract = valueIn;
			this.maxReceive = valueIn;
			return this;
		}
		
		public CosmosEnergyItem.Properties doesExtract(boolean valueIn) {
			this.doesExtract = valueIn;
			return this;
		}
		
		public CosmosEnergyItem.Properties doesCharge(boolean valueIn) {
			this.doesCharge = valueIn;
			return this;
		}

		public CosmosEnergyItem.Properties doesDisplayEnergyInTooltip(boolean valueIn) {
			this.doesDisplayEnergyInTooltip = valueIn;
			return this;
		}

		public CosmosEnergyItem.Properties doesDisplayEnergyBar(boolean valueIn) {
			this.doesDisplayEnergyBar = valueIn;
			return this;
		}
	}
}