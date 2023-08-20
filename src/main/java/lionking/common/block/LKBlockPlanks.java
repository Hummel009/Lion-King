package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import java.util.List;

public class LKBlockPlanks extends LKBlock {
	@SideOnly(Side.CLIENT)
	private Icon[] plankIcons;
	private String[] plankTypes = {"acacia", "rainforest", "mango", "passion", "banana", "deadwood"};

	public LKBlockPlanks(int i) {
		super(i, Material.wood);
		setCreativeTab(LKCreativeTabs.tabBlock);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		if (j >= plankTypes.length) {
			j = 0;
		}
		return plankIcons[j];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		plankIcons = new Icon[plankTypes.length];
		for (int i = 0; i < plankTypes.length; i++) {
			plankIcons[i] = iconregister.registerIcon(getTextureName() + "_" + plankTypes[i]);
		}
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int i, CreativeTabs tab, List list) {
		for (int j = 0; j <= 5; j++) {
			list.add(new ItemStack(i, 1, j));
		}
	}
}
