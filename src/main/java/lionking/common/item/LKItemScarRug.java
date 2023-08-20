package lionking.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKCreativeTabs;
import lionking.common.entity.LKEntityScarRug;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LKItemScarRug extends LKItem {
	private int type;

	public LKItemScarRug(int i, int j) {
		super(i);
		type = j;
		setCreativeTab(LKCreativeTabs.tabQuest);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f, float f1, float f2) {
		int i1 = world.getBlockId(i, j, k);
		if (i1 == Block.snow.blockID) {
			l = 1;
		} else if (i1 != Block.vine.blockID && i1 != Block.tallGrass.blockID && i1 != Block.deadBush.blockID && (Block.blocksList[i1] != null && !Block.blocksList[i1].isBlockReplaceable(world, i, j, k))) {
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
		if (world.doesBlockHaveSolidTopSurface(i, j - 1, k) || world.isBlockNormalCube(i, j - 1, k)) {
			if (!world.isRemote) {
				LKEntityScarRug rug = new LKEntityScarRug(world, type);
				rug.setLocationAndAngles((double) i + f, j, (double) k + f2, (entityplayer.rotationYaw % 360.0F) + 180.0F, 0.0F);
				if (world.checkNoEntityCollision(rug.boundingBox) && world.getCollidingBoundingBoxes(rug, rug.boundingBox).isEmpty() && !world.isAnyLiquid(rug.boundingBox)) {
					world.spawnEntityInWorld(rug);
					world.playSoundAtEntity(rug, "lionking:lion", 1.0F, (itemRand.nextFloat() - itemRand.nextFloat()) * 0.2F + 1.0F);
					itemstack.stackSize--;
					return true;
				}
				rug.setDead();
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemstack) {
		return EnumRarity.uncommon;
	}
}
