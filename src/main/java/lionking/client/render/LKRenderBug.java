package lionking.client.render;

import lionking.client.model.LKModelTermite;
import lionking.client.render.LKRenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LKRenderBug extends LKRenderLiving {
	private ResourceLocation texture = new ResourceLocation("lionking:mob/bug.png");

	public LKRenderBug() {
		super(new LKModelTermite(), 0.3F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}
