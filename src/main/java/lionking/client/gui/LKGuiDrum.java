package lionking.client.gui;

import lionking.common.inventory.LKContainerDrum;
import lionking.common.tileentity.LKTileEntityDrum;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class LKGuiDrum extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation("lionking:gui/drum.png");

	public LKGuiDrum(EntityPlayer entityplayer, World world, LKTileEntityDrum drum) {
		super(new LKContainerDrum(entityplayer, world, drum));
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;

		for (int j1 = 0; j1 < 3; j1++) {
			if (i >= l + 72 && i < l + 145 && j >= i1 + 25 + j1 * 18 && j < i1 + 42 + j1 * 18) {
				mc.playerController.sendEnchantPacket(inventorySlots.windowId, j1);
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		fontRenderer.drawString("Bongo Drum", 60, 7, 0x1F190B);

		ItemStack itemstack = inventorySlots.getSlot(0).getStack();
		if (itemstack != null && itemstack.isItemEnchantable()) {
			for (int k = 0; k < 3; k++) {
				int level = ((LKContainerDrum) inventorySlots).enchantLevels[k];
				if (level == 0) {
					return;
				}
				String s = String.valueOf(level);
				fontRenderer.drawString(s, 142 - fontRenderer.getStringWidth(s), 30 + k * 18, 0x1F190B);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		int l = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(l, k, 0, 0, xSize, ySize);

		if (inventorySlots.getSlot(0).getStack() != null) {
			for (int i1 = 0; i1 < 3; i1++) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.getTextureManager().bindTexture(texture);

				int level = ((LKContainerDrum) inventorySlots).enchantLevels[i1];

				if (level == 0) {
					drawTexturedModalRect(l + 72, k + 25 + i1 * 18, 183, 0, 73, 17);
				} else {
					if (mc.thePlayer.experienceLevel < level && !mc.thePlayer.capabilities.isCreativeMode) {
						drawTexturedModalRect(l + 72, k + 25 + i1 * 18, 183, 0, 73, 17);
					} else {
						if (i >= l + 72 && i < l + 145 && j >= k + 25 + i1 * 18 && j < k + 42 + i1 * 18) {
							drawTexturedModalRect(l + 72, k + 25 + i1 * 18, 183, 36, 73, 17);
						} else {
							drawTexturedModalRect(l + 72, k + 25 + i1 * 18, 183, 18, 73, 17);
						}
					}
				}
			}
		}
	}
}
