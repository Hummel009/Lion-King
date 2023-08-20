package lionking.client.render;

import lionking.client.model.LKModelCrocodile;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LKRenderCrocodile extends LKRenderLiving {
	private static ResourceLocation texture = new ResourceLocation("lionking:mob/crocodile.png");

	public LKRenderCrocodile() {
		super(new LKModelCrocodile(), 0.75F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}
