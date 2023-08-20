package lionking.common.world;

import lionking.common.LKMod;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class LKWorldProviderOutlands extends WorldProvider {
	@Override
	public void registerWorldChunkManager() {
		worldChunkMgr = new LKWorldChunkManagerOutlands(worldObj.getSeed(), worldObj.getWorldInfo().getTerrainType());
		dimensionId = LKMod.idOutlands;
	}

	@Override
	public IChunkProvider createChunkGenerator() {
		return new LKChunkProviderOutlands(worldObj, worldObj.getSeed());
	}

	@Override
	public float calculateCelestialAngle(long l, float f) {
		return 0.5F;
	}

	@Override
	public int getMoonPhase(long l) {
		return 0;
	}

	@Override
	public boolean canRespawnHere() {
		return false;
	}

	@Override
	public String getWelcomeMessage() {
		return "Entering the Outlands";
	}

	@Override
	public String getDepartMessage() {
		return "Leaving the Outlands";
	}

	@Override
	public String getSaveFolder() {
		return "Outlands";
	}

	@Override
	public String getDimensionName() {
		return "Outlands";
	}

	@Override
	public boolean shouldMapSpin(String entity, double x, double y, double z) {
		return false;
	}

	@Override
	public int getRespawnDimension(EntityPlayerMP player) {
		return LKMod.idPrideLands;
	}
}
