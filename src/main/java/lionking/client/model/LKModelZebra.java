package lionking.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LKModelZebra extends ModelBase {
	public ModelRenderer neck;
	public ModelRenderer body;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;
	public ModelRenderer head;
	public ModelRenderer mane;
	public ModelRenderer tail;

	public LKModelZebra() {
		neck = new ModelRenderer(this, 98, 26).setTextureSize(128, 64);
		neck.addBox(-5.0F, -4.0F, -6.0F, 6, 8, 9);
		neck.setRotationPoint(2.0F, 3.0F, -6.0F);

		body = new ModelRenderer(this, 0, 33).setTextureSize(128, 64);
		body.addBox(-6.0F, -10.0F, -7.0F, 12, 10, 21);
		body.setRotationPoint(0.0F, 12.0F, -1.0F);

		leg1 = new ModelRenderer(this, 0, 16).setTextureSize(128, 64);
		leg1.addBox(-3.0F, 0.0F, -2.0F, 4, 12, 4);
		leg1.setRotationPoint(-3.0F, 12.0F, 10.0F);

		leg2 = new ModelRenderer(this, 0, 16).setTextureSize(128, 64);
		leg2.addBox(-1.0F, 0.0F, -2.0F, 4, 12, 4);
		leg2.setRotationPoint(3.0F, 12.0F, 10.0F);

		leg3 = new ModelRenderer(this, 0, 16).setTextureSize(128, 64);
		leg3.addBox(-3.0F, 0.0F, -3.0F, 4, 12, 4);
		leg3.setRotationPoint(-3.0F, 12.0F, -5.0F);

		leg4 = new ModelRenderer(this, 0, 16).setTextureSize(128, 64);
		leg4.addBox(-1.0F, 0.0F, -3.0F, 4, 12, 4);
		leg4.setRotationPoint(3.0F, 12.0F, -5.0F);

		head = new ModelRenderer(this, 84, 0).setTextureSize(128, 64);
		head.addBox(0.0F, 0.0F, 0.0F, 8, 8, 14);
		head.setRotationPoint(-4.0F, -3.0F, -19.0F);

		head.setTextureOffset(72, 0).addBox(1.0F, -3.0F, 10.0F, 1, 3, 2);
		head.setTextureOffset(78, 0).addBox(6.0F, -3.0F, 10.0F, 1, 3, 2);

		mane = new ModelRenderer(this, 92, 47).setTextureSize(128, 64);
		mane.addBox(0.0F, 0.0F, 0.0F, 4, 3, 14);
		mane.setRotationPoint(-2.0F, 5.0F, -1.0F);

		tail = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
		tail.addBox(-1.0F, -4.0F, 13.5F, 2, 12, 2);
		tail.setRotationPoint(0.0F, 12.0F, -1.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (isChild) {
			float f6 = 2.0F;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 8.0F * f5, 4.0F * f5);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
			head.render(f5);
			mane.render(f5);
			neck.render(f5);
			body.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
			tail.render(f5);
			GL11.glPopMatrix();
		} else {
			head.render(f5);
			body.render(f5);
			mane.render(f5);
			neck.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
			tail.render(f5);
		}
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		neck.rotateAngleX = -1.064651F;
		head.rotateAngleX = 0.4014257F;
		mane.rotateAngleX = 2.111848F;
		leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		leg2.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
		leg3.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
		leg4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		tail.rotateAngleX = 0.296706F;
	}
}