package lionking.common.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class LKSlotBugBait extends Slot {
	public LKSlotBugBait(IInventory iinventory, int i, int j, int k) {
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		Item item = itemstack.getItem();
		if (item instanceof ItemFood) {
			ItemFood food = (ItemFood) item;
			if (food.isWolfsFavoriteMeat()) {
				return false;
			} else if (food == Item.fishRaw || food == Item.fishCooked) {
				return false;
			}
			return food.getSaturationModifier() > 0.0F;
		}
		return false;
	}
}
