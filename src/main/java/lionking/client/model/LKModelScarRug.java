package lionking.client.model;

import lionking.common.entity.LKEntityScarRug;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class LKModelScarRug extends ModelBase {
	private ModelRenderer body;
	private ModelRenderer mane;
	private ModelRenderer head;
	private ModelRenderer leg1;
	private ModelRenderer leg2;
	private ModelRenderer leg3;
	private ModelRenderer leg4;
	private ModelRenderer tail;

	public LKModelScarRug() {
		body = new ModelRenderer(this, 20, 0).setTextureSize(64, 64);
		body.addBox(0.0F, 0.0F, 0.0F, 20, 25, 2);
		body.setRotationPoint(-10.0F, 24.0F, -10.0F);

		mane = new ModelRenderer(this, 0, 43).setTextureSize(64, 64);
		mane.addBox(0.0F, 0.0F, 0.0F, 14, 12, 9);
		mane.setRotationPoint(-7.0F, 12.0F, -18.0F);

		head = new ModelRenderer(this, 32, 27).setTextureSize(64, 64);
		head.addBox(0.0F, 0.0F, 0.0F, 8, 8, 8);
		head.setRotationPoint(-4.0F, 15.0F, -20.0F);
		head.setTextureOffset(52, 45).addBox(2.0F, 4.0F, -2.0F, 4, 4, 2);

		leg1 = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		leg1.addBox(0.0F, 0.0F, -4.0F, 2, 12, 4);
		leg1.setRotationPoint(-8.0F, 22.1F, 14.0F);

		leg2 = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		leg2.addBox(-2.0F, 0.0F, -4.0F, 2, 12, 4);
		leg2.setRotationPoint(8.0F, 22.1F, 14.0F);

		leg3 = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		leg3.addBox(0.0F, 0.0F, 0.0F, 2, 12, 4);
		leg3.setRotationPoint(-8.0F, 22.1F, -10.0F);

		leg4 = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		leg4.addBox(-2.0F, 0.0F, 0.0F, 2, 12, 4);
		leg4.setRotationPoint(8.0F, 22.1F, -10.0F);

		tail = new ModelRenderer(this, 0, 24).setTextureSize(64, 64);
		tail.addBox(-1.5F, 0.0F, 0.0F, 3, 1, 12);
		tail.setRotationPoint(0.0F, 22.05F, 14.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles();
		body.render(f5);
		GL11.glPushMatrix();
		if (((LKEntityScarRug) entity).getType() == 1) {
			GL11.glTranslatef(0.0F, 0.0F, 0.125F);
		}
		mane.render(f5);
		head.render(f5);
		GL11.glPopMatrix();
		leg1.render(f5);
		leg2.render(f5);
		leg3.render(f5);
		leg4.render(f5);
		tail.render(f5);
	}

	public void setRotationAngles() {
		float f = ((float) Math.PI) / 180.0F;

		body.rotateAngleX = f * 90.0F;

		leg1.rotateAngleX = f * 22.0F;
		leg1.rotateAngleZ = f * 90.0F;

		leg2.rotateAngleX = f * 22.0F;
		leg2.rotateAngleZ = f * -90.0F;

		leg3.rotateAngleX = f * -22.0F;
		leg3.rotateAngleZ = f * 90.0F;

		leg4.rotateAngleX = f * -22.0F;
		leg4.rotateAngleZ = f * -90.0F;

		tail.rotateAngleX = f * -4.0F;
	}
}
