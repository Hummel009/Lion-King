package lionking.common.world.genlayer;

import lionking.common.world.biome.LKOutlandsBiome;
import lionking.common.world.biome.LKPrideLandsBiome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class LKGenLayerHills extends GenLayer {
	public LKGenLayerHills(long l, GenLayer layer) {
		super(l);
		parent = layer;
	}

	@Override
	public int[] getInts(int par1, int par2, int par3, int par4) {
		int[] var5 = parent.getInts(par1 - 1, par2 - 1, par3 + 2, par4 + 2);
		int[] var6 = IntCache.getIntCache(par3 * par4);

		for (int var7 = 0; var7 < par4; ++var7) {
			for (int var8 = 0; var8 < par3; ++var8) {
				initChunkSeed((var8 + par1), (var7 + par2));
				int var9 = var5[var8 + 1 + (var7 + 1) * (par3 + 2)];

				if (nextInt(3) == 0) {
					int var10 = var9;

					if (var9 == LKPrideLandsBiome.rainforest.biomeID) {
						var10 = LKPrideLandsBiome.rainforestHills.biomeID;
					}
					if (var9 == LKOutlandsBiome.outlands.biomeID) {
						var10 = LKOutlandsBiome.outlandsMountains.biomeID;
					}

					if (var10 == var9) {
						var6[var8 + var7 * par3] = var9;
					} else {
						int var11 = var5[var8 + 1 + (var7 + 1 - 1) * (par3 + 2)];
						int var12 = var5[var8 + 1 + 1 + (var7 + 1) * (par3 + 2)];
						int var13 = var5[var8 + 1 - 1 + (var7 + 1) * (par3 + 2)];
						int var14 = var5[var8 + 1 + (var7 + 1 + 1) * (par3 + 2)];

						if (var11 == var9 && var12 == var9 && var13 == var9 && var14 == var9) {
							var6[var8 + var7 * par3] = var10;
						} else {
							var6[var8 + var7 * par3] = var9;
						}
					}
				} else {
					var6[var8 + var7 * par3] = var9;
				}
			}
		}

		return var6;
	}
}
