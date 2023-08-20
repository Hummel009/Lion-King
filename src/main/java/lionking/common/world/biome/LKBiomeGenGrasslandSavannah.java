package lionking.common.world.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LKBiomeGenGrasslandSavannah extends LKBiomeGenSavannahBase {
	public LKBiomeGenGrasslandSavannah(int i) {
		super(i);
		lkDecorator.whiteFlowersPerChunk = 6;
		lkDecorator.logsPerChunk = 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeGrassColor() {
		return 0xF1DC52;
	}
}
