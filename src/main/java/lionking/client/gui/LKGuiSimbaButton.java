package lionking.client.gui;

import lionking.common.entity.LKEntitySimba;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LKGuiSimbaButton extends GuiButton {
	private static final ResourceLocation texture = new ResourceLocation("lionking:gui/simba.png");
	private LKEntitySimba theSimba;

	public LKGuiSimbaButton(int i, int j, int k, LKEntitySimba simba) {
		super(i, j, k, 22, 22, "Simba");
		theSimba = simba;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		GL11.glDisable(GL11.GL_LIGHTING);
		mc.getTextureManager().bindTexture(texture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int u = getHoverState(i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height) == 2 ? 202 : 178;
		int v = theSimba.isSitting() ? 24 : 0;
		drawTexturedModalRect(xPosition, yPosition, u, v, width, height);
		mouseDragged(mc, i, j);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
}
