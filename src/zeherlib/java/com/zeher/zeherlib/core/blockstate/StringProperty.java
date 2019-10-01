package com.zeher.zeherlib.core.blockstate;

import net.minecraftforge.common.property.IUnlistedProperty;

public class StringProperty implements IUnlistedProperty<String> {
	private String name;

	public StringProperty(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isValid(String value) {
		return value != null;
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}

	@Override
	public String valueToString(String value) {
		return value;
	}

}
