package lionking.common.world;

import lionking.common.world.biome.LKBiomeGenUpendi;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LKWorldChunkManagerUpendi extends WorldChunkManager {
	private BiomeGenBase theBiome;
	private float temperature;
	private float rainfall;

	public LKWorldChunkManagerUpendi() {
		BiomeGenBase biome = LKBiomeGenUpendi.upendi;
		theBiome = biome;
		temperature = biome.temperature;
		rainfall = biome.rainfall;
	}

	@Override
	public BiomeGenBase getBiomeGenAt(int i, int j) {
		return theBiome;
	}

	@Override
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomes, int i, int j, int k, int l) {
		if (biomes == null || biomes.length < k * l) {
			biomes = new BiomeGenBase[k * l];
		}

		Arrays.fill(biomes, 0, k * l, theBiome);
		return biomes;
	}

	@Override
	public float[] getTemperatures(float[] floats, int i, int j, int k, int l) {
		if (floats == null || floats.length < k * l) {
			floats = new float[k * l];
		}

		Arrays.fill(floats, 0, k * l, temperature);
		return floats;
	}

	@Override
	public float[] getRainfall(float[] floats, int i, int j, int k, int l) {
		if (floats == null || floats.length < k * l) {
			floats = new float[k * l];
		}

		Arrays.fill(floats, 0, k * l, rainfall);
		return floats;
	}

	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] biomes, int i, int j, int k, int l) {
		if (biomes == null || biomes.length < k * l) {
			biomes = new BiomeGenBase[k * l];
		}

		Arrays.fill(biomes, 0, k * l, theBiome);
		return biomes;
	}

	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] biomes, int i, int j, int k, int l, boolean flag) {
		return loadBlockGeneratorData(biomes, i, j, k, l);
	}

	@Override
	public ChunkPosition findBiomePosition(int i, int j, int k, List list, Random random) {
		return list.contains(theBiome) ? new ChunkPosition(i - k + random.nextInt(k * 2 + 1), 0, j - k + random.nextInt(k * 2 + 1)) : null;
	}

	@Override
	public boolean areBiomesViable(int i, int j, int k, List list) {
		return list.contains(theBiome);
	}
}
