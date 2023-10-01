package lionking.common.item;

import lionking.common.LKCreativeTabs;
import lionking.common.LKIngame;
import lionking.common.LKMod;
import lionking.common.block.LKBlockKiwanoStem;
import lionking.common.block.LKBlockSapling;
import lionking.common.world.biome.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LKItemHyenaMeal extends LKItem {
	public LKItemHyenaMeal(int i) {
		super(i);
		setCreativeTab(LKCreativeTabs.tabMaterials);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f, float f1, float f2) {
		if (useHyenaMeal(itemstack, entityplayer, world, i, j, k, l)) {
			if (!world.isRemote) {
				world.playAuxSFX(2005, i, j, k, 0);
			}
			return true;
		}
		return false;
	}

	private boolean useHyenaMeal(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		if (world.isRemote || !LKIngame.isLKWorld(world.provider.dimensionId) || !entityplayer.canPlayerEdit(i, j, k, l, itemstack)) {
			return false;
		}

		int i1 = world.getBlockId(i, j, k);
		if (i1 == LKMod.sapling.blockID) {
			itemstack.stackSize--;
			if (world.rand.nextFloat() < 0.45D) {
				((LKBlockSapling) LKMod.sapling).growTree(world, i, j, k, world.rand);
			}
			return true;
		}

		if (i1 == LKMod.forestSapling.blockID) {
			itemstack.stackSize--;
			if (world.rand.nextFloat() < 0.45D) {
				((LKBlockSapling) LKMod.forestSapling).growTree(world, i, j, k, world.rand);
			}
			return true;
		}

		if (i1 == LKMod.mangoSapling.blockID) {
			itemstack.stackSize--;
			if (world.rand.nextFloat() < 0.45D) {
				((LKBlockSapling) LKMod.mangoSapling).growTree(world, i, j, k, world.rand);
			}
			return true;
		}

		if (i1 == LKMod.passionSapling.blockID) {
			itemstack.stackSize--;
			if (world.rand.nextFloat() < 0.45D) {
				((LKBlockSapling) LKMod.passionSapling).growTree(world, i, j, k, world.rand);
			}
			return true;
		}

		if (i1 == LKMod.bananaSapling.blockID) {
			itemstack.stackSize--;
			if (world.rand.nextFloat() < 0.45D) {
				((LKBlockSapling) LKMod.bananaSapling).growTree(world, i, j, k, world.rand);
			}
			return true;
		}

		if ((i1 == Block.crops.blockID || i1 == LKMod.yamCrops.blockID) && world.getBlockMetadata(i, j, k) < 7) {
			((BlockCrops) Block.blocksList[i1]).fertilize(world, i, j, k);
			itemstack.stackSize--;
			return true;
		}

		if (i1 == LKMod.kiwanoStem.blockID && world.getBlockMetadata(i, j, k) < 7) {
			((LKBlockKiwanoStem) LKMod.kiwanoStem).fertilizePartially(world, i, j, k);
			itemstack.stackSize--;
			return true;
		}

		if (i1 == Block.grass.blockID) {
			BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(i, k);
			world.notifyBlockChange(i, j, k, world.getBlockId(i, j, k));
			label0:
			for (int l1 = 0; l1 < 128; ++l1) {
				int i2 = i;
				int j2 = j + 1;
				int k2 = k;
				for (int l2 = 0; l2 < l1 / 16; ++l2) {
					i2 += itemRand.nextInt(3) - 1;
					j2 += (itemRand.nextInt(3) - 1) * itemRand.nextInt(3) / 2;
					k2 += itemRand.nextInt(3) - 1;
					if (world.getBlockId(i2, j2 - 1, k2) != Block.grass.blockID || world.isBlockNormalCube(i2, j2, k2)) {
						continue label0;
					}
				}
				if (world.isAirBlock(i2, j2, k2)) {
					if (itemRand.nextInt(7) != 0 && Block.tallGrass.canBlockStay(world, i2, j2, k2)) {
						int j3 = 1;
						if ((biome instanceof LKBiomeGenRainforest || biome instanceof LKBiomeGenUpendi) && itemRand.nextInt(5) == 0) {
							j3 = 2;
						}
						world.setBlock(i2, j2, k2, Block.tallGrass.blockID, j3, 3);
					} else {
						if (biome instanceof LKBiomeGenUpendi) {
							int i3 = itemRand.nextInt(6);
							if (i3 == 0 && world.isAirBlock(i2, j2 + 1, k2) && LKMod.flowerBase.canBlockStay(world, i2, j2, k2)) {
								world.setBlock(i2, j2, k2, LKMod.flowerBase.blockID, 1, 3);
								world.setBlock(i2, j2 + 1, k2, LKMod.flowerTop.blockID, 1, 3);
							} else if (i3 > 3 && world.isAirBlock(i2, j2 + 1, k2) && LKMod.flowerBase.canBlockStay(world, i2, j2, k2)) {
								world.setBlock(i2, j2, k2, LKMod.flowerBase.blockID, 0, 3);
								world.setBlock(i2, j2 + 1, k2, LKMod.flowerTop.blockID, 0, 3);
							} else if (LKMod.whiteFlower.canBlockStay(world, i2, j2, k2)) {
								world.setBlock(i2, j2, k2, LKMod.whiteFlower.blockID, 0, 3);
							}
						}
						if (biome instanceof LKBiomeGenMountains && LKMod.blueFlower.canBlockStay(world, i2, j2, k2)) {
							world.setBlock(i2, j2, k2, LKMod.blueFlower.blockID, 0, 3);
						}
						if (biome instanceof LKBiomeGenRainforest) {
							int i3 = itemRand.nextInt(5);
							if (i3 < 2 && world.isAirBlock(i2, j2 + 1, k2) && LKMod.flowerBase.canBlockStay(world, i2, j2, k2)) {
								world.setBlock(i2, j2, k2, LKMod.flowerBase.blockID, 0, 3);
								world.setBlock(i2, j2 + 1, k2, LKMod.flowerTop.blockID, 0, 3);
							} else if (LKMod.whiteFlower.canBlockStay(world, i2, j2, k2)) {
								world.setBlock(i2, j2, k2, LKMod.whiteFlower.blockID, 0, 3);
							}
						}
						if (biome instanceof LKBiomeGenSavannahBase || biome instanceof LKBiomeGenRiver || biome instanceof LKBiomeGenAridSavannah) {
							int i3 = itemRand.nextInt(5);
							if (i3 == 0 && world.isAirBlock(i2, j2 + 1, k2) && LKMod.flowerBase.canBlockStay(world, i2, j2, k2)) {
								world.setBlock(i2, j2, k2, LKMod.flowerBase.blockID, 0, 3);
								world.setBlock(i2, j2 + 1, k2, LKMod.flowerTop.blockID, 0, 3);
							} else if (LKMod.whiteFlower.canBlockStay(world, i2, j2, k2)) {
								world.setBlock(i2, j2, k2, LKMod.whiteFlower.blockID, 0, 3);
							}
						}
					}
				}
			}
			itemstack.stackSize--;
			return true;
		}

		if (i1 == Block.sand.blockID) {
			BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(i, k);
			if (biome instanceof LKBiomeGenAridSavannah || biome instanceof LKBiomeGenDesert) {
				world.notifyBlockChange(i, j, k, world.getBlockId(i, j, k));
				label0:
				for (int l1 = 0; l1 < 128; ++l1) {
					int i2 = i;
					int j2 = j + 1;
					int k2 = k;
					for (int l2 = 0; l2 < l1 / 16; ++l2) {
						i2 += itemRand.nextInt(3) - 1;
						j2 += (itemRand.nextInt(3) - 1) * itemRand.nextInt(3) / 2;
						k2 += itemRand.nextInt(3) - 1;
						if (world.getBlockId(i2, j2 - 1, k2) != Block.sand.blockID || world.isBlockNormalCube(i2, j2, k2)) {
							continue label0;
						}
					}
					if (world.isAirBlock(i2, j2, k2)) {
						if (world.rand.nextInt(16) == 0 && Block.deadBush.canBlockStay(world, i2, j2, k2)) {
							world.setBlock(i2, j2, k2, Block.deadBush.blockID, 0, 3);
						} else if (LKMod.aridGrass.canBlockStay(world, i2, j2, k2)) {
							world.setBlock(i2, j2, k2, LKMod.aridGrass.blockID, 0, 3);
						}
					}
				}
				itemstack.stackSize--;
				return true;
			}
		}

		return false;
	}
}