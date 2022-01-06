package com.tcn.cosmoslibrary.client.screen.widget;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tcn.cosmoslibrary.CosmosLibrary;
import com.tcn.cosmoslibrary.CosmosReference;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * 
 * Flexible Button, with various types to change the texture.
 * Removes the need for a Button Class per type.
 * 
 * @author TheCosmicNebula	_
 * 
 */
@OnlyIn(Dist.CLIENT)
public class CosmosButtonWithType extends Button {
	
	/*
	 * Enum to hold various button types, with their respective texture paths.
	 */
	public enum TYPE {
		ICON (0, "icon", CosmosLibrary.MOD_ID, CosmosReference.RESOURCE.BASE.BUTTON_ICON_PATH),
		GENERAL (1, "general", CosmosLibrary.MOD_ID, CosmosReference.RESOURCE.BASE.BUTTON_GENERAL_PATH),
		ENERGY (0, "energy", CosmosLibrary.MOD_ID, CosmosReference.RESOURCE.BASE.BUTTON_ENERGY_PATH),
		FLUID (0, "fluid", CosmosLibrary.MOD_ID, CosmosReference.RESOURCE.BASE.BUTTON_FLUID_PATH),
		ITEM (0, "item", CosmosLibrary.MOD_ID, CosmosReference.RESOURCE.BASE.BUTTON_ITEM_PATH),
		STORAGE (0, "storage", CosmosLibrary.MOD_ID, CosmosReference.RESOURCE.BASE.BUTTON_STORAGE_PATH);
		
		//Index.
		private int index;
		
		//Basic name.
		private String name;
		
		//ModID.
		private String namespace;
		
		//Path for texture.
		private String path;
		
		TYPE (int index, String name, String namespace, String path) {
			this.index = index;
			this.name = name;
			this.namespace = namespace;
			this.path = path;
		}
		
		public int getIndex() {
			return this.index;
		}
		
		public String getName() {
			return this.name;
		}
		
		public ResourceLocation getButtonTexture(int i) {
			if (i == 0) {
				return new ResourceLocation(this.namespace, this.path.replace(".png", "" + ".png"));
			} else if (i == 1) {
				return new ResourceLocation(this.namespace, this.path.replace(".png", "") + "_0" + ".png");
			}
			
			return new ResourceLocation(namespace, path);
		}
	}
	
	private TYPE buttonType;
	protected int width;
	protected int height;
	public int x;
	public int y;
	protected boolean isHovered;
	public boolean active = true;
	public boolean visible = true;
	private int identifier;

	public CosmosButtonWithType(TYPE typeIn, int x, int y, int size, boolean enabled, boolean visible, int identifier, Component title, Button.OnPress pressedAction) {
		super(x, y, size, size, title, pressedAction);
		this.buttonType = typeIn;
		
		this.x = x;
		this.y = y;
		this.width = size;
		this.height = size;

		this.active = enabled;
		this.visible = visible;
		this.identifier = identifier;
	}

	public CosmosButtonWithType(TYPE typeIn, int x, int y, boolean enabled, boolean visible, int identifier, Component title, Button.OnPress pressedAction) {
		this(typeIn, x, y, 20, enabled, visible, identifier, title, pressedAction);
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float ticks) {
		if (this.visible) {
			if (this.buttonType != null) {
				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderTexture(0, this.buttonType.getButtonTexture(0));
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
				
				this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
				int i = this.getHoverState(this.isHovered);
	
				GlStateManager._enableBlend();
				GlStateManager._blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.value, GlStateManager.SourceFactor.ONE.value, GlStateManager.DestFactor.ZERO.value);
				GlStateManager._blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.value);
				
				if (this.identifier >= 0 && this.identifier <= 23) {
					if (this.identifier >= 0 && this.identifier <= 5) {
						if (this.width == 20 && this.height == 20) {
							this.blit(matrixStack, this.x, this.y, CosmosReference.RESOURCE.INFO.BUTTON_STATE_X[identifier], CosmosReference.RESOURCE.INFO.BUTTON_STATE_Y[i], this.width, this.height);
						} else if (this.width == 18 && this.height == 18) {
							this.blit(matrixStack, this.x, this.y, CosmosReference.RESOURCE.INFO.BUTTON_STATE_X_SMALL[identifier], CosmosReference.RESOURCE.INFO.BUTTON_STATE_Y_SMALL[i], this.width, this.height);
						}
					} else if (this.identifier > 5 && this.identifier <= 11) {
						if (this.width == 20 && this.height == 20) {
							this.blit(matrixStack, this.x, this.y, CosmosReference.RESOURCE.INFO.BUTTON_STATE_X[identifier - 6], CosmosReference.RESOURCE.INFO.BUTTON_STATE_Y[i + 3], this.width, this.height);
						} else if (this.width == 18 && this.height == 18) {
							this.blit(matrixStack, this.x, this.y, CosmosReference.RESOURCE.INFO.BUTTON_STATE_X_SMALL[identifier - 6], CosmosReference.RESOURCE.INFO.BUTTON_STATE_Y_SMALL[i + 3], this.width, this.height);
						}
					} else if (this.identifier > 11 && this.identifier <= 17) {
						if (this.width == 20 && this.height == 20) {
							this.blit(matrixStack, this.x, this.y, CosmosReference.RESOURCE.INFO.BUTTON_STATE_X[identifier - 12], CosmosReference.RESOURCE.INFO.BUTTON_STATE_Y[i + 6], this.width, this.height);
						} else if (this.width == 18 && this.height == 18) {
							this.blit(matrixStack, this.x, this.y, CosmosReference.RESOURCE.INFO.BUTTON_STATE_X_SMALL[identifier - 12], CosmosReference.RESOURCE.INFO.BUTTON_STATE_Y_SMALL[i + 6], this.width, this.height);
						}
					} else if (this.identifier > 17 && this.identifier <= 23) {
						if (this.width == 20 && this.height == 20) {
							this.blit(matrixStack, this.x, this.y, CosmosReference.RESOURCE.INFO.BUTTON_STATE_X[identifier - 18], CosmosReference.RESOURCE.INFO.BUTTON_STATE_Y[i + 9], this.width, this.height);
						} else if (this.width == 18 && this.height == 18) {
							this.blit(matrixStack, this.x, this.y, CosmosReference.RESOURCE.INFO.BUTTON_STATE_X_SMALL[identifier - 18], CosmosReference.RESOURCE.INFO.BUTTON_STATE_Y_SMALL[i + 9], this.width, this.height);
						}
					} 
				}
				
				else if (this.identifier > 23 && this.identifier <= 47) {
					RenderSystem.setShaderTexture(0, this.buttonType.getButtonTexture(1));
					//Minecraft.getInstance().getTextureManager().bind(this.buttonType.getButtonTexture(1));
					
					if (this.identifier > 23 && this.identifier <= 29) {
						if (this.width == 20 && this.height == 20) {
							this.blit(matrixStack, this.x, this.y, CosmosReference.RESOURCE.INFO.BUTTON_STATE_X[identifier - 24], CosmosReference.RESOURCE.INFO.BUTTON_STATE_Y[i], this.width, this.height);
						} else if (this.width == 18 && this.height == 18) {
							this.blit(matrixStack, this.x, this.y, CosmosReference.RESOURCE.INFO.BUTTON_STATE_X_SMALL[identifier - 24], CosmosReference.RESOURCE.INFO.BUTTON_STATE_Y_SMALL[i], this.width, this.height);
						}
					} else if (this.identifier > 29 && this.identifier <= 35) {
						if (this.width == 20 && this.height == 20) {
							this.blit(matrixStack, this.x, this.y, CosmosReference.RESOURCE.INFO.BUTTON_STATE_X[identifier - 30], CosmosReference.RESOURCE.INFO.BUTTON_STATE_Y[i + 3], this.width, this.height);
						} else if (this.width == 18 && this.height == 18) {
							this.blit(matrixStack, this.x, this.y, CosmosReference.RESOURCE.INFO.BUTTON_STATE_X_SMALL[identifier - 30], CosmosReference.RESOURCE.INFO.BUTTON_STATE_Y_SMALL[i + 3], this.width, this.height);
						}
					} else if (this.identifier > 35 && this.identifier <= 41) {
						if (this.width == 20 && this.height == 20) {
							this.blit(matrixStack, this.x, this.y, CosmosReference.RESOURCE.INFO.BUTTON_STATE_X[identifier - 36], CosmosReference.RESOURCE.INFO.BUTTON_STATE_Y[i + 6], this.width, this.height);
						} else if (this.width == 18 && this.height == 18) {
							this.blit(matrixStack, this.x, this.y, CosmosReference.RESOURCE.INFO.BUTTON_STATE_X_SMALL[identifier - 36], CosmosReference.RESOURCE.INFO.BUTTON_STATE_Y_SMALL[i + 6], this.width, this.height);
						}
					} else if (this.identifier > 41 && this.identifier <= 47) {
						if (this.width == 20 && this.height == 20) {
							this.blit(matrixStack, this.x, this.y, CosmosReference.RESOURCE.INFO.BUTTON_STATE_X[identifier - 42], CosmosReference.RESOURCE.INFO.BUTTON_STATE_Y[i + 9], this.width, this.height);
						} else if (this.width == 18 && this.height == 18) {
							this.blit(matrixStack, this.x, this.y, CosmosReference.RESOURCE.INFO.BUTTON_STATE_X_SMALL[identifier - 42], CosmosReference.RESOURCE.INFO.BUTTON_STATE_Y_SMALL[i + 9], this.width, this.height);
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		if (!this.active) {
			return false;
		}
		return super.isMouseOver(mouseX, mouseY);
	}

	@Override
	public void onPress() {
		this.onPress.onPress(this);
	}

	@Override
	public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.renderButton(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	protected int getHoverState(boolean mouseOver) {
		int i = 0;

		if (!this.active) {
			i = 2;
		} else if (mouseOver) {
			i = 1;
		}
		return i;
	}
}