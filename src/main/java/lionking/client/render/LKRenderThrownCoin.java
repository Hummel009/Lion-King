package lionking.client.render;

import lionking.common.entity.LKEntityCoin;
import lionking.common.LKMod;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class LKRenderThrownCoin extends Render {
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationItemsTexture;
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		Icon icon = ((LKEntityCoin) entity).getCoinType() == 0 ? LKMod.rafikiCoin.getIconFromDamage(0) : LKMod.ziraCoin.getIconFromDamage(0);
		if (icon != null) {
			GL11.glPushMatrix();
			GL11.glTranslatef((float) d, (float) d1, (float) d2);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			bindEntityTexture(entity);
			renderThrowable(Tessellator.instance, icon);
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
		}
	}

	private void renderThrowable(Tessellator tessellator, Icon icon) {
		float f = icon.getMinU();
		float f1 = icon.getMaxU();
		float f2 = icon.getMinV();
		float f3 = icon.getMaxV();
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;
		GL11.glRotatef(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.addVertexWithUV((0.0F - f5), (0.0F - f6), 0.0D, f, f3);
		tessellator.addVertexWithUV((f4 - f5), (0.0F - f6), 0.0D, f1, f3);
		tessellator.addVertexWithUV((f4 - f5), (f4 - f6), 0.0D, f1, f2);
		tessellator.addVertexWithUV((0.0F - f5), (f4 - f6), 0.0D, f, f2);
		tessellator.draw();
	}
}
