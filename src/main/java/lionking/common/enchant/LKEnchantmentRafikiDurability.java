package lionking.common.enchant;

import lionking.common.LKMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public class LKEnchantmentRafikiDurability extends Enchantment {
	public LKEnchantmentRafikiDurability(int i, int j) {
		super(i, j, EnumEnchantmentType.weapon);
		setName("rafiki.durability");
	}

	@Override
	public int getMinEnchantability(int i) {
		return 4 + (i - 1) * 10;
	}

	@Override
	public int getMaxEnchantability(int i) {
		return super.getMinEnchantability(i) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 3;
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
