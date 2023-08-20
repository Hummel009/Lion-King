package lionking.client.render;

import lionking.client.model.LKModelScarRug;
import lionking.common.entity.LKEntityScarRug;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LKRenderScarRug extends Render {
	private static ResourceLocation textureScar = new ResourceLocation("lionking:item/rug_scar.png");
	private static ResourceLocation textureZira = new ResourceLocation("lionking:item/rug_zira.png");
	private LKModelScarRug model = new LKModelScarRug();

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glTranslatef((float) d, (float) d1 + 1.5F, (float) d2);
		bindEntityTexture(entity);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		GL11.glRotatef(entity.rotationYaw % 360.0F, 0.0F, 1.0F, 0.0F);
		model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		int i = ((LKEntityScarRug) entity).getType();
		return i == 1 ? textureZira : textureScar;
	}
}
