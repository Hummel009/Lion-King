package lionking.common.block;

import lionking.common.LKCreativeTabs;
import net.minecraft.block.BlockFlower;

public class LKBlockFlower extends BlockFlower {
	public LKBlockFlower(int i) {
		super(i);
		setCreativeTab(LKCreativeTabs.tabDeco);
	}
}
