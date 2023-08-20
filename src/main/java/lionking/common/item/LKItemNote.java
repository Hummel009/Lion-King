package lionking.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import java.util.List;

public class LKItemNote extends LKItem {
	@SideOnly(Side.CLIENT)
	private Icon[] noteIcons;
	private String[] noteNames = {"c", "d", "e", "f", "g", "a", "b"};

	public LKItemNote(int i) {
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(LKCreativeTabs.tabMisc);
	}

	public static int getNoteValue(int damage) {
		switch (damage) {
			case 2:
				return 2;
			case 3:
			case 4:
				return 5;
			case 5:
				return 10;
			case 6:
				return 20;
			default:
				return 1;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int i) {
		if (i >= noteNames.length) {
			i = 0;
		}
		return noteIcons[i];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		noteIcons = new Icon[noteNames.length];
		for (int i = 0; i < noteNames.length; i++) {
			noteIcons[i] = iconregister.registerIcon("lionking:note_" + noteNames[i]);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack itemstack, int pass) {
		return true;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + itemstack.getItemDamage();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int i, CreativeTabs tab, List list) {
		for (int j = 0; j < 7; j++) {
			list.add(new ItemStack(i, 1, j));
		}
	}
}