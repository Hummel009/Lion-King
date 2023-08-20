package lionking.client.render;

import lionking.common.entity.LKEntityVulture;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class LKRenderVulture extends RenderLiving {
	private static final ResourceLocation texture = new ResourceLocation("lionking:mob/vulture.png");

	public LKRenderVulture(ModelBase modelbase, float f) {
		super(modelbase, f);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}

	protected float getWingRotation(EntityLivingBase entity, float f) {
		LKEntityVulture entityVulture = (LKEntityVulture) entity;
		float f1 = entityVulture.field_756_e + (entityVulture.field_752_b - entityVulture.field_756_e) * f;
		float f2 = entityVulture.field_757_d + (entityVulture.destPos - entityVulture.field_757_d) * f;
		return (MathHelper.sin(f1) + 1.0F) * f2;
	}
}
