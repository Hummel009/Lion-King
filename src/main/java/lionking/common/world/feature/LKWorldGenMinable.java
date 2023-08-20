package lionking.common.world.feature;

import lionking.common.LKMod;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LKWorldGenMinable extends WorldGenerator {
	private int minableBlockId;
	private int numberOfBlocks;
	private int minableMetadata;

	public LKWorldGenMinable(int i, int j) {
		this(i, j, 0);
	}

	public LKWorldGenMinable(int i, int j, int k) {
		minableBlockId = i;
		numberOfBlocks = j;
		minableMetadata = k;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		float f = random.nextFloat() * 3.141593F;
		double d = (i + 8) + (MathHelper.sin(f) * numberOfBlocks) / 8.0F;
		double d1 = (i + 8) - (MathHelper.sin(f) * numberOfBlocks) / 8.0F;
		double d2 = (k + 8) + (MathHelper.cos(f) * numberOfBlocks) / 8.0F;
		double d3 = (k + 8) - (MathHelper.cos(f) * numberOfBlocks) / 8.0F;
		double d4 = (j + random.nextInt(3)) - 2;
		double d5 = (j + random.nextInt(3)) - 2;
		for (int l = 0; l <= numberOfBlocks; l++) {
			double d6 = d + ((d1 - d) * l) / numberOfBlocks;
			double d7 = d4 + ((d5 - d4) * l) / numberOfBlocks;
			double d8 = d2 + ((d3 - d2) * l) / numberOfBlocks;
			double d9 = (random.nextDouble() * numberOfBlocks) / 16.0D;
			double d10 = (MathHelper.sin((l * 3.141593F) / numberOfBlocks) + 1.0F) * d9 + 1.0D;
			double d11 = (MathHelper.sin((l * 3.141593F) / numberOfBlocks) + 1.0F) * d9 + 1.0D;
			int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
			int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
			int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
			int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
			int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
			int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);
			for (int k2 = i1; k2 <= l1; k2++) {
				double d12 = ((k2 + 0.5D) - d6) / (d10 / 2.0D);
				if (d12 * d12 >= 1.0D) {
					continue;
				}
				for (int l2 = j1; l2 <= i2; l2++) {
					double d13 = ((l2 + 0.5D) - d7) / (d11 / 2.0D);
					if (d12 * d12 + d13 * d13 >= 1.0D) {
						continue;
					}
					for (int i3 = k1; i3 <= j2; i3++) {
						double d14 = ((i3 + 0.5D) - d8) / (d10 / 2.0D);
						if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && world.getBlockId(k2, l2, i3) == LKMod.pridestone.blockID) {
							world.setBlock(k2, l2, i3, minableBlockId, minableMetadata, 2);
						}
					}

				}

			}

		}

		return true;
	}
}
