package com.zeher.trzcore.tool.item;

import com.zeher.trzcore.api.value.TRZItemPowerValues;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TRZSwordEnergy extends ItemSword {
	public TRZSwordEnergy(ToolMaterial par2EnumToolMaterial2, CreativeTabs tab, int maxDamage, String name) {
		super(par2EnumToolMaterial2);
		this.setCreativeTab(tab);
		this.setMaxDamage(maxDamage);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_) {
		if (this.getDamage(stack) < this.getMaxDamage(stack) - TRZItemPowerValues.getEnergyToolDamage() * 2) {
			stack.damageItem(TRZItemPowerValues.getEnergyToolDamage(), p_77644_3_);
		} else if (this.getDamage(stack) >= this.getMaxDamage(stack) - TRZItemPowerValues.getEnergyToolDamage() * 2) {
			return false;
		}
		return true;
	}

}
