package com.zeher.trzlib.core.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TRZBlockWorld  extends TRZBlock
{
  private Item itemDrop;
  private Item itemID;
  
  public TRZBlockWorld(String name, Item item, CreativeTabs tab, String toolClass, int level, int hardness, int resistance)
  {
    super(name, Material.ROCK, toolClass, level, hardness, resistance, tab);
    this.setHardness(hardness);
    this.setCreativeTab(tab);
    this.itemDrop = item;
    this.itemID = item;
    this.setHarvestLevel(toolClass, level);
  }
  
  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune)
  {
    return this.itemID;
  }
}
