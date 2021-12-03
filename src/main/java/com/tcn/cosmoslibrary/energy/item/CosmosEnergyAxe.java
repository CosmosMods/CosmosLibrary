package com.tcn.cosmoslibrary.energy.item;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.tcn.cosmoslibrary.client.renderer.item.CosmosBEWLR;
import com.tcn.cosmoslibrary.common.comp.CosmosColour;
import com.tcn.cosmoslibrary.common.comp.CosmosCompHelper;
import com.tcn.cosmoslibrary.common.comp.CosmosCompHelper.Value;
import com.tcn.cosmoslibrary.common.util.CosmosUtil;
import com.tcn.cosmoslibrary.energy.interfaces.ICosmosEnergyItem;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.ToolActions;

public class CosmosEnergyAxe extends AxeItem implements ICosmosEnergyItem {

	private int maxEnergyStored;
	private int maxExtract;
	private int maxReceive;
	private int maxUse;
	private boolean doesExtract;
	private boolean doesCharge;
	private boolean doesDisplayEnergyInTooltip;
	private boolean doesDisplayEnergyBar;

	public CosmosEnergyAxe(Tier itemTier, int attackDamageIn, float attackSpeedIn, Properties builderIn, CosmosEnergyItem.Properties energyProperties) {
		super(itemTier, attackDamageIn, attackSpeedIn, builderIn);
		
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
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new IItemRenderProperties() {
			@Override
			public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
				return CosmosBEWLR.INSTANCE;
			}
		});
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		if (stack.hasTag()) {
			CompoundTag stackTag = stack.getTag();
			tooltip.add(CosmosCompHelper.locComp(CosmosColour.GRAY, false, "cosmoslibrary.tooltip.energy_item.stored").append(CosmosCompHelper.locComp(Value.LIGHT_GRAY + "[ " + Value.RED + CosmosUtil.formatIntegerMillion(stackTag.getInt("energy")) + Value.LIGHT_GRAY + " / " + Value.RED + CosmosUtil.formatIntegerMillion(this.getMaxEnergyStored(stack)) + Value.LIGHT_GRAY + " ]")));
		}
	}

	@Override
	public boolean canAttackBlock(BlockState stateIn, Level worldIn, BlockPos posIn, Player playerEntity) {
		ItemStack heldStack = playerEntity.getInventory().getSelected();
		
		if (heldStack.getItem() instanceof CosmosEnergyAxe) {
			if (this.hasEnergy(heldStack)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean hurtEnemy(ItemStack stackIn, LivingEntity target, LivingEntity attacker) {
		if (this.hasEnergy(stackIn)) {
			this.extractEnergy(stackIn, (this.getMaxUse(stackIn) / 2), false);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stackIn, Player player, Entity entity) {
		if (this.hasEnergy(stackIn)) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean onEntitySwing(ItemStack stackIn, LivingEntity entity) {
		if (this.hasEnergy(stackIn)) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}

	@Override
	public boolean mineBlock(ItemStack stackIn, Level worldIn, BlockState blockStateIn, BlockPos posIn, LivingEntity entityLiving) {
		if (this.hasEnergy(stackIn)) {
			this.extractEnergy(stackIn, this.getMaxUse(stackIn), false);
			return true;
		}
		
		return false;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		Player player = context.getPlayer();
		BlockState blockstate = level.getBlockState(blockpos);
		Optional<BlockState> optional = Optional.ofNullable(blockstate.getToolModifiedState(level, blockpos, player, context.getItemInHand(), ToolActions.AXE_STRIP));
		Optional<BlockState> optional1 = Optional.ofNullable(blockstate.getToolModifiedState(level, blockpos, player, context.getItemInHand(), ToolActions.AXE_SCRAPE));
		Optional<BlockState> optional2 = Optional.ofNullable(blockstate.getToolModifiedState(level, blockpos, player, context.getItemInHand(), ToolActions.AXE_WAX_OFF));
		ItemStack itemstack = context.getItemInHand();
		Optional<BlockState> optional3 = Optional.empty();
		ItemStack selected = player.getInventory().getSelected();
		
		if (optional.isPresent()) {
			level.playSound(player, blockpos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
			optional3 = optional;
		} else if (optional1.isPresent()) {
			level.playSound(player, blockpos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
			level.levelEvent(player, 3005, blockpos, 0);
			optional3 = optional1;
		} else if (optional2.isPresent()) {
			level.playSound(player, blockpos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
			level.levelEvent(player, 3004, blockpos, 0);
			optional3 = optional2;
		}

		if (optional3.isPresent()) {
			if (player instanceof ServerPlayer) {
				CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockpos, itemstack);
			}

			level.setBlock(blockpos, optional3.get(), 11);
			this.extractEnergy(selected, this.getMaxUse(selected), false);
			
			return InteractionResult.sidedSuccess(level.isClientSide);
		} else {
			return InteractionResult.PASS;
		}
	}

	@Override
	public int getMaxEnergyStored(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyAxe) ? 0 : ((CosmosEnergyAxe)item).maxEnergyStored;
	}

	@Override
	public int getMaxExtract(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyAxe) ? 0 : ((CosmosEnergyAxe)item).maxExtract;
	}

	@Override
	public int getMaxReceive(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyAxe) ? 0 : ((CosmosEnergyAxe)item).maxReceive;
	}

	@Override
	public int getMaxUse(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyAxe) ? 0 : ((CosmosEnergyAxe)item).maxUse;
	}

	@Override
	public boolean doesExtract(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyAxe) ? false : ((CosmosEnergyAxe)item).doesExtract;
	}

	@Override
	public boolean doesCharge(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyAxe) ? false : ((CosmosEnergyAxe)item).doesCharge;
	}

	@Override
	public boolean doesDisplayEnergyInTooltip(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyAxe) ? false : ((CosmosEnergyAxe)item).doesDisplayEnergyInTooltip;
	}

	@Override
	public boolean doesDisplayEnergyBar(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyAxe) ? false : ((CosmosEnergyAxe)item).doesDisplayEnergyBar;
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
	
}