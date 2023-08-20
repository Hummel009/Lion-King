package lionking.common.inventory;

import lionking.common.item.LKItemInfo;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LKSlotItemInfo extends Slot {
	public LKSlotItemInfo(IInventory iinventory, int i, int j, int k) {
		super(iinventory, i, j, k);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return LKItemInfo.getItemInfo(itemstack) != null;
	}
}
