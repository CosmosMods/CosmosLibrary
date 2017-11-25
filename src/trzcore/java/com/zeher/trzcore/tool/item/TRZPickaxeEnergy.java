package com.zeher.trzcore.tool.item;

import java.util.List;

import com.zeher.trzcore.api.TRZTextUtil;
import com.zeher.trzcore.api.value.TRZItemPowerValues;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TRZPickaxeEnergy
  extends ItemPickaxe
{
	public static int hrvLvl;

	public static String info;
  public TRZPickaxeEnergy(ToolMaterial par2EnumToolMaterial2, int harvestLvl, CreativeTabs tab,int maxDamage, String info)
  {
    super(par2EnumToolMaterial2);
    this.setCreativeTab(tab);
    this.hrvLvl = harvestLvl;
    this.info = info;
    this.setHarvestLevel("pickaxe", harvestLvl);
    this.setNoRepair();
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
  
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack item, EntityPlayer player, List list, boolean bool) 
	{
		if (!TRZTextUtil.isShiftKeyDown())
	    {
	      list.add(TRZTextUtil.getInfoText("item.orecraft.pickaxe." + info));
	      if (TRZTextUtil.displayShiftForDetail) {
	        list.add(TRZTextUtil.shiftForDetails());
	      }
	      return;
	    }
		if(TRZTextUtil.isShiftKeyDown())
	      {
	    	  list.add(TRZTextUtil.getFlavorText("item.orecraft.pickaxe.powerinfo") + this.getDamage(item) + "/" + this.getMaxDamage(new ItemStack(this)));
			  list.add(TRZTextUtil.getInfoText("item.orecraft.pickaxe." + info));
	      }
		return;
	}
}
