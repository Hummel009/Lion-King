package lionking.common.item;

import lionking.common.LKCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.item.ItemReed;

public class LKItemBlockPlacer extends ItemReed {
	public LKItemBlockPlacer(int i, Block block) {
		super(i, block);
		setCreativeTab(LKCreativeTabs.tabDeco);
	}
}
