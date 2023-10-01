package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import lionking.common.LKIngame;
import lionking.common.LKMod;
import lionking.common.world.biome.LKBiomeGenUpendi;
import lionking.common.world.feature.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LKBlockSapling extends LKBlockFlower {
	public LKBlockSapling(int i) {
		super(i);
		float f = 0.4F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		setCreativeTab(LKCreativeTabs.tabDeco);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		WorldChunkManager worldchunkmanager = world.getWorldChunkManager();
		if (worldchunkmanager != null) {
			boolean canGrow = LKIngame.isLKWorld(world.provider.dimensionId);
			if (blockID == LKMod.passionSapling.blockID) {
				canGrow = worldchunkmanager.getBiomeGenAt(i, k) instanceof LKBiomeGenUpendi;
			}

			if (canGrow) {
				if (world.isRemote) {
					return;
				}

				super.updateTick(world, i, j, k, random);
				if (world.getBlockLightValue(i, j + 1, k) >= 9 && random.nextInt(7) == 0) {
					incrementGrowth(world, i, j, k, random);
				}
			}
		}
	}

	public void incrementGrowth(World world, int i, int j, int k, Random random) {
		int l = world.getBlockMetadata(i, j, k);
		if ((l & 8) == 0) {
			world.setBlockMetadataWithNotify(i, j, k, l | 8, 4);
		} else {
			growTree(world, i, j, k, random);
		}
	}

	public void growTree(World world, int i, int j, int k, Random random) {
		WorldGenerator tree = null;
		int i1 = 0;
		int j1 = 0;
		boolean growHugeTree = false;

		if (blockID == LKMod.bananaSapling.blockID) {
			tree = new LKWorldGenBananaTrees(true);
		} else if (blockID == LKMod.passionSapling.blockID) {
			tree = new LKWorldGenPassionTrees(true);
		} else if (blockID == LKMod.mangoSapling.blockID) {
			tree = new LKWorldGenMangoTrees(true);
		} else if (blockID == LKMod.forestSapling.blockID || blockID == LKMod.sapling.blockID) {
			for (i1 = 0; i1 >= -1; --i1) {
				for (j1 = 0; j1 >= -1; --j1) {
					if (world.getBlockId(i + i1, j, k + j1) == blockID && world.getBlockId(i + i1 + 1, j, k + j1) == blockID && world.getBlockId(i + i1, j, k + j1 + 1) == blockID && world.getBlockId(i + i1 + 1, j, k + j1 + 1) == blockID) {
						if (blockID == LKMod.forestSapling.blockID) {
							tree = new LKWorldGenHugeRainforestTrees(true);
						} else if (blockID == LKMod.sapling.blockID) {
							tree = new LKWorldGenTrees(true).setLarge();
						}
						growHugeTree = true;
						break;
					}
				}

				if (tree != null) {
					break;
				}
			}

			if (tree == null) {
				j1 = 0;
				i1 = 0;
				if (blockID == LKMod.forestSapling.blockID) {
					tree = new LKWorldGenRainforestTrees(true);
				} else if (blockID == LKMod.sapling.blockID) {
					tree = new LKWorldGenTrees(true);
				}
			}
		}

		if (growHugeTree) {
			world.setBlock(i + i1, j, k + j1, 0, 0, 4);
			world.setBlock(i + i1 + 1, j, k + j1, 0, 0, 4);
			world.setBlock(i + i1, j, k + j1 + 1, 0, 0, 4);
			world.setBlock(i + i1 + 1, j, k + j1 + 1, 0, 0, 4);
		} else {
			world.setBlock(i, j, k, 0, 0, 4);
		}

		if (tree != null) {
			if (!tree.generate(world, random, i + i1, j, k + j1)) {
				if (growHugeTree) {
					world.setBlock(i + i1, j, k + j1, blockID, 0, 4);
					world.setBlock(i + i1 + 1, j, k + j1, blockID, 0, 4);
					world.setBlock(i + i1, j, k + j1 + 1, blockID, 0, 4);
					world.setBlock(i + i1 + 1, j, k + j1 + 1, blockID, 0, 4);
				} else {
					world.setBlock(i, j, k, blockID, 0, 4);
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (blockID == LKMod.passionSapling.blockID) {
			for (int l = 0; l < 4; l++) {
				double d = i + random.nextFloat();
				double d1 = j + random.nextFloat();
				double d2 = k + random.nextFloat();
				double d3 = (-0.5F + random.nextFloat()) * 0.01F;
				double d4 = random.nextFloat() * 0.01F;
				double d5 = (-0.5F + random.nextFloat()) * 0.01F;
				LKMod.proxy.spawnParticle("passion", d, d1, d2, d3, d4, d5);
			}
		}
	}
}
