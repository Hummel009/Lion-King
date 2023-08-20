package lionking.common.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;

public class LKEntityAITermiteQueenAttack extends EntityAIAttackOnCollide {
	private int privateSuperField;
	private EntityCreature theEntity;
	private double speed;
	private boolean aBoolean;
	private int lkAttackTick;

	public LKEntityAITermiteQueenAttack(EntityCreature entity, Class entityclass, double d, boolean flag) {
		super(entity, entityclass, d, flag);
		theEntity = entity;
		speed = d;
		aBoolean = flag;
	}

	@Override
	public void startExecuting() {
		super.startExecuting();
		privateSuperField = 0;
	}

	@Override
	public void updateTask() {
		EntityLivingBase target = theEntity.getAttackTarget();
		theEntity.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);

		if ((aBoolean || theEntity.getEntitySenses().canSee(target)) && --privateSuperField <= 0) {
			privateSuperField = 4 + theEntity.getRNG().nextInt(7);
			theEntity.getNavigator().tryMoveToEntityLiving(target, speed);
		}

		lkAttackTick = Math.max(lkAttackTick - 1, 0);
		double d = (theEntity.width * 1.6F * theEntity.width * 1.6F);

		if (theEntity.getDistanceSq(target.posX, target.boundingBox.minY, target.posZ) <= d) {
			if (lkAttackTick <= 0) {
				lkAttackTick = 20;
				theEntity.attackEntityAsMob(target);
			}
		}
	}
}
