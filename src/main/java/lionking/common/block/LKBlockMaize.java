package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKIngame;
import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.Random;

public class LKBlockMaize extends LKBlock {
	@SideOnly(Side.CLIENT)
	private Icon cornIcon;

	public LKBlockMaize(int i) {
		super(i, Material.plants);
		float f = 0.375F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
		setTickRandomly(true);
		setCreativeTab(null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		if (j == 1) {
			return cornIcon;
		}
		return super.getIcon(i, j);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		super.registerIcons(iconregister);
		cornIcon = iconregister.registerIcon("lionking:maize_corn");
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		checkBlockCoordValid(world, i, j, k);

		if (LKIngame.isLKWorld(world.provider.dimensionId)) {
			int l;
			for (l = 1; world.getBlockId(i, j - l, k) == blockID; l++) {
			}

			if (l < 4) {
				int growthRate = 8;
				for (int l1 = 1; l1 < 5; l1++) {
					if (world.getBlockId(i, j - l1, k) == Block.tilledField.blockID) {
						growthRate = world.getBlockMetadata(i, j - l1, k) > 0 ? 2 : 4;
					}
				}

				if (random.nextInt(growthRate) == 0) {
					if (world.isAirBlock(i, j + 1, k) && random.nextInt(22) == 0) {
						world.setBlock(i, j + 1, k, blockID, 1, 3);
					}
					if (world.getBlockMetadata(i, j, k) == 0 && world.getBlockId(i, j - 1, k) == blockID && random.nextInt(25) == 0) {
						world.setBlockMetadataWithNotify(i, j, k, 1, 3);
					}
				}
			}
		}
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		int l = world.getBlockId(i, j - 1, k);
		return l == blockID || l == Block.tilledField.blockID || ((l == Block.grass.blockID || l == Block.dirt.blockID) && (world.getBlockMaterial(i - 1, j - 1, k) == Material.water || (world.getBlockMaterial(i + 1, j - 1, k) == Material.water || (world.getBlockMaterial(i, j - 1, k - 1) == Material.water || world.getBlockMaterial(i, j - 1, k + 1) == Material.water))));
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		checkBlockCoordValid(world, i, j, k);
	}

	private void checkBlockCoordValid(World world, int i, int j, int k) {
		if (!canBlockStay(world, i, j, k)) {
			dropBlockAsItem(world, i, j, k, 0, 0);
			world.setBlockToAir(i, j, k);
		}
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		return canPlaceBlockAt(world, i, j, k);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return LKMod.cornStalk.itemID;
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
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float f, float f1, float f2) {
		if (!world.isRemote) {
			if (world.getBlockMetadata(i, j, k) == 1) {
				world.setBlockMetadataWithNotify(i, j, k, 0, 3);
				dropBlockAsItem_do(world, i, j, k, new ItemStack(LKMod.corn));
				if (world.rand.nextInt(4) == 0) {
					dropBlockAsItem_do(world, i, j, k, new ItemStack(LKMod.corn));
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, int l, int i1) {
		if (!world.isRemote) {
			if (world.getBlockMetadata(i, j, k) == 1) {
				dropBlockAsItem_do(world, i, j, k, new ItemStack(LKMod.corn));
				if (world.rand.nextInt(4) == 0) {
					dropBlockAsItem_do(world, i, j, k, new ItemStack(LKMod.corn));
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World world, int i, int j, int k) {
		return LKMod.cornStalk.itemID;
	}
}
