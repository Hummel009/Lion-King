package lionking.client.render;

import lionking.client.model.LKModelGrindingStick;
import lionking.common.tileentity.LKTileEntityGrindingBowl;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LKRenderGrindingStick extends TileEntitySpecialRenderer {
	private static final ResourceLocation texture = new ResourceLocation("lionking:item/stick.png");
	private ModelBase model = new LKModelGrindingStick();

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		LKTileEntityGrindingBowl bowl = (LKTileEntityGrindingBowl) tileentity;
		renderStick((float) d, (float) d1, (float) d2, bowl.stickRotation + (f * 8.0F));
	}

	private void renderStick(float f, float f1, float f2, float rotation) {
		bindTexture(texture);
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		GL11.glTranslatef(f + 0.5F, f1 + 1.4F, f2 + 0.5F);
		GL11.glEnable(32826);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		GL11.glEnable(3008);
		GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
		model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}
}
