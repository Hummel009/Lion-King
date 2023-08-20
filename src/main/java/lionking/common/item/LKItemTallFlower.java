package lionking.common.item;

import lionking.common.LKCreativeTabs;
import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LKItemTallFlower extends LKItem {
	private int flowerMetadata;

	public LKItemTallFlower(int i, int j) {
		super(i);
		flowerMetadata = j;
		setCreativeTab(LKCreativeTabs.tabDeco);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f, float f1, float f2) {
		int i1 = world.getBlockId(i, j, k);
		if (i1 == Block.snow.blockID) {
			l = 1;
		} else if (i1 != Block.vine.blockID && i1 != Block.tallGrass.blockID && i1 != Block.deadBush.blockID) {
			if (l == 0) {
				--j;
			}
			if (l == 1) {
				++j;
			}
			if (l == 2) {
				--k;
			}
			if (l == 3) {
				++k;
			}
			if (l == 4) {
				--i;
			}
			if (l == 5) {
				++i;
			}
		}
		if (!entityplayer.canPlayerEdit(i, j, k, l, itemstack)) {
			return false;
		}
		Block block = LKMod.flowerBase;
		if (j < 255 && (world.getBlockId(i, j - 1, k) == Block.grass.blockID || world.getBlockId(i, j - 1, k) == Block.dirt.blockID || world.getBlockId(i, j - 1, k) == Block.tilledField.blockID) && isReplaceableBlock(world, i, j, k) && isReplaceableBlock(world, i, j + 1, k)) {
			if (!world.isRemote) {
				world.setBlock(i, j, k, LKMod.flowerBase.blockID, flowerMetadata, 3);
				world.setBlock(i, j + 1, k, LKMod.flowerTop.blockID, flowerMetadata, 3);
				world.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
				itemstack.stackSize--;
				return true;
			}
		}
		return false;
	}

	private boolean isReplaceableBlock(IBlockAccess world, int i, int j, int k) {
		int l = world.getBlockId(i, j, k);
		return l == 0 || Block.blocksList[l].blockMaterial.isReplaceable();
	}
}