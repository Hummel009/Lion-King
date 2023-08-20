package lionking.client.render;

import lionking.client.model.LKModelZebra;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LKRenderZebra extends LKRenderLiving {
	private static final ResourceLocation texture = new ResourceLocation("lionking:mob/zebra.png");

	public LKRenderZebra() {
		super(new LKModelZebra());
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}
