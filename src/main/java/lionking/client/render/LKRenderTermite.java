package lionking.client.render;

import lionking.client.model.LKModelTermite;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LKRenderTermite extends LKRenderLiving {
	private ResourceLocation texture = new ResourceLocation("lionking:mob/termite.png");

	public LKRenderTermite() {
		super(new LKModelTermite(), 0.3F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}
