package lionking.common.item;

import lionking.common.LKMod;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LKItemLily extends LKItemMetadata {
	public LKItemLily(int i) {
		super(i);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		MovingObjectPosition m = getMovingObjectPositionFromPlayer(world, entityplayer, true);

		if (m != null) {
			if (m.typeOfHit == EnumMovingObjectType.TILE) {
				int i1 = m.blockX;
				int j1 = m.blockY;
				int k1 = m.blockZ;

				if (!world.canMineBlock(entityplayer, i1, j1, k1)) {
					return itemstack;
				}

				if (!entityplayer.canPlayerEdit(i1, j1, k1, m.sideHit, itemstack)) {
					return itemstack;
				}

				if (world.getBlockMaterial(i1, j1, k1) == Material.water && world.getBlockMetadata(i1, j1, k1) == 0 && world.isAirBlock(i1, j1 + 1, k1)) {
					world.setBlock(i1, j1 + 1, k1, LKMod.lily.blockID, getMetadata(itemstack.getItemDamage()), 3);

					if (!entityplayer.capabilities.isCreativeMode) {
						--itemstack.stackSize;
					}
				}
			}

		}
		return itemstack;
	}
}
