package com.zeher.zeherlib.tool.item;

import java.util.List;

import com.zeher.zeherlib.api.util.TextUtil;
import com.zeher.zeherlib.api.value.ItemPowerValues;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EnergyPickaxe
  extends ItemPickaxe
{
	public static int hrvLvl;

	public static String info;
  public EnergyPickaxe(ToolMaterial par2EnumToolMaterial2, int harvestLvl, CreativeTabs tab,int maxDamage, String info)
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
    	  if(this.getDamage(stack) < this.getMaxDamage(stack) - ItemPowerValues.getEnergyToolDamage()*2){
    	  stack.damageItem(ItemPowerValues.getEnergyToolDamage(), entityLiving);
         }
    	  else if(this.getDamage(stack) >= this.getMaxDamage(stack) - ItemPowerValues.getEnergyToolDamage()*2){
    		  return false;
         }
      }
      return true;
  }
  
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack item, EntityPlayer player, List list, boolean bool) 
	{
		if (!TextUtil.isShiftKeyDown())
	    {
	      list.add(TextUtil.getInfoText("item.orecraft.pickaxe." + info));
	      if (TextUtil.displayShiftForDetail) {
	        list.add(TextUtil.shiftForDetails());
	      }
	      return;
	    }
		if(TextUtil.isShiftKeyDown())
	      {
	    	  list.add(TextUtil.getFlavorText("item.orecraft.pickaxe.powerinfo") + this.getDamage(item) + "/" + this.getMaxDamage(new ItemStack(this)));
			  list.add(TextUtil.getInfoText("item.orecraft.pickaxe." + info));
	      }
		return;
	}
}
