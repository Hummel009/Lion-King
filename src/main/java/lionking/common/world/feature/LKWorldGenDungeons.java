package lionking.common.world.feature;

import lionking.common.LKLevelData;
import lionking.common.tileentity.LKTileEntityMobSpawner;
import lionking.common.entity.LKEntities;
import lionking.common.entity.LKEntityCrocodile;
import lionking.common.entity.LKEntityHyena;
import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LKWorldGenDungeons extends WorldGenerator {
	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		byte byte0 = 3;
		int l = random.nextInt(2) + 2;
		int i1 = random.nextInt(2) + 2;
		int j1 = 0;
		boolean spawnCrocodiles = false;
		for (int k1 = i - l - 1; k1 <= i + l + 1; k1++) {
			for (int j2 = j - 1; j2 <= j + byte0 + 1; j2++) {
				for (int i3 = k - i1 - 1; i3 <= k + i1 + 1; i3++) {
					Material material = world.getBlockMaterial(k1, j2, i3);
					if (j2 == j - 1 && !material.isSolid()) {
						return false;
					}
					if (j2 == j + byte0 + 1 && !material.isSolid()) {
						return false;
					}
					if ((k1 == i - l - 1 || k1 == i + l + 1 || i3 == k - i1 - 1 || i3 == k + i1 + 1) && j2 == j && world.isAirBlock(k1, j2, i3) && world.isAirBlock(k1, j2 + 1, i3)) {
						j1++;
					}
				}

			}
		}

		if (random.nextInt(3) == 0) {
			spawnCrocodiles = true;
			l += random.nextInt(2) + 1;
			i1 += random.nextInt(2) + 1;
		}

		if (j1 < 1 || j1 > 5) {
			return false;
		}

		int j5 = random.nextInt(4);
		int j6 = random.nextInt(4);
		if (random.nextInt(4) == 0) {
			j5 = -1;
		}
		if (random.nextInt(4) == 0) {
			j6 = -1;
		}

		for (int l1 = i - l - 1; l1 <= i + l + 1; l1++) {
			for (int k2 = j + byte0; k2 >= j - 1; k2--) {
				for (int j3 = k - i1 - 1; j3 <= k + i1 + 1; j3++) {
					if (l1 == i - l - 1 || k2 == j - 1 || j3 == k - i1 - 1 || l1 == i + l + 1 || k2 == j + byte0 + 1 || j3 == k + i1 + 1) {
						if (!spawnCrocodiles) {
							if (k2 >= 0 && !world.getBlockMaterial(l1, k2 - 1, j3).isSolid()) {
								world.setBlock(l1, k2, j3, 0, 0, 2);
								continue;
							}
							if (!world.getBlockMaterial(l1, k2, j3).isSolid()) {
								continue;
							}
						}
						if (k2 == j - 1 && random.nextInt(4) != 0) {
							world.setBlock(l1, k2, j3, LKMod.prideBrickMossy.blockID, 0, 2);
						} else {
							world.setBlock(l1, k2, j3, LKMod.prideBrick.blockID, 0, 2);
						}
					} else {
						world.setBlock(l1, k2, j3, 0, 0, 2);
					}
					if (j5 == 0 || j6 == 0) {
						if (l1 == i - l && j3 == k - i1 && k2 >= j) {
							world.setBlock(l1, k2, j3, LKMod.pridePillar.blockID, k2 - j == 0 || k2 - j == 3 ? 1 : 2, 2);
						}
					}
					if (j5 == 1 || j6 == 1) {
						if (l1 == i + l && j3 == k - i1 && k2 >= j) {
							world.setBlock(l1, k2, j3, LKMod.pridePillar.blockID, k2 - j == 0 || k2 - j == 3 ? 1 : 2, 2);
						}
					}
					if (j5 == 2 || j6 == 2) {
						if (l1 == i - l && j3 == k + i1 && k2 >= j) {
							world.setBlock(l1, k2, j3, LKMod.pridePillar.blockID, k2 - j == 0 || k2 - j == 3 ? 1 : 2, 2);
						}
					}
					if (j5 == 3 || j6 == 3) {
						if (l1 == i + l && j3 == k + i1 && k2 >= j) {
							world.setBlock(l1, k2, j3, LKMod.pridePillar.blockID, k2 - j == 0 || k2 - j == 3 ? 1 : 2, 2);
						}
					}
					if (spawnCrocodiles && l1 > i - l && l1 < i + l && j3 > k - i1 && j3 < k + i1 && k2 == j - 1) {
						if (l1 != i || j3 != k) {
							world.setBlock(l1, k2, j3, Block.waterStill.blockID, 0, 2);
						}
						world.setBlock(l1, k2 - 1, j3, random.nextInt(4) != 0 ? LKMod.prideBrickMossy.blockID : LKMod.prideBrick.blockID, 0, 2);
					}
				}
			}
		}

		int j7 = spawnCrocodiles ? 3 : 2;
		for (int i2 = 0; i2 < j7; i2++) {
			label0:
			for (int l2 = 0; l2 <= j7; l2++) {
				int k3 = (i + random.nextInt(l * 2 + 1)) - l;
				int i4 = (k + random.nextInt(i1 * 2 + 1)) - i1;
				if (!world.isAirBlock(k3, j, i4) && world.getBlockId(k3, j, i4) != Block.vine.blockID) {
					continue;
				}
				int j4 = 0;
				if (world.getBlockMaterial(k3 - 1, j, i4).isSolid()) {
					j4++;
				}
				if (world.getBlockMaterial(k3 + 1, j, i4).isSolid()) {
					j4++;
				}
				if (world.getBlockMaterial(k3, j, i4 - 1).isSolid()) {
					j4++;
				}
				if (world.getBlockMaterial(k3, j, i4 + 1).isSolid()) {
					j4++;
				}
				if (j4 != 1) {
					continue;
				}
				if (!world.isBlockOpaqueCube(k3, j - 1, i4)) {
					continue;
				}
				world.setBlock(k3, j, i4, Block.chest.blockID, 0, 2);
				IInventory tileentitychest = (IInventory) world.getBlockTileEntity(k3, j, i4);
				if (tileentitychest == null) {
					break;
				}
				int k4 = 0;
				do {
					if (k4 >= 8) {
						if (random.nextInt(4) == 0) {
							tileentitychest.setInventorySlotContents(random.nextInt(tileentitychest.getSizeInventory()), new ItemStack(LKMod.passionSapling));
						}
						break label0;
					}
					ItemStack itemstack = pickCheckLootItem(random);
					if (itemstack != null) {
						if (itemstack.getItem().getItemEnchantability() > 0 && random.nextInt(3) != 0) {
							EnchantmentHelper.addRandomEnchantment(random, itemstack, 3);
						}
						tileentitychest.setInventorySlotContents(random.nextInt(tileentitychest.getSizeInventory()), itemstack);
					}
					k4++;
				}
				while (true);
			}
		}

		world.setBlock(i, j, k, LKMod.mobSpawner.blockID, 0, 2);
		LKTileEntityMobSpawner spawner = (LKTileEntityMobSpawner) world.getBlockTileEntity(i, j, k);
		if (spawner != null) {
			spawner.setMobID(spawnCrocodiles ? LKEntities.getEntityIDFromClass(LKEntityCrocodile.class) : LKEntities.getEntityIDFromClass(LKEntityHyena.class));
		}

		if (spawnCrocodiles) {
			for (int l1 = i - l - 1; l1 <= i + l + 1; l1++) {
				for (int k2 = j + byte0; k2 >= j - 1; k2--) {
					for (int j3 = k - i1 - 1; j3 <= k + i1 + 1; j3++) {
						Block block = Block.blocksList[world.getBlockId(l1, k2, j3)];
						if (block != null && (block == LKMod.pridestone || block == LKMod.prideBrick)) {
							if (random.nextInt(4) == 0 && world.isAirBlock(l1 - 1, k2, j3)) {
								generateVines(world, random, l1 - 1, k2, j3, 8);
							}
							if (random.nextInt(4) == 0 && world.isAirBlock(l1 + 1, k2, j3)) {
								generateVines(world, random, l1 + 1, k2, j3, 2);
							}
							if (random.nextInt(4) == 0 && world.isAirBlock(l1, k2, j3 - 1)) {
								generateVines(world, random, l1, k2, j3 - 1, 2);
							}
							if (random.nextInt(4) == 0 && world.isAirBlock(l1, k2, j3 + 1)) {
								generateVines(world, random, l1, k2, j3 + 1, 4);
							}
						}
					}
				}
			}
		}
		return true;
	}

	private ItemStack pickCheckLootItem(Random random) {
		int i = random.nextInt(12);
		if (i == 0) {
			return new ItemStack(LKMod.hyenaBone, random.nextInt(3) + 2);
		}
		if (i == 1) {
			return new ItemStack(LKMod.bug, random.nextInt(4) + 2);
		}
		if (i == 2 || i == 3 || i == 4) {
			int j = random.nextInt(7);
			switch (j) {
				case 0:
					return new ItemStack(LKMod.dartBlue, random.nextInt(5) + 3);
				case 1:
					return new ItemStack(LKMod.dartYellow, random.nextInt(4) + 3);
				case 2:
					return new ItemStack(LKMod.dartRed, random.nextInt(4) + 3);
				case 3:
					return new ItemStack(LKMod.featherBlue, random.nextInt(4) + 3);
				case 4:
					return new ItemStack(LKMod.featherYellow, random.nextInt(3) + 3);
				case 5:
					return new ItemStack(LKMod.featherRed, random.nextInt(3) + 3);
				case 6:
					return new ItemStack(LKMod.silverDartShooter);
			}
		}
		if (i == 5) {
			return new ItemStack(LKMod.chocolateMufasa, random.nextInt(3) + 1);
		}
		if (i == 6) {
			return new ItemStack(LKMod.silver, random.nextInt(3) + 2);
		}
		if (i == 7) {
			return new ItemStack(LKMod.dartQuiver, 1, LKLevelData.quiverDamage++);
		}
		if (i == 8) {
			return new ItemStack(LKMod.mango, random.nextInt(3) + 1);
		}
		if (i == 9) {
			int j = random.nextInt(3);
			switch (j) {
				case 0:
					return new ItemStack(LKMod.peacockGem, random.nextInt(2) + 1);
				case 1:
					return new ItemStack(Item.compass);
				case 2:
					return new ItemStack(LKMod.jar);
			}
		}
		int j = random.nextInt(7);
		switch (j) {
			case 0:
				return new ItemStack(LKMod.shovelSilver);
			case 1:
				return new ItemStack(LKMod.pickaxeSilver);
			case 2:
				return new ItemStack(LKMod.axeSilver);
			case 3:
				return new ItemStack(LKMod.swordSilver);
			case 4:
				return new ItemStack(LKMod.helmetSilver);
			case 5:
				return new ItemStack(LKMod.bootsSilver);
			case 6:
				return new ItemStack(LKMod.note, 1 + random.nextInt(3), 6);
		}
		return null;
	}

	private void generateVines(World world, Random random, int i, int j, int k, int l) {
		world.setBlock(i, j, k, Block.vine.blockID, l, 2);
		Block.vine.onNeighborBlockChange(world, i, j, k, 0);
		int i1 = random.nextInt(2) + 4;
		while (true) {
			--j;
			if (world.getBlockId(i, j, k) != 0 || i1 <= 0) {
				return;
			}
			world.setBlock(i, j, k, Block.vine.blockID, l, 2);
			Block.vine.onNeighborBlockChange(world, i, j, k, 0);
			--i1;
		}
	}
}
