package lionking.client.render;

import lionking.client.model.LKModelTimon;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LKRenderTimon extends LKRenderLiving {
	private ResourceLocation texture = new ResourceLocation("lionking:mob/timon.png");

	public LKRenderTimon() {
		super(new LKModelTimon(), 0.5F, "Timon");
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}
