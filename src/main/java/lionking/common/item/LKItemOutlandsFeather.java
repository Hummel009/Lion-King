package lionking.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import lionking.common.LKIngame;
import lionking.common.LKLevelData;
import lionking.common.LKMod;
import lionking.common.world.LKTeleporterFeather;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class LKItemOutlandsFeather extends LKItem {
	public LKItemOutlandsFeather(int i) {
		super(i);
		setMaxStackSize(16);
		setCreativeTab(LKCreativeTabs.tabQuest);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (LKLevelData.ziraStage > 9 && LKLevelData.ziraStage < 14) {
			return itemstack;
		}
		if (entityplayer.dimension != LKMod.idPrideLands && entityplayer.dimension != LKMod.idOutlands) {
			return itemstack;
		}
		world.playSoundAtEntity(entityplayer, "portal.portal", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
		int dimension = LKMod.idOutlands;
		if (entityplayer.dimension == LKMod.idOutlands) {
			dimension = LKMod.idPrideLands;
		} else if (entityplayer.dimension == LKMod.idPrideLands) {
			dimension = LKMod.idOutlands;
		}
		if (entityplayer instanceof EntityPlayerMP && world instanceof WorldServer) {
			MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) entityplayer, dimension, new LKTeleporterFeather(DimensionManager.getWorld(dimension), LKIngame.getSimbas(entityplayer)));
		}
		itemstack.stackSize--;
		return itemstack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemstack) {
		return EnumRarity.uncommon;
	}
}
