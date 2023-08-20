package lionking.common.inventory;

import lionking.common.LKMod;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LKSlotNote extends Slot {
	public LKSlotNote(IInventory iinventory, int i, int j, int k) {
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return itemstack.itemID == LKMod.note.itemID;
	}
}
