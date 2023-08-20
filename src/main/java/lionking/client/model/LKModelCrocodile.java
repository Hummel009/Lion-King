package lionking.client.model;

import lionking.common.entity.LKEntityCrocodile;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class LKModelCrocodile extends ModelBase {
	ModelRenderer body;
	ModelRenderer tail1;
	ModelRenderer tail2;
	ModelRenderer tail3;
	ModelRenderer jaw;
	ModelRenderer head;
	ModelRenderer legFrontLeft;
	ModelRenderer legBackLeft;
	ModelRenderer legFrontRight;
	ModelRenderer legBackRight;
	ModelRenderer spines;

	public LKModelCrocodile() {
		body = new ModelRenderer(this, 18, 83).setTextureSize(128, 128);
		body.addBox(-8.0F, -5.0F, 0.0F, 16, 9, 36);
		body.setRotationPoint(0.0F, 17.0F, -16.0F);

		tail1 = new ModelRenderer(this, 0, 28).setTextureSize(128, 128);
		tail1.addBox(-7.0F, 0.0F, 0.0F, 14, 7, 19);
		tail1.setRotationPoint(0.0F, 13.0F, 18.0F);

		tail2 = new ModelRenderer(this, 0, 55).setTextureSize(128, 128);
		tail2.addBox(-6.0F, 1.5F, 17.0F, 12, 5, 16);
		tail2.setRotationPoint(0.0F, 13.0F, 18.0F);

		tail3 = new ModelRenderer(this, 0, 77).setTextureSize(128, 128);
		tail3.addBox(-5.0F, 3.0F, 31.0F, 10, 3, 14);
		tail3.setRotationPoint(0.0F, 13.0F, 18.0F);

		jaw = new ModelRenderer(this, 58, 18).setTextureSize(128, 128);
		jaw.addBox(-6.5F, 0.3F, -19.0F, 13, 4, 19);
		jaw.setRotationPoint(0.0F, 17.0F, -16.0F);

		head = new ModelRenderer(this, 0, 0).setTextureSize(128, 128);
		head.addBox(-7.5F, -6.0F, -21.0F, 15, 6, 21);
		head.setRotationPoint(0.0F, 18.5F, -16.0F);

		legFrontLeft = new ModelRenderer(this, 2, 104).setTextureSize(128, 128);
		legFrontLeft.addBox(0.0F, 0.0F, -3.0F, 16, 3, 6);
		legFrontLeft.setRotationPoint(6.0F, 15.0F, -11.0F);

		legBackLeft = new ModelRenderer(this, 2, 104).setTextureSize(128, 128);
		legBackLeft.addBox(0.0F, 0.0F, -3.0F, 16, 3, 6);
		legBackLeft.setRotationPoint(6.0F, 15.0F, 15.0F);

		legFrontRight = new ModelRenderer(this, 2, 104).setTextureSize(128, 128);
		legFrontRight.addBox(-16.0F, 0.0F, -3.0F, 16, 3, 6);
		legFrontRight.setRotationPoint(-6.0F, 15.0F, -11.0F);
		legFrontRight.mirror = true;

		legBackRight = new ModelRenderer(this, 2, 104).setTextureSize(128, 128);
		legBackRight.addBox(-16.0F, 0.0F, -3.0F, 16, 3, 6);
		legBackRight.setRotationPoint(-6.0F, 15.0F, 15.0F);
		legBackRight.mirror = true;

		spines = new ModelRenderer(this, 46, 45).setTextureSize(128, 128);
		spines.addBox(-5.0F, 0.0F, 0.0F, 10, 4, 32);
		spines.setRotationPoint(0.0F, 9.5F, -14.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		body.render(f5);
		tail1.render(f5);
		tail2.render(f5);
		tail3.render(f5);
		jaw.render(f5);
		head.render(f5);
		legFrontLeft.render(f5);
		legBackLeft.render(f5);
		legFrontRight.render(f5);
		legBackRight.render(f5);
		spines.render(f5);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entityliving, float f, float f1, float f2) {
		legBackRight.rotateAngleY = MathHelper.cos(f * 0.6662F) * f1;
		legBackLeft.rotateAngleY = MathHelper.cos(f * 0.6662F) * f1;
		legFrontRight.rotateAngleY = MathHelper.cos(f * 0.6662F) * f1;
		legFrontLeft.rotateAngleY = MathHelper.cos(f * 0.6662F) * f1;

		tail1.rotateAngleY = MathHelper.cos(f * 0.6662F) * f1 * 0.5F;
		tail2.rotateAngleY = MathHelper.cos(f * 0.6662F) * f1 * 0.5625F;
		tail3.rotateAngleY = MathHelper.cos(f * 0.6662F) * f1 * 0.59375F;

		head.rotateAngleX = (((LKEntityCrocodile) entityliving).getSnapTime() / 20.0F) * (float) Math.PI * -0.3F;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		spines.rotateAngleX = ((float) Math.PI / 180.0F) * -2.0F;
		legBackLeft.rotateAngleZ = ((float) Math.PI / 180.0F) * 25.0F;
		legBackRight.rotateAngleZ = ((float) Math.PI / 180.0F) * -25.0F;
		legFrontLeft.rotateAngleZ = ((float) Math.PI / 180.0F) * 25.0F;
		legFrontRight.rotateAngleZ = ((float) Math.PI / 180.0F) * -25.0F;
	}
}
