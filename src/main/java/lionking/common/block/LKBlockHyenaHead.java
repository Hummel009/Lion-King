package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.tileentity.LKTileEntityHyenaHead;
import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class LKBlockHyenaHead extends BlockContainer {
	public LKBlockHyenaHead(int i) {
		super(i, Material.circuits);
		setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		return Block.slowSand.getIcon(i, j);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
	}

	@Override
	public int getRenderType() {
		return -1;
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
	public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k) & 0x7;
		switch (l) {
			case 2:
				setBlockBounds(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
				break;
			case 3:
				setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
				break;
			case 4:
				setBlockBounds(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
				break;
			case 5:
				setBlockBounds(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
				break;
			default:
				setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
				break;
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		setBlockBoundsBasedOnState(world, i, j, k);
		return super.getCollisionBoundingBoxFromPool(world, i, j, k);
	}

	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving) {
		int i1 = MathHelper.floor_double(entityliving.rotationYaw * 4.0F / 360.0F + 2.5D) & 0x3;
		world.setBlockMetadataWithNotify(i, j, k, i1, 2);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new LKTileEntityHyenaHead();
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return LKMod.hyenaHeadItem.itemID;
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World world, int i, int j, int k) {
		return LKMod.hyenaHeadItem.itemID;
	}

	@Override
	public int getDamageValue(World world, int i, int j, int k) {
		TileEntity tileentity = world.getBlockTileEntity(i, j, k);
		if (tileentity instanceof LKTileEntityHyenaHead) {
			return ((LKTileEntityHyenaHead) tileentity).getHyenaType();
		}
		return 0;
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int i, int j, int k, int l, float f, int i1) {
	}

	@Override
	public void onBlockHarvested(World world, int i, int j, int k, int l, EntityPlayer entityplayer) {
		if (entityplayer.capabilities.isCreativeMode) {
			l |= 8;
			world.setBlockMetadataWithNotify(i, j, k, l, 4);
		}
		super.onBlockHarvested(world, i, j, k, l, entityplayer);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, int l, int i1) {
		if (!world.isRemote && (i1 & 8) == 0) {
			dropBlockAsItem_do(world, i, j, k, new ItemStack(LKMod.hyenaHeadItem.itemID, 1, getDamageValue(world, i, j, k)));
		}
	}
}
