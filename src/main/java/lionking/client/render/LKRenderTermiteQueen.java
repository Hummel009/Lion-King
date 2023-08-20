package lionking.client.render;

import lionking.client.model.LKModelTermiteQueen;
import lionking.common.entity.LKEntityTermiteQueen;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LKRenderTermiteQueen extends RenderLiving {
	private static ResourceLocation texture = new ResourceLocation("lionking:mob/termite_queen.png");

	public LKRenderTermiteQueen() {
		super(new LKModelTermiteQueen(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}

	@Override
	protected void preRenderCallback(EntityLivingBase entityliving, float f) {
		float scale = ((LKEntityTermiteQueen) entityliving).getScale();
		GL11.glScalef(scale, scale, scale);
	}
}
