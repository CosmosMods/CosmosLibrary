package com.tcn.cosmoslibrary.client.screen.option;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.tcn.cosmoslibrary.common.comp.CosmosColour;

import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public class CosmosOptionPercentageSlider extends Option {
   protected final float steps;
   protected final double minValue;
   protected double maxValue;
   private final Function<Options, Double> getter;
   private final BiConsumer<Options, Double> setter;
private final BiFunction<Options, CosmosOptionPercentageSlider, Component> toString;

   public CosmosOptionPercentageSlider(CosmosColour colour, boolean bold, String caption, double minValueIn, double maxValueIn, float stepSizeIn, Function<Options, Double> getter, BiConsumer<Options, Double> setter, BiFunction<Options, CosmosOptionPercentageSlider, Component> getDisplayString) {
      super(caption);
      this.minValue = minValueIn;
      this.maxValue = maxValueIn;
      this.steps = stepSizeIn;
      this.getter = getter;
      this.setter = setter;
      this.toString = getDisplayString;
   }

   public AbstractWidget createButton(Options p_216586_1_, int p_216586_2_, int p_216586_3_, int p_216586_4_) {
      return new CosmosOptionSlider(p_216586_1_, p_216586_2_, p_216586_3_, p_216586_4_, 20, this, null);
   }

   public double toPct(double p_216726_1_) {
      return Mth.clamp((this.clamp(p_216726_1_) - this.minValue) / (this.maxValue - this.minValue), 0.0D, 1.0D);
   }

   public double toValue(double p_216725_1_) {
      return this.clamp(Mth.lerp(Mth.clamp(p_216725_1_, 0.0D, 1.0D), this.minValue, this.maxValue));
   }

   private double clamp(double p_216731_1_) {
      if (this.steps > 0.0F) {
         p_216731_1_ = (double)(this.steps * (float)Math.round(p_216731_1_ / (double)this.steps));
      }

      return Mth.clamp(p_216731_1_, this.minValue, this.maxValue);
   }

   public double getMinValue() {
      return this.minValue;
   }

   public double getMaxValue() {
      return this.maxValue;
   }

   public void setMaxValue(float p_216728_1_) {
      this.maxValue = (double)p_216728_1_;
   }

   public void set(Options p_216727_1_, double p_216727_2_) {
      this.setter.accept(p_216727_1_, p_216727_2_);
   }

   public double get(Options p_216729_1_) {
      return this.getter.apply(p_216729_1_);
   }

   public Component getMessage(Options p_238334_1_) {
      return (Component) null;//this.toString.apply(p_238334_1_, this);
   }
}