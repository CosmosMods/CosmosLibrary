package com.zeher.trzcore.storage.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.*;

import java.util.List;

import com.zeher.trzcore.api.TRZTextUtil;

public class TRZItemStorage extends Item {
	
	public static int maxPower;
	
	public TRZItemStorage(String name, int maxPower, CreativeTabs tab){
		super();
		this.maxPower = maxPower;
		this.setMaxDamage(maxPower);
		this.setMaxStackSize(1);
		this.setCreativeTab(tab);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
	}
}
