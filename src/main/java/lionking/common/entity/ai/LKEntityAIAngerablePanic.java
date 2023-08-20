package lionking.common.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.Vec3;

public class LKEntityAIAngerablePanic extends EntityAIBase {
	private EntityAnimal theAnimal;
	private double speed;
	private double randPosX;
	private double randPosY;
	private double randPosZ;

	public LKEntityAIAngerablePanic(EntityAnimal animal, double d) {
		theAnimal = animal;
		speed = d;
		setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		if (!theAnimal.isChild()) {
			return false;
		}
		if (theAnimal.getAITarget() == null) {
			return false;
		} else {
			Vec3 vec3 = RandomPositionGenerator.findRandomTarget(theAnimal, 5, 4);

			if (vec3 == null) {
				return false;
			} else {
				randPosX = vec3.xCoord;
				randPosY = vec3.yCoord;
				randPosZ = vec3.zCoord;
				return true;
			}
		}
	}

	@Override
	public void startExecuting() {
		theAnimal.getNavigator().tryMoveToXYZ(randPosX, randPosY, randPosZ, speed);
	}

	@Override
	public boolean continueExecuting() {
		return !theAnimal.getNavigator().noPath();
	}
}
