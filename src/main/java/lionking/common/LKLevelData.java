package lionking.common;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import lionking.common.quest.LKQuestBase;
import lionking.common.tileentity.LKTileEntityOutlandsPool;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.DimensionManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LKLevelData {
	public static Collection ticketBoothLocations = new ArrayList();
	public static int receivedQuestBook;
	public static int generatedMound;
	public static int quiverDamage;

	public static int homePortalX;
	public static int homePortalY;
	public static int homePortalZ;
	public static int defeatedScar;
	public static int moundX;
	public static int moundY;
	public static int moundZ;
	public static int ziraStage;
	public static int pumbaaStage;
	public static int outlandersHostile;
	public static int flatulenceSoundsRemaining;
	public static Map<String, Integer> simbas = new HashMap();

	public static boolean needsLoad = true;
	public static boolean needsSave;

	public static void setHomePortalLocation(int i, int j, int k) {
		homePortalX = i;
		homePortalY = j;
		homePortalZ = k;
		byte[] data = new byte[12];
		byte[] portalX = ByteBuffer.allocate(4).putInt(i).array();
		byte[] portalY = ByteBuffer.allocate(4).putInt(j).array();
		byte[] portalZ = ByteBuffer.allocate(4).putInt(k).array();
		for (int l = 0; l < 4; l++) {
			data[l] = portalX[l];
			data[l + 4] = portalY[l];
			data[l + 8] = portalZ[l];
		}
		Packet250CustomPayload packet = new Packet250CustomPayload("lk.homePortal", data);
		PacketDispatcher.sendPacketToAllPlayers(packet);
		needsSave = true;
	}

	public static void markMoundLocation(int i, int j, int k) {
		moundX = i;
		moundY = j;
		moundZ = k;
		generatedMound = 1;
		byte[] data = new byte[12];
		byte[] x = ByteBuffer.allocate(4).putInt(i).array();
		byte[] y = ByteBuffer.allocate(4).putInt(j).array();
		byte[] z = ByteBuffer.allocate(4).putInt(k).array();
		for (int l = 0; l < 4; l++) {
			data[l] = x[l];
			data[l + 4] = y[l];
			data[l + 8] = z[l];
		}
		Packet250CustomPayload packet = new Packet250CustomPayload("lk.mound", data);
		PacketDispatcher.sendPacketToAllPlayers(packet);
		needsSave = true;
	}

	public static void setDefeatedScar(boolean flag) {
		defeatedScar = flag ? 1 : 0;
		byte[] data = new byte[1];
		data[0] = flag ? (byte) 1 : (byte) 0;
		Packet250CustomPayload packet = new Packet250CustomPayload("lk.scar", data);
		PacketDispatcher.sendPacketToAllPlayers(packet);
		needsSave = true;
	}

	public static void setOutlandersHostile(int i) {
		outlandersHostile = i;
		byte[] data = new byte[1];
		data[0] = (byte) i;
		Packet250CustomPayload packet = new Packet250CustomPayload("lk.outlanders", data);
		PacketDispatcher.sendPacketToAllPlayers(packet);
		needsSave = true;
	}

	public static void setZiraStage(int i) {
		ziraStage = i;
		byte[] data = new byte[1];
		data[0] = (byte) i;
		Packet250CustomPayload packet = new Packet250CustomPayload("lk.zira", data);
		PacketDispatcher.sendPacketToAllPlayers(packet);
		needsSave = true;
	}

	public static void setPumbaaStage(int i) {
		pumbaaStage = i;
		byte[] data = new byte[1];
		data[0] = (byte) i;
		Packet250CustomPayload packet = new Packet250CustomPayload("lk.pumbaa", data);
		PacketDispatcher.sendPacketToAllPlayers(packet);
		needsSave = true;
	}

	public static void setFlatulenceSoundsRemaining(int i) {
		flatulenceSoundsRemaining = i;
		byte[] data = new byte[1];
		data[0] = (byte) i;
		Packet250CustomPayload packet = new Packet250CustomPayload("lk.flatulence", data);
		PacketDispatcher.sendPacketToAllPlayers(packet);
		needsSave = true;
	}

	public static void save() {
		try {
			File file = new File(DimensionManager.getCurrentSaveRootDirectory(), "LionKing.dat");
			if (!file.exists()) {
				FileOutputStream outputStream = new FileOutputStream(file);
				CompressedStreamTools.writeCompressed(new NBTTagCompound(), outputStream);
				outputStream.close();
			}

			FileOutputStream outputStream = new FileOutputStream(file);
			NBTTagCompound levelData = new NBTTagCompound();

			levelData.setInteger("HomePortalX", homePortalX);
			levelData.setInteger("HomePortalY", homePortalY);
			levelData.setInteger("HomePortalZ", homePortalZ);
			levelData.setInteger("DefeatedScar", defeatedScar);
			levelData.setInteger("Book", receivedQuestBook);
			levelData.setInteger("Mound", generatedMound);
			levelData.setInteger("MoundX", moundX);
			levelData.setInteger("MoundY", moundY);
			levelData.setInteger("MoundZ", moundZ);
			levelData.setInteger("OutlandersHostile", outlandersHostile);
			levelData.setInteger("ZiraStage", ziraStage);
			levelData.setInteger("PumbaaStage", pumbaaStage);
			levelData.setInteger("FlatulenceRemaining", flatulenceSoundsRemaining);
			levelData.setInteger("QuiverDamage", quiverDamage);
			LKQuestBase.writeAllQuestsToNBT(levelData);
			LKTileEntityOutlandsPool.writeInventoryToNBT(levelData);

			NBTTagList simbaList = new NBTTagList();
			for (Map.Entry<String, Integer> entry : simbas.entrySet()) {
				if (entry.getKey() instanceof String && entry.getValue() instanceof Integer) {
					NBTTagCompound nbt = new NBTTagCompound();
					nbt.setString("Username", entry.getKey());
					nbt.setInteger("HasSimba", entry.getValue());
					simbaList.appendTag(nbt);
				}
			}
			levelData.setTag("Simbas", simbaList);

			NBTTagList boothList = new NBTTagList();
			for (Object obj : ticketBoothLocations) {
				if (obj instanceof ChunkCoordinates) {
					ChunkCoordinates coords = (ChunkCoordinates) obj;
					NBTTagCompound nbt = new NBTTagCompound();
					nbt.setInteger("XPos", coords.posX);
					nbt.setInteger("YPos", coords.posY);
					nbt.setInteger("ZPos", coords.posZ);
					boothList.appendTag(nbt);
				}
			}
			levelData.setTag("BoothLocations", boothList);

			CompressedStreamTools.writeCompressed(levelData, outputStream);
			outputStream.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void load() {
		try {
			File file = new File(DimensionManager.getCurrentSaveRootDirectory(), "LionKing.dat");
			if (!file.exists()) {
				FileOutputStream outputStream = new FileOutputStream(file);
				CompressedStreamTools.writeCompressed(new NBTTagCompound(), outputStream);
				outputStream.close();
			}

			FileInputStream inputStream = new FileInputStream(file);
			NBTTagCompound levelData = CompressedStreamTools.readCompressed(inputStream);

			homePortalX = levelData.getInteger("HomePortalX");
			homePortalY = levelData.getInteger("HomePortalY");
			homePortalZ = levelData.getInteger("HomePortalZ");
			defeatedScar = levelData.getInteger("DefeatedScar");
			receivedQuestBook = levelData.getInteger("Book");
			generatedMound = levelData.getInteger("Mound");
			moundX = levelData.getInteger("MoundX");
			moundY = levelData.getInteger("MoundY");
			moundZ = levelData.getInteger("MoundZ");
			outlandersHostile = levelData.getInteger("OutlandersHostile");
			ziraStage = levelData.getInteger("ZiraStage");
			pumbaaStage = levelData.getInteger("PumbaaStage");
			flatulenceSoundsRemaining = levelData.getInteger("FlatulenceRemaining");
			quiverDamage = levelData.getInteger("QuiverDamage");
			LKQuestBase.readAllQuestsFromNBT(levelData);
			LKTileEntityOutlandsPool.inventory.clear();
			LKTileEntityOutlandsPool.readInventoryFromNBT(levelData);

			simbas.clear();
			NBTTagList simbaList = levelData.getTagList("Simbas");
			if (simbaList != null) {
				for (int i = 0; i < simbaList.tagCount(); i++) {
					NBTTagCompound nbt = (NBTTagCompound) simbaList.tagAt(i);
					simbas.put(nbt.getString("Username"), nbt.getInteger("HasSimba"));
				}
			}

			ticketBoothLocations.clear();
			NBTTagList boothList = levelData.getTagList("BoothLocations");
			if (boothList != null) {
				for (int i = 0; i < boothList.tagCount(); i++) {
					NBTTagCompound nbt = (NBTTagCompound) boothList.tagAt(i);
					int posX = nbt.getInteger("XPos");
					int posY = nbt.getInteger("YPos");
					int posZ = nbt.getInteger("ZPos");
					ChunkCoordinates coords = new ChunkCoordinates(posX, posY, posZ);
					ticketBoothLocations.add(coords);
				}
			}

			inputStream.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static boolean hasSimba(EntityPlayer entityplayer) {
		Object obj = simbas.get(entityplayer.username);
		return obj instanceof Integer && (Integer) obj == 1;
	}

	public static void setHasSimba(EntityPlayer entityplayer, boolean flag) {
		int i = flag ? 1 : 0;
		simbas.put(entityplayer.username, i);
		byte[] data = ByteBuffer.allocate(4).putInt(i).array();
		Packet250CustomPayload packet = new Packet250CustomPayload("lk.hasSimba", data);
		PacketDispatcher.sendPacketToPlayer(packet, (Player) entityplayer);
		needsSave = true;
	}

	public static Packet250CustomPayload getLoginPacket(EntityPlayer entityplayer) {
		byte[] data = new byte[333];
		byte[] portalX = ByteBuffer.allocate(4).putInt(homePortalX).array();
		byte[] portalY = ByteBuffer.allocate(4).putInt(homePortalY).array();
		byte[] portalZ = ByteBuffer.allocate(4).putInt(homePortalZ).array();
		byte[] moundXBytes = ByteBuffer.allocate(4).putInt(moundX).array();
		byte[] moundYBytes = ByteBuffer.allocate(4).putInt(moundY).array();
		byte[] moundZBytes = ByteBuffer.allocate(4).putInt(moundZ).array();
		for (int i = 0; i < 4; i++) {
			data[i] = portalX[i];
			data[i + 4] = portalY[i];
			data[i + 8] = portalZ[i];
			data[i + 12] = moundXBytes[i];
			data[i + 16] = moundYBytes[i];
			data[i + 20] = moundZBytes[i];
		}
		data[24] = (byte) defeatedScar;
		data[25] = (byte) ziraStage;
		data[26] = (byte) pumbaaStage;
		data[27] = (byte) outlandersHostile;
		data[28] = (byte) flatulenceSoundsRemaining;

		for (int i = 0; i < 16; i++) {
			LKQuestBase quest = LKQuestBase.allQuests[i];
			if (quest == null) {
				continue;
			}

			data[29 + i * 19] = (byte) quest.stagesDelayed;
			data[29 + i * 19 + 1] = (byte) quest.currentStage;
			data[29 + i * 19 + 2] = (byte) quest.stagesDelayed;

			for (int j = 0; j < 16; j++) {
				if (j >= quest.stagesCompleted.length) {
					continue;
				}
				data[29 + i * 19 + 3 + j] = (byte) quest.stagesCompleted[j];
			}
		}

		return new Packet250CustomPayload("lk.login", data);
	}
}
