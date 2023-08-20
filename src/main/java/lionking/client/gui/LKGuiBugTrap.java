package lionking.client.gui;

import lionking.common.inventory.LKContainerBugTrap;
import lionking.common.tileentity.LKTileEntityBugTrap;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LKGuiBugTrap extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation("lionking:gui/trap.png");

	public LKGuiBugTrap(EntityPlayer entityplayer, LKTileEntityBugTrap trap) {
		super(new LKContainerBugTrap(entityplayer, trap));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		int textcolour = 0xFFFFFF;
		fontRenderer.drawString("Bug Trap", 49, 6, textcolour);
		fontRenderer.drawString("Inventory", 8, (ySize - 96), textcolour);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
	}
}
