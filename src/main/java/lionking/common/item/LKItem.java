package lionking.common.item;

import lionking.common.LKCreativeTabs;
import net.minecraft.item.Item;

public class LKItem extends Item {
	public LKItem(int i) {
		super(i);
		setCreativeTab(LKCreativeTabs.tabMisc);
	}
}
