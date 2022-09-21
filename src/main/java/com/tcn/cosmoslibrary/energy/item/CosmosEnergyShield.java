package com.tcn.cosmoslibrary.energy.item;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.tcn.cosmoslibrary.client.renderer.item.CosmosShieldBEWLR;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper.Value;
import com.tcn.cosmoslibrary.common.util.CosmosUtil;
import com.tcn.cosmoslibrary.energy.interfaces.ICosmosEnergyItem;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

@SuppressWarnings("deprecation")
public class CosmosEnergyShield extends ShieldItem implements ICosmosEnergyItem {
	public static final int EFFECTIVE_BLOCK_DELAY = 5;
	public static final float MINIMUM_DURABILITY_DAMAGE = 3.0F;
	public static final String TAG_BASE_COLOR = "Base";
	
	private int maxEnergyStored;
	private int maxExtract;
	private int maxReceive;
	private int maxUse;
	private boolean doesExtract;
	private boolean doesCharge;
	private boolean doesDisplayEnergyInTooltip;
	private ComponentColour barColour;
	
	private ResourceLocation normal, noPattern;
	
	public CosmosEnergyShield(Item.Properties properties, CosmosEnergyItem.Properties energyProperties, ResourceLocation normalIn, ResourceLocation noPatternIn) {
		super(properties);
		DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);

		this.maxEnergyStored = energyProperties.maxEnergyStored;
		this.maxExtract = energyProperties.maxExtract;
		this.maxReceive = energyProperties.maxReceive;
		this.maxUse = energyProperties.maxUse;
		this.doesExtract = energyProperties.doesExtract;
		this.doesCharge = energyProperties.doesCharge;
		this.doesDisplayEnergyInTooltip = energyProperties.doesDisplayEnergyInTooltip;
		this.barColour = energyProperties.barColour;
		
		this.normal = normalIn;
		this.noPattern = noPatternIn;
	}
	
	@Override
	public int getEnchantmentValue() {
		return 1;
	}
	
	@Override
	public String getDescriptionId(ItemStack stackIn) {
		return BlockItem.getBlockEntityData(stackIn) != null ? this.getDescriptionId() + "." + getColor(stackIn).getName() : super.getDescriptionId(stackIn);
	}

	@Override
	public void appendHoverText(ItemStack stackIn, @Nullable Level levelIn, List<Component> tooltipIn, TooltipFlag flagIn) {
		BannerItem.appendHoverTextFromBannerBlockEntityTag(stackIn, tooltipIn);

		if (stackIn.hasTag()) {
			CompoundTag stackTag = stackIn.getTag();
			tooltipIn.add(ComponentHelper.style(ComponentColour.GRAY, "cosmoslibrary.tooltip.energy_item.stored").append(ComponentHelper.comp(Value.LIGHT_GRAY + "[ " + Value.RED + CosmosUtil.formatIntegerMillion(stackTag.getInt("energy")) + Value.LIGHT_GRAY + " / " + Value.RED + CosmosUtil.formatIntegerMillion(this.getMaxEnergyStored(stackIn)) + Value.LIGHT_GRAY + " ]")));
		}
	}
	
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return new CosmosShieldBEWLR(CosmosEnergyShield.this.normal, CosmosEnergyShield.this.noPattern);
			}
		});
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stackIn) {
		return UseAnim.BLOCK;
	}

	@Override
	public int getUseDuration(ItemStack stackIn) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);

		if (this.hasEnergy(itemstack)) {
			playerIn.startUsingItem(handIn);
			return InteractionResultHolder.consume(itemstack);
		} else {
			return InteractionResultHolder.fail(itemstack);
		}
	}

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stackIn, int amount, T entity, Consumer<T> onBroken) {
		if (this.getDamage(stackIn) < this.getMaxDamage(stackIn)) {
			this.setDamage(stackIn, 0);
		}
		
		if (this.hasEnergy(stackIn)) {
			this.extractEnergy(stackIn, this.getMaxUse(stackIn), false);
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
	public boolean isValidRepairItem(ItemStack stackIn, ItemStack repairStackIn) {
		return false;
	}

	public static DyeColor getColor(ItemStack stackIn) {
		CompoundTag compoundtag = BlockItem.getBlockEntityData(stackIn);
		return compoundtag != null ? DyeColor.byId(compoundtag.getInt("Base")) : DyeColor.WHITE;
	}

	@Override
	public boolean canPerformAction(ItemStack stackIn, ToolAction toolAction) {
		if (this.hasEnergy(stackIn)) {
			return ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
		} else {
			return false;
		}
	}

	@Override
	public int getMaxEnergyStored(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyShield) ? 0 : ((CosmosEnergyShield)item).maxEnergyStored;
	}
	
	@Override
	public int getMaxExtract(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyShield) ? 0 : ((CosmosEnergyShield)item).maxExtract;
	}
	
	@Override
	public int getMaxReceive(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyShield) ? 0 : ((CosmosEnergyShield)item).maxReceive;
	}

	@Override
	public int getMaxUse(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyShield) ? 0 : ((CosmosEnergyShield)item).maxUse;
	}

	@Override
	public boolean doesExtract(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyShield) ? false : ((CosmosEnergyShield)item).doesExtract;
	}

	@Override
	public boolean doesCharge(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyShield) ? false : ((CosmosEnergyShield)item).doesCharge;
	}

	@Override
	public boolean doesDisplayEnergyInTooltip(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyShield) ? false : ((CosmosEnergyShield)item).doesDisplayEnergyInTooltip;
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