package lionking.common.world.genlayer;

import lionking.common.world.biome.LKOutlandsBiome;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class LKGenLayerOutlandsBiomes extends GenLayer {
	private BiomeGenBase[] biomesForGeneration = {LKOutlandsBiome.outlands};

	public LKGenLayerOutlandsBiomes(long l, GenLayer genlayer) {
		super(l);
		parent = genlayer;
	}

	public LKGenLayerOutlandsBiomes(long l) {
		super(l);
	}

	@Override
	public int[] getInts(int i, int j, int k, int l) {
		int[] intArray = IntCache.getIntCache(k * l);
		for (int i1 = 0; i1 < l; i1++) {
			for (int j1 = 0; j1 < k; j1++) {
				initChunkSeed(j1 + i, i1 + j);
				intArray[j1 + i1 * k] = biomesForGeneration[nextInt(biomesForGeneration.length)].biomeID;
			}
		}
		return intArray;
	}
}
