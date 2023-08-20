package lionking.client.render;

import lionking.client.model.LKModelLion;
import lionking.common.entity.LKEntityOutlandess;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LKRenderOutlander extends LKRenderLiving {
	private ResourceLocation textureLion = new ResourceLocation("lionking:mob/outlander.png");
	private ResourceLocation textureLioness = new ResourceLocation("lionking:mob/outlandess.png");

	public LKRenderOutlander() {
		super(new LKModelLion(true));
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return entity instanceof LKEntityOutlandess ? textureLioness : textureLion;
	}
}
