package lionking.common.item;

import lionking.common.LKCreativeTabs;
import lionking.common.entity.LKEntityGiraffe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class LKItemGiraffeSaddle extends LKItem {
	public LKItemGiraffeSaddle(int i) {
		super(i);
		setMaxStackSize(1);
		setCreativeTab(LKCreativeTabs.tabMisc);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer entityplayer, EntityLivingBase entityliving) {
		if (entityliving instanceof LKEntityGiraffe) {
			LKEntityGiraffe giraffe = (LKEntityGiraffe) entityliving;

			if (!giraffe.getSaddled() && !giraffe.isChild()) {
				giraffe.setSaddled(true);
				itemstack.stackSize--;
			}

			return true;
		} else {
			return false;
		}
	}
}
