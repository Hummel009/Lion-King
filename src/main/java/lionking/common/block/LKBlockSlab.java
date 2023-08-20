package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import lionking.common.LKMod;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.List;
import java.util.Random;

public class LKBlockSlab extends BlockHalfSlab {
	public LKBlockSlab(int i, boolean flag) {
		super(i, flag, Material.rock);
		setCreativeTab(LKCreativeTabs.tabBlock);
	}

	public static String getSlabName(int i) {
		if (i == 0) {
			return "Pridestone Slab";
		}
		if (i == 1) {
			return "Pridestone Brick Slab";
		}
		if (i == 2) {
			return "Pridestone Pillar Slab";
		}
		if (i == 3) {
			return "Corrupt Pridestone Slab";
		}
		if (i == 4) {
			return "Corrupt Pridestone Brick Slab";
		}
		if (i == 5) {
			return "Corrupt Pridestone Pillar Slab";
		}
		return "";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		int k = j & 7;
		if (k == 1) {
			return LKMod.prideBrick.getIcon(i, 0);
		}
		if (k == 2) {
			return LKMod.pridePillar.getIcon(i, 0);
		}
		if (k == 3) {
			return LKMod.pridestone.getIcon(i, 1);
		}
		if (k == 4) {
			return LKMod.prideBrick.getIcon(i, 1);
		}
		if (k == 5) {
			return LKMod.pridePillar.getIcon(i, 4);
		}
		return LKMod.pridestone.getIcon(i, 0);
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return LKMod.slabSingle.blockID;
	}

	@Override
	protected ItemStack createStackedBlock(int i) {
		return new ItemStack(LKMod.slabSingle, 2, i & 7);
	}

	@Override
	public String getFullSlabName(int i) {
		return getUnlocalizedName() + "." + i;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int i, int j, int k, int l) {
		if (blockID == LKMod.slabDouble.blockID) {
			return super.shouldSideBeRendered(world, i, j, k, l);
		} else if (l != 1 && l != 0 && !super.shouldSideBeRendered(world, i, j, k, l)) {
			return false;
		} else {
			int i1 = i + Facing.offsetsXForSide[Facing.oppositeSide[l]];
			int j1 = j + Facing.offsetsYForSide[Facing.oppositeSide[l]];
			int k1 = k + Facing.offsetsZForSide[Facing.oppositeSide[l]];
			boolean flag = (world.getBlockMetadata(i1, j1, k1) & 8) != 0;
			return flag ? (l == 0 || (l == 1 && super.shouldSideBeRendered(world, i, j, k, l) || world.getBlockId(i, j, k) != LKMod.slabSingle.blockID || (world.getBlockMetadata(i, j, k) & 8) == 0)) : (l == 1 || (l == 0 && super.shouldSideBeRendered(world, i, j, k, l) || world.getBlockId(i, j, k) != LKMod.slabSingle.blockID || (world.getBlockMetadata(i, j, k) & 8) != 0));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int i, CreativeTabs tab, List list) {
		if (i != LKMod.slabDouble.blockID) {
			for (int j = 0; j < 6; j++) {
				list.add(new ItemStack(i, 1, j));
			}
		}
	}

	@Override
	public float getBlockHardness(World world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k) & 7;

		if (l == 2) {
			return 1.2F;
		}

		if (l == 3 || l == 4) {
			return blockHardness * 0.7F;
		}

		if (l == 5) {
			return 1.2F * 0.7F;
		}

		return blockHardness;
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		return (((meta & 8) == 8) || isOpaqueCube());
	}

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
		return (((world.getBlockMetadata(x, y, z) & 8) == 8 && (side.ordinal() == 1)) || isOpaqueCube());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World world, int i, int j, int k) {
		return LKMod.slabSingle.blockID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
	}
}
