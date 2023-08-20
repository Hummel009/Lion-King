package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import lionking.common.LKMod;
import net.minecraft.block.BlockButtonStone;
import net.minecraft.util.Icon;

public class LKBlockButton extends BlockButtonStone {
	public LKBlockButton(int i) {
		super(i);
		setCreativeTab(LKCreativeTabs.tabMisc);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		return LKMod.pridestone.getIcon(i, 0);
	}
}
