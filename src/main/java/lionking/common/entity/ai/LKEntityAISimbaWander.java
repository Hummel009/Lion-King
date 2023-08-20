package lionking.common.entity.ai;

import lionking.common.entity.LKEntitySimba;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class LKEntityAISimbaWander extends EntityAIBase {
	private LKEntitySimba theSimba;
	private double xPosition;
	private double yPosition;
	private double zPosition;
	private double speed;

	public LKEntityAISimbaWander(LKEntitySimba simba, double d) {
		theSimba = simba;
		speed = d;
		setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		if (theSimba.isSitting()) {
			return false;
		}
		if (theSimba.getAge() >= 100) {
			return false;
		} else if (theSimba.getRNG().nextInt(120) != 0) {
			return false;
		} else {
			Vec3 vec = RandomPositionGenerator.findRandomTarget(theSimba, 10, 7);

			if (vec == null) {
				return false;
			} else {
				xPosition = vec.xCoord;
				yPosition = vec.yCoord;
				zPosition = vec.zCoord;
				return true;
			}
		}
	}

	@Override
	public boolean continueExecuting() {
		return !theSimba.getNavigator().noPath() && !theSimba.isSitting();
	}

	@Override
	public void startExecuting() {
		theSimba.getNavigator().tryMoveToXYZ(xPosition, yPosition, zPosition, speed);
	}
}
