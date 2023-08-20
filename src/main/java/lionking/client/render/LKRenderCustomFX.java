package lionking.client.render;

import lionking.client.LKClientProxy;
import lionking.client.effect.LKEntityCustomFX;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class LKRenderCustomFX extends Render {
	public static float ticks;

	public LKRenderCustomFX() {
		shadowSize = 0.0F;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return LKClientProxy.iconsTexture;
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float ticks, float yaw) {
		LKEntityCustomFX entityfx = (LKEntityCustomFX) entity;
		EntityPlayer entityplayer = Minecraft.getMinecraft().thePlayer;
		float f = ActiveRenderInfo.rotationX;
		float f1 = ActiveRenderInfo.rotationZ;
		float f2 = ActiveRenderInfo.rotationYZ;
		float f3 = ActiveRenderInfo.rotationXY;
		float f4 = ActiveRenderInfo.rotationXZ;
		EntityFX.interpPosX = entityplayer.lastTickPosX + (entityplayer.posX - entityplayer.lastTickPosX) * ticks;
		EntityFX.interpPosY = entityplayer.lastTickPosY + (entityplayer.posY - entityplayer.lastTickPosY) * ticks;
		EntityFX.interpPosZ = entityplayer.lastTickPosZ + (entityplayer.posZ - entityplayer.lastTickPosZ) * ticks;

		bindEntityTexture(entity);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();

		tessellator.setBrightness(entityfx.getBrightnessForRender(ticks));
		entityfx.renderParticle(tessellator, ticks, f, f4, f1, f2, f3);

		tessellator.draw();
	}
}
