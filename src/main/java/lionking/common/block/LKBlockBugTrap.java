package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCommonProxy;
import lionking.common.LKCreativeTabs;
import lionking.common.tileentity.LKTileEntityBugTrap;
import lionking.common.LKMod;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.Random;

public class LKBlockBugTrap extends BlockContainer {
	private Random rand = new Random();

	public LKBlockBugTrap(int i) {
		super(i, Material.wood);
		setCreativeTab(LKCreativeTabs.tabDeco);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		return LKMod.prideWood.getIcon(2, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new LKTileEntityBugTrap();
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float f, float f1, float f2) {
		if (!world.isRemote) {
			entityplayer.openGui(LKMod.instance, LKCommonProxy.GUI_ID_TRAP, world, i, j, k);
		}
		return true;
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, int i3, int j3) {
		IInventory tileentity = (IInventory) world.getBlockTileEntity(i, j, k);
		if (tileentity != null) {
			label0:
			for (int l = 0; l < tileentity.getSizeInventory(); l++) {
				ItemStack itemstack = tileentity.getStackInSlot(l);
				if (itemstack == null) {
					continue;
				}
				float f = rand.nextFloat() * 0.8F + 0.1F;
				float f1 = rand.nextFloat() * 0.8F + 0.1F;
				float f2 = rand.nextFloat() * 0.8F + 0.1F;
				do {
					if (itemstack.stackSize <= 0) {
						continue label0;
					}
					int i1 = rand.nextInt(21) + 10;
					if (i1 > itemstack.stackSize) {
						i1 = itemstack.stackSize;
					}
					itemstack.stackSize -= i1;
					EntityItem entityitem = new EntityItem(world, i + f, j + f1, k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
					float f3 = 0.05F;
					entityitem.motionX = (float) rand.nextGaussian() * f3;
					entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
					entityitem.motionZ = (float) rand.nextGaussian() * f3;
					if (itemstack.hasTagCompound()) {
						entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
					}
					world.spawnEntityInWorld(entityitem);
				}
				while (true);
			}
		}
		super.breakBlock(world, i, j, k, i3, j3);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return super.canPlaceBlockAt(world, i, j, k) && world.doesBlockHaveSolidTopSurface(i, j - 1, k);
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		super.onNeighborBlockChange(world, i, j, k, l);
		if (!world.doesBlockHaveSolidTopSurface(i, j - 1, k)) {
			world.setBlockToAir(i, j, k);
			dropBlockAsItem_do(world, i, j, k, new ItemStack(this));
		}
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
		return LKMod.proxy.getBugTrapRenderID();
	}
}
