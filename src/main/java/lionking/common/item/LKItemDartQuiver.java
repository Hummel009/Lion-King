package lionking.common.item;

import lionking.common.LKCommonProxy;
import lionking.common.LKCreativeTabs;
import lionking.common.LKMod;
import lionking.common.inventory.LKInventoryQuiver;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LKItemDartQuiver extends LKItem {
	public LKItemDartQuiver(int i) {
		super(i);
		setMaxStackSize(1);
		setCreativeTab(LKCreativeTabs.tabCombat);
	}

	public static LKInventoryQuiver getQuiverInventory(int i, World world) {
		String s = "lk.quiver_" + i;
		LKInventoryQuiver inv = (LKInventoryQuiver) world.loadItemData(LKInventoryQuiver.class, s);
		if (inv == null) {
			inv = new LKInventoryQuiver(s);
			inv.markDirty();
			world.setItemData(s, inv);
		}
		return inv;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!world.isRemote) {
			entityplayer.openGui(LKMod.instance, LKCommonProxy.GUI_ID_QUIVER, world, itemstack.getItemDamage(), 0, 0);
		}
		return itemstack;
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (!world.isRemote) {
			return;
		}

		String s = "lk.quiver_" + itemstack.getItemDamage();
		LKInventoryQuiver inv = (LKInventoryQuiver) world.loadItemData(LKInventoryQuiver.class, s);
		if (inv == null) {
			inv = new LKInventoryQuiver(s);
			inv.markDirty();
			world.setItemData(s, inv);
		}
	}
}