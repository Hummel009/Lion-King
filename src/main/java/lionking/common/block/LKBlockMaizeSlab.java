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

public class LKBlockMaizeSlab extends BlockHalfSlab {
	public LKBlockMaizeSlab(int i, boolean flag) {
		super(i, flag, Material.grass);
		setCreativeTab(LKCreativeTabs.tabBlock);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		int k = j & 7;
		return LKMod.driedMaizeBlock.getIcon(i, k);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return LKMod.driedMaizeSlabSingle.blockID;
	}

	@Override
	protected ItemStack createStackedBlock(int i) {
		return new ItemStack(LKMod.driedMaizeSlabSingle, 2, i & 7);
	}

	@Override
	public String getFullSlabName(int i) {
		return getUnlocalizedName() + "." + i;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int i, int j, int k, int l) {
		if (blockID == LKMod.driedMaizeSlabDouble.blockID) {
			return super.shouldSideBeRendered(world, i, j, k, l);
		} else if (l != 1 && l != 0 && !super.shouldSideBeRendered(world, i, j, k, l)) {
			return false;
		} else {
			int i1 = i + Facing.offsetsXForSide[Facing.oppositeSide[l]];
			int j1 = j + Facing.offsetsYForSide[Facing.oppositeSide[l]];
			int k1 = k + Facing.offsetsZForSide[Facing.oppositeSide[l]];
			boolean flag = (world.getBlockMetadata(i1, j1, k1) & 8) != 0;
			return flag ? (l == 0 || (l == 1 && super.shouldSideBeRendered(world, i, j, k, l) || world.getBlockId(i, j, k) != LKMod.driedMaizeSlabSingle.blockID || (world.getBlockMetadata(i, j, k) & 8) == 0)) : (l == 1 || (l == 0 && super.shouldSideBeRendered(world, i, j, k, l) || world.getBlockId(i, j, k) != LKMod.driedMaizeSlabSingle.blockID || (world.getBlockMetadata(i, j, k) & 8) != 0));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int i, CreativeTabs tab, List list) {
		if (i != LKMod.driedMaizeSlabDouble.blockID) {
			list.add(new ItemStack(i, 1, 0));
		}
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
		return LKMod.driedMaizeSlabSingle.blockID;
	}
}
