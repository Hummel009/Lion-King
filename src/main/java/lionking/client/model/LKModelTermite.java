package lionking.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class LKModelTermite extends ModelBase {
	ModelRenderer body;
	ModelRenderer head;
	ModelRenderer leg1;
	ModelRenderer leg2;
	ModelRenderer leg3;
	ModelRenderer leg5;
	ModelRenderer leg4;
	ModelRenderer leg6;
	ModelRenderer rightfeeler;
	ModelRenderer leftfeeler;

	public LKModelTermite() {
		body = new ModelRenderer(this, 10, 5);
		body.addBox(0.0F, 0.0F, 0.0F, 6, 6, 21);
		body.setRotationPoint(-3.0F, 17.0F, -5.0F);
		setRotation(body, 0.0F, 0.0F, 0.0F);
		head = new ModelRenderer(this, 0, 0);
		head.addBox(0.0F, 0.0F, 0.0F, 8, 8, 7);
		head.setRotationPoint(-4.0F, 14.0F, -10.0F);
		setRotation(head, 0.0F, 0.0F, 0.0F);
		leg1 = new ModelRenderer(this, 34, 0);
		leg1.addBox(0.0F, 0.0F, -2.0F, 13, 2, 2);
		leg1.setRotationPoint(0.0F, 19.0F, -1.0F);
		setRotation(leg1, 0.0F, 0.311111F, 0.311111F);
		leg2 = new ModelRenderer(this, 34, 0);
		leg2.addBox(0.0F, 0.0F, 0.0F, 13, 2, 2);
		leg2.setRotationPoint(0.0F, 19.0F, 2.0F);
		setRotation(leg2, 0.0F, 0.0F, 0.311111F);
		leg3 = new ModelRenderer(this, 34, 0);
		leg3.addBox(0.0F, 0.0F, 0.0F, 13, 2, 2);
		leg3.setRotationPoint(0.0F, 19.0F, 4.0F);
		setRotation(leg3, 0.0F, -0.6222222F, 0.311111F);
		leg4 = new ModelRenderer(this, 34, 0);
		leg4.addBox(0.0F, 0.0F, 0.0F, 13, 2, 2);
		leg4.setRotationPoint(0.0F, 19.0F, -1.0F);
		setRotation(leg4, 0.0F, 2.85F, -0.311111F);
		leg5 = new ModelRenderer(this, 34, 0);
		leg5.addBox(0.0F, 0.0F, -2.0F, 13, 2, 2);
		leg5.setRotationPoint(0.0F, 19.0F, 2.0F);
		setRotation(leg5, 0.0F, 3.11F, -0.311111F);
		leg6 = new ModelRenderer(this, 34, 0);
		leg6.addBox(0.0F, 0.0F, -2.0F, 13, 2, 2);
		leg6.setRotationPoint(0.0F, 19.0F, 4.0F);
		setRotation(leg6, 0.0F, 3.75F, -0.311111F);
		rightfeeler = new ModelRenderer(this, 50, 18);
		rightfeeler.addBox(0.0F, 0.0F, -8.0F, 1, 1, 6);
		rightfeeler.setRotationPoint(-3.0F, 15.0F, -8.0F);
		setRotation(rightfeeler, 0.0F, 0.0F, -0.1F);
		leftfeeler = new ModelRenderer(this, 50, 18);
		leftfeeler.addBox(0.0F, 0.0F, -8.0F, 1, 1, 6);
		leftfeeler.setRotationPoint(2.0F, 15.0F, -8.0F);
		setRotation(leftfeeler, 0.0F, 0.0F, 0.1F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		body.render(f5);
		head.render(f5);
		leg1.render(f5);
		leg2.render(f5);
		leg3.render(f5);
		leg4.render(f5);
		leg5.render(f5);
		leg6.render(f5);
		rightfeeler.render(f5);
		leftfeeler.render(f5);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entityliving, float f, float f1, float f2) {
		leg1.rotateAngleZ = 0.311111F + MathHelper.cos(f * 0.6662F) * f1 * 0.25F;
		leg2.rotateAngleZ = 0.311111F + MathHelper.cos(f * 0.6662F) * f1 * -0.25F;
		leg3.rotateAngleZ = 0.311111F + MathHelper.cos(f * 0.6662F) * f1 * 0.25F;

		leg4.rotateAngleZ = -0.311111F + MathHelper.cos(f * 0.6662F) * f1 * 0.25F;
		leg5.rotateAngleZ = -0.311111F + MathHelper.cos(f * 0.6662F) * f1 * -0.25F;
		leg6.rotateAngleZ = -0.311111F + MathHelper.cos(f * 0.6662F) * f1 * 0.25F;
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
