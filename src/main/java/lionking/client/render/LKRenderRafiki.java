package lionking.client.render;

import lionking.client.model.LKModelRafiki;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LKRenderRafiki extends RenderLiving {
	private static final ResourceLocation texture = new ResourceLocation("lionking:mob/rafiki.png");
	private LKModelRafiki modelRafiki = new LKModelRafiki();

	public LKRenderRafiki(LKModelRafiki model) {
		super(model, 0.5F);
		modelRafiki = model;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}

	public void doRenderLiving(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
		super.doRenderLiving(entity, d, d1, d2, f, f1);
		renderLivingLabel(entity, "Rafiki", d, d1, d2, 64);
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase entityliving, float f) {
		super.renderEquippedItems(entityliving, f);
		ItemStack itemstack = entityliving.getHeldItem();
		if (itemstack != null) {
			GL11.glPushMatrix();
			modelRafiki.getRightArm().postRender(0.0625F);
			GL11.glTranslatef(0.0625F, 0.4375F, 0.2625F);

			if (Item.itemsList[itemstack.itemID].isFull3D()) {
				float f3 = 0.625F;
				GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
				GL11.glScalef(f3, -f3, f3);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else {
				float f4 = 0.375F;
				GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
				GL11.glScalef(f4, f4, f4);
				GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
			}

			renderManager.itemRenderer.renderItem(entityliving, itemstack, 0);
			GL11.glPopMatrix();
		}
	}
}