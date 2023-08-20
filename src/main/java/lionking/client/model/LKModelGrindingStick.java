package lionking.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LKModelGrindingStick extends ModelBase {
	private ModelRenderer stick;

	public LKModelGrindingStick() {
		stick = new ModelRenderer(this, 0, 0);
		stick.addBox(-0.5F, -11.0F, -0.5F, 1, 12, 1);
		stick.setRotationPoint(0.0F, 18.0F, 0.0F);
		stick.rotateAngleX = (float) Math.PI / 180.0F * -35.0F;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		stick.render(f5);
	}
}
