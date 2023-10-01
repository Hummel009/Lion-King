package lionking.client.gui;

import lionking.common.LKMod;
import lionking.common.entity.LKEntityTimon;
import lionking.common.inventory.LKContainerTimon;
import lionking.common.inventory.LKSlotTimon;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LKGuiTimon extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation("lionking:gui/timon.png");

	public LKGuiTimon(EntityPlayer entityplayer, LKEntityTimon entitytimon) {
		super(new LKContainerTimon(entityplayer, entitytimon));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		int textcolour = 0x7F472F;
		fontRenderer.drawString("Timon & Pumbaa", 51, 13, textcolour);
		fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, textcolour);

		for (int k = 0; k < 5; k++) {
			String s = Integer.valueOf(((LKSlotTimon) inventorySlots.inventorySlots.get(k)).cost).toString();
			itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.getTextureManager(), new ItemStack(LKMod.bug), s.length() == 1 ? 18 + 33 * k : 23 + 33 * k, 51);
			fontRenderer.drawString(s, s.length() == 1 ? 11 + 33 * k : 10 + 33 * k, 55, textcolour);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		int l = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(l, k, 0, 0, xSize, ySize + 24);
	}
}
