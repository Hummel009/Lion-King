package lionking.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;

public class LKItemShovelCorrupt extends LKItemShovel {
	public LKItemShovelCorrupt(int i, EnumToolMaterial enumtoolmaterial) {
		super(i, enumtoolmaterial);
	}

	@Override
	public float getStrVsBlock(ItemStack itemstack, Block block, int meta) {
		int currentDamage = itemstack.getItemDamage();
		if (currentDamage < 0) {
			currentDamage = 0;
		}

		float f = 0.15F + ((float) (getMaxDamage() - currentDamage) / getMaxDamage() * 0.85F);
		f *= super.getStrVsBlock(itemstack, block, meta);

		if (f < 1.0F) {
			f = 1.0F;
		}

		return f;
	}
}
