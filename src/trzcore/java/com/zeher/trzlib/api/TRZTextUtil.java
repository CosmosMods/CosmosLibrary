package com.zeher.trzlib.api;

import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;

public final class TRZTextUtil
{
	public static final String BLACK = (char) 167 + "0";
	public static final String BLUE = (char) 167 + "1";
	public static final String GREEN = (char) 167 + "2";
	public static final String TEAL = (char) 167 + "3";
	public static final String RED = (char) 167 + "4";
	public static final String PURPLE = (char) 167 + "5";
	public static final String ORANGE = (char) 167 + "6";
	public static final String LIGHT_GRAY = (char) 167 + "7";
	public static final String GRAY = (char) 167 + "8";
	public static final String LIGHT_BLUE = (char) 167 + "9";
	public static final String BRIGHT_GREEN = (char) 167 + "a";
	public static final String BRIGHT_BLUE = (char) 167 + "b";
	public static final String LIGHT_RED = (char) 167 + "c";
	public static final String PINK = (char) 167 + "d";
	public static final String YELLOW = (char) 167 + "e";
	public static final String WHITE = (char) 167 + "f";

	public static final String OBFUSCATED = (char) 167 + "k";
	public static final String BOLD = (char) 167 + "l";
	public static final String STRIKETHROUGH = (char) 167 + "m";
	public static final String UNDERLINE = (char) 167 + "n";
	public static final String ITALIC = (char) 167 + "o";
	public static final String END = (char) 167 + "r";
  
  public static boolean isAltKeyDown()
  {
    return (Keyboard.isKeyDown(56)) || (Keyboard.isKeyDown(184));
  }
  
  public static boolean isControlKeyDown()
  {
    return (Keyboard.isKeyDown(29)) || (Keyboard.isKeyDown(157));
  }
  
  public static boolean isShiftKeyDown()
  {
    return (Keyboard.isKeyDown(42)) || (Keyboard.isKeyDown(54));
  }
  
  public static int getSplitStringHeight(FontRenderer fontRenderer, String input, int width)
  {
    List stringRows = fontRenderer.listFormattedStringToWidth(input, width);
    return stringRows.size() * fontRenderer.FONT_HEIGHT;
  }
  
  public static String camelCase(String input)
  {
    return input.substring(0, 1).toLowerCase() + input.substring(1);
  }
  
  public static String titleCase(String input)
  {
    return input.substring(0, 1).toUpperCase() + input.substring(1);
  }
  
  public static String localize(String key)
  {
    return I18n.translateToLocal(key);
  }
  
  public static String getFluidName(FluidStack fluid)
  {
    return getFluidName(fluid.getFluid());
  }
  
  public static String getFluidName(Fluid fluid)
  {
    String fluidName = "";
    if (fluid.getRarity() == EnumRarity.UNCOMMON) {
      fluidName = fluidName + YELLOW;
    } else if (fluid.getRarity() == EnumRarity.RARE) {
      fluidName = fluidName + BRIGHT_BLUE;
    } else if (fluid.getRarity() == EnumRarity.EPIC) {
      fluidName = fluidName + PINK;
    }
    fluidName = fluidName + fluid.getLocalizedName(null) + END;
    
    return fluidName;
  }
  
  public static String getScaledNumber(int number)
  {
    return getScaledNumber(number, 2);
  }
  
  public static String getScaledNumber(int number, int minDigits)
  {
    String numString = "";
    
    int numMod = 10 * minDigits;
    if (number > 100000 * numMod) {
      numString = numString + number / 1000000 + "M";
    } else if (number > 100 * numMod) {
      numString = numString + number / 1000 + "k";
    } else {
      numString = numString + number;
    }
    return numString;
  }
  
 
  public static String shiftForDetails() {

		return LIGHT_GRAY + localize("info.orecraft.hold") + " " + 
			   ORANGE + BOLD + localize("info.orecraft.shift") + " " + END + 
			   LIGHT_GRAY + localize("info.orecraft.forDetails") + END;
	}
  
  public static String getActivationText(String key)
  {
    return BRIGHT_BLUE + localize(key) + END;
  }
  
  public static String getDeactivationText(String key)
  {
    return YELLOW + localize(key) + END;
  }
  
  public static String getInfoText(String key)
  {
    return TEAL + localize(key) + END;
  }
  
  public static String getFlavorText(String key)
  {
    return TEAL + localize(key) + END;
  }
  
  public static boolean displayShiftForDetail = true;
  public static boolean displayStackCount = false;
  }
