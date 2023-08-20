package lionking.client.render;

import lionking.client.model.LKModelSimba;
import lionking.client.model.LKModelSimbaCharm;
import lionking.common.entity.LKEntitySimba;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LKRenderSimba extends RenderLiving {
	private static final ResourceLocation texture = new ResourceLocation("lionking:mob/simba.png");
	private static final ResourceLocation textureCharm = new ResourceLocation("lionking:mob/simba_charm.png");

	public LKRenderSimba() {
		super(new LKModelSimba(), 0.5F);
		setRenderPassModel(new LKModelSimbaCharm());
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}

	public void doRenderLiving(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
		LKEntitySimba simba = (LKEntitySimba) entity;
		super.doRenderLiving(simba, d, d1, d2, f, f1);
		d1 += 0.25D;
		renderLivingLabel(simba, "Simba", d, d1, d2, 64);
		renderHealthBar(simba, d, d1, d2, 64);
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase entityliving, int i, float f) {
		bindTexture(textureCharm);
		if (i == 0 && ((LKEntitySimba) entityliving).hasCharm()) {
			return 1;
		}
		return -1;
	}

	private void renderHealthBar(LKEntitySimba simba, double d, double d1, double d2, int i) {
		float f = simba.getDistanceToEntity(renderManager.livingPlayer);

		if (f <= i) {
			float f1 = 1.6F;
			float f2 = 0.016666668F * f1;
			GL11.glPushMatrix();
			GL11.glTranslatef((float) d, (float) d1 + 2.3F, (float) d2);
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
			GL11.glScalef(-f2, -f2, f2);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDepthMask(false);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			Tessellator tessellator = Tessellator.instance;
			GL11.glDisable(GL11.GL_TEXTURE_2D);

			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(1.0F, 0.0F, 0.0F, 1.0F);
			tessellator.addVertex(-19.0D, 16.0D, 0.0D);
			tessellator.addVertex(-19.0D, 21.0D, 0.0D);
			tessellator.addVertex(19.0D, 21.0D, 0.0D);
			tessellator.addVertex(19.0D, 16.0D, 0.0D);
			tessellator.draw();

			double healthRemaining = (double) simba.getHealth() / simba.getMaxHealth();
			if (healthRemaining < 0.0D) {
				healthRemaining = 0.0D;
			}

			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(0.0F, 1.0F, 0.0F, 1.0F);
			tessellator.addVertex(-19.0D, 16.0D, 0.0D);
			tessellator.addVertex(-19.0D, 21.0D, 0.0D);
			tessellator.addVertex(-19.0D + (38.0D * healthRemaining), 21.0D, 0.0D);
			tessellator.addVertex(-19.0D + (38.0D * healthRemaining), 16.0D, 0.0D);
			tessellator.draw();

			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
		}
	}
}
