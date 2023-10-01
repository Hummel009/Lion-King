package lionking.common.inventory;

import lionking.common.LKAchievementList;
import lionking.common.LKMod;
import lionking.common.entity.LKEntityTimon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LKSlotTimon extends Slot {
	public int cost;
	private LKEntityTimon theTimon;

	public LKSlotTimon(IInventory iinventory, int i, int j, int k, LKEntityTimon timon, int l) {
		super(iinventory, i, j, k);
		theTimon = timon;
		cost = l;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return false;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
		for (int i = 0; i < cost; i++) {
			entityplayer.inventory.consumeInventoryItem(LKMod.bug.itemID);
		}
		theTimon.setEaten();
		entityplayer.triggerAchievement(LKAchievementList.hakunaMatata);
		super.onPickupFromSlot(entityplayer, itemstack);
	}
}
