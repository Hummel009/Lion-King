package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;

public class LKBlockGlass extends BlockBreakable {
	public LKBlockGlass(int i, Material material, boolean flag) {
		super(i, "lionking:outglass", material, flag);
		setCreativeTab(LKCreativeTabs.tabBlock);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 0;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
