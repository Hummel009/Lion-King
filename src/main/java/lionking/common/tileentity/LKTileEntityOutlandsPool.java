package lionking.common.tileentity;

import lionking.common.LKLevelData;
import lionking.common.LKMod;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LKTileEntityOutlandsPool extends TileEntity {
	public static ArrayList inventory = new ArrayList();
	private static int timeUntilInventoryClear;

	public static void updateInventory(World world) {
		if (world.isRemote) {
			return;
		}

		if (timeUntilInventoryClear > 0) {
			timeUntilInventoryClear--;
		}

		if (timeUntilInventoryClear == 0) {
			if (!inventory.isEmpty()) {
				ArrayList silverList = new ArrayList();
				ArrayList kivuliteList = new ArrayList();
				int silver = 0;
				int kivulite = 0;

				for (int i = 0; i < inventory.size(); i++) {
					ListedItem l = (ListedItem) inventory.get(i);
					if (l != null) {
						if (l.item.itemID == LKMod.silver.itemID) {
							for (int j = 0; j < l.item.stackSize; j++) {
								silver++;
								silverList.add(i);
							}
						}
						if (l.item.itemID == LKMod.kivulite.itemID) {
							for (int j = 0; j < l.item.stackSize; j++) {
								kivulite++;
								kivuliteList.add(i);
							}
						}
					}
				}

				if (silver > 1 && kivulite > 4) {
					int count = MathHelper.floor_double((double) silver / 2);
					if (MathHelper.floor_double((double) kivulite / 5) < count) {
						count = MathHelper.floor_double((double) kivulite / 5);
					}

					for (int i = 0; i < count; i++) {
						for (int k = 0; k < 2; k++) {
							int j = (Integer) silverList.get(i * 2 + k);
							ListedItem l = (ListedItem) inventory.get(j);
							if (l != null) {
								ItemStack itemstack = l.item;
								itemstack.stackSize--;
								l.item = itemstack;
								if (l.item.stackSize <= 0) {
									l = null;
								}
								inventory.set(j, l);
							}
						}
						for (int k = 0; k < 5; k++) {
							int j = (Integer) kivuliteList.get(i * 5 + k);
							ListedItem l = (ListedItem) inventory.get(j);
							if (l != null) {
								ItemStack itemstack = l.item;
								itemstack.stackSize--;
								l.item = itemstack;
								if (l.item.stackSize <= 0) {
									l = null;
								}
								inventory.set(j, l);
							}
						}
					}

					for (int i = 0; i < count; i++) {
						EntityItem item = new EntityItem(world, LKLevelData.moundX + 0.45D + (world.rand.nextFloat() * 0.1F), LKLevelData.moundY + 9.0D, LKLevelData.moundZ + 0.45D + (world.rand.nextFloat() * 0.1F), new ItemStack(LKMod.outlandsHelm));
						item.delayBeforeCanPickup = 10;
						item.motionX = 0.0D;
						item.motionY = 0.4D + (world.rand.nextFloat() / 5.0F);
						item.motionZ = 0.0D;
						world.spawnEntityInWorld(item);
						world.playSoundAtEntity(item, "random.explode", 1.5F, 0.5F + world.rand.nextFloat() * 0.2F);
					}
				}

				ArrayList featherBlueList = new ArrayList();
				ArrayList featherYellowList = new ArrayList();
				ArrayList featherRedList = new ArrayList();
				ArrayList featherBlackList = new ArrayList();
				int featherBlue = 0;
				int featherYellow = 0;
				int featherRed = 0;
				int featherBlack = 0;

				for (int i = 0; i < inventory.size(); i++) {
					ListedItem l = (ListedItem) inventory.get(i);
					if (l != null) {
						if (l.item.itemID == LKMod.featherBlue.itemID) {
							for (int j = 0; j < l.item.stackSize; j++) {
								featherBlue++;
								featherBlueList.add(i);
							}
						}
						if (l.item.itemID == LKMod.featherYellow.itemID) {
							for (int j = 0; j < l.item.stackSize; j++) {
								featherYellow++;
								featherYellowList.add(i);
							}
						}
						if (l.item.itemID == LKMod.featherRed.itemID) {
							for (int j = 0; j < l.item.stackSize; j++) {
								featherRed++;
								featherRedList.add(i);
							}
						}
						if (l.item.itemID == LKMod.featherBlack.itemID) {
							for (int j = 0; j < l.item.stackSize; j++) {
								featherBlack++;
								featherBlackList.add(i);
							}
						}
					}
				}

				if (featherBlue > 0 && featherYellow > 0 && featherRed > 0 && featherBlack > 0) {
					int count = featherBlue;
					if (featherYellow < count) {
						count = featherYellow;
					}
					if (featherRed < count) {
						count = featherRed;
					}
					if (featherBlack < count) {
						count = featherBlack;
					}

					for (int i = 0; i < count; i++) {
						int j = (Integer) featherBlueList.get(i);
						ListedItem l = (ListedItem) inventory.get(j);
						if (l != null) {
							ItemStack itemstack = l.item;
							itemstack.stackSize--;
							l.item = itemstack;
							if (l.item.stackSize <= 0) {
								l = null;
							}
							inventory.set(j, l);
						}
						j = (Integer) featherYellowList.get(i);
						l = (ListedItem) inventory.get(j);
						if (l != null) {
							ItemStack itemstack = l.item;
							itemstack.stackSize--;
							l.item = itemstack;
							if (l.item.stackSize <= 0) {
								l = null;
							}
							inventory.set(j, l);
						}
						j = (Integer) featherRedList.get(i);
						l = (ListedItem) inventory.get(j);
						if (l != null) {
							ItemStack itemstack = l.item;
							itemstack.stackSize--;
							l.item = itemstack;
							if (l.item.stackSize <= 0) {
								l = null;
							}
							inventory.set(j, l);
						}
						j = (Integer) featherBlackList.get(i);
						l = (ListedItem) inventory.get(j);
						if (l != null) {
							ItemStack itemstack = l.item;
							itemstack.stackSize--;
							l.item = itemstack;
							if (l.item.stackSize <= 0) {
								l = null;
							}
							inventory.set(j, l);
						}
					}

					for (int i = 0; i < count; i++) {
						EntityItem item = new EntityItem(world, LKLevelData.moundX + 0.45D + (world.rand.nextFloat() * 0.1F), LKLevelData.moundY + 9.0D, LKLevelData.moundZ + 0.45D + (world.rand.nextFloat() * 0.1F), new ItemStack(LKMod.outlandsFeather));
						item.delayBeforeCanPickup = 10;
						item.motionX = 0.0D;
						item.motionY = 0.4D + (world.rand.nextFloat() / 5.0F);
						item.motionZ = 0.0D;
						world.spawnEntityInWorld(item);
						world.playSoundAtEntity(item, "random.explode", 1.5F, 0.5F + world.rand.nextFloat() * 0.2F);
					}
				}

				Collection rafikiCoinList = new ArrayList();

				for (int i = 0; i < inventory.size(); i++) {
					ListedItem l = (ListedItem) inventory.get(i);
					if (l != null) {
						if (l.item.itemID == LKMod.rafikiCoin.itemID) {
							for (int j = 0; j < l.item.stackSize; j++) {
								rafikiCoinList.add(i);
							}
						}
					}
				}

				if (!rafikiCoinList.isEmpty()) {
					for (Object o : rafikiCoinList) {
						int j = (Integer) o;
						ListedItem l = (ListedItem) inventory.get(j);
						if (l != null) {
							ItemStack itemstack = l.item;
							itemstack.stackSize--;
							l.item = itemstack;
							if (l.item.stackSize <= 0) {
								l = null;
							}
							inventory.set(j, l);
						}
					}

					for (int i = 0; i < rafikiCoinList.size(); i++) {
						EntityItem item = new EntityItem(world, LKLevelData.moundX + 0.45D + (world.rand.nextFloat() * 0.1F), LKLevelData.moundY + 9.0D, LKLevelData.moundZ + 0.45D + (world.rand.nextFloat() * 0.1F), new ItemStack(LKMod.ziraCoin));
						item.delayBeforeCanPickup = 10;
						item.motionX = 0.0D;
						item.motionY = 0.4D + (world.rand.nextFloat() / 5.0F);
						item.motionZ = 0.0D;
						world.spawnEntityInWorld(item);
						world.playSoundAtEntity(item, "random.explode", 1.5F, 0.5F + world.rand.nextFloat() * 0.2F);
					}
				}

				for (Object o : inventory) {
					ListedItem l = (ListedItem) o;
					if (l != null) {
						int randPosX = LKLevelData.moundX + (world.rand.nextBoolean() ? 1 : -1);
						int randPosZ = LKLevelData.moundZ + (world.rand.nextBoolean() ? 1 : -1);

						EntityItem item = new EntityItem(world, randPosX + 0.25D + (world.rand.nextFloat() / 2.0F), LKLevelData.moundY + 8.0D, randPosZ + 0.25D + (world.rand.nextFloat() / 2.0F), l.item);
						item.delayBeforeCanPickup = 10;
						item.motionX = 0.0D;
						item.motionY = 0.3D + (world.rand.nextFloat() / 5.0F);
						item.motionZ = 0.0D;
						world.spawnEntityInWorld(item);
					}
				}

				inventory.clear();
			}
		}
	}

	public static void writeInventoryToNBT(NBTTagCompound levelData) {
		NBTTagList taglist = new NBTTagList();

		for (Object o : inventory) {
			ListedItem l = (ListedItem) o;
			if (l != null) {
				ItemStack itemstack = l.item;
				Location location = l.location;
				NBTTagCompound nbt = new NBTTagCompound();

				itemstack.writeToNBT(nbt);
				nbt.setDouble("X", location.posX);
				nbt.setDouble("Y", location.posY);
				nbt.setDouble("Z", location.posZ);
				nbt.setLong("TIMESTAMP", l.timestamp);

				taglist.appendTag(nbt);
			}
		}

		levelData.setTag("PoolInventory", taglist);
	}

	public static void readInventoryFromNBT(NBTTagCompound levelData) {
		inventory.clear();

		NBTTagList taglist = levelData.getTagList("PoolInventory");
		if (taglist != null) {
			for (int i1 = 0; i1 < taglist.tagCount(); i1++) {
				NBTTagCompound nbt = (NBTTagCompound) taglist.tagAt(i1);

				ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbt);
				double d = nbt.getDouble("X");
				double d1 = nbt.getDouble("Y");
				double d2 = nbt.getDouble("Z");
				long l = nbt.getLong("TIMESTAMP");

				inventory.add(new ListedItem(l, itemstack, new Location(d, d1, d2)));
			}
		}
	}

	@Override
	public void updateEntity() {
		if (worldObj.provider.dimensionId == LKMod.idOutlands && !worldObj.isRemote) {
			List list = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 0.875, zCoord + 1));
			if (!list.isEmpty()) {
				for (Object o : list) {
					EntityItem entity = (EntityItem) o;
					ItemStack itemstack = entity.getEntityItem();
					worldObj.playSoundAtEntity(entity, "random.fizz", 0.7F, 1.6F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.4F);
					if (itemstack != null) {
						ListedItem item = new ListedItem(worldObj.getWorldTime(), itemstack, new Location(entity.posX, entity.posY, entity.posZ));
						if (!inventory.contains(item)) {
							inventory.add(item);
							timeUntilInventoryClear = 75;
						}
					}
					entity.setDead();
				}
			}
		}

		if (timeUntilInventoryClear > 0) {
			if (worldObj.rand.nextInt(3) == 0) {
				worldObj.spawnParticle("smoke", (xCoord + worldObj.rand.nextFloat()), (yCoord + 0.8F), (zCoord + worldObj.rand.nextFloat()), 0.0D, 0.0D + (worldObj.rand.nextFloat() * 0.25F), 0.0D);
			}
			if (worldObj.rand.nextInt(100) == 0) {
				worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.fizz", 0.7F, 1.6F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.4F);
			}
		}
	}

	private static class ListedItem {
		public long timestamp;
		public ItemStack item;
		public Location location;

		private ListedItem(long l, ItemStack itemstack, Location coords) {
			timestamp = l;
			item = itemstack;
			location = coords;
		}

		public boolean equals(Object obj) {
			if (!(obj instanceof ListedItem)) {
				return false;
			}
			ListedItem l = (ListedItem) obj;
			return timestamp == l.timestamp && location.equals(l.location) && item.itemID == l.item.itemID && item.stackSize == l.item.stackSize && item.getItemDamage() == l.item.getItemDamage();
		}
	}

	private static class Location {
		public double posX;
		public double posY;
		public double posZ;

		private Location(double d, double d1, double d2) {
			posX = d;
			posY = d1;
			posZ = d2;
		}

		public boolean equals(Object obj) {
			if (!(obj instanceof Location)) {
				return false;
			}
			Location l = (Location) obj;
			return posX == l.posX && posY == l.posY && posZ == l.posZ;
		}
	}
}