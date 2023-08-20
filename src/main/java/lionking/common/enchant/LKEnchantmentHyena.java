package lionking.common.enchant;

import lionking.common.entity.LKEntityHyena;
import lionking.common.entity.LKEntitySkeletalHyenaHead;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.EntityLivingBase;

public class LKEnchantmentHyena extends EnchantmentDamage {
	public LKEnchantmentHyena(int i, int j) {
		super(i, j, 0);
	}

	@Override
	public int getMinEnchantability(int i) {
		return 5 + (i - 1) * 8;
	}

	@Override
	public int getMaxEnchantability(int i) {
		return getMinEnchantability(i) + 20;
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	public float calcModifierLiving(int i, EntityLivingBase entity) {
		return entity instanceof LKEntityHyena || entity instanceof LKEntitySkeletalHyenaHead ? i * 4.0F : 0.0F;
	}

	@Override
	public String getName() {
		return "enchantment.damage.hyena";
	}

	@Override
	public boolean canApplyTogether(Enchantment iEnchantment) {
		return !(iEnchantment instanceof EnchantmentDamage);
	}
}
