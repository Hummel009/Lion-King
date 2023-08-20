package lionking.common.item;

import lionking.common.LKMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LKItemZebraMilk extends LKItem {
	public LKItemZebraMilk(int i) {
		super(i);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		itemstack.stackSize--;
		if (!world.isRemote) {
			entityplayer.clearActivePotions();
		}
		if (itemstack.stackSize <= 0) {
			return new ItemStack(LKMod.jar);
		} else {
			return itemstack;
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.drink;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}
}
