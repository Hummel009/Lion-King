package lionking.common.item;

import lionking.common.LKCreativeTabs;
import lionking.common.entity.LKEntityPumbaaBomb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LKItemPumbaaBomb extends LKItem {
	public LKItemPumbaaBomb(int i) {
		super(i);
		setMaxStackSize(16);
		setCreativeTab(LKCreativeTabs.tabQuest);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!world.isRemote) {
			world.spawnEntityInWorld(new LKEntityPumbaaBomb(world, entityplayer));
			world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!entityplayer.capabilities.isCreativeMode) {
				itemstack.stackSize--;
			}
		}
		return itemstack;
	}
}
