package lionking.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class LKModelTermiteQueen extends ModelBase {
	private ModelRenderer body;
	private ModelRenderer tail1;
	private ModelRenderer tail2;
	private ModelRenderer head;
	private ModelRenderer leg1;
	private ModelRenderer leg2;
	private ModelRenderer leg3;
	private ModelRenderer leg4;
	private ModelRenderer leg5;
	private ModelRenderer leg6;

	public LKModelTermiteQueen() {
		body = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
		body.addBox(-5.0F, -4.0F, 0.0F, 10, 8, 26);
		body.setRotationPoint(0.0F, 13.0F, -11.0F);

		tail1 = new ModelRenderer(this, 0, 35).setTextureSize(128, 64);
		tail1.addBox(-4.0F, -3.0F, 0.0F, 8, 6, 12);
		tail1.setRotationPoint(0.0F, 13.0F, 14.0F);

		tail2 = new ModelRenderer(this, 42, 37).setTextureSize(128, 64);
		tail2.addBox(-3.0F, -2.0F, 0.0F, 6, 4, 12);
		tail2.setRotationPoint(0.0F, 13.0F, 25.0F);

		head = new ModelRenderer(this, 47, 11).setTextureSize(128, 64);
		head.addBox(-3.0F, -3.0F, -8.0F, 6, 6, 8);
		head.setRotationPoint(0.0F, 13.0F, -11.0F);
		head.setTextureOffset(47, 4).addBox(-2.5F, -2.5F, -13.0F, 1, 1, 5);
		head.setTextureOffset(47, 4).addBox(1.5F, -2.5F, -13.0F, 1, 1, 5);

		leg2 = new ModelRenderer(this, 96, 6).setTextureSize(128, 64);
		leg2.addBox(-1.0F, 0.0F, -1.0F, 2, 9, 2);
		leg2.setRotationPoint(5.0F, 13.0F, -8.0F);
		leg2.setTextureOffset(85, 18).addBox(-13.5F, 7.5F, -0.5F, 14, 1, 1);

		leg4 = new ModelRenderer(this, 96, 6).setTextureSize(128, 64);
		leg4.addBox(-1.0F, 0.0F, -1.0F, 2, 9, 2);
		leg4.setRotationPoint(5.0F, 13.0F, -1.0F);
		leg4.setTextureOffset(85, 18).addBox(-13.5F, 7.5F, -0.5F, 14, 1, 1);

		leg6 = new ModelRenderer(this, 96, 6).setTextureSize(128, 64);
		leg6.addBox(-1.0F, 0.0F, -1.0F, 2, 9, 2);
		leg6.setRotationPoint(5.0F, 13.0F, 10.0F);
		leg6.setTextureOffset(85, 18).addBox(-13.5F, 7.5F, -0.5F, 14, 1, 1);

		leg1 = new ModelRenderer(this, 96, 6).setTextureSize(128, 64);
		leg1.addBox(-1.0F, 0.0F, -1.0F, 2, 9, 2);
		leg1.setRotationPoint(-5.0F, 13.0F, -8.0F);
		leg1.setTextureOffset(85, 18).addBox(-13.5F, 7.5F, -0.5F, 14, 1, 1);

		leg3 = new ModelRenderer(this, 96, 6).setTextureSize(128, 64);
		leg3.addBox(-1.0F, 0.0F, -1.0F, 2, 9, 2);
		leg3.setRotationPoint(-5.0F, 13.0F, -1.0F);
		leg3.setTextureOffset(85, 18).addBox(-13.5F, 7.5F, -0.5F, 14, 1, 1);

		leg5 = new ModelRenderer(this, 96, 6).setTextureSize(128, 64);
		leg5.addBox(-1.0F, 0.0F, -1.0F, 2, 9, 2);
		leg5.setRotationPoint(-5.0F, 13.0F, 10.0F);
		leg5.setTextureOffset(85, 18).addBox(-13.5F, 7.5F, -0.5F, 14, 1, 1);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		body.render(f5);
		tail1.render(f5);
		tail2.render(f5);
		head.render(f5);
		leg2.render(f5);
		leg4.render(f5);
		leg6.render(f5);
		leg1.render(f5);
		leg3.render(f5);
		leg5.render(f5);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entityliving, float f, float f1, float f2) {
		leg2.rotateAngleZ = ((float) Math.PI / 180.0F * -100.0F) + MathHelper.cos(f * 0.6662F) * f1 * 0.25F;
		leg4.rotateAngleZ = ((float) Math.PI / 180.0F * -100.0F) + MathHelper.cos(f * 0.6662F) * f1 * -0.25F;
		leg6.rotateAngleZ = ((float) Math.PI / 180.0F * -100.0F) + MathHelper.cos(f * 0.6662F) * f1 * 0.25F;

		leg1.rotateAngleZ = ((float) Math.PI / 180.0F * -80.0F) + MathHelper.cos(f * 0.6662F) * f1 * 0.25F;
		leg3.rotateAngleZ = ((float) Math.PI / 180.0F * -80.0F) + MathHelper.cos(f * 0.6662F) * f1 * -0.25F;
		leg5.rotateAngleZ = ((float) Math.PI / 180.0F * -80.0F) + MathHelper.cos(f * 0.6662F) * f1 * 0.25F;

		tail1.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1 * 0.15F;
		tail2.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1 * 0.2F;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleX = f4 / (180.0F / (float) Math.PI);
		head.rotateAngleY = f3 / (180.0F / (float) Math.PI);

		leg1.rotateAngleX = (float) Math.PI;
		leg3.rotateAngleX = (float) Math.PI;
		leg5.rotateAngleX = (float) Math.PI;
	}
}
