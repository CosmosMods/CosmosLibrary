package com.tcn.cosmoslibrary.client.ui.screen.option;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;

public class CosmosOptionBoolean extends Option {
	
	private final Predicate<Options> getter;
	private final BiConsumer<Options, Boolean> setter;
	@Nullable
	private final BaseComponent tooltipText;
	
	private final BaseComponent newCaption;
	private final TYPE type;

	public CosmosOptionBoolean(ComponentColour colour, boolean bold, String caption, TYPE type, Predicate<Options> getter, BiConsumer<Options, Boolean> setter) {
		this(colour, bold, caption, type, (BaseComponent) null, getter, setter);
	}

	public CosmosOptionBoolean(ComponentColour colour, boolean bold, String caption, TYPE type, @Nullable BaseComponent tooltip, Predicate<Options> getter, BiConsumer<Options, Boolean> setter) {
		super("");
		this.getter = getter;
		this.setter = setter;
		this.tooltipText = tooltip;
		this.newCaption = ComponentHelper.locComp(colour, bold, caption, ":");
		this.type = type;
	}

	public void set(Options options, String valueIn) {
		this.set(options, "true".equals(valueIn));
	}

	public void toggle(Options options) {
		this.set(options, !this.get(options));
		options.save();
	}

	private void set(Options options, boolean valueIn) {
		this.setter.accept(options, valueIn);
	}

	public boolean get(Options options) {
		return this.getter.test(options);
	}

	@Override
	public Button createButton(Options options, int xIn, int yIn, int width) {
		return new Button(xIn, yIn, width, 20, this.getMessage(options), (button) -> {
			this.toggle(options);
			button.setMessage(this.getMessage(options));
		});
	}

	public Component getMessage(Options options) {
		return optionStatusMessage(this.getCaption(), this.get(options), this.type);
	}
	
	@Override
	public BaseComponent getCaption() {
		if (this.newCaption != null) {
			return this.newCaption;
		}
		
		return (BaseComponent) super.getCaption();
	}

	public static final BaseComponent OPTION_ON = new TranslatableComponent("options.on");
	public static final BaseComponent OPTION_OFF = new TranslatableComponent("options.off");

	public static BaseComponent optionStatusMessage(BaseComponent caption, boolean valueIn, TYPE type) {
		if (type.equals(TYPE.ON_OFF)) {
			TranslatableComponent comp = new TranslatableComponent(valueIn ? "cosmoslibrary.options.on.composed" : "cosmoslibrary.options.off.composed", caption);
			
			comp.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(valueIn ? ComponentColour.GREEN.dec() : ComponentColour.RED.dec())).withBold(true));
			return comp;
		} else if (type.equals(TYPE.YES_NO)) {
			TranslatableComponent comp = new TranslatableComponent(valueIn ? "cosmoslibrary.options.yes.composed" : "cosmoslibrary.options.no.composed", caption);
			
			comp.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(valueIn ? ComponentColour.GREEN.dec() : ComponentColour.RED.dec())).withBold(true));
			return comp;
		}
		
		return (TranslatableComponent) null;
	}
	
	public enum TYPE {
		ON_OFF,
		YES_NO
	}
}