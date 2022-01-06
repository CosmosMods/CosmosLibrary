package com.tcn.cosmoslibrary.common.interfaces.blockentity;

import com.tcn.cosmoslibrary.common.enums.EnumUIHelp;
import com.tcn.cosmoslibrary.common.enums.EnumUIMode;

public interface IBlockEntityUIMode {
	
	public EnumUIMode getUIMode();
	
	public void setUIMode(EnumUIMode modeIn);
	
	public void cycleUIMode();
	
	public EnumUIHelp getUIHelp();
	
	public void setUIHelp(EnumUIHelp modeIn);
	
	public void cycleUIHelp();
	
}
