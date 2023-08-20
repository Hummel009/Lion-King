package lionking.common.entity.ai;

import lionking.common.entity.LKEntitySimba;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;

public class LKEntityAISimbaAttackPlayerAttacker extends EntityAITarget {
	private LKEntitySimba theSimba;
	private EntityLivingBase attackTarget;
	private int ownerRevengeTimer;

	public LKEntityAISimbaAttackPlayerAttacker(LKEntitySimba simba) {
		super(simba, false);
		theSimba = simba;
		setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		if (theSimba.isSitting()) {
			return false;
		}
		EntityPlayer entityplayer = theSimba.getOwner();
		if (entityplayer == null) {
			return false;
		} else {
			attackTarget = entityplayer.getAITarget();
			int i = entityplayer.func_142015_aE();
			return i != ownerRevengeTimer && isSuitableTarget(attackTarget, false);
		}
	}

	public void startExecuting() {
		taskOwner.setAttackTarget(attackTarget);
		EntityPlayer entityplayer = theSimba.getOwner();
		if (entityplayer != null) {
			ownerRevengeTimer = entityplayer.func_142015_aE();
		}
		super.startExecuting();
	}

	@Override
	public boolean continueExecuting() {
		return !theSimba.isSitting() && super.continueExecuting();
	}
}
