package lionking.common.item;

import lionking.common.LKCreativeTabs;
import net.minecraft.item.ItemSeeds;

public class LKItemSeeds extends ItemSeeds {
	public LKItemSeeds(int i, int j, int k) {
		super(i, j, k);
		setCreativeTab(LKCreativeTabs.tabMaterials);
	}
}
