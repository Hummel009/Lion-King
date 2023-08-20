package lionking.client.render;

import lionking.client.model.LKModelGiraffe;
import lionking.common.entity.LKEntityGiraffe;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class LKRenderGiraffe extends RenderLiving {
	private static ResourceLocation texture = new ResourceLocation("lionking:mob/giraffe/giraffe.png");

	private static ResourceLocation saddleTexture = new ResourceLocation("lionking:mob/giraffe/saddle.png");
	private static String[] tieNames = {"tie", "tie_white", "tie_blue", "tie_yellow", "tie_red", "tie_purple", "tie_green", "tie_black"};
	private static HashMap tieTextures = new HashMap();

	public LKRenderGiraffe(ModelBase model, ModelBase model1) {
		super(model, 0.5F);
		setRenderPassModel(model1);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase entity, int i, float f) {
		if (i == 0 && ((LKEntityGiraffe) entity).getSaddled()) {
			bindTexture(saddleTexture);
			return 1;
		} else if (i == 1 && ((LKEntityGiraffe) entity).getTie() > -1) {
			String s = tieNames[((LKEntityGiraffe) entity).getTie()];
			if (tieTextures.get(s) == null) {
				tieTextures.put(s, new ResourceLocation("lionking:mob/giraffe/" + s + ".png"));
			}
			ResourceLocation r = (ResourceLocation) tieTextures.get(s);
			bindTexture(r);
			((LKModelGiraffe) renderPassModel).tie.showModel = true;
			return 1;
		}
		return -1;
	}
}
