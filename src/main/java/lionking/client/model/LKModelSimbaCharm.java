package lionking.client.model;

import lionking.common.entity.LKEntitySimba;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class LKModelSimbaCharm extends ModelBase {
	private ModelRenderer body;

	public LKModelSimbaCharm() {
		body = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		body.addBox(-6.0F, -20.0F, -39.0F, 12, 1, 15, 0.0F);
		body.setRotationPoint(0.0F, 5.0F, 2.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (isChild) {
			GL11.glPushMatrix();
			GL11.glScalef(0.3F, 0.3F, 0.3F);
			if (((LKEntitySimba) entity).isSitting()) {
				GL11.glTranslatef(0.0F, 1.3F, 0.0F);
			} else {
				GL11.glTranslatef(0.0F, 1.6F, 0.2F);
			}
		} else {
			GL11.glPushMatrix();
			GL11.glScalef(0.4F, 0.4F, 0.4F);
			if (((LKEntitySimba) entity).isSitting()) {
				GL11.glTranslatef(0.0F, -0.3F, -0.5F);
			} else {
				GL11.glTranslatef(0.0F, -0.2F, -0.2F);
			}
		}
		body.render(f5);
		GL11.glPopMatrix();
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		body.rotateAngleX = ((float) Math.PI) / 2.0F;
		body.rotationPointY = 5.0F;
	}
}
