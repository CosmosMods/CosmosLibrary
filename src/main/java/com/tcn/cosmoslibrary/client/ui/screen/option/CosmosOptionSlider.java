package com.tcn.cosmoslibrary.client.ui.screen.option;

import java.util.List;

import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractOptionSliderButton;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CosmosOptionSlider extends AbstractOptionSliderButton {
	private final CosmosOptionPercentageSlider option;
	   private final List<FormattedCharSequence> tooltip;

	public CosmosOptionSlider(Options options, int p_i51129_2_, int p_i51129_3_, int p_i51129_4_, int p_i51129_5_, CosmosOptionPercentageSlider p_i51129_6_, List<FormattedCharSequence> list) {
		super(options, p_i51129_2_, p_i51129_3_, p_i51129_4_, p_i51129_5_, (double) ((float) p_i51129_6_.toPct(p_i51129_6_.get(options))));
		this.option = p_i51129_6_;
		this.tooltip = list;
		this.updateMessage();
	}

	@Override
	protected void applyValue() {
		this.option.set(this.options, this.option.toValue(this.value));
		this.options.save();
	}

	@Override
	protected void updateMessage() {
		this.setMessage(this.option.getMessage(this.options));
	}

	public List<FormattedCharSequence> getTooltip() {
		return this.tooltip;
	}
}