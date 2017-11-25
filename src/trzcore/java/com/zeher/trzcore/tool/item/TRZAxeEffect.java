package com.zeher.trzcore.tool.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;

public class TRZAxeEffect
  extends ItemAxe
{
  public TRZAxeEffect(ToolMaterial par2EnumToolMaterial2, int harvestLvl, CreativeTabs tab)
  {
    super(par2EnumToolMaterial2);
    this.setCreativeTab(tab);
    this.setHarvestLevel("axe", harvestLvl);
  }
  
  public boolean hasEffect(ItemStack itemStack)
  {
	  return true;
  }
}
