package lionking.client.render;

import lionking.common.entity.LKEntityOutsand;
import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LKRenderOutsand extends Render {
	private RenderBlocks renderBlocks = new RenderBlocks();

	public LKRenderOutsand() {
		shadowSize = 0.5F;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationBlocksTexture;
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		bindEntityTexture(entity);
		GL11.glDisable(GL11.GL_LIGHTING);
		LKEntityOutsand blockEntity = (LKEntityOutsand) entity;
		Block block = LKMod.outsand;
		renderBlocks.setRenderBoundsFromBlock(block);
		renderBlocks.renderBlockAsItem(block, 0, blockEntity.getBrightness(f1));
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
}
