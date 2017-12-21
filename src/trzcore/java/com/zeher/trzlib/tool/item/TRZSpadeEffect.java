package com.zeher.trzlib.tool.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;

public class TRZSpadeEffect
  extends ItemSpade
{
  public TRZSpadeEffect(ToolMaterial par2EnumToolMaterial2, int harvestLvl, CreativeTabs tab)
  {
    super(par2EnumToolMaterial2);
    this.setCreativeTab(tab);
    this.setHarvestLevel("shovel", harvestLvl);
  }
  
  public boolean hasEffect(ItemStack itemStack)
  {
	  return true;
  }
}
