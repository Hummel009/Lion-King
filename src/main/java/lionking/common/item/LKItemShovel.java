package lionking.common.item;

import lionking.common.LKCreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;

public class LKItemShovel extends ItemSpade {
	public LKItemShovel(int i, EnumToolMaterial enumtoolmaterial) {
		super(i, enumtoolmaterial);
		setCreativeTab(LKCreativeTabs.tabTools);
	}
}
