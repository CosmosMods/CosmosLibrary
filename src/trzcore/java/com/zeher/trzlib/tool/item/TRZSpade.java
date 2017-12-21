package com.zeher.trzlib.tool.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.Item.ToolMaterial;

public class TRZSpade
  extends ItemSpade
{
  public TRZSpade(ToolMaterial par2EnumToolMaterial2, int harvestLvl, CreativeTabs tab)
  {
    super(par2EnumToolMaterial2);
    this.setCreativeTab(tab);
    this.setHarvestLevel("shovel", harvestLvl);
  }
}
