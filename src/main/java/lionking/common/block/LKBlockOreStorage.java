package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.List;

public class LKBlockOreStorage extends LKBlock {
	@SideOnly(Side.CLIENT)
	private Icon kivuliteIcon;

	public LKBlockOreStorage(int i) {
		super(i, Material.iron);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		if (blockID == LKMod.blockSilver.blockID && j == 1) {
			return kivuliteIcon;
		}
		return super.getIcon(i, j);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		super.registerIcons(iconregister);
		if (blockID == LKMod.blockSilver.blockID) {
			kivuliteIcon = iconregister.registerIcon("lionking:blockKivulite");
		}
	}

	@Override
	public int damageDropped(int i) {
		if (blockID == LKMod.blockSilver.blockID) {
			return i;
		}
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int i, CreativeTabs tab, List list) {
		if (i == LKMod.blockSilver.blockID) {
			for (int j = 0; j < 2; j++) {
				list.add(new ItemStack(i, 1, j));
			}
		} else {
			super.getSubBlocks(i, tab, list);
		}
	}

	@Override
	public boolean isBeaconBase(World world, int i, int j, int k, int beaconX, int beaconY, int beaconZ) {
		return true;
	}
}
