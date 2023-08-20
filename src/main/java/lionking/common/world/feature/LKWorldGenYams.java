package lionking.common.world.feature;

import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LKWorldGenYams extends WorldGenerator {
	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		for (int l = 0; l < 64; l++) {
			int i1 = i + random.nextInt(8) - random.nextInt(8);
			int j1 = j + random.nextInt(4) - random.nextInt(4);
			int k1 = k + random.nextInt(8) - random.nextInt(8);

			if (world.getBlockId(i1, j1, k1) == Block.grass.blockID && (world.getFullBlockLightValue(i1, j1 + 1, k1) >= 8 || world.canBlockSeeTheSky(i1, j1 + 1, k1))) {
				world.setBlock(i1, j1 + 1, k1, LKMod.yamCrops.blockID, 8, 2);
			}
		}
		return true;
	}
}
