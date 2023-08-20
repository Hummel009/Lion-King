package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKMod;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.Random;

public class LKBlockHangingBanana extends LKBlock {
	@SideOnly(Side.CLIENT)
	private Icon[] bananaIcons;
	private String[] bananaSides = {"top", "side", "bottom"};

	public LKBlockHangingBanana(int i) {
		super(i, Material.plants);
		setTickRandomly(true);
		setCreativeTab(null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		if (i == 0) {
			return bananaIcons[2];
		}
		if (i == 1) {
			return bananaIcons[0];
		}
		return bananaIcons[1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		bananaIcons = new Icon[3];
		for (int i = 0; i < 3; i++) {
			bananaIcons[i] = iconregister.registerIcon("lionking:banana_" + bananaSides[i]);
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if (!canBlockStay(world, i, j, k)) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockToAir(i, j, k);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		if (!canBlockStay(world, i, j, k)) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockToAir(i, j, k);
		}
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k);
		ForgeDirection dir = ForgeDirection.getOrientation(l + 2);
		int id = world.getBlockId(i + dir.offsetX, j, k + dir.offsetZ);
		int meta = world.getBlockMetadata(i + dir.offsetX, j, k + dir.offsetZ);
		return id == LKMod.prideWood2.blockID && BlockLog.limitToValidMetadata(meta) == 0;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		setBlockBoundsBasedOnState(world, i, j, k);
		return super.getCollisionBoundingBoxFromPool(world, i, j, k);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		setBlockBoundsBasedOnState(world, i, j, k);
		return super.getSelectedBoundingBoxFromPool(world, i, j, k);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k) {
		int dir = world.getBlockMetadata(i, j, k);
		switch (dir) {
			case 0:
				setBlockBounds(0.375F, 0.1875F, 0.0F, 0.625F, 0.9375F, 0.25F);
				break;
			case 1:
				setBlockBounds(0.375F, 0.1875F, 0.75F, 0.625F, 0.9375F, 1.0F);
				break;
			case 2:
				setBlockBounds(0.0F, 0.1875F, 0.375F, 0.25F, 0.9375F, 0.625F);
				break;
			case 3:
				setBlockBounds(0.75F, 0.1875F, 0.375F, 1.0F, 0.9375F, 0.625F);
		}
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return LKMod.banana.itemID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World world, int i, int j, int k) {
		return LKMod.banana.itemID;
	}
}
