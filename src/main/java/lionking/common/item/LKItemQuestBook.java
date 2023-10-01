package lionking.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCommonProxy;
import lionking.common.LKCreativeTabs;
import lionking.common.LKMod;
import lionking.common.quest.LKQuestBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class LKItemQuestBook extends LKItem {
	public LKItemQuestBook(int i) {
		super(i);
		setMaxStackSize(1);
		setCreativeTab(LKCreativeTabs.tabQuest);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!world.isRemote) {
			entityplayer.openGui(LKMod.instance, LKCommonProxy.GUI_ID_QUESTS, world, 0, 0, 0);
		}
		return itemstack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		if (LKQuestBase.anyUncheckedQuests()) {
			list.add("New quests available");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack itemstack, int pass) {
		return itemstack.getItemDamage() == 0 && LKQuestBase.anyUncheckedQuests();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemstack) {
		return EnumRarity.uncommon;
	}
}
