package lionking.common.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class LKSlotDrum extends Slot {
	public LKSlotDrum(IInventory inventory, int i, int j, int k) {
		super(inventory, i, j, k);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
