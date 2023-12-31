package lionking.client.render.tileentity;

import lionking.client.model.LKModelMountedShooter;
import lionking.common.tileentity.LKTileEntityMountedShooter;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LKTileEntityMountedShooterRenderer extends TileEntitySpecialRenderer {
	private static final ResourceLocation textureWood = new ResourceLocation("lionking:item/shooter.png");
	private static final ResourceLocation textureSilver = new ResourceLocation("lionking:item/shooter_silver.png");
	private ModelBase model = new LKModelMountedShooter();

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		LKTileEntityMountedShooter shooter = (LKTileEntityMountedShooter) tileentity;
		renderShooter((float) d, (float) d1, (float) d2, shooter.getBlockMetadata(), shooter.getShooterType(), shooter.fireCounter);
	}

	private void renderShooter(float f, float f1, float f2, int metadata, int shooterType, float fireCounter) {
		if (shooterType == 0) {
			bindTexture(textureWood);
		} else {
			bindTexture(textureSilver);
		}
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		GL11.glTranslatef(f + 0.5F, f1 + 1.5F, f2 + 0.5F);
		GL11.glEnable(32826);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		GL11.glEnable(3008);
		GL11.glRotatef(metadata * 90.0F, 0.0F, 1.0F, 0.0F);
		model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, -fireCounter, 0.0625F);
		GL11.glPopMatrix();
	}
}
