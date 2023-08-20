package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.List;

public class LKBlockPillar extends LKBlock {
	@SideOnly(Side.CLIENT)
	private Icon[] pillarIcons;
	@SideOnly(Side.CLIENT)
	private Icon[] corruptIcons;

	public LKBlockPillar(int i) {
		super(i, Material.rock);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		if (j > 3 && j < 8) {
			if (i == 0 || i == 1) {
				return corruptIcons[1];
			}
			return corruptIcons[0];
		} else {
			if (i == 0 || i == 1) {
				return pillarIcons[1];
			}
			return pillarIcons[0];
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		pillarIcons = new Icon[2];
		corruptIcons = new Icon[2];
		pillarIcons[0] = iconregister.registerIcon("lionking:pridestonePillar_side");
		pillarIcons[1] = iconregister.registerIcon("lionking:pridestonePillar_top");
		corruptIcons[0] = iconregister.registerIcon("lionking:pridestonePillar_corrupt_side");
		corruptIcons[1] = iconregister.registerIcon("lionking:pridestonePillar_corrupt_top");
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k);
		if (l > 3 && l < 8) {
			l -= 4;
		}
		float f = 0.125F * l;
		setBlockBounds(0.0F + f, 0.0F, 0.0F + f, 1.0F - f, 1.0F, 1.0F - f);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k);
		if (l > 3 && l < 8) {
			l -= 4;
		}
		double d = 0.125D * l;
		return AxisAlignedBB.getAABBPool().getAABB(i + d, j, k + d, i + 1.0D - d, (double) j + 1.0F, k + 1.0D - d);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return LKMod.proxy.getPillarRenderID();
	}

	@Override
	public boolean isBlockSolidOnSide(World world, int i, int j, int k, ForgeDirection side) {
		if (world.getBlockMetadata(i, j, k) == 0 || world.getBlockMetadata(i, j, k) == 4) {
			return true;
		}
		return side == ForgeDirection.UP;
	}

	@Override
	public float getBlockHardness(World world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k);
		if (l > 3 && l < 8) {
			return blockHardness * 0.7F;
		}
		return blockHardness;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int i, CreativeTabs tab, List list) {
		for (int j = 0; j < 8; j++) {
			list.add(new ItemStack(i, 1, j));
		}
	}
}
