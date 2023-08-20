package lionking.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.*;
import lionking.common.entity.LKEntityLightning;
import lionking.common.entity.LKEntitySimba;
import lionking.common.quest.LKQuestBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LKItemDust extends LKItem {
	public LKItemDust(int i) {
		super(i);
		setCreativeTab(LKCreativeTabs.tabQuest);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f, float f1, float f2) {
		if (LKQuestBase.rafikiQuest.getQuestStage() == 5 && LKQuestBase.rafikiQuest.isDelayed()) {
			return false;
		}

		if (LKLevelData.hasSimba(entityplayer)) {
			return false;
		}

		int i1 = world.getBlockId(i, j, k);
		if (i1 == LKMod.starAltar.blockID) {
			itemstack.stackSize--;
			LKEntityLightning bolt = new LKEntityLightning(entityplayer, world, i, j, k, 0);
			if (!world.isRemote) {
				world.spawnEntityInWorld(bolt);
			}
			world.createExplosion(entityplayer, i, (double) j + 1, k, 0.0F, false);
			LKEntitySimba simba = new LKEntitySimba(world);
			simba.setLocationAndAngles(i + 0.5, j + 1, k + 0.5, 0.0F, 0.0F);
			simba.setAge(-36000);
			simba.setHealth(15);
			simba.setOwnerName(entityplayer.username);
			if (!world.isRemote) {
				world.spawnEntityInWorld(simba);
			}
			entityplayer.triggerAchievement(LKAchievementList.simba);
			if (LKQuestBase.rafikiQuest.getQuestStage() == 6) {
				LKQuestBase.rafikiQuest.progress(7);
				LKIngame.sendMessageToAllPlayers("\u00a7e<Rafiki> \u00a7fYou see? He lives in you! Ohohoho!");
			}
			return true;
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemstack) {
		return EnumRarity.uncommon;
	}
}
