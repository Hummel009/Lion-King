package lionking.common.world.feature;

import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LKWorldGenHugeRainforestTrees extends WorldGenerator {
	public LKWorldGenHugeRainforestTrees(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int l = 25 + random.nextInt(25);

		if (j < 1 || j + l + 5 > 256) {
			return false;
		}

		for (int i1 = i; i1 < i + 2; i1++) {
			for (int k1 = k; k1 < k + 2; k1++) {
				for (int j1 = j; j1 < j + l; j1++) {
					if (!isBlockReplaceable(world, i1, j1, k1)) {
						return false;
					}
					if (j1 == j) {
						if (world.getBlockId(i1, j1 - 1, k1) != Block.grass.blockID && world.getBlockId(i1, j1 - 1, k1) != Block.dirt.blockID) {
							return false;
						}
						if (!world.isAirBlock(i1, j1, k1) && world.getBlockId(i1, j1, k1) != LKMod.forestSapling.blockID) {
							return false;
						}
					}
				}
			}
		}

		for (int i1 = i - 1; i1 < i + 3; i1++) {
			for (int k1 = k - 1; k1 < k + 3; k1++) {
				if ((i1 == i - 1 && k1 == k - 1) || (i1 == i + 2 && k1 == k - 1) || (i1 == i - 1 && k1 == k + 2) || (i1 == i + 2 && k1 == k + 2)) {
				} else if (!isBlockReplaceable(world, i1, j + l, k1)) {
					return false;
				}
			}
		}

		for (int i1 = i - 2; i1 < i + 4; i1++) {
			for (int k1 = k - 2; k1 < k + 4; k1++) {
				if ((i1 == i - 2 && k1 == k - 2) || (i1 == i + 3 && k1 == k - 2) || (i1 == i - 2 && k1 == k + 3) || (i1 == i + 3 && k1 == k + 3)) {
				} else if (!isBlockReplaceable(world, i1, j + l + 1, k1)) {
					return false;
				}
			}
		}

		if (!isBlockReplaceable(world, i, j + l + 1, k - 3) || !isBlockReplaceable(world, i + 1, j + l + 1, k - 3)
				|| !isBlockReplaceable(world, i, j + l + 1, k + 4) || !isBlockReplaceable(world, i + 1, j + l + 1, k + 4)
				|| !isBlockReplaceable(world, i - 3, j + l + 1, k) || !isBlockReplaceable(world, i - 3, j + l + 1, k + 1)
				|| !isBlockReplaceable(world, i + 4, j + l + 1, k) || !isBlockReplaceable(world, i + 4, j + l + 1, k + 1)) {
			return false;
		}

		for (int i1 = i - 5; i1 < i + 7; i1++) {
			for (int k1 = k - 5; k1 < k + 7; k1++) {
				if (!isBlockReplaceable(world, i1, j + l + 2, k1)) {
					return false;
				}
			}
		}

		if (!isBlockReplaceable(world, i - 2, j + l + 2, k - 5) || !isBlockReplaceable(world, i - 1, j + l + 2, k - 5) || !isBlockReplaceable(world, i, j + l + 2, k - 5) || !isBlockReplaceable(world, i + 1, j + l + 2, k - 5) || !isBlockReplaceable(world, i + 2, j + l + 2, k - 5) || !isBlockReplaceable(world, i + 3, j + l + 2, k - 5)
				|| !isBlockReplaceable(world, i - 2, j + l + 2, k + 6) || !isBlockReplaceable(world, i - 1, j + l + 2, k + 6) || !isBlockReplaceable(world, i, j + l + 2, k + 6) || !isBlockReplaceable(world, i + 1, j + l + 2, k + 6) || !isBlockReplaceable(world, i + 2, j + l + 2, k + 6) || !isBlockReplaceable(world, i + 3, j + l + 2, k + 6)
				|| !isBlockReplaceable(world, i - 5, j + l + 2, k - 2) || !isBlockReplaceable(world, i - 5, j + l + 2, k - 1) || !isBlockReplaceable(world, i - 5, j + l + 2, k) || !isBlockReplaceable(world, i - 5, j + l + 2, k + 1) || !isBlockReplaceable(world, i - 5, j + l + 2, k + 2) || !isBlockReplaceable(world, i - 5, j + l + 2, k + 3)
				|| !isBlockReplaceable(world, i + 6, j + l + 2, k - 2) || !isBlockReplaceable(world, i + 6, j + l + 2, k - 1) || !isBlockReplaceable(world, i + 6, j + l + 2, k) || !isBlockReplaceable(world, i + 6, j + l + 2, k + 1) || !isBlockReplaceable(world, i + 6, j + l + 2, k + 2) || !isBlockReplaceable(world, i + 6, j + l + 2, k + 3)) {
			return false;
		}

		for (int i1 = i - 3; i1 < i + 5; i1++) {
			for (int k1 = k - 3; k1 < k + 5; k1++) {
				if (!isBlockReplaceable(world, i1, j + l + 3, k1)) {
					return false;
				}
			}
		}

		if (!isBlockReplaceable(world, i - 2, j + l + 3, k - 3) || !isBlockReplaceable(world, i - 1, j + l + 3, k - 3) || !isBlockReplaceable(world, i, j + l + 3, k - 3) || !isBlockReplaceable(world, i + 1, j + l + 3, k - 3) || !isBlockReplaceable(world, i + 2, j + l + 3, k - 3) || !isBlockReplaceable(world, i + 3, j + l + 3, k - 3)
				|| !isBlockReplaceable(world, i - 2, j + l + 3, k + 4) || !isBlockReplaceable(world, i - 1, j + l + 3, k + 4) || !isBlockReplaceable(world, i, j + l + 3, k + 4) || !isBlockReplaceable(world, i + 1, j + l + 3, k + 4) || !isBlockReplaceable(world, i + 2, j + l + 3, k + 4) || !isBlockReplaceable(world, i + 3, j + l + 3, k + 4)
				|| !isBlockReplaceable(world, i - 3, j + l + 3, k - 2) || !isBlockReplaceable(world, i - 3, j + l + 3, k - 1) || !isBlockReplaceable(world, i - 3, j + l + 3, k) || !isBlockReplaceable(world, i - 3, j + l + 3, k + 1) || !isBlockReplaceable(world, i - 3, j + l + 3, k + 2) || !isBlockReplaceable(world, i - 3, j + l + 3, k + 3)
				|| !isBlockReplaceable(world, i + 4, j + l + 3, k - 2) || !isBlockReplaceable(world, i + 4, j + l + 3, k - 1) || !isBlockReplaceable(world, i + 4, j + l + 3, k) || !isBlockReplaceable(world, i + 4, j + l + 3, k + 1) || !isBlockReplaceable(world, i + 4, j + l + 3, k + 2) || !isBlockReplaceable(world, i + 4, j + l + 3, k + 3)) {
			return false;
		}

		for (int j2 = -1; j2 < 2; j2++) {
			for (int l2 = 0; l2 < 4; l2++) {
				if (l2 == 0 && !isBlockReplaceable(world, i - 2 - j2, j + l + j2, k - 2 - j2)) {
					return false;
				}
				if (l2 == 1 && !isBlockReplaceable(world, i - 2 - j2, j + l + j2, k + 3 + j2)) {
					return false;
				}
				if (l2 == 2 && !isBlockReplaceable(world, i + 3 + j2, j + l + j2, k - 2 - j2)) {
					return false;
				}
				if (l2 == 3 && !isBlockReplaceable(world, i + 3 + j2, j + l + j2, k + 3 + j2)) {
					return false;
				}
			}
		}

		int l1 = MathHelper.floor_double((double) l / 2);

		for (int i1 = i - 1; i1 < i + 3; i1++) {
			for (int k1 = k - 1; k1 < k + 3; k1++) {
				if ((i1 == i - 1 && k1 == k - 1) || (i1 == i + 2 && k1 == k - 1) || (i1 == i - 1 && k1 == k + 2) || (i1 == i + 2 && k1 == k + 2)) {
				} else if (!isBlockReplaceable(world, i1, j + l1, k1)) {
					return false;
				}
			}
		}

		for (int i1 = i - 1; i1 < i + 4; i1++) {
			for (int k1 = k - 1; k1 < k + 4; k1++) {
				if ((i1 == i - 2 && k1 == k - 2) || (i1 == i + 3 && k1 == k - 2) || (i1 == i - 2 && k1 == k + 3) || (i1 == i + 3 && k1 == k + 3)) {
				} else if (!isBlockReplaceable(world, i1, j + l1 + 1, k1)) {
					return false;
				}
			}
		}

		for (int i1 = i; i1 < i + 2; i1++) {
			for (int k1 = k; k1 < k + 2; k1++) {
				for (int j1 = j; j1 < j + l; j1++) {
					setBlockAndMetadata(world, i1, j1, k1, LKMod.prideWood.blockID, 1);
					if (random.nextInt(5) != 0) {
						generateVines(world, random, i1, j1, k1);
					}
					if (j1 == j) {
						setBlockAndMetadata(world, i1, j1 - 1, k1, Block.dirt.blockID, 0);
					}
				}
			}
		}

		for (int i1 = i - 1; i1 < i + 3; i1++) {
			for (int k1 = k - 1; k1 < k + 3; k1++) {
				if ((i1 == i - 1 && k1 == k - 1) || (i1 == i + 2 && k1 == k - 1) || (i1 == i - 1 && k1 == k + 2) || (i1 == i + 2 && k1 == k + 2)) {
				} else {
					setBlockAndMetadata(world, i1, j + l, k1, LKMod.prideWood.blockID, 1);
				}
			}
		}

		for (int i1 = i - 2; i1 < i + 4; i1++) {
			for (int k1 = k - 2; k1 < k + 4; k1++) {
				if ((i1 == i - 2 && k1 == k - 2) || (i1 == i + 3 && k1 == k - 2) || (i1 == i - 2 && k1 == k + 3) || (i1 == i + 3 && k1 == k + 3)) {
				} else {
					setBlockAndMetadata(world, i1, j + l + 1, k1, LKMod.forestLeaves.blockID, 0);
				}
			}
		}

		for (int i1 = 0; i1 < 2; i1++) {
			for (int k1 = 0; k1 < 2; k1++) {
				setBlockAndMetadata(world, i + i1 - 2, j + l + 1, k + k1, LKMod.prideWood.blockID, 1);
				setBlockAndMetadata(world, i + i1 + 2, j + l + 1, k + k1, LKMod.prideWood.blockID, 1);
				setBlockAndMetadata(world, i + i1, j + l + 1, k + k1 - 2, LKMod.prideWood.blockID, 1);
				setBlockAndMetadata(world, i + i1, j + l + 1, k + k1 + 2, LKMod.prideWood.blockID, 1);
				setBlockAndMetadata(world, i + i1, j + l + 1, k + k1, LKMod.prideWood.blockID, 1);
			}
		}

		setBlockAndMetadata(world, i, j + l + 1, k - 3, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 1, j + l + 1, k - 3, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i, j + l + 1, k + 4, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 1, j + l + 1, k + 4, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 3, j + l + 1, k, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 3, j + l + 1, k + 1, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 4, j + l + 1, k, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 4, j + l + 1, k + 1, LKMod.forestLeaves.blockID, 0);

		for (int i1 = i - 5; i1 < i + 7; i1++) {
			for (int k1 = k - 5; k1 < k + 7; k1++) {
				setBlockAndMetadata(world, i1, j + l + 2, k1, LKMod.forestLeaves.blockID, 0);
			}
		}

		for (int i1 = 0; i1 < 2; i1++) {
			for (int k1 = 0; k1 < 2; k1++) {
				setBlockAndMetadata(world, i + i1 - 3, j + l + 2, k + k1, LKMod.prideWood.blockID, 1);
				setBlockAndMetadata(world, i + i1 + 3, j + l + 2, k + k1, LKMod.prideWood.blockID, 1);
				setBlockAndMetadata(world, i + i1, j + l + 2, k + k1 - 3, LKMod.prideWood.blockID, 1);
				setBlockAndMetadata(world, i + i1, j + l + 2, k + k1 + 3, LKMod.prideWood.blockID, 1);
				setBlockAndMetadata(world, i + i1, j + l + 2, k + k1, LKMod.prideWood.blockID, 1);
			}
		}

		setBlockAndMetadata(world, i - 2, j + l + 2, k - 6, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 1, j + l + 2, k - 6, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i, j + l + 2, k - 6, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 1, j + l + 2, k - 6, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 2, j + l + 2, k - 6, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 3, j + l + 2, k - 6, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 2, j + l + 2, k + 7, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 1, j + l + 2, k + 7, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i, j + l + 2, k + 7, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 1, j + l + 2, k + 7, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 2, j + l + 2, k + 7, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 3, j + l + 2, k + 7, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 6, j + l + 2, k - 2, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 6, j + l + 2, k - 1, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 6, j + l + 2, k, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 6, j + l + 2, k + 1, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 6, j + l + 2, k + 2, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 6, j + l + 2, k + 3, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 7, j + l + 2, k - 2, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 7, j + l + 2, k - 1, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 7, j + l + 2, k, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 7, j + l + 2, k + 1, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 7, j + l + 2, k + 2, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 7, j + l + 2, k + 3, LKMod.forestLeaves.blockID, 0);

		for (int i1 = i - 3; i1 < i + 5; i1++) {
			for (int k1 = k - 3; k1 < k + 5; k1++) {
				setBlockAndMetadata(world, i1, j + l + 3, k1, LKMod.forestLeaves.blockID, 0);
			}
		}

		setBlockAndMetadata(world, i - 2, j + l + 3, k - 4, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 1, j + l + 3, k - 4, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i, j + l + 3, k - 4, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 1, j + l + 3, k - 4, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 2, j + l + 3, k - 4, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 3, j + l + 3, k - 4, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 2, j + l + 3, k + 5, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 1, j + l + 3, k + 5, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i, j + l + 3, k + 5, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 1, j + l + 3, k + 5, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 2, j + l + 3, k + 5, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 3, j + l + 3, k + 5, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 4, j + l + 3, k - 2, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 4, j + l + 3, k - 1, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 4, j + l + 3, k, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 4, j + l + 3, k + 1, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 4, j + l + 3, k + 2, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 4, j + l + 3, k + 3, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 5, j + l + 3, k - 2, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 5, j + l + 3, k - 1, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 5, j + l + 3, k, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 5, j + l + 3, k + 1, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 5, j + l + 3, k + 2, LKMod.forestLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 5, j + l + 3, k + 3, LKMod.forestLeaves.blockID, 0);

		for (int j2 = -1; j2 < 2; j2++) {
			for (int l2 = 0; l2 < 4; l2++) {
				if (l2 == 0) {
					setBlockAndMetadata(world, i - 2 - j2, j + l + j2, k - 2 - j2, LKMod.prideWood.blockID, 1);
				}
				if (l2 == 1) {
					setBlockAndMetadata(world, i - 2 - j2, j + l + j2, k + 3 + j2, LKMod.prideWood.blockID, 1);
				}
				if (l2 == 2) {
					setBlockAndMetadata(world, i + 3 + j2, j + l + j2, k - 2 - j2, LKMod.prideWood.blockID, 1);
				}
				if (l2 == 3) {
					setBlockAndMetadata(world, i + 3 + j2, j + l + j2, k + 3 + j2, LKMod.prideWood.blockID, 1);
				}
			}
		}

		for (int i1 = i - 5; i1 < i + 7; i1++) {
			for (int k1 = k - 5; k1 < k + 7; k1++) {
				for (int j1 = j + l + 1; j1 <= j + l + 3; j1++) {
					if (world.getBlockId(i1, j1, k1) == LKMod.forestLeaves.blockID && world.isAirBlock(i1, j1 - 1, k1)) {
						generateVines(world, random, i1, j1, k1);
					}
				}
			}
		}

		for (int i1 = i - 1; i1 < i + 3; i1++) {
			for (int k1 = k - 1; k1 < k + 3; k1++) {
				for (int j1 = j; j1 < j + l; j1++) {
					if ((i1 == i - 1 && k1 == k - 1) || (i1 == i - 1 && k1 == k + 2) || (i1 == i + 2 && k1 == k - 1) || (i1 == i + 2 && k1 == k + 2)) {
					} else if (random.nextInt(40) == 0 && isBlockReplaceable(world, i1, j1, k1)) {
						setBlockAndMetadata(world, i1, j1, k1, LKMod.prideWood.blockID, 1);
					}
				}
			}
		}

		for (int i1 = i - 1; i1 < i + 3; i1++) {
			for (int k1 = k - 1; k1 < k + 3; k1++) {
				if ((i1 == i - 1 && k1 == k - 1) || (i1 == i + 2 && k1 == k - 1) || (i1 == i - 1 && k1 == k + 2) || (i1 == i + 2 && k1 == k + 2)) {
				} else {
					setBlockAndMetadata(world, i1, j + l1, k1, LKMod.prideWood.blockID, 1);
				}
			}
		}

		for (int i1 = i - 2; i1 < i + 4; i1++) {
			for (int k1 = k - 2; k1 < k + 4; k1++) {
				if ((i1 == i - 2 && k1 == k - 2) || (i1 == i + 3 && k1 == k - 2) || (i1 == i - 2 && k1 == k + 3) || (i1 == i + 3 && k1 == k + 3)) {
				} else if (isBlockReplaceable(world, i1, j + l1 + 1, k1)) {
					setBlockAndMetadata(world, i1, j + l1 + 1, k1, LKMod.forestLeaves.blockID, 0);
					generateVines(world, random, i1, j + l1 + 1, k1);
				}
			}
		}

		return true;
	}

	private void generateVines(World world, Random random, int i, int j, int k) {
		if (random.nextInt(7) == 0 && world.isAirBlock(i - 1, j, k)) {
			placeVines(world, i - 1, j, k, 8);
		}
		if (random.nextInt(7) == 0 && world.isAirBlock(i + 1, j, k)) {
			placeVines(world, i + 1, j, k, 2);
		}
		if (random.nextInt(7) == 0 && world.isAirBlock(i, j, k - 1)) {
			placeVines(world, i, j, k - 1, 1);
		}
		if (random.nextInt(7) == 0 && world.isAirBlock(i, j, k + 1)) {
			placeVines(world, i, j, k + 1, 4);
		}
	}

	private void placeVines(World world, int i, int j, int k, int meta) {
		world.setBlock(i, j, k, Block.vine.blockID, meta, 3);
		int l = world.rand.nextInt(8) + 7;
		while (true) {
			--j;
			if (world.getBlockId(i, j, k) != 0 || l <= 0) {
				return;
			}
			world.setBlock(i, j, k, Block.vine.blockID, meta, 3);
			--l;
		}
	}

	private boolean isBlockReplaceable(IBlockAccess world, int i, int j, int k) {
		int l = world.getBlockId(i, j, k);
		return l == 0 || l == Block.vine.blockID || l == LKMod.forestLeaves.blockID;
	}
}
