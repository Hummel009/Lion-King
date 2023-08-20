package lionking.common.world.feature;

import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LKWorldGenLogs extends WorldGenerator {
	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		for (int l = 0; l < 7; l++) {
			if (!(world.getBlockId(i, j - 1, k + l) == Block.grass.blockID || world.getBlockId(i, j - 1, k + l) == Block.sand.blockID) || !isBlockReplaceable(world, i, j, k + l)) {
				return false;
			}
		}

		for (int k1 = 0; k1 < 7; k1++) {
			world.setBlock(i, j, k + k1, LKMod.log.blockID, 0, 2);
			if (world.getBlockId(i, j - 1, k + k1) == Block.grass.blockID) {
				world.setBlock(i, j - 1, k + k1, Block.dirt.blockID, 0, 2);
			}
		}

		for (int i1 = -5; i1 < 6; i1++) {
			for (int j1 = -3; j1 < 3; j1++) {
				for (int k1 = -3; k1 < 10; k1++) {
					if (world.getBlockId(i + i1, j + j1, k + k1) == Block.grass.blockID && world.isAirBlock(i + i1, j + j1 + 1, k + k1) && random.nextInt(4) != 0) {
						world.setBlock(i + i1, j + j1 + 1, k + k1, Block.tallGrass.blockID, 1, 2);
					}
				}
			}
		}

		return true;
	}

	private boolean isBlockReplaceable(IBlockAccess world, int i, int j, int k) {
		int l = world.getBlockId(i, j, k);
		return l == Block.tallGrass.blockID || l == LKMod.aridGrass.blockID || world.isAirBlock(i, j, k);
	}
}