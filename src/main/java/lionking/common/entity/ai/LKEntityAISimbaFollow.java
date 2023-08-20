package lionking.common.entity.ai;

import lionking.common.entity.LKEntitySimba;
import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LKEntityAISimbaFollow extends EntityAIBase {
	private LKEntitySimba theSimba;
	private EntityPlayer thePlayer;
	private World theWorld;
	private double speed;
	private PathNavigate pathfinder;
	private int moveTick;
	private float maxDist;
	private float minDist;
	private boolean avoidWater;

	public LKEntityAISimbaFollow(LKEntitySimba simba, double d, float f1, float f2) {
		theSimba = simba;
		theWorld = simba.worldObj;
		speed = d;
		pathfinder = simba.getNavigator();
		minDist = f1;
		maxDist = f2;
		setMutexBits(3);
	}

	@Override
	public boolean shouldExecute() {
		EntityPlayer entityplayer = theSimba.getOwner();

		if (entityplayer == null) {
			return false;
		} else if (theSimba.isSitting()) {
			return false;
		} else if (theSimba.getDistanceSqToEntity(entityplayer) < (minDist * minDist)) {
			return false;
		} else {
			thePlayer = entityplayer;
			return true;
		}
	}

	@Override
	public boolean continueExecuting() {
		return !pathfinder.noPath() && theSimba.getDistanceSqToEntity(thePlayer) > (maxDist * maxDist) && !theSimba.isSitting();
	}

	@Override
	public void startExecuting() {
		moveTick = 0;
		avoidWater = theSimba.getNavigator().getAvoidsWater();
		theSimba.getNavigator().setAvoidsWater(false);
	}

	@Override
	public void resetTask() {
		thePlayer = null;
		pathfinder.clearPathEntity();
		theSimba.getNavigator().setAvoidsWater(avoidWater);
	}

	@Override
	public void updateTask() {
		theSimba.getLookHelper().setLookPositionWithEntity(thePlayer, 10.0F, theSimba.getVerticalFaceSpeed());

		if (!theSimba.isSitting()) {
			if (--moveTick <= 0) {
				moveTick = 10;

				if (!pathfinder.tryMoveToEntityLiving(thePlayer, speed)) {
					if (theSimba.getDistanceSqToEntity(thePlayer) >= 144.0D) {
						int i = MathHelper.floor_double(thePlayer.posX);
						int j = MathHelper.floor_double(thePlayer.boundingBox.minY);
						int k = MathHelper.floor_double(thePlayer.posZ);

						if (theWorld.isBlockNormalCube(i, j - 1, k)) {
							boolean canMoveHere = true;

							for (int i1 = -1; i1 <= 1; i1++) {
								for (int j1 = 0; j1 <= 1; j1++) {
									for (int k1 = -1; k1 <= 1; k1++) {
										Block block = Block.blocksList[theWorld.getBlockId(i + i1, j + j1, k + k1)];
										if (block != null && (block.getCollisionBoundingBoxFromPool(theWorld, i + i1, j + j1, k + k1) != null || block.blockMaterial == Material.lava || block == LKMod.outlandsPool)) {
											canMoveHere = false;
										}
									}
								}
							}

							if (canMoveHere) {
								theSimba.fallDistance = 0.0F;
								theSimba.setLocationAndAngles(i + 0.5F, j, k + 0.5F, theSimba.rotationYaw, theSimba.rotationPitch);
							}
						}
					}
				}
			}
		}
	}
}
