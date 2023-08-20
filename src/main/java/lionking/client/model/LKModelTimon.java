package lionking.client.model;

import lionking.common.LKIngame;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LKModelTimon extends ModelBase {
	private ModelRenderer head;
	private ModelRenderer hat;
	private ModelRenderer body;
	private ModelRenderer rightarm;
	private ModelRenderer leftarm;
	private ModelRenderer rightleg;
	private ModelRenderer leftleg;
	private ModelRenderer tail;
	private ModelRenderer leftear;
	private ModelRenderer rightear;

	public LKModelTimon() {
		head = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		head.addBox(-4.0F, -8.0F, -4.0F, 8, 7, 6);
		head.setRotationPoint(0.0F, 1.0F, 1.0F);
		head.setTextureOffset(46, 7).addBox(-3.5F, -9.0F, -2.0F, 7, 1, 2);
		head.setTextureOffset(0, 0).addBox(-1.0F, -5.0F, -5.0F, 2, 2, 1);

		hat = new ModelRenderer(this, 0, 31).setTextureSize(64, 64);
		hat.addBox(-4.0F, -17.0F, -1.0F, 8, 9, 1);
		hat.setRotationPoint(0.0F, 1.0F, 1.0F);

		body = new ModelRenderer(this, 40, 12).setTextureSize(64, 64);
		body.addBox(-4.0F, 0.0F, -2.0F, 8, 15, 4);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);

		rightarm = new ModelRenderer(this, 20, 13).setTextureSize(64, 64);
		rightarm.addBox(-3.0F, -2.0F, -2.0F, 3, 12, 3);
		rightarm.setRotationPoint(-4.0F, 3.0F, 0.5F);

		leftarm = new ModelRenderer(this, 20, 13).setTextureSize(64, 64);
		leftarm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 3);
		leftarm.setRotationPoint(5.0F, 3.0F, 0.5F);

		rightleg = new ModelRenderer(this, 0, 13).setTextureSize(64, 64);
		rightleg.addBox(-2.0F, 0.0F, -2.0F, 3, 9, 3);
		rightleg.setRotationPoint(-1.7F, 15.0F, 0.5F);
		rightleg.setTextureOffset(44, 0).addBox(-2.7F, 8.0F, -3.9F, 4, 1, 6);

		leftleg = new ModelRenderer(this, 0, 13).setTextureSize(64, 64);
		leftleg.addBox(-2.0F, 0.0F, -2.0F, 3, 9, 3);
		leftleg.setRotationPoint(2.7F, 15.0F, 0.5F);
		leftleg.setTextureOffset(44, 0).addBox(-2.3F, 8.0F, -3.9F, 4, 1, 6);

		tail = new ModelRenderer(this, 0, 13).setTextureSize(64, 64);
		tail.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 16);
		tail.setRotationPoint(0.0F, 9.0F, 1.0F);

		leftear = new ModelRenderer(this, 36, 0).setTextureSize(64, 64);
		leftear.addBox(-7.0F, -5.0F, -1.5F, 3, 5, 1);
		leftear.setRotationPoint(0.0F, 1.0F, 1.0F);

		rightear = new ModelRenderer(this, 36, 0).setTextureSize(64, 64);
		rightear.addBox(4.0F, -5.0F, -1.5F, 3, 5, 1);
		rightear.setRotationPoint(0.0F, 1.0F, 1.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.render(f5);
		if (LKIngame.isChristmas()) {
			hat.render(f5);
		}
		body.render(f5);
		rightarm.render(f5);
		leftarm.render(f5);
		rightleg.render(f5);
		leftleg.render(f5);
		tail.render(f5);
		leftear.render(f5);
		rightear.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleY = f3 / 57.29578F;
		head.rotateAngleX = f4 / 57.29578F;
		hat.rotateAngleY = f3 / 57.29578F;
		hat.rotateAngleX = f4 / 57.29578F;
		rightarm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		leftarm.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
		rightleg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		leftleg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
		tail.rotateAngleX = -0.908F + (f4 / 57.29578F);
		leftear.rotateAngleZ = 0.38397F;
		leftear.rotateAngleY = head.rotateAngleY;
		leftear.rotateAngleX = head.rotateAngleX;
		rightear.rotateAngleZ = -0.38397F;
		rightear.rotateAngleY = head.rotateAngleY;
		rightear.rotateAngleX = head.rotateAngleX;
	}
}
