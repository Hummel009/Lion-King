package lionking.client.model;

import lionking.common.entity.LKEntitySkeletalHyena;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class LKModelHyena extends ModelBase {
	private ModelRenderer head;
	private ModelRenderer body;
	private ModelRenderer leg1;
	private ModelRenderer leg2;
	private ModelRenderer leg3;
	private ModelRenderer leg4;
	private ModelRenderer tail;

	public LKModelHyena() {
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3.0F, -3.0F, -2.0F, 6, 6, 6);
		head.setTextureOffset(0, 15).addBox(-3.0F, -5.0F, 1.0F, 1, 2, 2);
		head.setTextureOffset(6, 15).addBox(2.0F, -5.0F, 1.0F, 1, 2, 2);
		head.setRotationPoint(-1.0F, 13.5F, -9.0F);

		body = new ModelRenderer(this, 28, 11);
		body.addBox(-4.0F, -8.0F, -3.0F, 6, 15, 6);
		body.setTextureOffset(16, 20).addBox(-2.0F, -8.0F, 3.0F, 2, 11, 1);
		body.setRotationPoint(0.0F, 14.0F, 2.0F);

		leg1 = new ModelRenderer(this, 0, 22);
		leg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2);
		leg1.setRotationPoint(-2.5F, 16.0F, 7.0F);

		leg2 = new ModelRenderer(this, 0, 22);
		leg2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2);
		leg2.setRotationPoint(0.5F, 16.0F, 7.0F);

		leg3 = new ModelRenderer(this, 0, 22);
		leg3.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2);
		leg3.setRotationPoint(-2.5F, 16.0F, -4.0F);

		leg4 = new ModelRenderer(this, 0, 22);
		leg4.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2);
		leg4.setRotationPoint(0.5F, 16.0F, -4.0F);

		tail = new ModelRenderer(this, 16, 20);
		tail.addBox(-1.0F, 1.5F, -1.0F, 2, 9, 1);
		tail.setRotationPoint(-1.0F, 12.0F, 8.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (entity instanceof LKEntitySkeletalHyena) {
			if (!((LKEntitySkeletalHyena) entity).isHeadless()) {
				head.render(f5);
			}
		} else {
			head.render(f5);
		}
		body.render(f5);
		leg1.render(f5);
		leg2.render(f5);
		leg3.render(f5);
		leg4.render(f5);
		tail.render(f5);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entityliving, float f, float f1, float f2) {
		tail.rotateAngleY = 0.0F;
		body.rotateAngleX = 1.570796F;
		leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		leg2.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
		leg3.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
		leg4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		tail.rotateAngleX = 1.2F;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleX = f4 / 57.29578F;
		head.rotateAngleY = f3 / 57.29578F;
	}
}
