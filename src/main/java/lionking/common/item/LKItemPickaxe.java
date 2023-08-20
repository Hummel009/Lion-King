package lionking.common.item;

import lionking.common.LKCreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;

public class LKItemPickaxe extends ItemPickaxe {
	public LKItemPickaxe(int i, EnumToolMaterial enumtoolmaterial) {
		super(i, enumtoolmaterial);
		setCreativeTab(LKCreativeTabs.tabTools);
	}
}
