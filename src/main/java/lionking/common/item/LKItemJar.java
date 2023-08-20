package lionking.common.item;

import lionking.common.entity.LKEntityZebra;
import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LKItemJar extends LKItem {
	private int isFull;

	public LKItemJar(int i, int j) {
		super(i);
		maxStackSize = 1;
		isFull = j;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		float f = 1.0F;
		double d = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * f;
		double d1 = (entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * f + 1.6200000000000001D) - entityplayer.yOffset;
		double d2 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * f;
		boolean flag = isFull == 0;
		MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, entityplayer, flag);
		if (movingobjectposition == null) {
			return itemstack;
		}
		if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
			int i = movingobjectposition.blockX;
			int j = movingobjectposition.blockY;
			int k = movingobjectposition.blockZ;
			if (!world.canMineBlock(entityplayer, i, j, k)) {
				return itemstack;
			}
			if (isFull == 0) {
				if (!entityplayer.canPlayerEdit(i, j, k, movingobjectposition.sideHit, itemstack)) {
					return itemstack;
				}
				if (world.getBlockMaterial(i, j, k) == Material.water && world.getBlockMetadata(i, j, k) == 0) {
					world.setBlockToAir(i, j, k);
					if (entityplayer.capabilities.isCreativeMode) {
						return itemstack;
					} else {
						return new ItemStack(LKMod.jarWater);
					}
				}
				if (world.getBlockMaterial(i, j, k) == Material.lava && world.getBlockMetadata(i, j, k) == 0) {
					world.setBlockToAir(i, j, k);
					if (entityplayer.capabilities.isCreativeMode) {
						return itemstack;
					} else {
						return new ItemStack(LKMod.jarLava);
					}
				}
			} else {
				if (isFull < 0) {
					return new ItemStack(LKMod.jar);
				}
				if (movingobjectposition.sideHit == 0) {
					j--;
				}
				if (movingobjectposition.sideHit == 1) {
					j++;
				}
				if (movingobjectposition.sideHit == 2) {
					k--;
				}
				if (movingobjectposition.sideHit == 3) {
					k++;
				}
				if (movingobjectposition.sideHit == 4) {
					i--;
				}
				if (movingobjectposition.sideHit == 5) {
					i++;
				}
				if (!entityplayer.canPlayerEdit(i, j, k, movingobjectposition.sideHit, itemstack)) {
					return itemstack;
				}
				if (world.isAirBlock(i, j, k) || !world.getBlockMaterial(i, j, k).isSolid()) {
					if (world.provider.isHellWorld && isFull == Block.waterMoving.blockID) {
						world.playSoundEffect(d + 0.5D, d1 + 0.5D, d2 + 0.5D, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
						for (int l = 0; l < 8; l++) {
							world.spawnParticle("largesmoke", i + Math.random(), j + Math.random(), k + Math.random(), 0.0D, 0.0D, 0.0D);
						}

					} else {
						world.setBlock(i, j, k, isFull, 0, 3);
					}
					if (entityplayer.capabilities.isCreativeMode) {
						return itemstack;
					} else {
						return new ItemStack(LKMod.jar);
					}
				}
			}
		} else if (isFull == 0 && (movingobjectposition.entityHit instanceof LKEntityZebra)) {
			return new ItemStack(LKMod.jarMilk);
		}
		return itemstack;
	}
}
