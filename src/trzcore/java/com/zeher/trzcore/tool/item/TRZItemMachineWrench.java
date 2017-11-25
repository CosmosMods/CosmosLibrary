package com.zeher.trzcore.tool.item;

import com.zeher.trzcore.core.block.*;
import com.zeher.trzcore.core.item.TRZItemBase;
import com.zeher.trzcore.storage.TRZBlockCapacitor;
import com.zeher.trzcore.transfer.TRZBlockEnergyPipe;
import com.zeher.trzcore.transfer.TRZBlockFluidPipe;
import com.zeher.trzcore.transfer.TRZBlockItemPipe;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TRZItemMachineWrench 
	extends TRZItemBase
	implements TRZIToolWrenchAdvanced{
	
	private ItemStack emptyItem = null;
	private static int maxDamage = 200;
	
	public TRZItemMachineWrench(String name, CreativeTabs tab)
	{
		super(name, tab);
		this.setMaxStackSize(1);
		this.setMaxDamage(maxDamage);
		this.setNoRepair();
	}
	
	@Override
	   public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand){
		IBlockState block = world.getBlockState(pos);
		if(block != null)
		{
			
		}
		return EnumActionResult.FAIL;
	}
	
	public boolean doesSneakBypassUse(ItemStack stack, net.minecraft.world.IBlockAccess world, BlockPos pos, EntityPlayer player){
		return true;
	}

	@Override
	public boolean isActive(ItemStack paramstack) {
		return true;
	}
	
	@Override
	public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player)
    {
		if(world.getBlockState(pos).getBlock() instanceof TRZBlockEnergyPipe){
			world.getBlockState(pos).getBlock().onBlockClicked(world, pos, player);
		}
		if(world.getBlockState(pos).getBlock() instanceof TRZBlockFluidPipe){
			world.getBlockState(pos).getBlock().onBlockClicked(world, pos, player);
		}
		if(world.getBlockState(pos).getBlock() instanceof TRZBlockItemPipe){
			world.getBlockState(pos).getBlock().onBlockClicked(world, pos, player);
		}
		if(world.getBlockState(pos).getBlock() instanceof TRZBlockCapacitor){
			world.getBlockState(pos).getBlock().onBlockClicked(world, pos, player);
		}
        return false;
    }
	
	@Override
	public boolean hasContainerItem() {
		return true;
	}
	
	public void setEmptyItem(ItemStack ei) {
		this.emptyItem = ei;
	}
	
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack) {
		return false;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		int dmg = stack.getItemDamage();
		if (dmg == maxDamage) {
			return new ItemStack(stack.getItem(), 0, maxDamage);
		}
		ItemStack tr = copyStack(stack, 1);
		tr.setItemDamage(dmg + 1);
		return tr;
	}
	
	public static ItemStack copyStack(ItemStack stack, int n) {
		return new ItemStack(stack.getItem(), n, stack.getItemDamage());
	}
}
