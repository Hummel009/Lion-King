package lionking.common.inventory;

import lionking.common.LKMod;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LKSlotQuiver extends Slot {
	public LKSlotQuiver(IInventory iinventory, int i, int j, int k) {
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		Item i = itemstack.getItem();
		return i == LKMod.dartBlue || i == LKMod.dartYellow || i == LKMod.dartRed || i == LKMod.dartBlack || i == LKMod.dartPink;
	}
}
