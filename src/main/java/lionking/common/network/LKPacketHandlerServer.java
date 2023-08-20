package lionking.common.network;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import lionking.common.LKIngame;
import lionking.common.LKMod;
import lionking.common.entity.LKEntitySimba;
import lionking.common.quest.LKQuestBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.stats.StatList;
import net.minecraftforge.common.DimensionManager;

import java.nio.ByteBuffer;

public class LKPacketHandlerServer implements IPacketHandler {
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		if ("lk.simbaSit".equals(packet.channel)) {
			Entity entity = LKMod.proxy.getEntityFromID(ByteBuffer.wrap(packet.data).getInt(0), DimensionManager.getWorld(packet.data[4]));
			if (entity instanceof LKEntitySimba) {
				LKEntitySimba simba = (LKEntitySimba) entity;
				simba.setSitting(!simba.isSitting());
			}
		} else if ("lk.damageItem".equals(packet.channel)) {
			Entity entity = LKMod.proxy.getEntityFromID(ByteBuffer.wrap(packet.data).getInt(0), DimensionManager.getWorld(packet.data[4]));
			if (entity instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) entity;
				int type = packet.data[5];
				if (type == (byte) 0) {
					ItemStack boots = entityplayer.inventory.armorItemInSlot(0);
					if (boots != null && boots.getItem() == LKMod.zebraBoots) {
						boots.damageItem(1, entityplayer);
						if (boots.getItemDamage() == boots.getMaxDamage()) {
							LKIngame.sendBreakItemPacket(entityplayer, 0);
							entityplayer.inventory.setInventorySlotContents(36, null);
							entityplayer.addStat(StatList.objectBreakStats[LKMod.zebraBoots.itemID], 1);
						}
					}
				} else if (type == (byte) 1) {
					ItemStack body = entityplayer.inventory.armorItemInSlot(2);
					if (body != null && body.getItem() == LKMod.wings) {
						body.damageItem(1, entityplayer);
						if (body.getItemDamage() == body.getMaxDamage()) {
							LKIngame.sendBreakItemPacket(entityplayer, 1);
							entityplayer.inventory.setInventorySlotContents(38, null);
							entityplayer.addStat(StatList.objectBreakStats[LKMod.wings.itemID], 1);
						}
					}
				}
			}
		} else if ("lk.sendQCheck".equals(packet.channel)) {
			LKQuestBase quest = LKQuestBase.allQuests[packet.data[0]];
			quest.setChecked(true);
		}
	}
}
