package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import java.util.List;

public class LKBlockWood2 extends LKBlockWood {
	@SideOnly(Side.CLIENT)
	private Icon[][] woodIcons;
	private String[] woodNames = {"banana", "deadwood"};

	public LKBlockWood2(int i) {
		super(i);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		int j1 = j & 12;
		j &= 3;

		if (j >= woodNames.length) {
			j = 0;
		}

		if ((j1 == 0 && (i == 0 || i == 1)) || (j1 == 4 && (i == 4 || i == 5)) || (j1 == 8 && (i == 2 || i == 3))) {
			return woodIcons[j][0];
		}
		return woodIcons[j][1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		woodIcons = new Icon[woodNames.length][2];
		for (int i = 0; i < woodNames.length; i++) {
			woodIcons[i][0] = iconregister.registerIcon("lionking:wood_" + woodNames[i] + "_top");
			woodIcons[i][1] = iconregister.registerIcon("lionking:wood_" + woodNames[i] + "_side");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int i, CreativeTabs tab, List list) {
		for (int j = 0; j <= 1; j++) {
			list.add(new ItemStack(i, 1, j));
		}
	}
}
