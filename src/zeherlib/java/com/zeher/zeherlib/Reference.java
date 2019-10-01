package com.zeher.zeherlib;

import net.minecraft.util.ResourceLocation;

public class Reference {
	
	public static class RESOURCE {
		public static final String PRE = ZeherLib.MOD_ID + ":";

		public static final String RESOURCE = PRE + "textures/";
		public static final String GUI = RESOURCE + "gui/";

		public static final String BLOCKS = PRE + "blocks/";
		public static final String ITEMS = RESOURCE + "items/";
		
		private static final String GUI_ENERGY_BAR = GUI + "util/gui_elements/gui_energy_bars.png";
		private static final String GUI_DIRECTION = GUI + "util/gui_direction.png";
		
		private static final String GUI_SLOTS = GUI + "util/gui_elements/gui_slots.png";
		
	}
	
	public static class TOOLTIP {
		public static final String HOLD = "info.hold.name";
		public static final String SHIFT = "info.shift.name";
		public static final String FOR_DETAILS = "info.fordetails.name";
		
		public static final String ENERGYITEM = "info.tooltip.energy.name";
		public static final String FLUIDITEM = "info.tooltip.fluid.name";
		
		public static final String CURRENT_POWER = "info.currentpower.name";
		public static final String MAX_POWER = "info.maxpower.name";
	}
	
	public static class DEPENDENCY {
		private static final String FORGE_BUILD = "2838";
		public static final String FORGE_REQ = "14.23.5." + FORGE_BUILD;
		public static final String FORGE_REQ_MAX = "14.24.0";
		
		public static final String FORGE_DEP = "required-after:" + "forge" +  "@[" + FORGE_REQ + "," + FORGE_REQ_MAX + "];";
		
		public static final String DOWN_URL = "";
	}
	
	public static class GUI {
		public static class RESOURCE {
			public static final ResourceLocation ENERGY_BAR_TEXTURE = new ResourceLocation(Reference.RESOURCE.GUI_ENERGY_BAR);
			
			public static final ResourceLocation ICON_BUTTON_TEXTURES = new ResourceLocation(Reference.RESOURCE.GUI + "util/button/gui_icon_button.png");
			public static final ResourceLocation ELEMENT_MISC_TEXTURES = new ResourceLocation(Reference.RESOURCE.GUI + "util/gui_elements/gui_element_misc.png");
			
			public static final ResourceLocation ENERGY_BUTTON_CUSTOM_TEXTURES = new ResourceLocation(Reference.RESOURCE.GUI + "util/button/gui_energy_button_custom.png");
			public static final ResourceLocation ENERGY_BUTTON_TEXTURES = new ResourceLocation(Reference.RESOURCE.GUI + "util/button/gui_energy_button.png");
			
			public static final ResourceLocation FLUID_BUTTON_TEXTURES = new ResourceLocation(Reference.RESOURCE.GUI + "util/button/gui_fluid_button.png");
			public static final ResourceLocation FLUID_BUTTON_CUSTOM_TEXTURES = new ResourceLocation(Reference.RESOURCE.GUI + "util/button/gui_fluid_button_custom.png");
			
			public static final ResourceLocation ITEM_BUTTON_TEXTURES = new ResourceLocation(Reference.RESOURCE.GUI + "util/button/gui_item_button.png");
			public static final ResourceLocation ITEM_BUTTON_CUSTOM_TEXTURES = new ResourceLocation(Reference.RESOURCE.GUI + "util/button/gui_item_button_custom.png");
			
			public static final ResourceLocation STORAGE_BUTTON_TEXTURES = new ResourceLocation(Reference.RESOURCE.GUI + "util/button/gui_storage_button.png");
			public static final ResourceLocation STORAGE_BUTTON_CUSTOM_TEXTURES = new ResourceLocation(Reference.RESOURCE.GUI + "util/button/gui_storage_button_custom.png");
			
			public static final ResourceLocation SLOT_TEXTURES = new ResourceLocation(Reference.RESOURCE.GUI_SLOTS);
			
			public static final ResourceLocation DIRECTION_TEXTURE = new ResourceLocation(Reference.RESOURCE.GUI_DIRECTION);
		}
		
		public static class GUI_BUTTON_INFO {
			public static final int X_SPACING = 44;
			public static final int Y_SPACING = 23;
			public static final int X_SPACING_SMALL = 67;
			
			public static final int[] BUTTON_STATE_X_SMALL = { 20, 58, 96, 134, 172, 210 };
			public static final int[] BUTTON_STATE_Y_SMALL = { 0, 20, 40, 60, 80, 100, 120, 140, 160, 180, 200, 220 };
			
			public static final int[] BUTTON_STATE_X = { 0, 38, 76, 114, 152, 190 };
			public static final int[] BUTTON_STATE_Y = { 0, 20, 40, 60, 80, 100, 120, 140, 160, 180, 200, 220 };
		}
		
		public static class GUI_SLOT_INFO {
			
			/**
			 * int[0] = textureX
			 * int[1] = textureY
			 * int[2] = width
			 * int[3] = height
			 */
			
			public static final int[] REGULAR_SMALL = new int[] {
				0, 0, 18, 18	
			};
			
			public static final int[] OUTPUT_SMALL = new int[] {
				18, 0, 18, 18	
			};
			
			public static final int[] INPUT_SMALL = new int[] {
				36, 0, 18, 18
			};
		}
		
		public static class ENERGY_BAR_INFO {
			/**
			 * int[0] = textureX
			 * int[1] = textureY
			 * int[2] = textureHeight
			 * int[3] = textureWidth
			 */
			
			public static final int[] POWERED_BAR = new int[] {
				18, 61, 62, 18	
			};
			
			public static final int[] POWERED_BAR_SMALL = new int[] {
				18, 102, 40, 18	
			};
			
			public static final int[] ENERGIZED_BAR = new int[] {
				36, 61, 62, 18
			};
			
			public static final int[] ENERGIZED_BAR_SMALL = new int[] {
				36, 102, 40, 18	
			};
			
			public static final int[] CREATIVE_BAR = new int[] {
				54, 61, 62, 18	
			};
			
			public static final int[] CREATIVE_BAR_SMALL = new int[] {
				54, 102, 40, 18	
			};
		}
	}
	
	public static class VALUE {
		public static class STORAGE {
			public static final int POWERED_CAPACITY = 8000000;
			public static final int POWERED_INPUT = 1200;
			public static final int POWERED_OUTPUT = 1200;
			
			public static final int ENERGIZED_CAPACITY = 24000000;
			public static final int ENERGIZED_INPUT = 12000;
			public static final int ENERGIZED_OUTPUT = 12000;
			
		}
		
		public static class FLUIDSTORAGE {
			public static final int FLUIDTANK_OUTPUT = 500;
			public static final int FLUIDTANK_STORED = 16000;
		}
		
		public static class FLUIDPIPE {
			public static final int FLUIDPIPE_OUTPUT = 50;
			public static final int FLUIDPIPE_STORED = 500;
		}
		
		public static class ENERGYPIPE {
			
			public static final int POWERED_CAPACITY = 600;
			public static final int POWERED_TRANSFER = 300;
			
			public static final int ENERGIZED_CAPACITY = 6000;
			public static final int ENERGIZED_TRANSFER = 3000;
			
			public static final int CREATIVE_CAPACITY = 10000000;
			public static final int CREATIVE_TRANSFER = 10000000;
		}
		
		public static class MACHINE {
			public static final int POWERED_INPUT_RATE = 60;
			
			public static final int ENERGIZED_INPUT_RATE = 120;
			
			public static int machineDrainRate(int i) {
				if (i == 0) {
					return 0;
				} else if (i == 10) {
					return 580;
				} else if (i == 20) {
					return 260;
				} else if (i == 30) {
					return 160;
				} else if (i == 40) {
					return 110;
				} else if (i == 50) {
					return 80;
				} else if (i == 60) {
					return 60;
				} else if (i == 70) {
					return 46;
				} else if (i == 80) {
					return 35;
				} else if (i == 90) {
					return 27;
				} else if (i == 100) {
					return 20;
				} else if (i > 100) {
					return 20;
				} else if (i < 10) {
					return 600;
				} else {
					return 0;
				}
			}
			
			public static double log10MachineDrainRate(double x) {
				double log = Math.log10(x / 10.0) * 13.0 - 20.0;
				double rounded = 0.0;

				rounded = -(Math.round(log) * 10.0 + 20.0);

				return rounded;
			}

			public static int poweredProcessSpeed(int i) {
				if (i == 0) {
					return 100;
				} else if (i == 1) {
					return 90;
				} else if (i == 2) {
					return 80;
				} else if (i == 3) {
					return 70;
				} else if (i == 4) {
					return 60;
				} else {
					return 100;
				}
			}

			public static int energizedProcessSpeed(int i) {
				if (i == 0) {
					return 60;
				} else if (i == 1) {
					return 40;
				} else if (i == 2) {
					return 30;
				} else if (i == 3) {
					return 20;
				} else if (i == 4) {
					return 10;
				} else {
					return 60;
				}
			}
			
			public static int poweredStored(int i) {
				if (i == 0) {
					return 50000;
				} else if (i == 1) {
					return 54000;
				} else if (i == 2) {
					return 58000;
				} else if (i == 3) {
					return 64000;
				} else if (i == 4) {
					return 68000;
				}
	
				return 50000;
			}
	
			public static int energizedStored(int i) {
				if (i == 0) {
					return 80000;
				} else if (i == 1) {
					return 100000;
				} else if (i == 2) {
					return 120000;
				} else if (i == 3) {
					return 140000;
				} else if (i == 4) {
					return 160000;
				}
	
				return 80000;
			}
		}
		
		public static class FLUIDMACHINE {
			
		}
		
		public static class PRODUCER {
			public static final int POWERED_OUTPUT_RATE = 60;
			public static final int ENERGIZED_OUTPUT_RATE = 120;
			
			public static int poweredGenerationSpeed(int i) {
				if (i == 1) {
					return 300;
				} else if (i == 2) {
					return 240;
				} else if (i == 3) {
					return 200;
				} else if (i == 4) {
					return 120;
				} else {
					return 380;
				}
			}
			
			public static int poweredGenerationRate(int i) {
				if (i == 1) {
					return 75;
				} else if (i == 2) {
					return 100;
				} else if (i == 3) {
					return 125;
				} else if (i == 4) {
					return 150;
				} else {
					return 50;
				}
			}
			
			public static int poweredSolarPanelGenerationRate(int i) {
				if (i == 1) {
					return 15;
				} else if (i == 2) {
					return 20;
				} else if (i == 3) {
					return 25;
				} else if (i == 4) {
					return 30;
				} else {
					return 10;
				}
			}
		}
	}
}