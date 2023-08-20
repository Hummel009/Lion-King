package lionking.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class LKItemBugStew extends LKItemFood {
	public LKItemBugStew(int i) {
		super(i, 8, 0.5F, false);
		setContainerItem(Item.bowlEmpty);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		super.onEaten(itemstack, world, entityplayer);
		if (!world.isRemote && world.rand.nextFloat() < 0.4F) {
			entityplayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 160 + world.rand.nextInt(100), 0));
			if (world.rand.nextFloat() < 0.3F) {
				entityplayer.addPotionEffect(new PotionEffect(Potion.poison.id, 40 + world.rand.nextInt(40), 0));
			}
		}
		return new ItemStack(Item.bowlEmpty);
	}
}
