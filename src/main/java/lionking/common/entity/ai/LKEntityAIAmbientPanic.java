package lionking.common.entity.ai;

import lionking.common.util.LKAmbientPositionGenerator;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.util.Vec3;

public class LKEntityAIAmbientPanic extends EntityAIBase {
	private EntityAmbientCreature entity;
	private double speed;
	private double xPosition;
	private double yPosition;
	private double zPosition;

	public LKEntityAIAmbientPanic(EntityAmbientCreature creature, double d) {
		entity = creature;
		speed = d;
		setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		if (entity.getAITarget() == null) {
			return false;
		} else {
			Vec3 v = LKAmbientPositionGenerator.findRandomTarget(entity, 5, 4);

			if (v == null) {
				return false;
			} else {
				xPosition = v.xCoord;
				yPosition = v.yCoord;
				zPosition = v.zCoord;
				return true;
			}
		}
	}

	@Override
	public void startExecuting() {
		entity.getNavigator().tryMoveToXYZ(xPosition, yPosition, zPosition, speed);
	}

	@Override
	public boolean continueExecuting() {
		return !entity.getNavigator().noPath();
	}
}
