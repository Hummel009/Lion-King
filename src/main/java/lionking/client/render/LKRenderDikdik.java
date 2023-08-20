package lionking.client.render;

import lionking.client.model.LKModelDikdik;
import lionking.common.entity.LKEntityDikdik;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class LKRenderDikdik extends LKRenderLiving {
	private static HashMap textures = new HashMap();

	public LKRenderDikdik() {
		super(new LKModelDikdik(), 0.8F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		int i = ((LKEntityDikdik) entity).getDikdikType();
		if (textures.get(i) == null) {
			textures.put(i, new ResourceLocation("lionking:mob/dikdik_" + i + ".png"));
		}
		return (ResourceLocation) textures.get(i);
	}
}
