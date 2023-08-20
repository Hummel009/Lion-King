package lionking.common.tileentity;

import lionking.common.recipe.LKGrindingRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class LKTileEntityGrindingBowl extends TileEntity implements ISidedInventory {
	public int grindTime;
	public float stickRotation;
	private ItemStack[] inventory = new ItemStack[2];
	private int[] inputSlots = {0};
	private int[] outputSlots = {1};

	public LKTileEntityGrindingBowl() {
		Random rand = new Random();
		stickRotation = rand.nextInt(360);
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (inventory[i] != null) {
			if (inventory[i].stackSize <= j) {
				ItemStack itemstack = inventory[i];
				inventory[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = inventory[i].splitStack(j);
			if (inventory[i].stackSize == 0) {
				inventory[i] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return "Grinding Bowl";
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("LKSlotGrindingBowl");
			if (byte0 >= 0 && byte0 < inventory.length) {
				inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		grindTime = nbttagcompound.getShort("GrindTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setShort("GrindTime", (short) grindTime);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("LKSlotGrindingBowl", (byte) i);
				inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Items", nbttaglist);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public int getGrindProgressScaled(int i) {
		return (grindTime * i) / 200;
	}

	@Override
	public void updateEntity() {
		boolean update = false;
		if (!worldObj.isRemote) {
			if (canGrind()) {
				grindTime++;
				if (grindTime == 200) {
					grindTime = 0;
					grindItem();
				}
			} else {
				grindTime = 0;
			}
			update = true;
		}
		if (update) {
			onInventoryChanged();
		}

		if (worldObj.rand.nextBoolean()) {
			worldObj.spawnParticle("smoke", (xCoord + 0.5F + (worldObj.rand.nextFloat() / 2.0F) - (worldObj.rand.nextFloat() / 2.0F)), (yCoord + 0.6F), (zCoord + 0.5F + (worldObj.rand.nextFloat() / 2.0F) - (worldObj.rand.nextFloat() / 2.0F)), 0.0D, 0.0D, 0.0D);
		}

		stickRotation += 8.0F;
		if (stickRotation >= 360.0F) {
			stickRotation -= 360.0F;
		}
	}

	public boolean canGrind() {
		if (inventory[0] == null) {
			return false;
		}
		ItemStack itemstack = LKGrindingRecipes.grinding().getGrindingResult(inventory[0]);
		if (itemstack == null) {
			return false;
		}
		if (inventory[1] == null) {
			return true;
		}
		if (!inventory[1].isItemEqual(itemstack)) {
			return false;
		}
		if (inventory[1].stackSize < getInventoryStackLimit() && inventory[1].stackSize < inventory[1].getMaxStackSize()) {
			return true;
		}
		return inventory[1].stackSize < itemstack.getMaxStackSize();
	}

	public void grindItem() {
		if (!canGrind()) {
			return;
		}
		ItemStack itemstack = LKGrindingRecipes.grinding().getGrindingResult(inventory[0]);
		if (inventory[1] == null) {
			inventory[1] = itemstack.copy();
		} else if (inventory[1].itemID == itemstack.itemID) {
			inventory[1].stackSize += itemstack.stackSize;
		}
		if (inventory[1].getItem().hasContainerItem()) {
			inventory[1] = new ItemStack(inventory[0].getItem().getContainerItem());
		} else {
			inventory[0].stackSize--;
		}
		if (inventory[0].stackSize <= 0) {
			inventory[0] = null;
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		}
		return entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (inventory[i] != null) {
			ItemStack stack = inventory[i];
			inventory[i] = null;
			return stack;
		} else {
			return null;
		}
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		return slot == 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if (side == 0) {
			return outputSlots;
		}
		return inputSlots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int j) {
		return isItemValidForSlot(slot, itemstack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int i) {
		return true;
	}
}