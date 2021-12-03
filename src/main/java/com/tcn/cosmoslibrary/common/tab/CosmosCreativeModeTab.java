package com.tcn.cosmoslibrary.common.tab;

import javax.annotation.Nonnull;

import com.google.common.base.Supplier;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CosmosCreativeModeTab extends CreativeModeTab {

	@Nonnull
	private final Supplier<ItemStack> iconSupplier;

	public CosmosCreativeModeTab(@Nonnull final String name, @Nonnull final Supplier<ItemStack> iconSupplier) {
		super(name);
		this.iconSupplier = iconSupplier;
	}
	
	@Override
	public ItemStack makeIcon() {
		return iconSupplier.get();
	}
}