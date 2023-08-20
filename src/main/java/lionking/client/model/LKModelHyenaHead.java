package lionking.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LKModelHyenaHead extends ModelBase {
	private ModelRenderer head;

	public LKModelHyenaHead(boolean isEntity) {
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3.0F, -6.0F, -3.0F, 6, 6, 6);
		head.setTextureOffset(0, 15).addBox(-3.0F, -8.0F, 0.0F, 1, 2, 2);
		head.setTextureOffset(6, 15).addBox(2.0F, -8.0F, 0.0F, 1, 2, 2);
		if (isEntity) {
			head.setRotationPoint(0.0F, 24.0F, 0.0F);
		}
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		head.render(f5);
	}
}
