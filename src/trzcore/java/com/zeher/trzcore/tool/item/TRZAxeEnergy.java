package com.zeher.trzcore.tool.item;

import com.zeher.trzcore.api.value.TRZItemPowerValues;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class TRZAxeEnergy
  extends ItemAxe
{
  public TRZAxeEnergy(ToolMaterial par2EnumToolMaterial2, int harvestLvl, CreativeTabs tab, int maxDamage)
  {
    super(par2EnumToolMaterial2);
    this.setCreativeTab(tab);
    this.setHarvestLevel("axe", harvestLvl);
    this.setMaxDamage(maxDamage);
  }
  
  public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
  {
      if ((double)state.getBlockHardness(worldIn, pos) != 0.0D)
      {
    	  if(this.getDamage(stack) < this.getMaxDamage(stack) - TRZItemPowerValues.getEnergyToolDamage()*2){
    	  stack.damageItem(TRZItemPowerValues.getEnergyToolDamage(), entityLiving);
         }
    	  else if(this.getDamage(stack) >= this.getMaxDamage(stack) - TRZItemPowerValues.getEnergyToolDamage()*2){
    		  return false;
         }
      }
      return true;
  }
}
