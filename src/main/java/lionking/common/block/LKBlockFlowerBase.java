package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.Random;

public class LKBlockFlowerBase extends LKBlock {
	@SideOnly(Side.CLIENT)
	private Icon[] flowerIcons;
	private String[] flowerNames = {"purpleFlower_base", "redFlower_base"};

	public LKBlockFlowerBase(int i) {
		super(i, Material.plants);
		setTickRandomly(true);
		float f = 0.2F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
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
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return super.canPlaceBlockAt(world, i, j, k) && canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
	}

	private boolean canThisPlantGrowOnThisBlockID(int i) {
		return i == Block.grass.blockID || i == Block.dirt.blockID || i == Block.tilledField.blockID;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		super.onNeighborBlockChange(world, i, j, k, l);
		checkFlowerChange(world, i, j, k);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		checkFlowerChange(world, i, j, k);
	}

	private void checkFlowerChange(World world, int i, int j, int k) {
		if (world.getBlockId(i, j + 1, k) != LKMod.flowerTop.blockID || !canBlockStay(world, i, j, k)) {
			world.setBlockToAir(i, j, k);
			world.setBlockToAir(i, j + 1, k);
		}
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, int l, int i1) {
		dropBlockAsItem_do(world, i, j, k, new ItemStack(world.getBlockMetadata(i, j, k) == 0 ? LKMod.purpleFlower : LKMod.redFlower));
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l) {
		checkFlowerChange(world, i, j, k);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		boolean flag = world.getBlockMetadata(i, j, k) == 1 || world.getFullBlockLightValue(i, j, k) >= 8;
		return (flag || world.canBlockSeeTheSky(i, j, k)) && canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
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
	@SideOnly(Side.CLIENT)
	public int idPicked(World world, int i, int j, int k) {
		return world.getBlockMetadata(i, j, k) == 1 ? LKMod.redFlower.itemID : LKMod.purpleFlower.itemID;
	}
}
