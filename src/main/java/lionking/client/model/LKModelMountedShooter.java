package lionking.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LKModelMountedShooter extends ModelBase {
	private ModelRenderer body;
	private ModelRenderer leg1;
	private ModelRenderer leg2;

	public LKModelMountedShooter() {
		body = new ModelRenderer(this, 0, 0);
		body.addBox(-1.0F, -2.0F, -8.0F, 4, 4, 16);
		body.setRotationPoint(-1.0F, 16.0F, 0.0F);

		leg1 = new ModelRenderer(this, 0, 0);
		leg1.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1);
		leg1.setRotationPoint(-1.5F, 17.0F, -1.0F);
		leg1.rotateAngleZ = 25.0F / 180.0F * (float) Math.PI;

		leg2 = new ModelRenderer(this, 0, 0);
		leg2.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1);
		leg2.setRotationPoint(1.5F, 17.0F, -1.0F);
		leg2.rotateAngleZ = -25.0F / 180.0F * (float) Math.PI;
		leg2.mirror = true;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		body.rotateAngleX = f4 / 180.0F * (float) Math.PI;
		leg1.render(f5);
		leg2.render(f5);
		body.render(f5);
	}
}
