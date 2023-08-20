package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class LKBlockFlowerTop extends LKBlock {
	@SideOnly(Side.CLIENT)
	private Icon[] flowerIcons;
	private String[] flowerNames = {"purpleFlower_top", "redFlower_top"};

	public LKBlockFlowerTop(int i) {
		super(i, Material.plants);
		setTickRandomly(true);
		float f = 0.2F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.7F, 0.5F + f);
		setCreativeTab(null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		if (j >= flowerNames.length) {
			j = 0;
		}
		return flowerIcons[j];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		flowerIcons = new Icon[flowerNames.length];
		for (int i = 0; i < flowerNames.length; i++) {
			flowerIcons[i] = iconregister.registerIcon("lionking:" + flowerNames[i]);
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l) {
		checkFlowerChange(world, i, j, k);
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		super.onNeighborBlockChange(world, i, j, k, l);
		checkFlowerChange(world, i, j, k);
	}

	private void checkFlowerChange(World world, int i, int j, int k) {
		if (world.getBlockId(i, j - 1, k) != LKMod.flowerBase.blockID) {
			world.setBlockToAir(i, j, k);
		}
		if (world.getBlockId(i, j - 1, k) == Block.waterStill.blockID || world.getBlockId(i, j - 1, k) == Block.waterMoving.blockID) {
			world.setBlockToAir(i, j, k);
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		checkFlowerChange(world, i, j, k);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
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
		return 1;
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return 0;
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public int getLightValue(IBlockAccess iblockaccess, int i, int j, int k) {
		if (iblockaccess.getBlockMetadata(i, j, k) == 1) {
			return 11;
		}
		return super.getLightValue(iblockaccess, i, j, k);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World world, int i, int j, int k) {
		return world.getBlockMetadata(i, j, k) == 1 ? LKMod.redFlower.itemID : LKMod.purpleFlower.itemID;
	}
}
