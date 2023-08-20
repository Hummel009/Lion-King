package lionking.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import java.util.List;

public class LKItemRugDye extends LKItem {
	@SideOnly(Side.CLIENT)
	private Icon[] dyeIcons;
	private String[] dyeTypes = {"white", "blue", "yellow", "red", "purple", "lightBlue", "green", "black", "violet", "pink", "lightGreen"};

	public LKItemRugDye(int i) {
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(LKCreativeTabs.tabMaterials);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int i) {
		if (i >= dyeTypes.length) {
			i = 0;
		}
		return dyeIcons[i];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		dyeIcons = new Icon[dyeTypes.length];
		for (int i = 0; i < dyeTypes.length; i++) {
			dyeIcons[i] = iconregister.registerIcon("lionking:dye_" + dyeTypes[i]);
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + dyeTypes[itemstack.getItemDamage()];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int i, CreativeTabs tab, List list) {
		for (int j = 0; j <= 10; j++) {
			list.add(new ItemStack(i, 1, j));
		}
	}
}