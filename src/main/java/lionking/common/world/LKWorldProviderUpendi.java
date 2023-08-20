package lionking.common.world;

import lionking.common.LKMod;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class LKWorldProviderUpendi extends WorldProvider {
	@Override
	public void registerWorldChunkManager() {
		worldChunkMgr = new LKWorldChunkManagerUpendi();
		dimensionId = LKMod.idUpendi;
	}

	@Override
	public IChunkProvider createChunkGenerator() {
		return new LKChunkProviderUpendi(worldObj, worldObj.getSeed());
	}

	@Override
	public Vec3 getFogColor(float f, float f1) {
		double R = 209;
		double G = 86;
		double B = 234;
		return worldObj.getWorldVec3Pool().getVecFromPool(R / 255, G / 255, B / 255);
	}

	@Override
	public boolean canRespawnHere() {
		return false;
	}

	@Override
	public String getWelcomeMessage() {
		return "Entering Upendi";
	}

	@Override
	public String getDepartMessage() {
		return "Leaving Upendi";
	}

	@Override
	public String getSaveFolder() {
		return "Upendi";
	}

	@Override
	public String getDimensionName() {
		return "Upendi";
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
