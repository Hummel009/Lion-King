package lionking.client.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class LKTileEntityBugTrapRenderer extends TileEntitySpecialRenderer {
	private RenderBlocks renderblocks = new RenderBlocks();

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		IInventory bugTrap = (IInventory) tileentity;

		for (int l = 0; l < 4; l++) {
			GL11.glPushMatrix();
			GL11.glTranslatef((float) d + 0.5F + (l == 2 ? 0.035F : (l == 1 ? -0.035F : 0.0F)), (float) d1 + 0.4F, (float) d2 + 0.5F + (l == 3 ? 0.035F : (l == 0 ? -0.035F : 0.0F)));

			switch (l) {
				case 0:
					GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
					break;

				case 1:
					GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
					break;

				case 2:
					GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
					break;

				case 3:
					GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
					break;
			}

			GL11.glEnable(GL12.GL_RESCALE_NORMAL);

			ItemStack itemstack = bugTrap.getStackInSlot(l);

			if (itemstack != null && Item.itemsList[itemstack.itemID] != null) {
				if (itemstack.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType())) {
					bindTexture(TextureMap.locationBlocksTexture);
					float f1 = 0.25F;
					int renderType = Block.blocksList[itemstack.itemID].getRenderType();

					if (renderType == 1 || renderType == 19 || renderType == 12 || renderType == 2) {
						f1 = 0.5F;
					}

					GL11.glPushMatrix();

					GL11.glScalef(f1, f1, f1);
					GL11.glTranslatef(0.0F, 0.35F, 0.0F);
					renderblocks.renderBlockAsItem(Block.blocksList[itemstack.itemID], itemstack.getItemDamage(), 1.0F);
					GL11.glPopMatrix();
				} else {
					GL11.glPushMatrix();
					GL11.glScalef(0.4F, 0.4F, 0.4F);

					if (itemstack.getItem().requiresMultipleRenderPasses()) {
						Item item = itemstack.getItem();
						bindTexture(TextureMap.locationItemsTexture);

						for (int index = 0; index < item.getRenderPasses(itemstack.getItemDamage()); index++) {
							int color = itemstack.getItem().getColorFromItemStack(itemstack, index);
							float f2 = (color >> 16 & 255) / 255.0F;
							float f3 = (color >> 8 & 255) / 255.0F;
							float f4 = (color & 255) / 255.0F;
							GL11.glColor4f(f2, f3, f4, 1.0F);
							drawItemSprite(itemstack.getItem().getIconFromDamageForRenderPass(itemstack.getItemDamage(), index));
						}
					} else {
						if (itemstack.getItem() instanceof ItemBlock) {
							bindTexture(TextureMap.locationBlocksTexture);
						} else {
							bindTexture(TextureMap.locationItemsTexture);
						}

						int color = Item.itemsList[itemstack.itemID].getColorFromItemStack(itemstack, 0);
						float f1 = (color >> 16 & 255) / 255.0F;
						float f2 = (color >> 8 & 255) / 255.0F;
						float f3 = (color & 255) / 255.0F;
						GL11.glColor4f(f1, f2, f3, 1.0F);
						drawItemSprite(itemstack.getIconIndex());
					}

					GL11.glPopMatrix();
				}
			}

			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
		}
	}

	private void drawItemSprite(Icon icon) {
		Tessellator tessellator = Tessellator.instance;
		float f = icon.getMinU();
		float f1 = icon.getMaxU();
		float f2 = icon.getMinV();
		float f3 = icon.getMaxV();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.addVertexWithUV(-0.5D, -0.25D, 0.0D, f, f3);
		tessellator.addVertexWithUV(0.5D, -0.25D, 0.0D, f1, f3);
		tessellator.addVertexWithUV(0.5D, 0.75D, 0.0D, f1, f2);
		tessellator.addVertexWithUV(-0.5D, 0.75D, 0.0D, f, f2);
		tessellator.addVertexWithUV(-0.5D, 0.75D, 0.0D, f, f2);
		tessellator.addVertexWithUV(0.5D, 0.75D, 0.0D, f1, f2);
		tessellator.addVertexWithUV(0.5D, -0.25D, 0.0D, f1, f3);
		tessellator.addVertexWithUV(-0.5D, -0.25D, 0.0D, f, f3);
		tessellator.draw();
	}
}
