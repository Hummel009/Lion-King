package lionking.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LKContainerQuiver extends Container {
	private LKInventoryQuiver quiverInventory;

	public LKContainerQuiver(EntityPlayer entityplayer, LKInventoryQuiver quiver) {
		quiverInventory = quiver;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				addSlotToContainer(new LKSlotQuiver(quiverInventory, j + i * 2, 209 + j * 18, 32 + i * 18));
			}
		}

		for (int k = 0; k < 3; k++) {
			for (int j1 = 0; j1 < 9; j1++) {
				addSlotToContainer(new Slot(entityplayer.inventory, j1 + k * 9 + 9, 8 + j1 * 18, 18 + k * 18));
			}
		}
		for (int l = 0; l < 9; l++) {
			addSlotToContainer(new Slot(entityplayer.inventory, l, 8 + l * 18, 76));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return quiverInventory.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (i < 6) {
				if (!mergeItemStack(itemstack1, 6, 42, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (((Slot) inventorySlots.get(0)).isItemValid(itemstack1)) {
				if (!mergeItemStack(itemstack1, 0, 6, false)) {
					return null;
				}
			} else if (i < 33) {
				if (!mergeItemStack(itemstack1, 33, 42, false)) {
					return null;
				}
			} else if (i < 42) {
				if (!mergeItemStack(itemstack1, 6, 33, false)) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 6, 42, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(entityplayer, itemstack1);
		}

		return itemstack;
	}

	public void onContainerClosed(EntityPlayer entityplayer) {
		super.onContainerClosed(entityplayer);
		quiverInventory.closeChest();
	}
}
