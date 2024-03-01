package com.tcn.cosmoslibrary.common.crafting;

import com.tcn.cosmoslibrary.energy.item.CosmosEnergyShield;
import com.tcn.cosmoslibrary.runtime.ObjectManagerCosmos;

import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CosmosShieldDecorationRecipe extends CustomRecipe {
	
   public CosmosShieldDecorationRecipe(ResourceLocation locationIn, CraftingBookCategory categoryIn) {
      super(locationIn, categoryIn);
   }

   @Override
   public boolean matches(CraftingContainer containerIn, Level levelIn) {
      ItemStack itemstack = ItemStack.EMPTY;
      ItemStack itemstack1 = ItemStack.EMPTY;

      for(int i = 0; i < containerIn.getContainerSize(); ++i) {
         ItemStack itemstack2 = containerIn.getItem(i);
         
         if (!itemstack2.isEmpty()) {
            if (itemstack2.getItem() instanceof BannerItem) {
               if (!itemstack1.isEmpty()) {
                  return false;
               }

               itemstack1 = itemstack2;
            } else {
               if (!(itemstack2.getItem() instanceof CosmosEnergyShield)) {
                  return false;
               }

               if (!itemstack.isEmpty()) {
                  return false;
               }

               if (BlockItem.getBlockEntityData(itemstack2) != null) {
                  return false;
               }

               itemstack = itemstack2;
            }
         }
      }

      return !itemstack.isEmpty() && !itemstack1.isEmpty();
   }
   
   @Override
   public ItemStack assemble(CraftingContainer containerIn, RegistryAccess p_267165_) {
      ItemStack itemstack = ItemStack.EMPTY;
      ItemStack itemstack1 = ItemStack.EMPTY;

      for(int i = 0; i < containerIn.getContainerSize(); ++i) {
         ItemStack itemstack2 = containerIn.getItem(i);
         
         if (!itemstack2.isEmpty()) {
            if (itemstack2.getItem() instanceof BannerItem) {
               itemstack = itemstack2;
            } else if (itemstack2.getItem() instanceof CosmosEnergyShield) {
               itemstack1 = itemstack2.copy();
            }
         }
      }

      if (itemstack1.isEmpty()) {
         return itemstack1;
      } else {
         CompoundTag compoundtag = BlockItem.getBlockEntityData(itemstack);
         CompoundTag compoundtag1 = compoundtag == null ? new CompoundTag() : compoundtag.copy();
         compoundtag1.putInt("Base", ((BannerItem)itemstack.getItem()).getColor().getId());
         BlockItem.setBlockEntityData(itemstack1, BlockEntityType.BANNER, compoundtag1);
         return itemstack1;
      }
   }

   @Override
   public boolean canCraftInDimensions(int xIn, int yIn) {
      return xIn * yIn >= 2;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return ObjectManagerCosmos.crafting_special_shielddecoration;
   }

}