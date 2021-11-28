package com.tcn.cosmoslibrary.common.item;

import java.util.List;

import javax.annotation.Nullable;

import com.tcn.cosmoslibrary.common.comp.CosmosCompHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Basic ItemBlock for giving a Block a description.
 */
public class CosmosItemInfo extends Item {
	
	public String info;
	public String shift_desc_one;
	public String shift_desc_two;

	public CosmosItemInfo(Item.Properties properties, String info, String shift_desc_one, String shift_desc_two) {
		super(properties);
		
		this.info = info;
		this.shift_desc_one = shift_desc_one;
		this.shift_desc_two = shift_desc_two;
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		
		if (!shift_desc_one.isEmpty() && !shift_desc_two.isEmpty()) {
			if (!CosmosCompHelper.isShiftKeyDown(Minecraft.getInstance())) {
				tooltip.add(CosmosCompHelper.getTooltipInfo(this.info));
				
				if (CosmosCompHelper.displayShiftForDetail) {
					tooltip.add(CosmosCompHelper.shiftForMoreDetails());
				}
			} else {
				tooltip.add(CosmosCompHelper.getTooltipOne(shift_desc_one));
				tooltip.add(CosmosCompHelper.getTooltipTwo(shift_desc_two));
				
				tooltip.add(CosmosCompHelper.shiftForLessDetails());
			}
		} else {
			tooltip.add(CosmosCompHelper.getTooltipInfo(this.info));
		}
	}
}