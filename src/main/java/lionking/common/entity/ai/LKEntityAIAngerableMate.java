package lionking.common.entity.ai;

import lionking.common.entity.LKAngerable;
import lionking.common.entity.LKEntityLion;
import lionking.common.entity.LKEntityLioness;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class LKEntityAIAngerableMate extends EntityAIBase {
	public EntityAnimal targetMate;
	World theWorld;
	int breeding;
	double moveSpeed;
	private EntityAnimal theAnimal;

	public LKEntityAIAngerableMate(EntityAnimal animal, double d) {
		theAnimal = animal;
		theWorld = animal.worldObj;
		moveSpeed = d;
		setMutexBits(3);
	}

	@Override
	public boolean shouldExecute() {
		if (!theAnimal.isInLove()) {
			return false;
		}
		if (((LKAngerable) theAnimal).isHostile()) {
			return false;
		} else {
			targetMate = findMate();
			return targetMate != null;
		}
	}

	@Override
	public boolean continueExecuting() {
		return !((LKAngerable) theAnimal).isHostile() && !((LKAngerable) targetMate).isHostile() && targetMate.isEntityAlive() && targetMate.isInLove() && breeding < 60;
	}

	@Override
	public void resetTask() {
		targetMate = null;
		breeding = 0;
	}

	@Override
	public void updateTask() {
		theAnimal.getLookHelper().setLookPositionWithEntity(targetMate, 10.0F, theAnimal.getVerticalFaceSpeed());
		theAnimal.getNavigator().tryMoveToEntityLiving(targetMate, moveSpeed);
		++breeding;

		if (breeding == 60) {
			procreate();
		}
	}

	private EntityAnimal findMate() {
		float f = 8.0F;
		Class<? extends EntityAnimal> mateClass = theAnimal.getClass();
		if (theAnimal instanceof LKEntityLion) {
			mateClass = LKEntityLioness.class;

		} else if (theAnimal instanceof LKEntityLioness) {
			mateClass = LKEntityLion.class;
		}

		List list = theWorld.getEntitiesWithinAABB(mateClass, theAnimal.boundingBox.expand(f, f, f));
		Iterator i = list.iterator();
		EntityAnimal mate;
		do {
			if (!i.hasNext()) {
				return null;
			}

			mate = (EntityAnimal) i.next();
		}
		while (!theAnimal.canMateWith(mate));

		return mate;
	}

	private void procreate() {
		EntityAgeable babyAnimal = theAnimal.createChild(targetMate);
		if (babyAnimal != null) {
			theAnimal.setGrowingAge(6000);
			targetMate.setGrowingAge(6000);
			theAnimal.resetInLove();
			targetMate.resetInLove();
			babyAnimal.setGrowingAge(-24000);
			babyAnimal.setLocationAndAngles(theAnimal.posX, theAnimal.posY, theAnimal.posZ, 0.0F, 0.0F);
			theWorld.spawnEntityInWorld(babyAnimal);
			Random rand = theAnimal.getRNG();

			for (int i = 0; i < 7; ++i) {
				double var4 = rand.nextGaussian() * 0.02D;
				double var6 = rand.nextGaussian() * 0.02D;
				double var8 = rand.nextGaussian() * 0.02D;
				theWorld.spawnParticle("heart", theAnimal.posX + (rand.nextFloat() * theAnimal.width * 2.0F) - theAnimal.width, theAnimal.posY + 0.5D + (rand.nextFloat() * theAnimal.height), theAnimal.posZ + (rand.nextFloat() * theAnimal.width * 2.0F) - theAnimal.width, var4, var6, var8);
			}

			theWorld.spawnEntityInWorld(new EntityXPOrb(theWorld, theAnimal.posX, theAnimal.posY, theAnimal.posZ, rand.nextInt(4) + 1));
		}
	}
}
