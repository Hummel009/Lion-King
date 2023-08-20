package lionking.common.world.feature;

import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ForgeDirection;

import java.util.Random;

public class LKWorldGenBananaTrees extends WorldGenerator {
	public LKWorldGenBananaTrees(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int height = 4 + random.nextInt(3);
		int[] leaves = new int[4];
		for (int l = 0; l < 4; l++) {
			leaves[l] = 1 + random.nextInt(3);
		}

		if (j < 1 || j + height + 5 > 256) {
			return false;
		}

		if (!isBlockReplaceable(world, i, j, k) && world.getBlockId(i, j, k) != LKMod.bananaSapling.blockID) {
			return false;
		}

		int j1 = world.getBlockId(i, j - 1, k);
		if (j1 != Block.grass.blockID && j1 != Block.dirt.blockID) {
			return false;
		}

		for (int l = 0; l < height + 2; l++) {
			if (!isBlockReplaceable(world, i, j + l, k)) {
				return false;
			}
		}

		for (int l = 0; l < 4; l++) {
			ForgeDirection dir = ForgeDirection.getOrientation(l + 2);
			for (int l1 = -1; l1 < leaves[l]; l1++) {
				if (!isBlockReplaceable(world, i + dir.offsetX, j + height + l1, k + dir.offsetZ)) {
					return false;
				}
			}
			for (int l1 = -1; l1 < 1; l1++) {
				if (!isBlockReplaceable(world, i + dir.offsetX * 2, j + height + leaves[l] + l1, k + dir.offsetZ * 2)) {
					return false;
				}
			}
		}

		for (int l = 0; l < height + 2; l++) {
			setBlockAndMetadata(world, i, j + l, k, LKMod.prideWood2.blockID, 0);
		}

		for (int l = 0; l < 4; l++) {
			ForgeDirection dir = ForgeDirection.getOrientation(l + 2);
			for (int l1 = 0; l1 < leaves[l]; l1++) {
				setBlockAndMetadata(world, i + dir.offsetX, j + height + l1, k + dir.offsetZ, LKMod.bananaLeaves.blockID, 0);
			}
			setBlockAndMetadata(world, i + dir.getOpposite().offsetX, j + height - 1, k + dir.getOpposite().offsetZ, LKMod.hangingBanana.blockID, l);
			for (int l1 = -1; l1 < 1; l1++) {
				setBlockAndMetadata(world, i + dir.offsetX * 2, j + height + leaves[l] + l1, k + dir.offsetZ * 2, LKMod.bananaLeaves.blockID, 0);
			}
		}

		world.setBlock(i, j - 1, k, Block.dirt.blockID, 0, 2);
		return true;
	}

	private boolean isBlockReplaceable(IBlockAccess world, int i, int j, int k) {
		return world.isAirBlock(i, j, k) || world.getBlockId(i, j, k) == LKMod.bananaLeaves.blockID;
	}
}
