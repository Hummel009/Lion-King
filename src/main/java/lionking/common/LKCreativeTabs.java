package lionking.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class LKCreativeTabs extends CreativeTabs {
	public static LKCreativeTabs tabBlock = new LKCreativeTabs("Lion King Blocks");
	public static LKCreativeTabs tabDeco = new LKCreativeTabs("Lion King Decorations");
	public static LKCreativeTabs tabFood = new LKCreativeTabs("Lion King Foodstuffs");
	public static LKCreativeTabs tabMaterials = new LKCreativeTabs("Lion King Materials");
	public static LKCreativeTabs tabMisc = new LKCreativeTabs("Lion King Miscellaneous");
	public static LKCreativeTabs tabTools = new LKCreativeTabs("Lion King Tools");
	public static LKCreativeTabs tabCombat = new LKCreativeTabs("Lion King Combat");
	public static LKCreativeTabs tabQuest = new LKCreativeTabs("Lion King Quest Items");

	public ItemStack theIcon;

	public LKCreativeTabs(String label) {
		super(label);
	}

	public static void setupIcons() {
		tabBlock.theIcon = new ItemStack(LKMod.prideBrick);
		tabDeco.theIcon = new ItemStack(LKMod.sapling);
		tabFood.theIcon = new ItemStack(LKMod.zebraRaw);
		tabMaterials.theIcon = new ItemStack(LKMod.hyenaBone);
		tabMisc.theIcon = new ItemStack(LKMod.bug);
		tabTools.theIcon = new ItemStack(LKMod.pickaxe);
		tabCombat.theIcon = new ItemStack(LKMod.dartShooter);
		tabQuest.theIcon = new ItemStack(LKMod.questBook, 1, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return getTabLabel();
	}

	@Override
	public ItemStack getIconItemStack() {
		return theIcon;
	}
}
