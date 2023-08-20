package lionking.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LKModelZazu extends ModelBase {
	public ModelRenderer head;
	public ModelRenderer headwear;
	public ModelRenderer body;
	public ModelRenderer rightLeg;
	public ModelRenderer leftLeg;
	public ModelRenderer rightWing;
	public ModelRenderer leftWing;
	public ModelRenderer bill;
	public ModelRenderer tail;

	public LKModelZazu() {
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-2.0F, -6.0F, -2.0F, 4, 5, 3, 0.0F);
		head.setRotationPoint(0.0F, 16.0F, -4.0F);
		headwear = new ModelRenderer(this, 14, 0);
		headwear.addBox(-2.0F, -6.25F, -0.75F, 4, 5, 3, 0.25F);
		headwear.setRotationPoint(0.0F, 16.0F, -4.0F);
		bill = new ModelRenderer(this, 46, 25);
		bill.addBox(-2.0F, -4.0F, -7.0F, 4, 2, 5, 0.0F);
		bill.setRotationPoint(0.0F, 15.0F, -4.0F);
		body = new ModelRenderer(this, 0, 10);
		body.addBox(-3.0F, -4.0F, -3.0F, 5, 7, 5, 0.0F);
		body.setRotationPoint(0.5F, 16.0F, 0.5F);
		rightLeg = new ModelRenderer(this, 26, 0);
		rightLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
		rightLeg.setRotationPoint(-2.0F, 19.0F, 1.0F);
		leftLeg = new ModelRenderer(this, 26, 0);
		leftLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
		leftLeg.setRotationPoint(1.0F, 19.0F, 1.0F);
		rightWing = new ModelRenderer(this, 24, 13);
		rightWing.addBox(-0.5F, 0.0F, -3.0F, 1, 4, 6);
		rightWing.setRotationPoint(-3.0F, 15.0F, 0.0F);
		leftWing = new ModelRenderer(this, 24, 13);
		leftWing.addBox(-0.5F, 0.0F, -3.0F, 1, 4, 6);
		leftWing.setRotationPoint(3.0F, 15.0F, 0.0F);
		tail = new ModelRenderer(this, 44, 5);
		tail.addBox(-2.0F, 3.0F, 0.0F, 4, 9, 1);
		tail.setRotationPoint(0.0F, 16.0F, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (isChild) {
			float f6 = 2.0F;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 5.0F * f5, 2.0F * f5);
			head.render(f5);
			bill.render(f5);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
			body.render(f5);
			rightLeg.render(f5);
			leftLeg.render(f5);
			rightWing.render(f5);
			leftWing.render(f5);
			tail.render(f5);
			GL11.glPopMatrix();
		} else {
			headwear.render(f5);
			head.render(f5);
			bill.render(f5);
			body.render(f5);
			rightLeg.render(f5);
			leftLeg.render(f5);
			rightWing.render(f5);
			leftWing.render(f5);
			tail.render(f5);
		}
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleX = -(f4 / 57.29578F);
		headwear.rotateAngleX = -(f4 / 57.29578F) + 0.2F;
		head.rotateAngleY = f3 / 57.29578F;
		headwear.rotateAngleY = f3 / 57.29578F;
		bill.rotateAngleX = head.rotateAngleX;
		bill.rotateAngleY = head.rotateAngleY;
		body.rotateAngleX = 1.570796F;
		rightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		leftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
		rightWing.rotateAngleZ = f2 * 0.8F;
		leftWing.rotateAngleZ = -f2 * 0.8F;
		tail.rotateAngleX = 2.0F;
	}
}
