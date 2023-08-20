package lionking.common.world.biome;

public class LKBiomeGenOutlandsRiver extends LKOutlandsBiome {
	public LKBiomeGenOutlandsRiver(int i) {
		super(i);
		outlandsDecorator.treesPerChunk = 0;
		outlandsDecorator.deadBushPerChunk = 3;
	}
}
