package lionking.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import lionking.common.tileentity.LKTileEntityMountedShooter;
import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class LKItemMountedShooter extends LKItem {
	@SideOnly(Side.CLIENT)
	private Icon[] shooterIcons;
	private String[] shooterTypes = {"wood", "silver"};

	public LKItemMountedShooter(int i) {
		super(i);
		setCreativeTab(LKCreativeTabs.tabCombat);
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(1);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f, float f1, float f2) {
		if (l == 0 || !world.getBlockMaterial(i, j, k).isSolid()) {
			return false;
		}

		if (l == 1) {
			j++;
		}
		if (l == 2) {
			k--;
		}
		if (l == 3) {
			k++;
		}
		if (l == 4) {
			i--;
		}
		if (l == 5) {
			i++;
		}

		if (!entityplayer.canPlayerEdit(i, j, k, l, itemstack) || !LKMod.mountedShooter.canPlaceBlockAt(world, i, j, k)) {
			return false;
		}

		Block block = LKMod.mountedShooter;
		int rotation = MathHelper.floor_double((entityplayer.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
		world.setBlock(i, j, k, block.blockID, rotation, 3);
		world.playSoundEffect((i + 0.5F), (j + 0.5F), (k + 0.5F), block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);

		TileEntity tileentity = world.getBlockTileEntity(i, j, k);
		if (tileentity instanceof LKTileEntityMountedShooter) {
			((LKTileEntityMountedShooter) tileentity).setShooterType(itemstack.getItemDamage());
		}

		itemstack.stackSize--;
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int i) {
		if (i >= shooterTypes.length) {
			i = 0;
		}
		return shooterIcons[i];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		shooterIcons = new Icon[shooterTypes.length];
		for (int i = 0; i < shooterTypes.length; i++) {
			shooterIcons[i] = iconregister.registerIcon("lionking:mountedShooter_" + shooterTypes[i]);
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + itemstack.getItemDamage();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int i, CreativeTabs tab, List list) {
		for (int j = 0; j < 2; j++) {
			list.add(new ItemStack(i, 1, j));
		}
	}
}
