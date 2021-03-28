package com.tcn.cosmoslibrary.client.util;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.tcn.cosmoslibrary.client.enums.EnumTESRColour;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class TESRUtil {

	public static final int MAX_LIGHT_X = 0xF000F0;
	public static final int MAX_LIGHT_Y = 0xF000F0;
	
	/**
	 * Massive thanks to Ellpeck from Actually Additions, for this code.
	 * @author Ellpeck
	 */
	@SuppressWarnings("resource")
	public static void renderLaser(double firstX, double firstY, double firstZ, double secondX, double secondY, double secondZ, double rotationTime, float alpha, double beamWidth, EnumTESRColour enum_colour) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder render = tessellator.getBuilder();
		World world = Minecraft.getInstance().level;
		
		float[] colour = enum_colour.getColour();
		
		float r = colour[0];
		float g = colour[1];
		float b = colour[2];
		
		Vector3d vector_one = new Vector3d(firstX, firstY, firstZ);
		Vector3d vector_two = new Vector3d(secondX, secondY, secondZ);
		Vector3d vector_combined = vector_two.subtract(vector_one);
		
		double rotation = rotationTime > 0 ? (360D*((world.getGameTime()%rotationTime) / rotationTime)) : 0;
		double pitch = Math.atan2(vector_combined.y, Math.sqrt(vector_combined.x * vector_combined.x + vector_combined.z * vector_combined.z));
		double yaw = Math.atan2(-vector_combined.z, vector_combined.x);
		
		double length = vector_combined.length();
		
		GL11.glPushMatrix();
		
		GlStateManager._disableLighting();
		GlStateManager._enableBlend();
		GlStateManager._blendFunc(SourceFactor.SRC_ALPHA.value, DestFactor.ONE.value);
		
		int func = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC);
		float ref = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF);
		
		GlStateManager._alphaFunc(GL11.GL_ALWAYS, 0);
		//GlStateManager.translated(firstX - TileEntityRendererDispatcher.instance.staticPlayerX, firstY - TileEntityRendererDispatcher.staticPlayerY, firstZ - TileEntityRendererDispatcher.staticPlayerZ);
		GlStateManager._rotatef((float) (180 * yaw / Math.PI), 0, 1, 0);
		GlStateManager._rotatef((float) (180 * pitch / Math.PI), 0, 0, 1);
		GlStateManager._rotatef((float) rotation, 1, 0, 0);
		
		GlStateManager._disableTexture();
		render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LIGHTMAP_COLOR);
		for (double i = 0; i < 4; i++) {
			double width = beamWidth * (i / 4.0);
			
			render.vertex(length, width, width).uv(0.0F, 0.0F).uv2(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.vertex(0, width, width).uv(0.0F, 0.0F).uv2(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.vertex(0, -width, width).uv(0.0F, 0.0F).uv2(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.vertex(length, -width, width).uv(0.0F, 0.0F).uv2(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();

            render.vertex(length, -width, -width).uv(0.0F, 0.0F).uv2(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.vertex(0, -width, -width).uv(0.0F, 0.0F).uv2(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.vertex(0, width, -width).uv(0.0F, 0.0F).uv2(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.vertex(length, width, -width).uv(0.0F, 0.0F).uv2(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();

            render.vertex(length, width, -width).uv(0.0F, 0.0F).uv2(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.vertex(0, width, -width).uv(0.0F, 0.0F).uv2(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.vertex(0, width, width).uv(0.0F, 0.0F).uv2(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.vertex(length, width, width).uv(0.0F, 0.0F).uv2(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();

            render.vertex(length, -width, width).uv(0.0F, 0.0F).uv2(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.vertex(0, -width, width).uv(0.0F, 0.0F).uv2(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.vertex(0, -width, -width).uv(0.0F, 0.0F).uv2(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.vertex(length, -width, -width).uv(0.0F, 0.0F).uv2(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
		}
		
		tessellator.end();
		
		GlStateManager._enableTexture();
		
		GlStateManager._alphaFunc(func, ref);
		GlStateManager._blendFunc(SourceFactor.SRC_ALPHA.value, DestFactor.ONE_MINUS_SRC_ALPHA.value);
		GlStateManager._disableBlend();
		GlStateManager._enableLighting();
		GL11.glPopMatrix();
	}
}