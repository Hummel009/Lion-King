package lionking.common.entity.ai;

import lionking.common.LKMod;
import lionking.common.entity.LKAngerable;
import lionking.common.entity.LKEntityOutlander;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class LKEntityAIAngerableAttackableTarget extends EntityAINearestAttackableTarget {
	public LKEntityAIAngerableAttackableTarget(EntityCreature entity, Class entityclass, int i, boolean flag) {
		super(entity, entityclass, i, flag);
	}

	@Override
	public boolean shouldExecute() {
		if (((LKAngerable) taskOwner).isHostile()) {
			if (taskOwner instanceof LKEntityOutlander && !((LKEntityOutlander) taskOwner).inMound) {
				if (super.shouldExecute()) {
					if (taskOwner.getAttackTarget() instanceof EntityPlayer) {
						EntityPlayer entityplayer = (EntityPlayer) taskOwner.getAttackTarget();
						ItemStack helmet = entityplayer.inventory.armorItemInSlot(3);
						return helmet == null || helmet.itemID != LKMod.outlandsHelm.itemID;
					}
					return true;
				}
				return false;
			}
			return super.shouldExecute();
		}
		return false;
	}
}
