package lionking.common.item;

import lionking.common.LKIngame;
import lionking.common.LKMod;
import lionking.common.world.LKTeleporterUpendi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class LKItemPassionFruit extends LKItemFood {
	public LKItemPassionFruit(int i) {
		super(i, 0, 0.0F, false);
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		itemstack.stackSize--;
		LKMod.proxy.playPortalFXForUpendi(world);
		if (!world.isRemote && entityplayer instanceof EntityPlayerMP) {
			byte dimension;
			if (entityplayer.dimension == LKMod.idUpendi) {
				dimension = (byte) LKMod.idPrideLands;
			} else {
				dimension = (byte) LKMod.idUpendi;
			}
			MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) entityplayer, dimension, new LKTeleporterUpendi(DimensionManager.getWorld(dimension), LKIngame.getSimbas(entityplayer)));
		}
		return itemstack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.dimension == LKMod.idUpendi || entityplayer.dimension == LKMod.idPrideLands) {
			if (entityplayer.capabilities.isCreativeMode || entityplayer.getHealth() == entityplayer.getMaxHealth()) {
				entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
			}
		}
		return itemstack;
	}
}
