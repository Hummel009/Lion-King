package lionking.common.world.biome;

import lionking.common.entity.LKEntityGiraffe;
import net.minecraft.world.World;
import net.minecraft.world.biome.SpawnListEntry;

import java.util.Random;

public class LKBiomeGenMountains extends LKPrideLandsBiome {
	public LKBiomeGenMountains(int i) {
		super(i);
		lkDecorator.treesPerChunk = 0;
		lkDecorator.mangoPerChunk = 0;
		lkDecorator.grassPerChunk = 1;
		lkDecorator.whiteFlowersPerChunk = 0;
		lkDecorator.purpleFlowersPerChunk = 0;
		lkDecorator.blueFlowersPerChunk = 1;
		lkDecorator.logsPerChunk = 6;
		lkDecorator.maizePerChunk = 4;
		lkDecorator.zazuPerChunk = 80;
		spawnableCreatureList.add(new SpawnListEntry(LKEntityGiraffe.class, 3, 2, 4));
	}

	public void decoratePrideLands(World world, Random random, int i, int j) {
		if (random.nextInt(6) == 0) {
			lkDecorator.treesPerChunk++;
			if (random.nextInt(5) == 0) {
				lkDecorator.treesPerChunk += random.nextInt(2) + 1;
			}
		}

		if (random.nextInt(70) == 0) {
			lkDecorator.mangoPerChunk++;
		}

		super.decoratePrideLands(world, random, i, j);

		lkDecorator.treesPerChunk = 0;
		lkDecorator.mangoPerChunk = 0;
	}
}
