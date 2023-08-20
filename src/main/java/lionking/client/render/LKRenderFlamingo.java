package lionking.client.render;

import lionking.common.entity.LKEntityFlamingo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class LKRenderFlamingo extends RenderLiving {
	private ResourceLocation texture = new ResourceLocation("lionking:mob/flamingo.png");
	private ResourceLocation textureChick = new ResourceLocation("lionking:mob/flamingo_chick.png");

	public LKRenderFlamingo(ModelBase modelbase) {
		super(modelbase, 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ((EntityLivingBase) entity).isChild() ? textureChick : texture;
	}

	@Override
	protected float handleRotationFloat(EntityLivingBase entityliving, float f) {
		LKEntityFlamingo flamingo = (LKEntityFlamingo) entityliving;
		float f1 = flamingo.field_756_e + (flamingo.field_752_b - flamingo.field_756_e) * f;
		float f2 = flamingo.field_757_d + (flamingo.destPos - flamingo.field_757_d) * f;
		return (MathHelper.sin(f1) + 1.0F) * f2;
	}
}
