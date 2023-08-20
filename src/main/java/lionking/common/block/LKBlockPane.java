package lionking.common.block;

import lionking.common.LKCreativeTabs;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;

public class LKBlockPane extends BlockPane {
	public LKBlockPane(int i, String s, String s1, Material material, boolean flag) {
		super(i, s, s1, material, flag);
		setCreativeTab(LKCreativeTabs.tabDeco);
	}
}
