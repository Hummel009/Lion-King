package lionking.common.enchant;

import lionking.common.LKMod;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.item.ItemStack;

public class LKEnchantmentRafikiDamage extends EnchantmentDamage {
	public LKEnchantmentRafikiDamage(int i, int j) {
		super(i, j, 0);
		setName("damage");
	}

	@Override
	public boolean canApply(ItemStack item) {
		return item.itemID == LKMod.rafikiStick.itemID;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return canApply(stack);
	}
}
