package com.tcn.cosmoslibrary.energy.item;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.tcn.cosmoslibrary.common.item.CosmosArmourItemColourable;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper.Value;
import com.tcn.cosmoslibrary.common.util.CosmosUtil;
import com.tcn.cosmoslibrary.energy.interfaces.ICosmosEnergyItem;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class CosmosEnergyArmourItemColourable extends CosmosArmourItemColourable implements ICosmosEnergyItem {
	private int maxEnergyStored;
	private int maxExtract;
	private int maxReceive;
	private int maxUse;
	private boolean doesExtract;
	private boolean doesCharge;
	private boolean doesDisplayEnergyInTooltip;
	private ComponentColour barColour;
	
	private boolean isEnderMask;

	public CosmosEnergyArmourItemColourable(ArmorMaterial materialIn, EquipmentSlot slot, boolean isEnderMaskIn, Item.Properties builderIn, CosmosEnergyItem.Properties energyProperties) {
		super(materialIn, slot, builderIn);
		
		this.maxEnergyStored = energyProperties.maxEnergyStored;
		this.maxExtract = energyProperties.maxExtract;
		this.maxReceive = energyProperties.maxReceive;
		this.maxUse = energyProperties.maxUse;
		this.doesExtract = energyProperties.doesExtract;
		this.doesCharge = energyProperties.doesCharge;
		this.doesDisplayEnergyInTooltip = energyProperties.doesDisplayEnergyInTooltip;
		this.barColour = energyProperties.barColour;
		
		this.isEnderMask = isEnderMaskIn;
		
		builderIn.setNoRepair();
	}
	
	@Override
	public boolean isEnderMask(ItemStack stack, Player player, EnderMan endermanEntity) {
		return this.isEnderMask;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		
		if (stack.hasTag()) {
			CompoundTag stackTag = stack.getTag();
			tooltip.add(ComponentHelper.style(ComponentColour.GRAY, "cosmoslibrary.tooltip.energy_item.stored").append(ComponentHelper.comp(Value.LIGHT_GRAY + "[ " + Value.RED + CosmosUtil.formatIntegerMillion(stackTag.getInt("energy")) + Value.LIGHT_GRAY + " / " + Value.RED + CosmosUtil.formatIntegerMillion(this.getMaxEnergyStored(stack)) + Value.LIGHT_GRAY + " ]")));
		}
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		EquipmentSlot equipmentslottype = Mob.getEquipmentSlotForItem(itemstack);
		ItemStack itemstack1 = playerIn.getItemBySlot(equipmentslottype);

		if (itemstack1.isEmpty()) {
			playerIn.setItemSlot(equipmentslottype, itemstack.copy());
			if (!levelIn.isClientSide()) {
				playerIn.awardStat(Stats.ITEM_USED.get(this));
			}
			itemstack.setCount(0);
			return InteractionResultHolder.sidedSuccess(itemstack, levelIn.isClientSide());
		} else {
			return InteractionResultHolder.fail(itemstack);
		}
	}
	
	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stackIn, int amount, T entity, Consumer<T> onBroken) {
		if (this.getDamage(stackIn) < this.getMaxDamage(stackIn)) {
			this.setDamage(stackIn, 0);
		}
		
		if (entity instanceof Player) {
			if (this.hasEnergy(stackIn)) {
				this.extractEnergy(stackIn, this.getMaxUse(stackIn), false);
			}
		}
		
        return 0;
    }

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slotIn, ItemStack stackIn) {
		if (!this.hasEnergy(stackIn)) {
			return ImmutableMultimap.of();
		} else {
			return this.getDefaultAttributeModifiers(slotIn);
		}
	}
	
	@Override
	public int getMaxEnergyStored(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyArmourItemColourable) ? 0 : ((CosmosEnergyArmourItemColourable)item).maxEnergyStored;
	}

	@Override
	public int getMaxExtract(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyArmourItemColourable) ? 0 : ((CosmosEnergyArmourItemColourable)item).maxExtract;
	}

	@Override
	public int getMaxReceive(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyArmourItemColourable) ? 0 : ((CosmosEnergyArmourItemColourable)item).maxReceive;
	}

	@Override
	public int getMaxUse(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyArmourItemColourable) ? 0 : ((CosmosEnergyArmourItemColourable)item).maxUse;
	}

	@Override
	public boolean doesExtract(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyArmourItemColourable) ? false : ((CosmosEnergyArmourItemColourable)item).doesExtract;
	}

	@Override
	public boolean doesCharge(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyArmourItemColourable) ? false : ((CosmosEnergyArmourItemColourable)item).doesCharge;
	}

	@Override
	public boolean doesDisplayEnergyInTooltip(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyArmourItemColourable) ? false : ((CosmosEnergyArmourItemColourable)item).doesDisplayEnergyInTooltip;
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
	public boolean isBarVisible(ItemStack stackIn) {
		return true;
	}
	
	@Override
	public int getBarColor(ItemStack stackIn) {
		return this.barColour.dec();
	}
	
	@Override
	public int getBarWidth(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof ICosmosEnergyItem) ? 0 : Mth.clamp(Math.round((float) ((ICosmosEnergyItem) item).getScaledEnergy(stackIn, 13)), 0, 13);
	}
}