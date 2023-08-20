package lionking.common.inventory;

import lionking.common.tileentity.LKTileEntityDrum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

public class LKInventoryDrum extends InventoryBasic {
	private LKContainerDrum container;
	private LKTileEntityDrum drumInv;

	public LKInventoryDrum(LKContainerDrum drum, String s, boolean flag, int i) {
		super(s, flag, i);
		container = drum;
	}

	public LKInventoryDrum(LKContainerDrum drum, boolean flag, LKTileEntityDrum inv) {
		super(inv.getInvName(), flag, inv.getSizeInventory());
		container = drum;
		drumInv = inv;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (drumInv != null) {
			return drumInv.getStackInSlot(i);
		}
		return super.getStackInSlot(i);
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (drumInv != null) {
			if (drumInv.inventory[i] != null) {
				if (drumInv.inventory[i].stackSize <= j) {
					ItemStack itemstack = drumInv.inventory[i];
					drumInv.inventory[i] = null;
					onInventoryChanged();
					return itemstack;
				}
				ItemStack itemstack1 = drumInv.inventory[i].splitStack(j);
				if (drumInv.inventory[i].stackSize == 0) {
					drumInv.inventory[i] = null;
					onInventoryChanged();
				}
				return itemstack1;
			} else {
				return null;
			}
		}
		return super.decrStackSize(i, j);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (drumInv != null) {
			return drumInv.getStackInSlotOnClosing(i);
		}
		return super.getStackInSlotOnClosing(i);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if (drumInv != null) {
			drumInv.inventory[i] = itemstack;
			if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
				itemstack.stackSize = getInventoryStackLimit();
			}
			onInventoryChanged();
		} else {
			super.setInventorySlotContents(i, itemstack);
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (drumInv != null) {
			return drumInv.isUseableByPlayer(entityplayer);
		}
		return super.isUseableByPlayer(entityplayer);
	}

	@Override
	public int getInventoryStackLimit() {
		return drumInv != null ? drumInv.getInventoryStackLimit() : 1;
	}

	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
		if (drumInv != null) {
			drumInv.onInventoryChanged();
		}
		container.onCraftMatrixChanged(this);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		return true;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}
}
