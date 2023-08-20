package lionking.client.model;

import lionking.common.LKIngame;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LKModelLion extends ModelBase {
	private ModelRenderer head;
	private ModelRenderer headwear;
	private ModelRenderer mane;
	private ModelRenderer body;
	private ModelRenderer leg1;
	private ModelRenderer leg2;
	private ModelRenderer leg3;
	private ModelRenderer leg4;
	private ModelRenderer hat;

	public LKModelLion() {
		this(false);
	}

	public LKModelLion(boolean flag) {

		head = new ModelRenderer(this, 0, 0).setTextureSize(64, 96);
		head.addBox(-4.0F, -4.0F, -7.0F, 8, 8, 8, 0.0F);
		head.setRotationPoint(0.0F, 4.0F, -9.0F);
		head.setTextureOffset(52, 34).addBox(-2.0F, 0.0F, -9.0F, 4, 4, 2);
		headwear = new ModelRenderer(this, 32, 0).setTextureSize(64, 96);
		headwear.addBox(-4.0F, -4.0F, -7.0F, 8, 8, 8, 0.5F);
		headwear.setRotationPoint(0.0F, 4.0F, -9.0F);
		mane = new ModelRenderer(this, 0, 36).setTextureSize(64, 96);
		mane.addBox(-7.0F, -7.0F, -5.0F, 14, 14, 9, 0.0F);
		mane.setRotationPoint(0.0F, 4.0F, -9.0F);
		body = new ModelRenderer(this, 0, 68).setTextureSize(64, 96);
		body.addBox(flag ? -5.0F : -6.0F, -10.0F, -7.0F, flag ? 10 : 12, 18, 10, 0.0F);
		body.setRotationPoint(0.0F, 5.0F, 2.0F);
		leg1 = new ModelRenderer(this, 0, 19).setTextureSize(64, 96);
		leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
		leg1.setRotationPoint(-4.0F, 12.0F, 7.0F);
		leg2 = new ModelRenderer(this, 0, 19).setTextureSize(64, 96);
		leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
		leg2.setRotationPoint(4.0F, 12.0F, 7.0F);
		leg3 = new ModelRenderer(this, 0, 19).setTextureSize(64, 96);
		leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
		leg3.setRotationPoint(-4.0F, 12.0F, -5.0F);
		leg4 = new ModelRenderer(this, 0, 19).setTextureSize(64, 96);
		leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
		leg4.setRotationPoint(4.0F, 12.0F, -5.0F);
		hat = new ModelRenderer(this, 17, 25).setTextureSize(64, 96);
		hat.addBox(-4.0F, -16.0F, 0.0F, 8, 9, 1);
		hat.setRotationPoint(0.0F, 4.0F, -9.0F);

		if (flag) {
			leg1.rotationPointX += 1.0F;
			leg2.rotationPointX -= 1.0F;
			leg3.rotationPointX += 1.0F;
			leg4.rotationPointX -= 1.0F;
		}
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (isChild) {
			float f6 = 2.0F;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 8.0F * f5, 4.0F * f5);
			head.render(f5);
			headwear.render(f5);
			if (LKIngame.isChristmas()) {
				GL11.glTranslatef(0.0F, 3.0F, 0.0F);
				hat.render(f5);
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
			body.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
			GL11.glPopMatrix();
		} else {
			head.render(f5);
			headwear.render(f5);
			mane.render(f5);
			if (LKIngame.isChristmas()) {
				hat.render(f5);
			}
			body.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
		}
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleX = f4 / 57.29578F;
		head.rotateAngleY = f3 / 57.29578F;
		headwear.rotateAngleX = f4 / 57.29578F;
		headwear.rotateAngleY = f3 / 57.29578F;
		mane.rotateAngleX = f4 / 57.29578F;
		mane.rotateAngleY = f3 / 57.29578F;
		hat.rotateAngleX = f4 / 57.29578F;
		hat.rotateAngleY = f3 / 57.29578F;
		body.rotateAngleX = ((float) Math.PI) / 2.0F;
		leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		leg2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
		leg3.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
		leg4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
	}
}
