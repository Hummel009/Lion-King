package lionking.client;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import lionking.client.effect.LKEntityCustomFX;
import lionking.client.gui.LKGuiIngame;
import lionking.common.LKAchievementList;
import lionking.common.LKIngame;
import lionking.common.LKLevelData;
import lionking.common.LKMod;
import lionking.common.block.LKBlockLeaves;
import lionking.common.block.LKBlockRafikiLeaves;
import lionking.common.entity.LKEntityScar;
import lionking.common.entity.LKEntityZira;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Random;

public class LKTickHandlerClient implements ITickHandler {
	public static HashMap playersInPortals = new HashMap();
	public static HashMap playersInOutPortals = new HashMap();
	public static LKEntityScar scarBoss;
	public static LKEntityZira ziraBoss;
	private GuiScreen lastGuiOpen;
	private boolean checkedUpdate;
	private boolean wingFlight;
	private int wingTicks;

	private static void sendDamageItemPacket(EntityPlayer entityplayer, int type) {
		byte[] data = new byte[6];
		byte[] id = ByteBuffer.allocate(4).putInt(entityplayer.entityId).array();
		System.arraycopy(id, 0, data, 0, 4);
		data[4] = (byte) entityplayer.dimension;
		data[5] = (byte) type;
		Packet250CustomPayload packet = new Packet250CustomPayload("lk.damageItem", data);
		PacketDispatcher.sendPacketToServer(packet);
	}

	public void tickStart(EnumSet type, Object... tickData) {
	}

	public void tickEnd(EnumSet type, Object... tickData) {
		Minecraft minecraft = Minecraft.getMinecraft();

		if (type.equals(EnumSet.of(TickType.RENDER))) {
			float f = (Float) tickData[0];
			runRenderTick(f, minecraft);
		}

		if (type.equals(EnumSet.of(TickType.CLIENT))) {
			if (minecraft.currentScreen == null) {
				lastGuiOpen = null;
			}

			runClientTick(minecraft);

			((LKBlockLeaves) LKMod.forestLeaves).setGraphicsLevel(minecraft.gameSettings.fancyGraphics);
			((LKBlockLeaves) LKMod.mangoLeaves).setGraphicsLevel(minecraft.gameSettings.fancyGraphics);
			((LKBlockLeaves) LKMod.leaves).setGraphicsLevel(minecraft.gameSettings.fancyGraphics);
			((LKBlockLeaves) LKMod.passionLeaves).setGraphicsLevel(minecraft.gameSettings.fancyGraphics);
			((LKBlockLeaves) LKMod.bananaLeaves).setGraphicsLevel(minecraft.gameSettings.fancyGraphics);
			((LKBlockRafikiLeaves) LKMod.rafikiLeaves).setGraphicsLevel(minecraft.gameSettings.fancyGraphics);

			if (minecraft.thePlayer != null && minecraft.theWorld != null && !checkedUpdate && LKMod.shouldCheckUpdates) {
				try {
					URL updateURL = new URL("http://dl.dropbox.com/s/b9odiuz504iy6ol/version.txt");
					HttpURLConnection updateConnection = (HttpURLConnection) updateURL.openConnection();
					BufferedReader updateReader = new BufferedReader(new InputStreamReader(updateConnection.getInputStream()));
					String updateVersion = updateReader.readLine();
					updateReader.close();

					String version = null;

					for (ModContainer mod : Loader.instance().getModList()) {
						if (mod.getMod() == LKMod.instance) {
							version = mod.getVersion();
						}
					}

					if (version != null && updateVersion != null && !updateVersion.equals(version)) {
						minecraft.thePlayer.addChatMessage("\u00a7eThe Lion King Mod: \u00a7fUpdate available! (" + updateVersion + ")");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				checkedUpdate = true;
			}

			if (minecraft.thePlayer != null && minecraft.theWorld != null) {
				EntityPlayer entityplayer = minecraft.thePlayer;
				if ((entityplayer.dimension == 0 || entityplayer.dimension == LKMod.idPrideLands) && playersInPortals.containsKey(entityplayer)) {
					if (LKIngame.isPlayerInLionPortal(entityplayer, true)) {
						int i = (Integer) playersInPortals.get(entityplayer);
						i++;
						playersInPortals.put(entityplayer, i);
						if (i >= 100) {
							Minecraft.getMinecraft().sndManager.playSoundFX("portal.travel", 1.0F, minecraft.theWorld.rand.nextFloat() * 0.4F + 0.8F);
							playersInPortals.remove(entityplayer);
						}
					} else {
						playersInPortals.remove(entityplayer);
					}
				}
				if ((entityplayer.dimension == LKMod.idOutlands || entityplayer.dimension == LKMod.idPrideLands) && playersInOutPortals.containsKey(entityplayer)) {
					if (LKIngame.isPlayerInLionPortal(entityplayer, false)) {
						int i = (Integer) playersInOutPortals.get(entityplayer);
						i++;
						playersInOutPortals.put(entityplayer, i);
						if (i >= 100) {
							Minecraft.getMinecraft().sndManager.playSoundFX("portal.travel", 1.0F, minecraft.theWorld.rand.nextFloat() * 0.4F + 0.8F);
							playersInOutPortals.remove(entityplayer);
						}
					} else {
						playersInOutPortals.remove(entityplayer);
					}
				}
			}

			GuiScreen guiscreen = minecraft.currentScreen;
			if (guiscreen != null) {
				if (guiscreen instanceof GuiMainMenu && !(lastGuiOpen instanceof GuiMainMenu)) {
					LKLevelData.needsLoad = true;
				}
				lastGuiOpen = guiscreen;
			}
		}
	}

	private void runRenderTick(float f, Minecraft minecraft) {
		if (minecraft.currentScreen == null) {
			if (scarBoss != null) {
				LKGuiIngame.drawBossHealth(minecraft, scarBoss);
			}

			if (ziraBoss != null) {
				LKGuiIngame.drawBossHealth(minecraft, ziraBoss);
			}
		}

		if (minecraft.thePlayer != null && minecraft.theWorld != null) {
			if (playersInPortals.containsKey(minecraft.thePlayer)) {
				int i = (Integer) playersInPortals.get(minecraft.thePlayer);
				if (i > 0) {
					LKGuiIngame.renderPortalOverlay(0.1F + (i / 100.0F) * 0.6F, minecraft, true);
				}
			}

			if (playersInOutPortals.containsKey(minecraft.thePlayer)) {
				int i = (Integer) playersInOutPortals.get(minecraft.thePlayer);
				if (i > 0) {
					LKGuiIngame.renderPortalOverlay(0.1F + (i / 100.0F) * 0.6F, minecraft, false);
				}
			}
		}

		if (LKIngame.flatulenceSoundTick > 0) {
			LKGuiIngame.renderFlatulenceOverlay(0.1F + (LKIngame.flatulenceSoundTick / 25.0F) * 0.7F, minecraft);
		}

		if (LKIngame.loadRenderers) {
			Minecraft.getMinecraft().renderGlobal.loadRenderers();
			LKIngame.loadRenderers = false;
		}
	}

	private void runClientTick(Minecraft minecraft) {
		EntityPlayerSP entityplayer = minecraft.thePlayer;
		World world = minecraft.theWorld;
		if (entityplayer != null && world != null) {
			Random random = world.rand;

			if (entityplayer.dimension == LKMod.idPrideLands) {
				entityplayer.triggerAchievement(LKAchievementList.enterPrideLands);
			}

			ItemStack boots = entityplayer.inventory.armorItemInSlot(0);
			if (boots != null && boots.itemID == LKMod.zebraBoots.itemID) {
				damageZebraBoots(entityplayer, boots);
			}

			ItemStack body = entityplayer.inventory.armorItemInSlot(2);
			if (body != null && body.itemID == LKMod.wings.itemID) {
				applyWingFlight(entityplayer, body);
			} else {
				wingFlight = false;
			}

			ItemStack[] armor = entityplayer.inventory.armorInventory;
			if (armor[3] != null && armor[2] != null && armor[1] != null && armor[0] != null && armor[3].getItem() == LKMod.ticketLionHead && armor[2].getItem() == LKMod.ticketLionSuit && armor[1].getItem() == LKMod.ticketLionLegs && armor[0].getItem() == LKMod.ticketLionFeet) {
				entityplayer.triggerAchievement(LKAchievementList.ticketLionSuit);
				if (entityplayer.sprintingTicksLeft > 0 && random.nextInt(3) == 0) {
					double d = random.nextGaussian() * 0.02D;
					double d1 = random.nextGaussian() * 0.02D;
					double d2 = random.nextGaussian() * 0.02D;
					world.spawnEntityInWorld(new LKEntityCustomFX(world, 48, 32, false, (entityplayer.posX + (((double) (random.nextFloat() * entityplayer.width * 2.0F)) - entityplayer.width) * 0.75F), entityplayer.posY - 1.0F + (random.nextFloat() * entityplayer.height), (entityplayer.posZ + (((double) (random.nextFloat() * entityplayer.width * 2.0F)) - entityplayer.width) * 0.75F), d, d1, d2));
				}
			}

			if (armor[3] != null && armor[3].getItem() == LKMod.outlandsHelm && random.nextInt(3) == 0) {
				double d = random.nextGaussian() * 0.01D;
				double d1 = random.nextGaussian() * 0.02D;
				if (d1 < 0.0D) {
					d1 *= -1.0D;
				}
				double d2 = random.nextGaussian() * 0.01D;
				world.spawnParticle("flame", entityplayer.posX + ((((double) (random.nextFloat() * entityplayer.width * 2.0F)) - entityplayer.width) * 0.25F), entityplayer.boundingBox.maxY + 0.3F + (random.nextFloat() * 0.25F), (entityplayer.posZ + (((double) (random.nextFloat() * entityplayer.width * 2.0F)) - entityplayer.width) * 0.25F), d, d1, d2);
			}
		}
	}

	private void damageZebraBoots(EntityPlayerSP entityplayer, ItemStack itemstack) {
		if (!entityplayer.worldObj.isAnyLiquid(entityplayer.boundingBox) && entityplayer.movementInput.moveForward > 0.0F && itemstack.getItemDamage() != itemstack.getMaxDamage() && !wingFlight) {
			entityplayer.moveEntityWithHeading(entityplayer.movementInput.moveForward, 30.0F);
			sendDamageItemPacket(entityplayer, 0);
		}
	}

	private void applyWingFlight(EntityPlayerSP entityplayer, ItemStack itemstack) {
		if (entityplayer.onGround) {
			wingFlight = false;
		}
		if (entityplayer.posY < 0.0D) {
			wingFlight = false;
			wingTicks = 0;
		}
		if (wingTicks > 0) {
			wingTicks--;
			if (entityplayer.movementInput.sneak) {
				entityplayer.motionY = -0.22D;
			} else {
				entityplayer.motionY = ((double) wingTicks / 12) * 0.05D;
			}
		}
		if (itemstack.getItemDamage() != itemstack.getMaxDamage()) {
			if (entityplayer.motionY < 0.0D && !entityplayer.movementInput.sneak && wingFlight) {
				entityplayer.motionY = -0.22D;
			}
			if (entityplayer.movementInput.jump && wingTicks == 0 && entityplayer.posY < 240.0D && entityplayer.posY > 0.0D && !entityplayer.movementInput.sneak) {
				wingTicks = 50;
				entityplayer.motionY = 1.0D;
				sendDamageItemPacket(entityplayer, 1);
				wingFlight = false;
			}
		}
		if (itemstack.getItemDamage() == itemstack.getMaxDamage()) {
			wingTicks = 0;
			wingFlight = true;
		}
	}

	public EnumSet ticks() {
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}

	public String getLabel() {
		return "The Lion King Mod";
	}
}