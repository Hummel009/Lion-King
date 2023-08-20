package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import lionking.common.LKMod;
import net.minecraft.block.BlockWall;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.List;

public class LKBlockWall extends BlockWall {
	public LKBlockWall(int i) {
		super(i, LKMod.pridestone);
		setCreativeTab(LKCreativeTabs.tabBlock);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		if (j == 1) {
			return LKMod.prideBrick.getIcon(i, 0);
		}
		if (j == 2) {
			return LKMod.prideBrickMossy.getIcon(i, 0);
		}
		if (j == 3) {
			return LKMod.pridestone.getIcon(i, 1);
		}
		if (j == 4) {
			return LKMod.prideBrick.getIcon(i, 1);
		}
		return LKMod.pridestone.getIcon(i, 0);
	}

	@Override
	public float getBlockHardness(World world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k);
		if (l == 3 || l == 4) {
			return blockHardness * 0.7F;
		}
		return blockHardness;
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int i, CreativeTabs tab, List list) {
		for (int j = 0; j <= 4; j++) {
			list.add(new ItemStack(i, 1, j));
		}
	}
}
