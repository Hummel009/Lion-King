package lionking.common.item;

import lionking.common.LKMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LKItemMangoJuice extends LKItemFood {
	public LKItemMangoJuice(int i) {
		super(i, 7, 0.8F, false);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		itemstack.stackSize--;
		entityplayer.getFoodStats().addStats(this);
		world.playSoundAtEntity(entityplayer, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		if (itemstack.stackSize <= 0) {
			return new ItemStack(LKMod.jar);
		}
		return itemstack;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.drink;
	}
}
