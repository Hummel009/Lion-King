package lionking.common.recipe;

import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LKGrindingRecipes {
	private static LKGrindingRecipes grindingBase = new LKGrindingRecipes();
	private Map grindingList = new HashMap();
	private Map metaGrindingList = new HashMap();

	private LKGrindingRecipes() {
		addGrinding(LKMod.hyenaBone.itemID, new ItemStack(LKMod.hyenaBoneShard));
		addGrinding(LKMod.mango.itemID, new ItemStack(LKMod.mangoDust));
		addGrinding(LKMod.itemTermite.itemID, new ItemStack(LKMod.termiteDust));
		addGrinding(LKMod.horn.itemID, new ItemStack(LKMod.hornGround));
		addGrinding(LKMod.whiteFlower.blockID, new ItemStack(LKMod.rugDye, 1, 0));
		addGrinding(LKMod.featherBlue.itemID, new ItemStack(LKMod.rugDye, 1, 1));
		addGrinding(LKMod.featherYellow.itemID, new ItemStack(LKMod.rugDye, 1, 2));
		addGrinding(LKMod.featherRed.itemID, new ItemStack(LKMod.rugDye, 1, 3));
		addGrinding(LKMod.purpleFlower.itemID, new ItemStack(LKMod.rugDye, 1, 4));
		addGrinding(LKMod.blueFlower.blockID, new ItemStack(LKMod.rugDye, 1, 5));
		addGrinding(LKMod.leaves.blockID, new ItemStack(LKMod.rugDye, 1, 6));
		addGrinding(LKMod.forestLeaves.blockID, new ItemStack(LKMod.rugDye, 1, 6));
		addGrinding(LKMod.mangoLeaves.blockID, new ItemStack(LKMod.rugDye, 1, 6));
		addGrinding(LKMod.bananaLeaves.blockID, new ItemStack(LKMod.rugDye, 1, 6));
		addGrinding(LKMod.redFlower.itemID, new ItemStack(LKMod.rugDye, 1, 3));
		addGrinding(LKMod.featherBlack.itemID, new ItemStack(LKMod.rugDye, 1, 7));
		addGrinding(LKMod.nukaShard.itemID, new ItemStack(LKMod.poison));
		addGrinding(LKMod.lily.blockID, 0, new ItemStack(LKMod.rugDye, 1, 0));
		addGrinding(LKMod.lily.blockID, 1, new ItemStack(LKMod.rugDye, 1, 8));
		addGrinding(LKMod.lily.blockID, 2, new ItemStack(LKMod.rugDye, 1, 3));
		addGrinding(LKMod.pridestone.blockID, new ItemStack(Block.sand));
		addGrinding(LKMod.featherPink.itemID, new ItemStack(LKMod.rugDye, 1, 9));
		addGrinding(LKMod.passionLeaves.blockID, new ItemStack(LKMod.rugDye, 1, 10));

		for (int i = 0; i <= 2; i++) {
			addGrinding(LKMod.pridePillar.blockID, i, new ItemStack(LKMod.pridePillar, 1, i + 1));
		}

		for (int i = 4; i <= 6; i++) {
			addGrinding(LKMod.pridePillar.blockID, i, new ItemStack(LKMod.pridePillar, 1, i + 1));
		}
	}

	public static LKGrindingRecipes grinding() {
		return grindingBase;
	}

	private void addGrinding(int i, ItemStack itemstack) {
		grindingList.put(i, itemstack);
	}

	private void addGrinding(int i, int j, ItemStack itemstack) {
		metaGrindingList.put(Arrays.asList(i, j), itemstack);
	}

	public ItemStack getGrindingResult(ItemStack itemstack) {
		if (itemstack == null) {
			return null;
		}
		ItemStack stack = (ItemStack) metaGrindingList.get(Arrays.asList(itemstack.itemID, itemstack.getItemDamage()));
		if (stack != null) {
			return stack;
		}
		return (ItemStack) grindingList.get(itemstack.itemID);
	}
}
