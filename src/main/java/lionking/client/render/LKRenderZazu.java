package lionking.client.render;

import lionking.common.entity.LKEntityZazu;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class LKRenderZazu extends RenderLiving {
	private static final ResourceLocation texture = new ResourceLocation("lionking:mob/zazu.png");

	public LKRenderZazu(ModelBase modelbase, float f) {
		super(modelbase, f);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}

	@Override
	protected float handleRotationFloat(EntityLivingBase entityliving, float f) {
		LKEntityZazu zazu = (LKEntityZazu) entityliving;
		float f1 = zazu.field_756_e + (zazu.field_752_b - zazu.field_756_e) * f;
		float f2 = zazu.field_757_d + (zazu.destPos - zazu.field_757_d) * f;
		return (MathHelper.sin(f1) + 1.0F) * f2;
	}
}
