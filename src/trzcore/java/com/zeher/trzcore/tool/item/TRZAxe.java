package com.zeher.trzcore.tool.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAxe;

public class TRZAxe
  extends ItemAxe
{
  public TRZAxe(ToolMaterial par2EnumToolMaterial2, int harvestLvl, CreativeTabs tab)
  {
    super(par2EnumToolMaterial2);
    this.setCreativeTab(tab);
    this.setHarvestLevel("axe", harvestLvl);
  }
}
