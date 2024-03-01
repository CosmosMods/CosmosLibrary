package com.tcn.cosmoslibrary.client.ui.lib;

import java.util.Arrays;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Quaternionf;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.tcn.cosmoslibrary.CosmosReference;
import com.tcn.cosmoslibrary.client.renderer.lib.CosmosRendererHelper;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosButtonWithType;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosButtonWithType.TYPE;
import com.tcn.cosmoslibrary.common.enums.EnumUIMode;
import com.tcn.cosmoslibrary.common.interfaces.IEnergyEntity;
import com.tcn.cosmoslibrary.common.interfaces.blockentity.IBlockEntityUIMode;
import com.tcn.cosmoslibrary.common.interfaces.blockentity.IEnergyHolder;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper.Value;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.IFluidTank;

/**
 * 
 * Flexible Collection of Helpful methods to simply the Creation of in-game UIs.
 * 
 * @author TheCosmicNebula_
 * 
 */

@OnlyIn(Dist.CLIENT)
public class CosmosUISystem {
	
	public static final int DEFAULT_COLOUR_BACKGROUND = 4210752;
	public static final int DEFAULT_COLOUR_FONT_LIST = 16777215;
	
	public static final int BLACK = 0x000000;
	public static final int WHITE = 0xFFFFFF;
	public static final int LIGHT_BLUE = 0x5882FA;
	public static final int BLUE = 0x0000FF;
	public static final int LIGHT_GREY = 0xA4A4A4;
	public static final int GREY = 0x424242;
	public static final int GREEN = 0x00FF00;
	public static final int DARK_GREEN = 0x0B610B;
	public static final int RED = 0xFF0000;
	public static final int YELLOW = 0xFFFF00;
	public static final int ORANGE = 0xFF8000;
	public static final int CYAN = 0x01A9DB;
	public static final int MAGENTA = 0xDF01D7;
	public static final int PURPLE = 0x8904B1;
	public static final int PINK = 0xFE2EC8;
	public static final int BROWN = 0x61210B;
	
	public static final float[] NORMAL_COLOUR = new float[] { 1.0F, 1.0F, 1.0F, 1.0F };
	
	public static int[] getScreenCoords(Screen screen, int imageWidth, int imageHeight) {
		return new int[] { ((screen.width - imageWidth) / 2), ((screen.height - imageHeight) / 2) };
	}
	
	public static int[] getScreenCoords(AbstractContainerScreen<?> screen, int imageWidth, int imageHeight) {
		return new int[] { ((screen.width - imageWidth) / 2), ((screen.height - imageHeight) / 2) };
	}

	public static void setTextureWithColour(PoseStack poseStack, ResourceLocation textureIn, ComponentColour colourIn) {
		setTexture(poseStack, textureIn);
		setTextureColour(colourIn);
	}

	public static void setTextureWithColour(PoseStack poseStack, ResourceLocation textureIn, float redIn, float greenIn, float blueIn) {
		setTexture(poseStack, textureIn);
		setTextureColour(redIn, greenIn, blueIn);
	}

	public static void setTextureWithColour(PoseStack poseStack, ResourceLocation textureIn, float[] colourIn) {
		setTexture(poseStack, textureIn);
		setTextureColour(colourIn);
	}

	public static void setTextureWithColourAlpha(PoseStack poseStack, ResourceLocation textureIn, float redIn, float greenIn, float blueIn, float alphaIn) {
		setTexture(poseStack, textureIn);
		setTextureColour(redIn, greenIn, blueIn, alphaIn);
		enableAlpha();
	}
	
	public static void setTextureWithColourAlpha(PoseStack poseStack, ResourceLocation textureIn, float[] colourIn) {
		setTexture(poseStack, textureIn);
		setTextureColour(colourIn);
		enableAlpha();
	}

	public static void setTexture(PoseStack poseStack, ResourceLocation textureIn) {
		setTextureColour(NORMAL_COLOUR);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, textureIn);
	}

	public static void nullTexture(PoseStack poseStack, ResourceLocation textureIn) {
		RenderSystem.deleteTexture(0);
	}
	
	public static void enableAlpha() {
		RenderSystem.enableBlend();
	    RenderSystem.defaultBlendFunc();
	    RenderSystem.enableDepthTest();
	}

	public static void setTextureColour(ComponentColour colourIn) {
		float[] rgb = ComponentColour.rgbFloatArray(colourIn);
		setTextureColour(rgb[0], rgb[1], rgb[2], 1.0F);
	}

	public static void setTextureColour(float redIn, float greenIn, float blueIn, float alphaIn) {
		setTextureColour(new float[] { redIn, greenIn, blueIn, alphaIn });
	}

	public static void setTextureColour(float redIn, float greenIn, float blueIn) {
		setTextureColour(new float[] { redIn, greenIn, blueIn, 1.0F });
	}

	public static void setTextureColour(float[] colourIn) {
		RenderSystem.setShaderColor(colourIn[0], colourIn[1], colourIn[2], colourIn[3]);
	}
	
	public static List<Component> getItemTooltip(Screen screen, ItemStack stackIn) {
		Minecraft minecraft = screen.getMinecraft();
		return stackIn.getTooltipLines(minecraft.player, minecraft.options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);
	}
	
	public static void renderItemStack(Screen screen, Font font, GuiGraphics graphics, ItemStack itemStackIn, int[] screenCoords, int x, int y, int mouseX, int mouseY, boolean withTooltip) {
		int renderX = screenCoords[0] + x;
		int renderY = screenCoords[1] + y;
		
		//Minecraft.getInstance().getItemRenderer().renderGuiItem(itemStackIn, screenCoords[0] + x, screenCoords[1] + y);
		
		graphics.renderItem(itemStackIn, screenCoords[0] + x, screenCoords[1] + y);

		if (withTooltip) {
			if (mouseX > renderX && mouseX < renderX + 16) {
				if (mouseY > renderY && mouseY < renderY + 16) {
					graphics.renderComponentTooltip(font, getItemTooltip(screen, itemStackIn), mouseX, mouseY);
				}
			}
		}
	}
	
	public static void renderItemStack(Screen screen, Font font, GuiGraphics graphics, ItemStack itemStackIn, int count, int[] screenCoords, int x, int y, int mouseX, int mouseY, boolean withTooltip) {
		int renderX = screenCoords[0] + x;
		int renderY = screenCoords[1] + y;
		
		//Minecraft.getInstance().getItemRenderer().renderGuiItem(itemStackIn, screenCoords[0] + x, screenCoords[1] + y);

		graphics.renderItem(itemStackIn, screenCoords[0] + x, screenCoords[1] + y);

		if (withTooltip) {
			if (mouseX > renderX && mouseX < renderX + 16) {
				if (mouseY > renderY && mouseY < renderY + 16) {
					graphics.renderComponentTooltip(font, getItemTooltip(screen, itemStackIn), mouseX, mouseY);
				}
			}
		}
	}
	
	public static void renderEnergyDisplay(Screen screen, GuiGraphics graphics, ComponentColour colourIn, IEnergyHolder energyHolderIn, int[] screenCoords, int drawX, int drawY, int widthIn, int heightIn, boolean horizontal) {
		if (horizontal) {
			setTexture(graphics.pose(), CosmosReference.RESOURCE.BASE.UI_ENERGY_HORIZONTAL);
			setTextureColour(0.6F, 0.6F, 0.6F);
			renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_HORIZONTAL, screenCoords, drawX, drawY, 0, 0, widthIn, heightIn);
			
			if (energyHolderIn.hasEnergyStored()) {
				int scaled = energyHolderIn.getEnergyStoredScaled(widthIn);

				setTextureColour(colourIn);
				renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_HORIZONTAL, screenCoords, drawX, drawY, 0 + scaled, 0, scaled, heightIn);
				setTextureColour(NORMAL_COLOUR);
			}
		} else {
			setTexture(graphics.pose(), CosmosReference.RESOURCE.BASE.UI_ENERGY_VERTICAL);
			setTextureColour(0.6F, 0.6F, 0.6F);
			renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_VERTICAL, screenCoords, drawX, drawY, 0, 255 - heightIn, widthIn, heightIn);

			if (energyHolderIn.hasEnergyStored()) {
				int scaled = energyHolderIn.getEnergyStoredScaled(heightIn);

				setTextureColour(colourIn);
				renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_VERTICAL, screenCoords, drawX, drawY + heightIn - scaled, 0, 255 - scaled, widthIn, scaled);
				setTextureColour(NORMAL_COLOUR);
			}
		}
	}

	public static void renderEnergyDisplay(Screen screen, GuiGraphics graphics, ComponentColour colourIn, IEnergyEntity energyHolderIn, int[] screenCoords, int drawX, int drawY, int widthIn, int heightIn, boolean horizontal) {
		if (horizontal) {
			setTexture(graphics.pose(), CosmosReference.RESOURCE.BASE.UI_ENERGY_HORIZONTAL);
			setTextureColour(0.6F, 0.6F, 0.6F);
			renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_HORIZONTAL, screenCoords, drawX, drawY, 0, 0, widthIn, heightIn);
			
			if (energyHolderIn.hasEnergy()) {
				int scaled = energyHolderIn.getEnergyScaled(widthIn);

				setTextureColour(colourIn);
				renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_HORIZONTAL, screenCoords, drawX, drawY, 0 + scaled, 0, scaled, heightIn);
				setTextureColour(NORMAL_COLOUR);
			}
		} else {
			setTexture(graphics.pose(), CosmosReference.RESOURCE.BASE.UI_ENERGY_VERTICAL);
			setTextureColour(0.6F, 0.6F, 0.6F);
			renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_VERTICAL, screenCoords, drawX, drawY, 0, 255 - heightIn, widthIn, heightIn);

			if (energyHolderIn.hasEnergy()) {
				int scaled = energyHolderIn.getEnergyScaled(heightIn);

				setTextureColour(colourIn);
				renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_VERTICAL, screenCoords, drawX, drawY + heightIn - scaled, 0, 255 - scaled, widthIn, scaled);
				setTextureColour(NORMAL_COLOUR);
			}
		}
	}

	public static void renderEnergyDisplay(Screen screen, GuiGraphics graphics, ComponentColour colourIn, int energyIn, int maxEnergyIn, int scaleIn, int[] screenCoords, int drawX, int drawY, int widthIn, int heightIn, boolean horizontal) {
		if (horizontal) {
			setTexture(graphics.pose(), CosmosReference.RESOURCE.BASE.UI_ENERGY_HORIZONTAL);
			setTextureColour(0.6F, 0.6F, 0.6F);
			renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_HORIZONTAL, screenCoords, drawX, drawY, 0, 0, widthIn, heightIn);
			
			if (energyIn > 0) {
				int scaled = energyIn * scaleIn / maxEnergyIn;

				setTextureColour(colourIn);
				renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_HORIZONTAL, screenCoords, drawX, drawY, 0 + scaled, 0, scaled, heightIn);
				setTextureColour(NORMAL_COLOUR);
			}
		} else {
			setTexture(graphics.pose(), CosmosReference.RESOURCE.BASE.UI_ENERGY_VERTICAL);
			setTextureColour(0.6F, 0.6F, 0.6F);
			renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_VERTICAL, screenCoords, drawX, drawY, 0, 255 - heightIn, widthIn, heightIn);

			if (energyIn > 0) {
				int scaled = energyIn * scaleIn / maxEnergyIn;

				setTextureColour(colourIn);
				renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_VERTICAL, screenCoords, drawX, drawY + heightIn - scaled, 0, 255 - scaled, widthIn, scaled);
				setTextureColour(NORMAL_COLOUR);
			}
		}
	}
	
	public static void renderEnergyDisplay(Gui screen, GuiGraphics graphics, ComponentColour colourIn, IEnergyHolder energyHolderIn, int[] screenCoords, int drawX, int drawY, int widthIn, int heightIn, boolean horizontal) {
		if (horizontal) {
			setTexture(graphics.pose(), CosmosReference.RESOURCE.BASE.UI_ENERGY_HORIZONTAL);
			setTextureColour(0.6F, 0.6F, 0.6F);
			renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_HORIZONTAL, screenCoords, drawX, drawY, 0, 0, widthIn, heightIn);
			
			if (energyHolderIn.hasEnergyStored()) {
				int scaled = energyHolderIn.getEnergyStoredScaled(widthIn);

				setTextureColour(colourIn);
				renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_HORIZONTAL, screenCoords, drawX, drawY, 0 + scaled, 0, scaled, heightIn);
				setTextureColour(NORMAL_COLOUR);
			}
		} else {
			setTexture(graphics.pose(), CosmosReference.RESOURCE.BASE.UI_ENERGY_VERTICAL);
			setTextureColour(0.6F, 0.6F, 0.6F);
			renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_VERTICAL, screenCoords, drawX, drawY, 0, 255 - heightIn, widthIn, heightIn);

			if (energyHolderIn.hasEnergyStored()) {
				int scaled = energyHolderIn.getEnergyStoredScaled(heightIn);

				setTextureColour(colourIn);
				renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_VERTICAL, screenCoords, drawX, drawY + heightIn - scaled, 0, 255 - scaled, widthIn, scaled);
				setTextureColour(NORMAL_COLOUR);
			}
		}
	}

	public static void renderEnergyDisplay(Gui screen, GuiGraphics graphics, ComponentColour colourIn, int energyIn, int maxEnergyIn, int scaleIn, int[] screenCoords, int drawX, int drawY, int widthIn, int heightIn, boolean horizontal) {
		if (horizontal) {
			setTexture(graphics.pose(), CosmosReference.RESOURCE.BASE.UI_ENERGY_HORIZONTAL);
			setTextureColour(0.6F, 0.6F, 0.6F);
			renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_HORIZONTAL, screenCoords, drawX, drawY, 0, 0, widthIn, heightIn);
			
			if (energyIn > 0) {
				int scaled = energyIn * scaleIn / maxEnergyIn;

				setTextureColour(colourIn);
				renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_HORIZONTAL, screenCoords, drawX, drawY, 0 + scaled, 0, scaled, heightIn);
				setTextureColour(NORMAL_COLOUR);
			}
		} else {
			setTexture(graphics.pose(), CosmosReference.RESOURCE.BASE.UI_ENERGY_VERTICAL);
			setTextureColour(0.6F, 0.6F, 0.6F);
			renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_VERTICAL, screenCoords, drawX, drawY, 0, 255 - heightIn, widthIn, heightIn);

			if (energyIn > 0) {
				int scaled = energyIn * scaleIn / maxEnergyIn;

				setTextureColour(colourIn);
				renderStaticElement(screen, graphics, CosmosReference.RESOURCE.BASE.UI_ENERGY_VERTICAL, screenCoords, drawX, drawY + heightIn - scaled, 0, 255 - scaled, widthIn, scaled);
				setTextureColour(NORMAL_COLOUR);
			}
		}
	}
	
	public static void renderSlot(Screen screen, GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int[] slot_location) {
		setTexture(graphics.pose(), CosmosReference.RESOURCE.BASE.GUI_SLOT_LOC);
		
		graphics.blit(CosmosReference.RESOURCE.BASE.GUI_SLOT_LOC, screenCoords[0] + drawX, screenCoords[1] + drawY, slot_location[0], slot_location[1], slot_location[2], slot_location[3]);
	}
	
	public static void renderBackground(Screen screen, GuiGraphics graphics, int[] screenCoords, ResourceLocation textureIn) {
		setTexture(graphics.pose(), textureIn);
		
		graphics.blit(textureIn, screenCoords[0], screenCoords[1], 0, 0, screen.width, screen.height);
	}
	
	public static void renderBackground(Screen screen, GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, ResourceLocation textureIn) {
		setTexture(graphics.pose(), textureIn);
		
		graphics.blit(textureIn, screenCoords[0], screenCoords[1], drawX, drawY, screen.width, screen.height);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void renderFluidTank(Screen screen, GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, IFluidTank tank, int scaledIn, int scaleMax) {
		PoseStack poseStack = graphics.pose();
		
		if (tank.getFluidAmount() > 0) {
			Fluid renderFluid = tank.getFluid().getFluid();
			
			IClientFluidTypeExtensions props = IClientFluidTypeExtensions.of(renderFluid.defaultFluidState());
			TextureAtlas texture = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS);
			ResourceLocation textureLoc = props.getStillTexture();
			TextureAtlasSprite fluidTexture = texture.getSprite(textureLoc);
			
			int color = props.getTintColor();
			
			float r = ((color >> 16) & 0xFF) / 255f; // red
		    float g = ((color >> 8) & 0xFF) / 255f; // green
		    float b = ((color >> 0) & 0xFF) / 255f; // blue
		    float a = ((color >> 24) & 0xFF) / 255f; // alpha
		    
			setTextureColour(r, g, b, a);
			
		    if (fluidTexture != null) {
		    	poseStack.pushPose();
		    	
		    	if (scaledIn > 0) {
		    		int limited = Mth.clamp(scaledIn, 0, 16);
		    		graphics.blit(screenCoords[0] + drawX, screenCoords[1] + drawY + scaleMax - limited, 0, 16, limited, fluidTexture);
		    	}
		    	
				if (scaledIn > 16) {
					int scaled = scaledIn - 16;
					int limited = Mth.clamp(scaled, 0, 16);
					
					graphics.blit(screenCoords[0] + drawX, screenCoords[1] + drawY + (scaleMax - 16) - limited, 0, 16, limited, fluidTexture);
				}
				
				if (scaledIn > 32) {
					int scaled = scaledIn - 32;
					int limited = Mth.clamp(scaled, 0, 16);
					
					graphics.blit(screenCoords[0] + drawX, screenCoords[1] + drawY + (scaleMax - 32) - limited, 0, 16, limited, fluidTexture);
				}
				
				if (scaledIn > 48) {
					int scaled = scaledIn - 48;
					int limited = Mth.clamp(scaled, 0, 16);
					
					graphics.blit(screenCoords[0] + drawX, screenCoords[1] + drawY + (scaleMax - 48) - limited, 0, 16, limited, fluidTexture);
				}
				
				if (scaledIn > 64) {
					int scaled = scaledIn - 64;
					int limited = Mth.clamp(scaled, 0, 16);
					
					graphics.blit(screenCoords[0] + drawX, screenCoords[1] + drawY + (scaleMax - 64) - limited, 0, 16, limited, fluidTexture);
				}
				
				poseStack.popPose();
			}
		}
	}
	
	public static void renderEntityInInventory(PoseStack poseStackIn, int[] screenCoordsIn, int xPosIn, int yPosIn, int scaleIn, float mouseXPos, float mouseYPos, int mouseYOffset, LivingEntity entityIn) {
		float angleX = (float) Math.atan((double) (((screenCoordsIn[0] + xPosIn) - mouseXPos) / 40.0F));
		float angleY = (float) Math.atan((double) (((screenCoordsIn[1] + yPosIn - mouseYOffset) - mouseYPos) / 40.0F));
		renderEntityInInventoryRaw(poseStackIn, screenCoordsIn, xPosIn, yPosIn, scaleIn, angleX, angleY, entityIn);
	}

	@SuppressWarnings("deprecation")
	public static void renderEntityInInventoryRaw(PoseStack poseStackInA, int[] screenCoordsIn, int xPosIn, int yPosIn, int scaleIn, float angleXComponent, float angleYComponent, LivingEntity entityIn) {
		float f = angleXComponent;
		float f1 = angleYComponent;
		
	    PoseStack poseStackIn = RenderSystem.getModelViewStack();
		
		poseStackIn.pushPose();
		poseStackIn.translate(screenCoordsIn[0] + xPosIn, screenCoordsIn[1] + yPosIn, 1050.0D);
		poseStackIn.scale(1.0F, 1.0F, -1.0F);
		
		RenderSystem.applyModelViewMatrix();
		PoseStack posestack1 = new PoseStack();
		posestack1.translate(0.0D, 0.0D, 1000.0D);
		posestack1.scale((float) scaleIn, (float) scaleIn, (float) scaleIn);
		
		Quaternionf quaternionf = (new Quaternionf()).rotateZ((float)Math.PI);
	    Quaternionf quaternionf1 = (new Quaternionf()).rotateX(f1 * 20.0F * ((float)Math.PI / 180F));
		quaternionf.mul(quaternionf1);
		posestack1.mulPose(quaternionf);
		
		float entityYBodyRot = entityIn.yBodyRot;
		float entityYRot = entityIn.getYRot();
		float entityXRot = entityIn.getXRot();
		float entityHeadYRot0 = entityIn.yHeadRotO;
		float entityHeadYRot = entityIn.yHeadRot;
		
		entityIn.yBodyRot = 180.0F + f * 20.0F;
		entityIn.setYRot(180.0F + f * 40.0F);
		entityIn.setXRot(-f1 * 20.0F);
		entityIn.yHeadRot = entityIn.getYRot();
		entityIn.yHeadRotO = entityIn.getYRot();
		
		Lighting.setupForEntityInInventory();
		EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
		
		quaternionf1.conjugate();
		entityrenderdispatcher.overrideCameraOrientation(quaternionf1);
		entityrenderdispatcher.setRenderShadow(false);
		MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
		
		ItemStack heldStack = entityIn.getItemInHand(InteractionHand.MAIN_HAND);
		ItemStack heldStackOffHand = entityIn.getItemInHand(InteractionHand.OFF_HAND);
		
		entityIn.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
		entityIn.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
	
		RenderSystem.runAsFancy(() -> {
			entityrenderdispatcher.render(entityIn, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, posestack1, multibuffersource$buffersource, 15728880);
		});
		
		entityIn.setItemInHand(InteractionHand.MAIN_HAND, heldStack);
		entityIn.setItemInHand(InteractionHand.OFF_HAND, heldStackOffHand);
		
		multibuffersource$buffersource.endBatch();
		entityrenderdispatcher.setRenderShadow(true);
		entityIn.yBodyRot = entityYBodyRot;
		entityIn.setYRot(entityYRot);
		entityIn.setXRot(entityXRot);
		entityIn.yHeadRotO = entityHeadYRot0;
		entityIn.yHeadRot = entityHeadYRot;
		poseStackIn.popPose();
		
		RenderSystem.applyModelViewMatrix();
		Lighting.setupFor3DItems();
	}

	public static void blit(PoseStack poseStackIn, int posX, int posY, int ex, int widthIn, int heightIn, TextureAtlasSprite spriteIn) {
		float mappedHeight = CosmosRendererHelper.getMappedTextureHeight(spriteIn, heightIn);
		innerBlit(poseStackIn.last().pose(), posX, posX + widthIn, posY, posY + heightIn, ex, spriteIn.getU0(), spriteIn.getU1(), spriteIn.getV0() + mappedHeight, spriteIn.getV1());
	}

	private static void innerBlit(Matrix4f matrixIn, int minX, int maxX, int minY, int maxY, int zIn, float minU, float maxU, float minV, float maxV) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.vertex(matrixIn, (float) minX, (float) maxY, (float) zIn).uv(minU, maxV).endVertex();
		bufferbuilder.vertex(matrixIn, (float) maxX, (float) maxY, (float) zIn).uv(maxU, maxV).endVertex();
		bufferbuilder.vertex(matrixIn, (float) maxX, (float) minY, (float) zIn).uv(maxU, minV).endVertex();
		bufferbuilder.vertex(matrixIn, (float) minX, (float) minY, (float) zIn).uv(minU, minV).endVertex();
		bufferbuilder.end();
	}
	
	public static void renderToolTipPowerProducer(Screen screen, GuiGraphics graphics, Font font, int[] screenCoords, int drawX, int drawY, int mouseX, int mouseY, int stored, int generation_rate, boolean producing) {
		if (IS_HOVERING.isHoveringPower(mouseX, mouseY, screenCoords[0] + drawX, screenCoords[1] + drawY)) {
			if (producing) {
				graphics.renderComponentTooltip(font, TEXT_LIST.generationText(stored, generation_rate), mouseX - screenCoords[0], mouseY - screenCoords[1]);
			} else {
				graphics.renderComponentTooltip(font, TEXT_LIST.storedTextNo(stored), mouseX - screenCoords[0], mouseY - screenCoords[1]);
			}
		}
	}
	
	public static void renderToolTipFluidLarge(Screen screen, GuiGraphics graphics, Font font, int[] screenCoords, int drawX, int drawY, int mouseX, int mouseY, IFluidTank tank) {
		if (IS_HOVERING.isHoveringFluidLarge(mouseX, mouseY, screenCoords[0] + drawX, screenCoords[1] + drawY)) {
			if (tank.getFluidAmount() > 0) {
				graphics.renderComponentTooltip(font, TEXT_LIST.fluidText(tank.getFluid().getDisplayName().toString(), tank.getFluidAmount(), tank.getCapacity()), mouseX - screenCoords[0], mouseY - screenCoords[1]);
			} else {
				graphics.renderComponentTooltip(font, TEXT_LIST.fluidTextEmpty(), mouseX - screenCoords[0], mouseY - screenCoords[1]);
			}
		}
	}
	
	public static void renderToolTipEmptyFluidButton(Screen screen, GuiGraphics graphics, Font font, int[] screenCoords, int drawX, int drawY, int mouseX, int mouseY, boolean has_fluid) {
		if (IS_HOVERING.isHoveringButtonStandard(mouseX, mouseY, screenCoords[0] + drawX, screenCoords[1] + drawY)) {
			if (has_fluid) {
				if (ComponentHelper.isShiftKeyDown(screen.getMinecraft())) {
					graphics.renderComponentTooltip(font, TEXT_LIST.emptyFluidTankDo(), mouseX - screenCoords[0], mouseY - screenCoords[1]);
				} else {
					graphics.renderComponentTooltip(font, TEXT_LIST.emptyFluidTank(), mouseX - screenCoords[0], mouseY - screenCoords[1]);
				}
			}
		}
	}
	
	public static void renderScaledElementUpNestled(Screen screen, GuiGraphics graphics, ResourceLocation textureIn, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, int scaledIn) {
		graphics.blit(textureIn, screenCoords[0] + drawX, (screenCoords[1] + drawY + height) - scaledIn, textureInX, (textureInY + height) - scaledIn, width, scaledIn);
	}
	
	public static void renderScaledElementDownNestled(Screen screen, GuiGraphics graphics, ResourceLocation textureIn, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int scaledIn) {
		graphics.blit(textureIn, screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, width, scaledIn);
	}
	
	public static void renderScaledElementRightNestled(Screen screen, GuiGraphics graphics, ResourceLocation textureIn, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int height, int scaledIn) {
		graphics.blit(textureIn, screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, scaledIn + 1, height);
	}
	
	public static void renderScaledElementUpExternal(Screen screen, GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, int scaledIn, ResourceLocation textureIn) {
		setTexture(graphics.pose(), textureIn);
		graphics.blit(textureIn, screenCoords[0] + drawX, (screenCoords[1] + drawY + height) - scaledIn, textureInX, (textureInY + height) - scaledIn, width, scaledIn);
	}
	
	public static void renderScaledElementDownExternal(Screen screen, GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int scaledIn, ResourceLocation textureIn) {
		setTexture(graphics.pose(), textureIn);
		graphics.blit(textureIn, screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY + scaledIn, width, scaledIn);
		//RenderSystem.deleteTexture(0);
	}
	
	public static void renderStaticElementWithUIMode(Screen screen, GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, IBlockEntityUIMode entity, ResourceLocation[] locations) {
		if (entity.getUIMode().equals(EnumUIMode.DARK)) {
			setTexture(graphics.pose(), locations[1]);
			graphics.blit(locations[1], screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, width, height);
		} else {
			setTexture(graphics.pose(), locations[0]);
			graphics.blit(locations[0], screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, width, height);
		}
	}

	public static void renderStaticElementWithUIMode(Screen screen, GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, EnumUIMode uiMode, ResourceLocation[] locations) {
		if (uiMode.equals(EnumUIMode.DARK)) {
			setTexture(graphics.pose(), locations[1]);
			graphics.blit(locations[1], screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, width, height);
		} else {
			setTexture(graphics.pose(), locations[0]);
			graphics.blit(locations[0], screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, width, height);
		}
	}

	public static void renderStaticElementWithUIMode(Screen screen, GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, float[] colourIn, IBlockEntityUIMode entity, ResourceLocation[] locations) {
		if (entity.getUIMode().equals(EnumUIMode.DARK)) {
			setTextureWithColour(graphics.pose(), locations[1], colourIn);
			graphics.blit(locations[1], screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, width, height);
		} else {
			setTextureWithColour(graphics.pose(), locations[0], colourIn);
			graphics.blit(locations[0], screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, width, height);
		}
	}

	public static void renderStaticElementWithUIMode(Screen screen, GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, float[] colourIn, EnumUIMode uiMode, ResourceLocation[] locations) {
		if (uiMode.equals(EnumUIMode.DARK)) {
			setTextureWithColour(graphics.pose(), locations[1], colourIn);
			graphics.blit(locations[1], screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, width, height);
		} else {
			setTextureWithColour(graphics.pose(), locations[0], colourIn);
			graphics.blit(locations[0], screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, width, height);
		}
	}
	
	public static void renderStaticElement(Screen screen, GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, ResourceLocation location) {
		setTexture(graphics.pose(), location);
		graphics.blit(location, screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, width, height);
	}

	public static void renderStaticElement(Screen screen, GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, float[] colourIn, ResourceLocation location) {
		setTextureWithColour(graphics.pose(), location, colourIn);
		graphics.blit(location, screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, width, height);
	}

	public static void renderStaticElement(Screen screen, GuiGraphics graphics, ResourceLocation textureIn, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height) {
		graphics.blit(textureIn, screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, width, height);
	}
	
	public static void renderStaticElement(Gui screen, GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, ResourceLocation location) {
		setTexture(graphics.pose(), location);
		graphics.blit(location, screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, width, height);
	}

	public static void renderStaticElement(Gui screen, GuiGraphics graphics, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, float[] colourIn, ResourceLocation location) {
		setTextureWithColour(graphics.pose(), location, colourIn);
		graphics.blit(location, screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, width, height);
	}

	public static void renderStaticElement(Gui screen, GuiGraphics graphics, ResourceLocation textureIn, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height) {
		graphics.blit(textureIn, screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, width, height);
	}
	
	public static void renderStaticElementToggled(Screen screen, GuiGraphics graphics, ResourceLocation textureIn, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, boolean enabled) {
		if (enabled) {
			graphics.blit(textureIn, screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, width, height);
		}
	}	

	public static void renderStaticElementToggled(Gui screen, GuiGraphics graphics, ResourceLocation textureIn, int[] screenCoords, int drawX, int drawY, int textureInX, int textureInY, int width, int height, boolean enabled) {
		if (enabled) {
			graphics.blit(textureIn, screenCoords[0] + drawX, screenCoords[1] + drawY, textureInX, textureInY, width, height);
		}
	}	

	public static class Widget {
		public static CosmosButtonWithType addBucketButton(int indexIn, int[] screenCoords, int drawX, int drawY, int sizeIn, boolean enabled) {
			CosmosButtonWithType button;
			
			if (enabled) {
				button = new CosmosButtonWithType(TYPE.FLUID, screenCoords[0] + drawX, screenCoords[1] + drawY, sizeIn, true, false, 1, ComponentHelper.empty(), (buttonObj, isLeftClick) -> {  });
			} else {
				button = new CosmosButtonWithType(TYPE.FLUID, screenCoords[0] + drawX, screenCoords[1] + drawY, sizeIn, true, true, 4, ComponentHelper.empty(), (buttonObj, isLeftClick) -> {  });
			}
			
			return button;
		}

	}

	public static class FONT {
		public static void drawString(GuiGraphics graphics, Font font, int[] screenCoords, int x, int y, boolean drawFrom, Component comp) {
			graphics.drawString(font, comp, !drawFrom ? x : screenCoords[0] + x, !drawFrom ? y : screenCoords[1] + y, comp.getStyle().getColor().getValue(), false);
		}
		
		public static void drawStringShadow(GuiGraphics graphics, Font font, int[] screenCoords, int x, int y, boolean drawFrom, Component comp) {
			graphics.drawString(font, comp, !drawFrom ? x : screenCoords[0] + x, !drawFrom ? y : screenCoords[1] + y, comp.getStyle().getColor().getValue(), true);
		}

		public static void drawCenteredString(GuiGraphics graphics, Font font, int[] screenCoords, int xOffset, int yOffset, Component comp) {
			int x = (screenCoords[0] * 2) / 2;
			int y = (screenCoords[1] * 2) / 2 + 33;

			graphics.drawString(font, comp, ((x - font.width(comp) / 2) + xOffset), y + yOffset, comp.getStyle().getColor().getValue());
		}
		
		public static void drawWrappedStringBR(GuiGraphics graphics, Font font, int[] screenCoords, int xOffset, int yOffset, int length, Component comp) {
			int prevLines = 0;
			
			for (String str : comp.getString().split("<br>")) {
				int x = (screenCoords[0] * 2) / 2;
				int y = (screenCoords[1] * 2) / 2 + 33;

				graphics.drawString(font, str, ((x - font.width(str) / 2) + xOffset), y + yOffset + (font.lineHeight * prevLines), comp.getStyle().getColor().getValue());
				
				prevLines += (int) Math.ceil((float) (str.length() * 7) / (float) 204);
			}
		}

		public static void drawCenteredComponent(GuiGraphics graphics, Font font, int[] screenCoords, int xOffset, int yOffset, Component string, boolean shadow) {
			int x = (screenCoords[0] * 2) / 2;
			int y = screenCoords[1] * 2 / 2 + 33;

			graphics.drawString(font, string.toString(), ((float) (x - font.width(string) / 2) + xOffset), y + yOffset, string.getStyle().getColor().getValue(), shadow);
		}
		
		public static void drawCenteredString(GuiGraphics graphics, Font font, int[] screenCoords, int xOffset, int yOffset, int colourIn, String string, boolean shadow) {
			int x = (screenCoords[0] * 2) / 2;
			int y = screenCoords[1] * 2 / 2 + 33;

			
			graphics.drawString(font, string, ((float)(x - font.width(string) / 2) + xOffset), y + yOffset, colourIn, shadow);
		}
		
		public static void drawWrappedStringBR(GuiGraphics graphics, Font font, int[] screenCoords, int xOffset, int yOffset, int length, int colourIn, String string) {
			int prevLines = 0;
			
			for (String str : string.split("<br>")) {
				int x = (screenCoords[0] * 2) / 2;
				int y = (screenCoords[1] * 2) / 2 + 33;

				graphics.drawString(font, str, ((x - font.width(str) / 2) + xOffset), y + yOffset + (font.lineHeight * prevLines), colourIn, false);
				
				prevLines += (int) Math.ceil((float) (str.length() * 7) / (float) 204);
			}
		}

		public static void drawInventoryString(GuiGraphics graphics, Font font, int[] screenCoords, int drawX, int drawY, boolean drawFrom) {
			if (drawFrom) {
				graphics.drawString(font, I18n.get("screen.inventory"), screenCoords[0] + drawX, screenCoords[1] + drawY, DEFAULT_COLOUR_BACKGROUND);
			} else {
				graphics.drawString(font, I18n.get("screen.inventory"), drawX, drawY, DEFAULT_COLOUR_BACKGROUND);
			}
		}
		
		public static void drawInventoryString(GuiGraphics graphics, Font font, int[] screenCoords, int drawX, int drawY, int colourIn) {
			graphics.drawString(font, I18n.get("screen.inventory"), screenCoords[0] + drawX, screenCoords[1] + drawY, colourIn);
		}
	}
	
	public static class IS_HOVERING {
		public static boolean isHoveringPower(int mouseX, int mouseY, int x, int y) {
			if (mouseX >= x && mouseX <= x + 17) {
				if (mouseY >= y && mouseY <= y + 62) {
					return true;
				}
			}
			return false;
		}
		
		public static boolean isHoveringPowerSmall(int mouseX, int mouseY, int x, int y) {
			if (mouseX >= x && mouseX <= x + 17) {
				if (mouseY >= y && mouseY <= y + 40) {
					return true;
				}
			}
			return false;
		}
		
		public static boolean isHovering(int mouseX, int mouseY, int minX, int maxX, int minY, int maxY) {
			if (mouseX >= minX && mouseX <= maxX) {
				if (mouseY >= minY && mouseY <= maxY) {
					return true;
				}
			}
			return false;
		}
		
		public static boolean isHoveringFluid(int mouseX, int mouseY, int x, int y) {
			if (mouseX >= x && mouseX <= x + 16) {
				if (mouseY >= y && mouseY <= y + 38) {
					return true;
				}
			}
			return false;
		}
		
		public static boolean isHoveringFluidLarge(int mouseX, int mouseY, int x, int y) {
			if (mouseX >= x - 1 && mouseX <= x + 16) {
				if (mouseY >= y && mouseY <= y + 57) {
					return true;
				}
			}
			return false;
		}
		
		public static boolean isHoveringButtonStandard(int mouseX, int mouseY, int x, int y) {
			if (mouseX >= x && mouseX <= x + 18) {
				if (mouseY >= y && mouseY <= y + 18) {
					return true;
				}
			}
			return false;
		}
		
		public static boolean isHoveringButton(int mouseX, int mouseY, int x, int y, int xSize, int ySize) {
			if (mouseX >= x && mouseX <= x + xSize) {
				if (mouseY >= y && mouseY <= y + ySize) {
					return true;
				}
			}
			return false;
		}
	}
	
	@Deprecated(since = "1.18.1", forRemoval = true)
	public static class TEXT_LIST {
		public static List<Component> storedTextRF(int stored, int speed) {
			MutableComponent[] description = { 
					Component.literal(Value.PURPLE + "Stored: " + Value.ORANGE + stored), 
					Component.literal(Value.GREEN + "Using: " + Value.CYAN + speed + Value.GREEN + " FE/t.")};
			
			return Arrays.asList(description);
		}
		
		public static List<Component> storedTextNo(int stored) {
			MutableComponent[] description = {
					Component.literal(Value.PURPLE + "Stored: " + Value.ORANGE + stored)};
			
			return Arrays.asList(description);
		}
		
		public static List<Component> fluidText(String name, int amount, int capacity) {
			MutableComponent[] description = {
					Component.literal(Value.CYAN + "Fluid: " + name), 
					Component.literal(Value.ORANGE + "Amount: " + amount + " / " + capacity + " mB")};
			
			return Arrays.asList(description);
		}
		
		public static List<Component> fluidTextEmpty() {
			MutableComponent[] description = { 
					Component.literal(Value.CYAN + "Empty:"), 
					Component.literal(Value.ORANGE + "Amount: 0 mB")};
			
			return Arrays.asList(description);
		}
		
		public static List<Component> emptyFluidTankDo() {
			MutableComponent[] description = { 
					Component.literal(Value.GREEN + "Empty tank."), 
					Component.literal(Value.RED + "Warning: " + Value.ORANGE + "Cannot be undone!")};
			
			return Arrays.asList(description);
		}

		public static List<Component> emptyFluidTank() {
			MutableComponent[] description = {
					Component.literal(Value.GREEN + "Shift click " + Value.LIGHT_GRAY + "to empty tank.")};
			
			return Arrays.asList(description);
		}
		
		public static List<Component> modeChange(String colourIn, String mode) {
			MutableComponent[] description = { 
					Component.literal(Value.GREEN + "Click to change mode."), 
					Component.literal(Value.LIGHT_GRAY + "Current mode: " + colourIn + mode + Value.LIGHT_GRAY + ".")};
			
			return Arrays.asList(description);
		}
		
		public static List<Component> generationText(int storedIn, int generationRateIn) {
			MutableComponent[] description = { 
					Component.literal(Value.PURPLE + "Stored: " + Value.ORANGE + storedIn), 
					Component.literal(Value.RED + "Producing: " + Value.CYAN + generationRateIn + Value.RED + " FE/t.")};
			
			return Arrays.asList(description);
		}
	}
}