package lionking.common;

import cpw.mods.fml.common.network.PacketDispatcher;
import lionking.common.entity.LKEntityRafiki;
import lionking.common.entity.LKEntitySimba;
import lionking.common.quest.LKQuestBase;
import lionking.common.world.feature.LKWorldGenOutsand;
import lionking.common.world.feature.LKWorldGenZiraMound;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerInstance;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class LKIngame {
	public static int flatulenceSoundTick;
	public static boolean loadRenderers;

	public static void runMainWorldTick(World world) {
		Random random = world.rand;
		List players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;

		for (Object player : players) {
			EntityPlayer entityplayer = (EntityPlayer) player;

			ItemStack body = entityplayer.inventory.armorItemInSlot(2);
			if (body != null && body.getItem() == LKMod.wings) {
				entityplayer.fallDistance = 0.0F;
			}

			if (entityplayer.getHealth() > 0.0F && entityplayer.getHealth() < 5.0F) {
				activateCrystals(entityplayer, world, random);
			}

			checkInventory(entityplayer);
		}

		if (LKLevelData.defeatedScar == 1 && LKQuestBase.rafikiQuest.getQuestStage() == 2) {
			LKQuestBase.rafikiQuest.progress(3);
			sendMessageToAllPlayers("\u00a7e<Rafiki> \u00a7fYes! I knew you could save the Pride Lands from that villain! Now, quickly, return to my tree - we have no time to lose!");
		}

		if (flatulenceSoundTick > 0) {
			flatulenceSoundTick--;

			double d = -5.5D + (random.nextFloat() * 21.0F);
			double d1 = 104.0D + (random.nextFloat() * 8.0F);
			double d2 = -12.5D + (random.nextFloat() * 21.0F);
			DimensionManager.getWorld(LKMod.idPrideLands).spawnParticle("hugeexplosion", d, d1, d2, 0.0D, 0.0D, 0.0D);
		}

		if (LKLevelData.flatulenceSoundsRemaining > 0 && flatulenceSoundTick <= random.nextInt(16)) {
			LKLevelData.setFlatulenceSoundsRemaining(LKLevelData.flatulenceSoundsRemaining - 1);
			double d = -5.5D + (random.nextFloat() * 21.0F);
			double d1 = 104.0D + (random.nextFloat() * 8.0F);
			double d2 = -12.5D + (random.nextFloat() * 21.0F);
			DimensionManager.getWorld(LKMod.idPrideLands).playSoundEffect(d, d1, d2, "lionking:flatulence", 4.0F, (1.0F + (random.nextFloat() - random.nextFloat()) * 0.2F) * 0.7F);
			flatulenceSoundTick = 25;

			if (LKLevelData.flatulenceSoundsRemaining == 0) {
				LKLevelData.setZiraStage(20);
			}
		}
	}

	public static boolean isPlayerInLionPortal(EntityPlayer entityplayer, boolean isPrideLands) {
		AxisAlignedBB bb = entityplayer.boundingBox;

		int i = MathHelper.floor_double(bb.minX);
		int i1 = MathHelper.floor_double(bb.maxX + 1.0D);
		int j = MathHelper.floor_double(bb.minY);
		int j1 = MathHelper.floor_double(bb.maxY + 1.0D);
		int k = MathHelper.floor_double(bb.minZ);
		int k1 = MathHelper.floor_double(bb.maxZ + 1.0D);

		for (int i2 = i; i2 < i1; i2++) {
			for (int j2 = j; j2 < j1; j2++) {
				for (int k2 = k; k2 < k1; k2++) {
					if (entityplayer.worldObj.getBlockId(i2, j2, k2) == (isPrideLands ? LKMod.lionPortal.blockID : LKMod.outlandsPortal.blockID)) {
						if (isPrideLands && entityplayer.dimension == LKMod.idPrideLands) {
							entityplayer.worldObj.provider.setSpawnPoint(i2, j2, k2);
						}
						return true;
					}
				}
			}
		}

		return false;
	}

	public static void generateOutsand(EntityPlayer entityplayer, World world, Random random) {
		int i = MathHelper.floor_double(entityplayer.posX);
		int j = MathHelper.floor_double(entityplayer.posY);
		int k = MathHelper.floor_double(entityplayer.posZ);

		int i1 = i + random.nextInt(100) - random.nextInt(100);
		int j1 = j + random.nextInt(80) - random.nextInt(80);
		int k1 = k + random.nextInt(100) - random.nextInt(100);

		if (j1 > 1 && j1 < 256) {
			LKWorldGenOutsand outsandGen = new LKWorldGenOutsand();
			if (outsandGen.isRadiusClearOfOutsand(world, i1, j1, k1, 64) && world.isBlockOpaqueCube(i1, j1, k1) && world.canBlockSeeTheSky(i1, j1 + 1, k1)) {
				EntityLightningBolt entitylightningbolt = new EntityLightningBolt(world, i1, j1, k1);
				if (!world.isRemote) {
					world.spawnEntityInWorld(entitylightningbolt);
				}
				outsandGen.generate(world, random, i1, j1, k1);
			}
		}
	}

	private static void activateCrystals(EntityPlayer entityplayer, World world, Random random) {
		int j = MathHelper.floor_double(entityplayer.posY);
		if (j > 0 && entityplayer.inventory.hasItem(LKMod.crystal.itemID)) {
			entityplayer.inventory.consumeInventoryItem(LKMod.crystal.itemID);
			entityplayer.setHealth(entityplayer.getMaxHealth());
			entityplayer.triggerAchievement(LKAchievementList.crystal);
			if (!world.isRemote) {
				entityplayer.addChatMessage("The Hakuna Matata Crystal restored your health!");
			}
			for (int i = 0; i < 15; i++) {
				double d = random.nextGaussian() * 0.02D;
				double d1 = random.nextGaussian() * 0.02D;
				double d2 = random.nextGaussian() * 0.02D;
				world.spawnParticle("heart", (entityplayer.posX + (random.nextFloat() * entityplayer.width * 2.0F)) - entityplayer.width, entityplayer.posY - 0.5D + (random.nextFloat() * (entityplayer.height / 2)), (entityplayer.posZ + (random.nextFloat() * entityplayer.width * 2.0F)) - entityplayer.width, d, d1, d2);
			}
			world.playSoundAtEntity(entityplayer, "random.glass", 1.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
		}
	}

	private static void checkInventory(EntityPlayer entityplayer) {
		boolean[] rugFlags = new boolean[16];
		boolean[] headFlags = new boolean[3];
		for (int i = 0; i < 36; i++) {
			ItemStack itemstack = entityplayer.inventory.getStackInSlot(i);
			if (itemstack == null) {
				continue;
			}
			if (itemstack.itemID == LKMod.chocolateMufasa.itemID) {
				entityplayer.triggerAchievement(LKAchievementList.chocolateMufasa);
			}
			if (itemstack.itemID == LKMod.tunnahDiggah.itemID && EnchantmentHelper.getEnchantmentLevel(LKMod.diggahEnchantment.effectId, itemstack) == 1) {
				entityplayer.triggerAchievement(LKAchievementList.enchantDiggah);
			}
			if (itemstack.itemID == LKMod.rug.blockID && itemstack.getItemDamage() < 16) {
				rugFlags[itemstack.getItemDamage()] = true;
			}
			if (itemstack.itemID == LKMod.hyenaHeadItem.itemID && itemstack.getItemDamage() < 3) {
				headFlags[itemstack.getItemDamage()] = true;
			}
		}
		boolean hasAllRugs = true;
		for (boolean rugFlag : rugFlags) {
			if (!rugFlag) {
				hasAllRugs = false;
				break;
			}
		}
		if (hasAllRugs) {
			entityplayer.triggerAchievement(LKAchievementList.rugs);
		}
		boolean hasAllHeads = true;
		for (boolean headFlag : headFlags) {
			if (!headFlag) {
				hasAllHeads = false;
				break;
			}
		}
		if (hasAllHeads) {
			entityplayer.triggerAchievement(LKAchievementList.heads);
		}
	}

	public static List getSimbas(EntityPlayer entityplayer) {
		List simbaData = new ArrayList();
		List simbas = entityplayer.worldObj.getEntitiesWithinAABB(LKEntitySimba.class, entityplayer.boundingBox.addCoord(16.0F, 16.0F, 16.0F).addCoord(-16.0F, -16.0F, -16.0F));
		for (Object o : simbas) {
			LKEntitySimba simba = (LKEntitySimba) o;
			if (!simba.canUsePortal(entityplayer)) {
				continue;
			}
			NBTTagCompound data = new NBTTagCompound();
			simba.writeToNBT(data);
			simbaData.add(data);
			if (!entityplayer.worldObj.isRemote) {
				simba.setDead();
			}
		}

		return simbaData;
	}

	public static void doAdditionalPortalActions(EntityPlayer entityplayer, int dimension, boolean isPrideLands) {
		if (isPrideLands) {
			if (dimension == LKMod.idPrideLands && LKLevelData.receivedQuestBook == 0) {
				Random rand = entityplayer.getRNG();

				while (LKLevelData.receivedQuestBook == 0) {
					int i = MathHelper.floor_double(entityplayer.posX) + rand.nextInt(16) - rand.nextInt(16);
					int j = MathHelper.floor_double(entityplayer.posY) + rand.nextInt(8) - rand.nextInt(8);
					int k = MathHelper.floor_double(entityplayer.posZ) + rand.nextInt(16) - rand.nextInt(16);
					if (generateQuestBook(entityplayer.worldObj, i, j, k)) {
						LKLevelData.receivedQuestBook = 1;
					}
				}
			}
		}

		if (dimension == LKMod.idOutlands && LKLevelData.generatedMound == 0) {
			int count = 0;
			Random rand = entityplayer.getRNG();
			boolean generated = false;

			while (count < 256 && !generated) {
				int i = MathHelper.floor_double(entityplayer.posX);
				int k = MathHelper.floor_double(entityplayer.posZ);
				int i1 = 20 + rand.nextInt(180);
				int k1 = 20 + rand.nextInt(180);

				if (rand.nextBoolean()) {
					i += i1;
				} else {
					i -= i1;
				}
				if (rand.nextBoolean()) {
					k += k1;
				} else {
					k -= k1;
				}

				int j = entityplayer.worldObj.getHeightValue(i, k);

				if (j > 62 && j < 70) {
					if (!entityplayer.worldObj.isRemote) {
						new LKWorldGenZiraMound().generate(entityplayer.worldObj, rand, i, j - 50, k);
						generated = true;
					}
				}

				count++;
			}

			if (!generated) {
				int i = MathHelper.floor_double(entityplayer.posX);
				int k = MathHelper.floor_double(entityplayer.posZ);
				int i1 = 20 + rand.nextInt(180);
				int k1 = 20 + rand.nextInt(180);

				if (rand.nextBoolean()) {
					i += i1;
				} else {
					i -= i1;
				}
				if (rand.nextBoolean()) {
					k += k1;
				} else {
					k -= k1;
				}

				new LKWorldGenZiraMound().generate(entityplayer.worldObj, rand, i, 14, k);
			}
		}
	}

	private static boolean generateQuestBook(World world, int i, int j, int k) {
		if (world.getBlockMaterial(i, j - 1, k) == Material.leaves) {
			return false;
		}
		if (!world.isBlockOpaqueCube(i, j - 1, k) || !world.isAirBlock(i, j, k) || !world.isAirBlock(i, j + 1, k) || !world.isAirBlock(i, j + 1, k)) {
			return false;
		}
		if (!world.isAirBlock(i - 1, j + 1, k) || !world.isAirBlock(i + 1, j + 1, k) || !world.isAirBlock(i, j + 1, k - 1) || !world.isAirBlock(i, j + 1, k + 1)) {
			return false;
		}

		world.setBlock(i, j, k, LKMod.pridePillar.blockID, 1, 3);
		world.setBlock(i, j + 1, k, LKMod.prideBrick.blockID, 0, 3);
		world.setBlock(i - 1, j + 1, k, Block.torchWood.blockID, 0, 3);
		world.setBlock(i + 1, j + 1, k, Block.torchWood.blockID, 0, 3);
		world.setBlock(i, j + 1, k - 1, Block.torchWood.blockID, 0, 3);
		world.setBlock(i, j + 1, k + 1, Block.torchWood.blockID, 0, 3);
		world.setBlock(i, j + 2, k, Block.chest.blockID, 2 + world.rand.nextInt(4), 3);

		IInventory chest = (IInventory) world.getBlockTileEntity(i, j + 2, k);
		if (chest != null) {
			chest.setInventorySlotContents(13, new ItemStack(LKMod.questBook));
		}

		return true;
	}

	public static void startFlatulenceExplosion(World world) {
		LKLevelData.setZiraStage(19);
		LKQuestBase.outlandsQuest.setDelayed(true);
		LKQuestBase.outlandsQuest.progress(9);

		LKEntityRafiki rafiki = new LKEntityRafiki(world);
		rafiki.setLocationAndAngles(0, 103, 0, 0.0F, 0.0F);
		rafiki.isThisTheRealRafiki = true;
		for (int i = 17; i <= 31; i++) {
			rafiki.getDataWatcher().updateObject(i, (byte) 1);
		}
		world.spawnEntityInWorld(rafiki);

		LKLevelData.setFlatulenceSoundsRemaining(10);
		flatulenceSoundTick = 25;
		loadRenderers = true;
	}

	public static boolean hasAmulet(EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.inventory.armorItemInSlot(2);
		return itemstack != null && itemstack.itemID == LKMod.amulet.itemID;
	}

	public static boolean isLKWorld(int dimension) {
		return dimension == LKMod.idPrideLands || dimension == LKMod.idOutlands || dimension == LKMod.idUpendi;
	}

	public static boolean isChristmas() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) == 25;
	}

	public static void sendMessageToAllPlayers(String message) {
		Packet250CustomPayload packet = new Packet250CustomPayload("lk.message", message.getBytes());
		PacketDispatcher.sendPacketToAllPlayers(packet);
	}

	public static void spawnCustomFX(World world, int texture, int age, boolean glow, double posX, double posY, double posZ, double velX, double velY, double velZ) {
		if (!(world instanceof WorldServer)) {
			return;
		}

		byte[] data = new byte[51];

		data[0] = (byte) (texture & 255);
		data[1] = (byte) (age & 255);

		data[2] = glow ? (byte) 1 : (byte) 0;

		byte[] x = ByteBuffer.allocate(8).putDouble(posX).array();
		byte[] y = ByteBuffer.allocate(8).putDouble(posY).array();
		byte[] z = ByteBuffer.allocate(8).putDouble(posZ).array();

		byte[] xx = ByteBuffer.allocate(8).putDouble(velX).array();
		byte[] yy = ByteBuffer.allocate(8).putDouble(velY).array();
		byte[] zz = ByteBuffer.allocate(8).putDouble(velZ).array();

		for (int i = 0; i < 8; i++) {
			data[i + 3] = x[i];
			data[i + 11] = y[i];
			data[i + 19] = z[i];
			data[i + 27] = xx[i];
			data[i + 35] = yy[i];
			data[i + 43] = zz[i];
		}

		Packet250CustomPayload packet = new Packet250CustomPayload("lk.particles", data);
		PlayerInstance player = ((WorldServer) world).getPlayerManager().getOrCreateChunkWatcher(MathHelper.floor_double(posX) >> 4, MathHelper.floor_double(posZ) >> 4, false);
		if (player != null) {
			player.sendToAllPlayersWatchingChunk(packet);
		}
	}

	public static void sendBreakItemPacket(EntityPlayer entityplayer, int type) {
		World world = entityplayer.worldObj;
		if (!(world instanceof WorldServer)) {
			return;
		}

		byte[] data = new byte[6];
		byte[] id = ByteBuffer.allocate(4).putInt(entityplayer.entityId).array();
		System.arraycopy(id, 0, data, 0, 4);
		data[4] = (byte) entityplayer.dimension;
		data[5] = (byte) type;
		Packet250CustomPayload packet = new Packet250CustomPayload("lk.breakItem", data);
		PlayerInstance player = ((WorldServer) world).getPlayerManager().getOrCreateChunkWatcher(MathHelper.floor_double(entityplayer.posX) >> 4, MathHelper.floor_double(entityplayer.posZ) >> 4, false);
		if (player != null) {
			player.sendToAllPlayersWatchingChunk(packet);
		}
	}
}
