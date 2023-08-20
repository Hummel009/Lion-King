package lionking.client.gui;

import lionking.common.inventory.LKContainerGrindingBowl;
import lionking.common.tileentity.LKTileEntityGrindingBowl;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LKGuiGrindingBowl extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation("lionking:gui/grind.png");

	public LKGuiGrindingBowl(EntityPlayer entityplayer, LKTileEntityGrindingBowl bowl) {
		super(new LKContainerGrindingBowl(entityplayer, bowl));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		int textcolour = 0x140C02;
		fontRenderer.drawString("Grinding Bowl", 49, 6, textcolour);
		fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, textcolour);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
		int k1 = ((LKContainerGrindingBowl) inventorySlots).grindingBowl.getGrindProgressScaled(45);
		drawTexturedModalRect(l + 62, i1 + 35, 176, 0, k1, 15);
	}
}
