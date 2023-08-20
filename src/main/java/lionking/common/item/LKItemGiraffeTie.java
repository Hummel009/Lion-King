package lionking.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.entity.LKEntityGiraffe;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import java.util.List;

public class LKItemGiraffeTie extends LKItem {
	@SideOnly(Side.CLIENT)
	private Icon[] tieIcons;
	private String[] tieTypes = {"base", "white", "blue", "yellow", "red", "purple", "green", "black"};

	public LKItemGiraffeTie(int i) {
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int i) {
		if (i >= tieTypes.length) {
			i = 0;
		}
		return tieIcons[i];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		tieIcons = new Icon[tieTypes.length];
		for (int i = 0; i < tieTypes.length; i++) {
			tieIcons[i] = iconregister.registerIcon("lionking:tie_" + tieTypes[i]);
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + itemstack.getItemDamage();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int i, CreativeTabs tab, List list) {
		for (int j = 0; j < 8; j++) {
			list.add(new ItemStack(i, 1, j));
		}
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer entityplayer, EntityLivingBase entityliving) {
		if (entityliving instanceof LKEntityGiraffe) {
			LKEntityGiraffe giraffe = (LKEntityGiraffe) entityliving;

			if (giraffe.getTie() == -1 && !giraffe.isChild() && giraffe.getSaddled()) {
				giraffe.setTie(itemstack.getItemDamage());
				itemstack.stackSize--;
			}

			return true;
		} else {
			return false;
		}
	}
}
