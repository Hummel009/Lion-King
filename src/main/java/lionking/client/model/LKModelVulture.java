package lionking.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LKModelVulture extends ModelBase {
	ModelRenderer body;
	ModelRenderer tail;
	ModelRenderer legLeft;
	ModelRenderer legRight;
	ModelRenderer neck;
	ModelRenderer head;
	ModelRenderer wingLeft;
	ModelRenderer wingRight;

	public LKModelVulture() {
		body = new ModelRenderer(this, 0, 46).setTextureSize(64, 64);
		body.addBox(0.0F, 0.0F, 0.0F, 8, 10, 8);
		body.setRotationPoint(-4.0F, 8.0F, -4.0F);

		tail = new ModelRenderer(this, 44, 50).setTextureSize(64, 64);
		tail.addBox(0.0F, 0.0F, 0.0F, 6, 10, 4);
		tail.setRotationPoint(-3.0F, 13.0F, 4.0F);

		legLeft = new ModelRenderer(this, 40, 0).setTextureSize(64, 64);
		legLeft.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2);
		legLeft.setRotationPoint(1.0F, 12.0F, 2.0F);

		legRight = new ModelRenderer(this, 40, 0).setTextureSize(64, 64);
		legRight.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2);
		legRight.setRotationPoint(-3.0F, 12.0F, 2.0F);
		legRight.mirror = true;

		neck = new ModelRenderer(this, 0, 14).setTextureSize(64, 64);
		neck.addBox(0.0F, 0.0F, 0.0F, 4, 5, 4);
		neck.setRotationPoint(-2.0F, 3.0F, -4.0F);

		head = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		head.addBox(-3.0F, -2.0F, -3.0F, 6, 6, 6);
		head.setRotationPoint(0.5F, 0.0F, -2.5F);

		head.setTextureOffset(28, 0).addBox(-1.5F, 1.5F, -6.9F, 3, 2, 2);
		head.setTextureOffset(46, 29).addBox(-2.0F, 0.5F, -7.0F, 4, 2, 5);

		wingLeft = new ModelRenderer(this, 0, 26).setTextureSize(64, 64);
		wingLeft.addBox(0.0F, 0.0F, 0.0F, 2, 10, 7);
		wingLeft.setRotationPoint(4.0F, 5.0F, -2.0F);

		wingRight = new ModelRenderer(this, 0, 26).setTextureSize(64, 64);
		wingRight.addBox(-2.0F, 0.0F, 0.0F, 2, 10, 7);
		wingRight.setRotationPoint(-4.0F, 5.0F, -2.0F);
		wingRight.mirror = true;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		body.render(f5);
		tail.render(f5);
		legLeft.render(f5);
		legRight.render(f5);
		neck.render(f5);
		head.render(f5);
		wingLeft.render(f5);
		wingRight.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleX = -(f4 / 57.29578F);
		head.rotateAngleY = f3 / 57.29578F;
		body.rotateAngleX = 0.698131F;
		tail.rotateAngleX = body.rotateAngleX;
		neck.rotateAngleX = 0.4364323F;
		legRight.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		legLeft.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
		wingRight.rotateAngleZ = f2;
		wingLeft.rotateAngleZ = -f2;
	}
}
