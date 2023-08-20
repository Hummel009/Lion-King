package lionking.client.render;

import lionking.client.model.LKModelGemsbok;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LKRenderGemsbok extends LKRenderLiving {
	private ResourceLocation texture = new ResourceLocation("lionking:mob/gemsbok.png");

	public LKRenderGemsbok() {
		super(new LKModelGemsbok());
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}
