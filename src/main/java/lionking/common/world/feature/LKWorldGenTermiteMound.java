package lionking.common.world.feature;

import lionking.common.LKMod;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LKWorldGenTermiteMound extends WorldGenerator {
	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		return random.nextInt(3) == 0 ? generateLargeTermiteMound(world, random, i, j, k) : generateTermiteMound(world, random, i, j, k);
	}

	private boolean generateTermiteMound(World world, Random random, int i, int j, int k) {
		for (int i1 = 0; i1 < 4; i1++) {
			for (int k1 = 0; k1 < 4; k1++) {
				if (!world.isBlockOpaqueCube(i + i1, j, k + k1) || !world.isAirBlock(i + i1, j + 1, k + k1)) {
					return false;
				}
			}
		}

		for (int i1 = 0; i1 < 4; i1++) {
			for (int k1 = 0; k1 < 4; k1++) {
				generateMoundSection(world, random, i + i1, j, k + k1, 3, 2);
				if (i1 > 0 && i1 < 3 && k1 > 0 && k1 < 3) {
					generateMoundSection(world, random, i + i1, j, k + k1, 5, 3);
				}
			}
		}

		return true;
	}

	private boolean generateLargeTermiteMound(World world, Random random, int i, int j, int k) {
		for (int i1 = 0; i1 < 8; i1++) {
			for (int k1 = 0; k1 < 8; k1++) {
				if (!world.isBlockOpaqueCube(i + i1, j, k + k1) || !world.isAirBlock(i + i1, j + 1, k + k1)) {
					return false;
				}
			}
		}

		for (int i1 = 0; i1 < 8; i1++) {
			for (int k1 = 0; k1 < 8; k1++) {
				generateMoundSection(world, random, i + i1, j, k + k1, 2, 4);
				if (i1 > 0 && i1 < 7 && k1 > 0 && k1 < 7) {
					generateMoundSection(world, random, i + i1, j, k + k1, 7, 5);
				}
				if (i1 > 1 && i1 < 6 && k1 > 1 && k1 < 6) {
					generateMoundSection(world, random, i + i1, j, k + k1, 13, 7);
				}
				if (i1 > 2 && i1 < 5 && k1 > 2 && k1 < 5) {
					generateMoundSection(world, random, i + i1, j, k + k1, 18, 13);
				}
			}
		}

		return true;
	}

	public void generateMoundSection(World world, Random random, int i, int j, int k, int minHeight, int range) {
		int l = minHeight + random.nextInt(range);
		for (int j1 = 0; j1 < l; j1++) {
			world.setBlock(i, j + j1, k, LKMod.termite.blockID, 0, 2);
		}
	}
}

