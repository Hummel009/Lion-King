package lionking.common.util;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.Random;

public class LKAmbientPositionGenerator {
	private static Vec3 staticVector = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);

	public static Vec3 findRandomTarget(EntityAmbientCreature creature, int i, int j) {
		return findRandomTargetBlock(creature, i, j, null);
	}

	public static Vec3 findRandomTargetBlockTowards(EntityAmbientCreature creature, int i, int j, Vec3 vec) {
		staticVector.xCoord = vec.xCoord - creature.posX;
		staticVector.yCoord = vec.yCoord - creature.posY;
		staticVector.zCoord = vec.zCoord - creature.posZ;
		return findRandomTargetBlock(creature, i, j, staticVector);
	}

	public static Vec3 findRandomTargetBlockAwayFrom(EntityAmbientCreature creature, int i, int j, Vec3 vec) {
		staticVector.xCoord = creature.posX - vec.xCoord;
		staticVector.yCoord = creature.posY - vec.yCoord;
		staticVector.zCoord = creature.posZ - vec.zCoord;
		return findRandomTargetBlock(creature, i, j, staticVector);
	}

	private static Vec3 findRandomTargetBlock(EntityAmbientCreature creature, int i, int j, Vec3 vec) {
		Random rand = creature.getRNG();
		boolean flag = false;
		int k = 0;
		int l = 0;
		int i1 = 0;
		float f = -99999.0F;

		for (int j1 = 0; j1 < 10; ++j1) {
			int i2 = rand.nextInt(2 * i) - i;
			int j2 = rand.nextInt(2 * j) - j;
			int k2 = rand.nextInt(2 * i) - i;

			if (vec == null || i2 * vec.xCoord + k2 * vec.zCoord >= 0.0D) {
				i2 += MathHelper.floor_double(creature.posX);
				j2 += MathHelper.floor_double(creature.posY);
				k2 += MathHelper.floor_double(creature.posZ);

				float f1 = getBlockPathWeight(creature, i2, j2, k2);

				if (f1 > f) {
					f = f1;
					k = i2;
					l = j2;
					i1 = k2;
					flag = true;
				}
			}
		}

		if (flag) {
			return creature.worldObj.getWorldVec3Pool().getVecFromPool(k, l, i1);
		} else {
			return null;
		}
	}

	private static float getBlockPathWeight(EntityAmbientCreature entity, int i, int j, int k) {
		return entity.worldObj.getBlockId(i, j - 1, k) == Block.grass.blockID ? 10.0F : entity.worldObj.getLightBrightness(i, j, k) - 0.5F;
	}
}
