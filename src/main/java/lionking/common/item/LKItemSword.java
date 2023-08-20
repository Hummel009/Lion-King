package lionking.common.item;

import lionking.common.LKCreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSword;

public class LKItemSword extends ItemSword {
	public LKItemSword(int i, EnumToolMaterial enumtoolmaterial) {
		super(i, enumtoolmaterial);
		setCreativeTab(LKCreativeTabs.tabCombat);
	}
}
