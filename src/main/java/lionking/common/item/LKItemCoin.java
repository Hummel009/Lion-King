package lionking.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import lionking.common.LKMod;
import lionking.common.entity.LKEntityCoin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LKItemCoin extends LKItem {
	private byte type;

	public LKItemCoin(int i, byte b) {
		super(i);
		setMaxStackSize(16);
		type = b;
		setCreativeTab(LKCreativeTabs.tabQuest);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!world.isRemote && ((type == 0 && entityplayer.dimension == LKMod.idPrideLands) || (type == 1 && entityplayer.dimension == LKMod.idOutlands))) {
			world.spawnEntityInWorld(new LKEntityCoin(world, entityplayer, type));
			world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!entityplayer.capabilities.isCreativeMode) {
				itemstack.stackSize--;
			}
		}
		return itemstack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemstack) {
		return EnumRarity.uncommon;
	}
}