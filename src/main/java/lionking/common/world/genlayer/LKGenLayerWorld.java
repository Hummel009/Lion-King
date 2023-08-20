package lionking.common.world.genlayer;

import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.*;

public class LKGenLayerWorld {
	public static GenLayer[] createPrideLands(long seed, WorldType worldType) {
		byte scale = 4;
		if (worldType == WorldType.LARGE_BIOMES) {
			scale = 6;
		}

		GenLayer layer = new LKGenLayerPrideLandsBiomes(200L);
		layer = new GenLayerFuzzyZoom(2000L, layer);
		layer = new GenLayerZoom(2001L, layer);
		layer = new GenLayerZoom(2002L, layer);
		layer = new GenLayerZoom(2003L, layer);

		GenLayer rivers = GenLayerZoom.magnify(1000L, layer, 0);
		rivers = new GenLayerRiverInit(100L, rivers);
		rivers = GenLayerZoom.magnify(1000L, rivers, scale + 2);
		rivers = new LKGenLayerRiver(1L, rivers, 0);
		rivers = new GenLayerSmooth(1000L, rivers);

		GenLayer biomes = GenLayerZoom.magnify(1000L, layer, 0);
		biomes = new LKGenLayerPrideLandsBiomes(200L, biomes);
		biomes = GenLayerZoom.magnify(1000L, biomes, 2);

		layer = new LKGenLayerHills(1000L, biomes);

		for (int i = 0; i < scale; ++i) {
			layer = new GenLayerZoom(1000L + i, layer);
		}

		layer = new GenLayerSmooth(1000L, layer);
		layer = new GenLayerRiverMix(100L, layer, rivers);
		GenLayer layer1 = new GenLayerVoronoiZoom(10L, layer);

		layer.initWorldGenSeed(seed + 1994L);
		layer1.initWorldGenSeed(seed + 1994L);

		return new GenLayer[]{layer, layer1, layer};
	}

	public static GenLayer[] createOutlands(long seed, WorldType worldType) {
		byte scale = 4;

		GenLayer layer = new LKGenLayerOutlandsBiomes(200L);
		layer = new GenLayerFuzzyZoom(2000L, layer);
		layer = new GenLayerZoom(2001L, layer);
		layer = new GenLayerZoom(2002L, layer);
		layer = new GenLayerZoom(2003L, layer);

		GenLayer rivers = GenLayerZoom.magnify(1000L, layer, 0);
		rivers = new GenLayerRiverInit(100L, rivers);
		rivers = GenLayerZoom.magnify(1000L, rivers, scale + 2);
		rivers = new LKGenLayerRiver(1L, rivers, 1);
		rivers = new GenLayerSmooth(1000L, rivers);

		GenLayer biomes = GenLayerZoom.magnify(1000L, layer, 0);
		biomes = new LKGenLayerOutlandsBiomes(200L, biomes);
		biomes = GenLayerZoom.magnify(1000L, biomes, 2);

		layer = new LKGenLayerHills(1000L, biomes);

		for (int i = 0; i < scale; ++i) {
			layer = new GenLayerZoom((1000 + i), layer);
		}

		layer = new GenLayerSmooth(1000L, layer);
		layer = new GenLayerRiverMix(100L, layer, rivers);
		GenLayer layer1 = new GenLayerVoronoiZoom(10L, layer);

		layer.initWorldGenSeed(seed + 1994L);
		layer1.initWorldGenSeed(seed + 1994L);

		return new GenLayer[]{layer, layer1, layer};
	}
}
