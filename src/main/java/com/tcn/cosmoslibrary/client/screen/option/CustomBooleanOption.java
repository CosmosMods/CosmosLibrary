package com.tcn.cosmoslibrary.client.screen.option;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.tcn.cosmoslibrary.common.comp.CosmosColour;
import com.tcn.cosmoslibrary.common.comp.CosmosCompHelper;

import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;

public class CustomBooleanOption extends AbstractOption {
	private final Predicate<GameSettings> getter;
	private final BiConsumer<GameSettings, Boolean> setter;
	@Nullable
	private final ITextComponent tooltipText;
	
	private final IFormattableTextComponent newCaption;
	private final TYPE type;

	public CustomBooleanOption(CosmosColour colour, boolean bold, String caption, TYPE type, Predicate<GameSettings> getter, BiConsumer<GameSettings, Boolean> setter) {
		this(colour, bold, caption, type, (ITextComponent) null, getter, setter);
	}

	public CustomBooleanOption(CosmosColour colour, boolean bold, String caption, TYPE type, @Nullable ITextComponent tooltip, Predicate<GameSettings> getter, BiConsumer<GameSettings, Boolean> setter) {
		super("");
		this.getter = getter;
		this.setter = setter;
		this.tooltipText = tooltip;
		this.newCaption = CosmosCompHelper.locComp(colour, bold, caption, ":");
		this.type = type;
	}

	public void set(GameSettings options, String valueIn) {
		this.set(options, "true".equals(valueIn));
	}

	public void toggle(GameSettings options) {
		this.set(options, !this.get(options));
		options.save();
	}

	private void set(GameSettings options, boolean valueIn) {
		this.setter.accept(options, valueIn);
	}

	public boolean get(GameSettings options) {
		return this.getter.test(options);
	}

	public Widget createButton(GameSettings options, int xIn, int yIn, int width) {
		return new OptionButton(xIn, yIn, width, 20, this, this.getMessage(options), (button) -> {
			this.toggle(options); 
			button.setMessage(this.getMessage(options));
		});
	}

	public ITextComponent getMessage(GameSettings options) {
		return optionStatusMessage(this.getCaption(), this.get(options), this.type);
	}
	
	@Override
	public IFormattableTextComponent getCaption() {
		if (this.newCaption != null) {
			return this.newCaption;
		}
		
		return (IFormattableTextComponent) super.getCaption();
	}

	public static final ITextComponent OPTION_ON = new TranslationTextComponent("options.on");
	public static final ITextComponent OPTION_OFF = new TranslationTextComponent("options.off");

	public static IFormattableTextComponent optionStatusMessage(IFormattableTextComponent caption, boolean valueIn, TYPE type) {
		if (type.equals(TYPE.ON_OFF)) {
			IFormattableTextComponent comp = new TranslationTextComponent(valueIn ? "cosmoslibrary.options.on.composed" : "cosmoslibrary.options.off.composed", caption);
			
			comp.setStyle(Style.EMPTY.withColor(Color.fromRgb(valueIn ? CosmosColour.GREEN.dec() : CosmosColour.RED.dec())).withBold(true));
			return comp;
		} else if (type.equals(TYPE.YES_NO)) {
			IFormattableTextComponent comp = new TranslationTextComponent(valueIn ? "cosmoslibrary.options.yes.composed" : "cosmoslibrary.options.no.composed", caption);
			
			comp.setStyle(Style.EMPTY.withColor(Color.fromRgb(valueIn ? CosmosColour.GREEN.dec() : CosmosColour.RED.dec())).withBold(true));
			return comp;
		}
		
		return (IFormattableTextComponent) null;
	}
	
	public enum TYPE {
		ON_OFF,
		YES_NO
	}
}