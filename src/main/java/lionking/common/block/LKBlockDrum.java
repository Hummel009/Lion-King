package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCommonProxy;
import lionking.common.LKCreativeTabs;
import lionking.common.LKMod;
import lionking.common.tileentity.LKTileEntityDrum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.List;

public class LKBlockDrum extends BlockContainer {
	@SideOnly(Side.CLIENT)
	private Icon drumTopIcon;
	@SideOnly(Side.CLIENT)
	private Icon[] drumSideIcons;
	private String[] drumTypes = {"acacia", "rainforest", "mango", "passion", "banana", "deadwood"};

	public LKBlockDrum(int i) {
		super(i, Material.wood);
		float f = 0.0625F;
		setBlockBounds(0.0F + f, 0.0F, 0.0F + f, 1.0F - f, 0.75F, 1.0F - f);
		setCreativeTab(LKCreativeTabs.tabDeco);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new LKTileEntityDrum();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		if (i == 0) {
			Block woodBlock = j < 4 ? LKMod.prideWood : LKMod.prideWood2;
			return woodBlock.getIcon(2, j < 4 ? j : j - 4);
		} else if (i == 1) {
			return drumTopIcon;
		}

		if (j >= drumTypes.length) {
			j = 0;
		}
		return drumSideIcons[j];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		drumTopIcon = iconregister.registerIcon("lionking:drum_top");
		drumSideIcons = new Icon[drumTypes.length];
		for (int i = 0; i < drumTypes.length; i++) {
			drumSideIcons[i] = iconregister.registerIcon("lionking:drum_side_" + drumTypes[i]);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int i, CreativeTabs tab, List list) {
		for (int j = 0; j <= 5; j++) {
			list.add(new ItemStack(i, 1, j));
		}
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
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float f, float f1, float f2) {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (itemstack != null && itemstack.itemID == LKMod.staff.itemID) {
			if (!world.isRemote) {
				entityplayer.openGui(LKMod.instance, LKCommonProxy.GUI_ID_DRUM, world, i, j, k);
			}
			return true;
		}

		LKTileEntityDrum drum = (LKTileEntityDrum) world.getBlockTileEntity(i, j, k);
		if (drum != null) {
			drum.changePitch();
			playNote(world, i, j, k, drum.note);
		}
		return true;
	}

	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer) {
		LKTileEntityDrum drum = (LKTileEntityDrum) world.getBlockTileEntity(i, j, k);
		if (drum != null) {
			playNote(world, i, j, k, drum.note);
		}
	}

	private void playNote(World world, int i, int j, int k, int note) {
		float pitch = (float) Math.pow(2.0D, (note - 12) / 12.0D);
		world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, "lionking:bongo", 3.0F, pitch);
		world.spawnParticle("note", i + 0.5D, j + 1.2D, k + 0.5D, note / 24.0D, 0.0D, 0.0D);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, int i3, int j3) {
		IInventory drum = (IInventory) world.getBlockTileEntity(i, j, k);
		if (drum != null) {
			label0:
			for (int l = 0; l < drum.getSizeInventory(); l++) {
				ItemStack itemstack = drum.getStackInSlot(l);
				if (itemstack == null) {
					continue;
				}
				float f = world.rand.nextFloat() * 0.8F + 0.1F;
				float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
				float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
				do {
					if (itemstack.stackSize <= 0) {
						continue label0;
					}
					int i1 = world.rand.nextInt(21) + 10;
					if (i1 > itemstack.stackSize) {
						i1 = itemstack.stackSize;
					}
					itemstack.stackSize -= i1;
					EntityItem entityitem = new EntityItem(world, i + f, j + f1, k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
					float f3 = 0.05F;
					entityitem.motionX = (float) world.rand.nextGaussian() * f3;
					entityitem.motionY = (float) world.rand.nextGaussian() * f3 + 0.2F;
					entityitem.motionZ = (float) world.rand.nextGaussian() * f3;
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
}
