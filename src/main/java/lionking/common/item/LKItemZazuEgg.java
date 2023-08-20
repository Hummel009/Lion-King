package lionking.common.item;

import lionking.common.entity.LKEntityZazuEgg;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LKItemZazuEgg extends LKItem {
	public LKItemZazuEgg(int i) {
		super(i);
		setMaxStackSize(16);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!world.isRemote) {
			world.spawnEntityInWorld(new LKEntityZazuEgg(world, entityplayer));
			world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!entityplayer.capabilities.isCreativeMode) {
				itemstack.stackSize--;
			}
		}
		return itemstack;
	}
}
