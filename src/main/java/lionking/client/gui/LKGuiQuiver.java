package lionking.client.gui;

import lionking.common.inventory.LKContainerQuiver;
import lionking.common.inventory.LKInventoryQuiver;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LKGuiQuiver extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation("lionking:gui/quiver.png");
	private static final ResourceLocation chestTexture = new ResourceLocation("textures/gui/container/generic_54.png");

	public LKGuiQuiver(EntityPlayer entityplayer, LKInventoryQuiver quiver) {
		super(new LKContainerQuiver(entityplayer, quiver));
		xSize = 251;
		ySize = 100;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		fontRenderer.drawString("Inventory", 8, 6, 4210752);
		fontRenderer.drawString("Quiver", 209, 16, 0x3B4229);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(chestTexture);
		int l = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(l, k, 0, 0, 176, 3);
		drawTexturedModalRect(l, k + 3, 0, 125, 176, 97);

		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(l + 201, k, 0, 0, 50, ySize);
	}
}
