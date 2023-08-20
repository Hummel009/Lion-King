package lionking.client.gui;

import lionking.common.quest.LKQuestBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LKGuiQuestsButton extends GuiButton {
	private static final ResourceLocation texture = new ResourceLocation("lionking:gui/book_menu.png");

	private LKQuestBase theQuest;

	public LKGuiQuestsButton(int i, int j, int k, LKQuestBase quest) {
		super(i, j, k, 135, 20, quest == null ? "Main Page" : quest.getName());
		theQuest = quest;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		GL11.glDisable(GL11.GL_LIGHTING);
		mc.getTextureManager().bindTexture(texture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int yPos = theQuest != null && !theQuest.canStart() ? 196 : (theQuest != null && LKGuiQuests.flashTimer > 14 && theQuest.canStart() && theQuest.checked == 0 ? 236 : 216);
		drawTexturedModalRect(xPosition, yPosition, 121, yPos, width / 2, height);
		drawTexturedModalRect(xPosition + width / 2, yPosition, 256 - width / 2, yPos, width / 2, height);
		mouseDragged(mc, i, j);
		drawCenteredString(mc.fontRenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, 0x120C01);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k) {
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}
}
