package lionking.common.block;

import lionking.common.LKCreativeTabs;
import lionking.common.LKMod;
import net.minecraft.block.BlockLever;

public class LKBlockLever extends BlockLever {
	public LKBlockLever(int i) {
		super(i);
		setCreativeTab(LKCreativeTabs.tabMisc);
	}

	@Override
	public int getRenderType() {
		return LKMod.proxy.getLeverRenderID();
	}
}
