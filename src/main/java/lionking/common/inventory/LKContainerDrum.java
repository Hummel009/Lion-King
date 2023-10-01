package lionking.common.inventory;

import lionking.common.LKAchievementList;
import lionking.common.LKMod;
import lionking.common.item.LKItemNote;
import lionking.common.tileentity.LKTileEntityDrum;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class LKContainerDrum extends Container {
	public int[] enchantLevels = new int[3];
	private IInventory theDrum;
	private IInventory theDrumEnchant = new LKInventoryDrum(this, "Bongo Drum", false, 1);
	private World worldObj;
	private Random rand = new Random();
	private EntityPlayer thePlayer;

	public LKContainerDrum(EntityPlayer entityplayer, World world, LKTileEntityDrum drum) {
		theDrum = new LKInventoryDrum(this, false, drum);
		worldObj = world;
		thePlayer = entityplayer;

		addSlotToContainer(new LKSlotDrum(theDrumEnchant, 0, 43, 43));

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				addSlotToContainer(new LKSlotNote(theDrum, j + (i * 4), i == 0 ? 8 : 152, j * 20 + 5));
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 9; k++) {
				addSlotToContainer(new Slot(entityplayer.inventory, k + i * 9 + 9, 8 + k * 18, 85 + i * 18));
			}

		}
		for (int j = 0; j < 9; j++) {
			addSlotToContainer(new Slot(entityplayer.inventory, j, 8 + j * 18, 143));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, enchantLevels[0]);
		crafting.sendProgressBarUpdate(this, 1, enchantLevels[1]);
		crafting.sendProgressBarUpdate(this, 2, enchantLevels[2]);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (Object crafter : crafters) {
			ICrafting crafting = (ICrafting) crafter;
			crafting.sendProgressBarUpdate(this, 0, enchantLevels[0]);
			crafting.sendProgressBarUpdate(this, 1, enchantLevels[1]);
			crafting.sendProgressBarUpdate(this, 2, enchantLevels[2]);
		}
	}

	@Override
	public void updateProgressBar(int i, int j) {
		if (i >= 0 && i <= 2) {
			enchantLevels[i] = j;
		} else {
			super.updateProgressBar(i, j);
		}
	}

	@Override
	public void onCraftMatrixChanged(IInventory inv) {
		if (inv == theDrumEnchant || inv == theDrum) {
			ItemStack itemstack = theDrumEnchant.getStackInSlot(0);
			if (itemstack != null && itemstack.isItemEnchantable()) {
				if (!worldObj.isRemote) {
					int noteValues = 0;
					for (int i = 0; i < 8; i++) {
						ItemStack note = theDrum.getStackInSlot(i);
						if (note != null && note.itemID == LKMod.note.itemID) {
							noteValues += note.stackSize * LKItemNote.getNoteValue(note.getItemDamage());
						}
					}

					noteValues = MathHelper.floor_double(noteValues / 7.0F);

					for (int i = 0; i < 3; i++) {
						int j = EnchantmentHelper.calcItemStackEnchantability(rand, i, noteValues, itemstack);
						enchantLevels[i] = j;
						if (j >= 30) {
							thePlayer.triggerAchievement(LKAchievementList.powerDrum);
						}
					}

					detectAndSendChanges();
				}
			} else {
				for (int i = 0; i < 3; i++) {
					enchantLevels[i] = 0;
				}
			}
		}
	}

	@Override
	public boolean enchantItem(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = theDrumEnchant.getStackInSlot(0);
		if (enchantLevels[i] > 0 && itemstack != null && (entityplayer.experienceLevel >= enchantLevels[i] || entityplayer.capabilities.isCreativeMode)) {
			if (!worldObj.isRemote) {
				List list = EnchantmentHelper.buildEnchantmentList(rand, itemstack, enchantLevels[i]);
				if (list != null) {
					entityplayer.addExperienceLevel(-enchantLevels[i]);
					for (Object o : list) {
						EnchantmentData enchantment = (EnchantmentData) o;
						itemstack.addEnchantment(enchantment.enchantmentobj, enchantment.enchantmentLevel);
					}
					onCraftMatrixChanged(theDrumEnchant);
					entityplayer.triggerAchievement(LKAchievementList.drumEnchant);
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return theDrum.isUseableByPlayer(entityplayer);
	}

	@Override
	public void onContainerClosed(EntityPlayer entityplayer) {
		super.onContainerClosed(entityplayer);
		if (!worldObj.isRemote) {
			ItemStack itemstack = theDrumEnchant.getStackInSlotOnClosing(0);
			if (itemstack != null) {
				entityplayer.dropPlayerItem(itemstack);
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < 9) {
				if (!mergeItemStack(itemstack1, 9, 45, true)) {
					return null;
				}
			} else if (i < 36) {
				if (!mergeItemStack(itemstack1, 36, 45, false)) {
					return null;
				}
			} else if (i < 45) {
				if (!mergeItemStack(itemstack1, 9, 36, false)) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 9, 45, false)) {
				return null;
			}
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			} else {
				slot.onPickupFromSlot(entityplayer, itemstack1);
			}
		}
		return itemstack;
	}
}