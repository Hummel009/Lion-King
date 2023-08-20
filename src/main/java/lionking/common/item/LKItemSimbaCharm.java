package lionking.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKIngame;
import lionking.common.entity.LKEntityLightning;
import lionking.common.LKMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.List;

public class LKItemSimbaCharm extends LKItem {
	@SideOnly(Side.CLIENT)
	private Icon[] charmIcons;
	private String[] charmTypes = {"active", "inactive"};

	public LKItemSimbaCharm(int i) {
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int i) {
		if (i >= charmTypes.length) {
			i = 0;
		}
		return charmIcons[i];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		charmIcons = new Icon[charmTypes.length];
		for (int i = 0; i < charmTypes.length; i++) {
			charmIcons[i] = iconregister.registerIcon("lionking:charm_" + charmTypes[i]);
		}
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f, float f1, float f2) {
		int i1 = world.getBlockId(i, j, k);
		if (i1 == LKMod.starAltar.blockID && itemstack.getItemDamage() == 1) {
			itemstack.stackSize--;
			EntityItem item = new EntityItem(world, i + 0.25D + (itemRand.nextFloat() / 2.0F), j, k + 0.25D + (itemRand.nextFloat() / 2.0F), new ItemStack(this));
			item.delayBeforeCanPickup = 10;
			item.motionX = 0.0D;
			item.motionY = 0.4D + (itemRand.nextFloat() / 10.0F);
			item.motionZ = 0.0D;
			if (!world.isRemote) {
				world.spawnEntityInWorld(item);
			}

			LKEntityLightning bolt = new LKEntityLightning(entityplayer, world, i, j, k, 0);
			if (!world.isRemote) {
				world.spawnEntityInWorld(bolt);
				for (int j1 = 0; j1 < 64; j1++) {
					double d = i - 0.5F + world.rand.nextFloat() * 2.0F;
					double d1 = j - 0.5F + world.rand.nextFloat() * 2.0F;
					double d2 = k - 0.5F + world.rand.nextFloat() * 2.0F;
					double d3;
					double d4;
					double d5;
					int k1 = world.rand.nextInt(2) * 2 - 1;
					d3 = (world.rand.nextFloat() - 0.5D) * 0.5D;
					d4 = (world.rand.nextFloat() - 0.5D) * 0.5D;
					d5 = (world.rand.nextFloat() - 0.5D) * 0.5D;
					LKIngame.spawnCustomFX(world, 64 + world.rand.nextInt(4), 16, true, d, d1, d2, d3, d4, d5);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + charmTypes[itemstack.getItemDamage()];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int i, CreativeTabs tab, List list) {
		for (int j = 0; j < 2; j++) {
			list.add(new ItemStack(i, 1, j));
		}
	}
}
