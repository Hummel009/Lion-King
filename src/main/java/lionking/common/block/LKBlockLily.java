package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import lionking.common.LKMod;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import java.util.List;

public class LKBlockLily extends BlockLilyPad {
	@SideOnly(Side.CLIENT)
	private Icon[] lilyIcons;
	private String[] lilyTypes = {"white", "violet", "red"};

	public LKBlockLily(int i) {
		super(i);
		setCreativeTab(LKCreativeTabs.tabDeco);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int i, CreativeTabs tab, List list) {
		for (int j = 0; j < 3; j++) {
			list.add(new ItemStack(i, 1, j));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		if (j >= lilyTypes.length) {
			j = 0;
		}
		return lilyIcons[j];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		lilyIcons = new Icon[lilyTypes.length];
		for (int i = 0; i < lilyTypes.length; i++) {
			lilyIcons[i] = iconregister.registerIcon(getTextureName() + "_" + lilyTypes[i]);
		}
	}

	@Override
	public int getRenderType() {
		return LKMod.proxy.getLilyRenderID();
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return 0xFFFFFF;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int i) {
		return 0xFFFFFF;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
		return 0xFFFFFF;
	}
}
