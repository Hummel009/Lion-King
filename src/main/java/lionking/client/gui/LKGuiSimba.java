package lionking.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import lionking.common.entity.LKEntitySimba;
import lionking.common.inventory.LKContainerSimba;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

public class LKGuiSimba extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation("lionking:gui/simba.png");
	private LKEntitySimba theSimba;

	public LKGuiSimba(EntityPlayer entityplayer, LKEntitySimba simba) {
		super(new LKContainerSimba(entityplayer, simba));
		theSimba = simba;
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(new LKGuiSimbaButton(0, guiLeft + 77, guiTop + 54, theSimba));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.id == 0) {
			byte[] data = new byte[5];
			byte[] id = ByteBuffer.allocate(4).putInt(theSimba.entityId).array();
			System.arraycopy(id, 0, data, 0, 4);
			data[4] = (byte) mc.thePlayer.dimension;
			Packet250CustomPayload packet = new Packet250CustomPayload("lk.simbaSit", data);
			PacketDispatcher.sendPacketToServer(packet);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		int textcolour = 0x7A2804;
		fontRenderer.drawString("Simba", 74, 13, textcolour);
		fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, textcolour);
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
