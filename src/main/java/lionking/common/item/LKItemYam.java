package lionking.common.item;

import lionking.common.LKCreativeTabs;
import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.potion.Potion;

public class LKItemYam extends ItemSeedFood {
	public LKItemYam(int i) {
		super(i, 1, 0.4F, LKMod.yamCrops.blockID, Block.tilledField.blockID);
		setPotionEffect(Potion.hunger.id, 15, 0, 0.4F);
		setCreativeTab(LKCreativeTabs.tabFood);
	}
}
