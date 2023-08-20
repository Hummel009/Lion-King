package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKIngame;
import lionking.common.LKLevelData;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class LKBlockRafikiWood extends LKBlock {
	@SideOnly(Side.CLIENT)
	private Icon[] treeIcons;
	@SideOnly(Side.CLIENT)
	private Icon[] corruptTreeIcons;
	@SideOnly(Side.CLIENT)
	private Icon[] christmasIcons;
	private String[] woodTypes = {"side", "log", "top"};

	public LKBlockRafikiWood(int i) {
		super(i, Material.wood);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int i, CreativeTabs tab, List list) {
		for (int j = 0; j < 3; j++) {
			list.add(new ItemStack(i, 1, j));
		}
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		treeIcons = new Icon[3];
		corruptTreeIcons = new Icon[3];
		christmasIcons = new Icon[2];
		for (int i = 0; i < 3; i++) {
			treeIcons[i] = iconregister.registerIcon("lionking:rafikiWood_" + woodTypes[i]);
			corruptTreeIcons[i] = iconregister.registerIcon("lionking:rafikiWood_" + woodTypes[i] + "_corrupt");
		}
		christmasIcons[0] = iconregister.registerIcon("lionking:rafikiWood_christmas_top");
		christmasIcons[1] = iconregister.registerIcon("lionking:rafikiWood_christmas_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		boolean ziraOccupied = LKLevelData.ziraStage >= 14 && LKLevelData.ziraStage < 19;
		Icon[] icons = ziraOccupied ? corruptTreeIcons : treeIcons;
		if (j == 1) {
			if (i == 0 || i == 1) {
				return icons[1];
			}
		}
		if (j == 2) {
			return icons[2];
		}
		return icons[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess world, int i, int j, int k, int side) {
		int meta = world.getBlockMetadata(i, j, k);
		boolean ziraOccupied = LKLevelData.ziraStage >= 14 && LKLevelData.ziraStage < 19;
		Icon[] icons = ziraOccupied ? corruptTreeIcons : treeIcons;
		if (meta == 1) {
			if (side == 0 || side == 1) {
				return icons[1];
			}
		}
		if (meta == 2) {
			if (ziraOccupied) {
				return icons[2];
			}
			if (LKIngame.isChristmas()) {
				if (side == 1) {
					return christmasIcons[1];
				}
				if (side > 1 && !world.isBlockOpaqueCube(i, j + 1, k)) {
					return christmasIcons[0];
				}
			}
			return icons[2];
		}
		return icons[0];
	}

	@Override
	public int getMobilityFlag() {
		return 2;
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, World world, int i, int j, int k) {
		return false;
	}
}
