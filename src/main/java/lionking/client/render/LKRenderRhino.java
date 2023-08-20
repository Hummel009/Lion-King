package lionking.client.render;

import lionking.client.model.LKModelRhino;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LKRenderRhino extends LKRenderLiving {
	private ResourceLocation texture = new ResourceLocation("lionking:mob/rhino.png");

	public LKRenderRhino() {
		super(new LKModelRhino(), 1.2F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}
