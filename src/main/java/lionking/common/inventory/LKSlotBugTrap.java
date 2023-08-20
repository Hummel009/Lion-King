package lionking.common.inventory;

import lionking.common.LKAchievementList;
import lionking.common.LKMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LKSlotBugTrap extends Slot {
	public LKSlotBugTrap(IInventory iinventory, int i, int j, int k) {
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return false;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
		if (itemstack.itemID == LKMod.bug.itemID) {
			entityplayer.triggerAchievement(LKAchievementList.bugTrap);
		}
		super.onPickupFromSlot(entityplayer, itemstack);
	}
}
