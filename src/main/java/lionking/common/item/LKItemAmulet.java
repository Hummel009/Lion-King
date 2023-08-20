package lionking.common.item;

import lionking.common.LKCreativeTabs;
import lionking.common.LKMod;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class LKItemAmulet extends LKItemArmor {
	public LKItemAmulet(int i) {
		super(i, LKMod.armorSuit, 0, 1);
		setMaxDamage(0);
		setMaxStackSize(1);
		setCreativeTab(LKCreativeTabs.tabQuest);
	}

	@Override
	public boolean isDamageable() {
		return false;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
		return "lionking:item/amulet.png";
	}
}
