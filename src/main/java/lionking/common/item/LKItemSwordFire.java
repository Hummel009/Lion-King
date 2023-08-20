package lionking.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LKItemSwordFire extends LKItemSword {
	public LKItemSwordFire(int i, EnumToolMaterial enumtoolmaterial) {
		super(i, enumtoolmaterial);
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1) {
		if (!entityliving.isImmuneToFire() && entityliving.getHealth() > 0) {
			entityliving.setFire(3 + itemRand.nextInt(3));
			for (int i = 0; i < 8; i++) {
				double d = itemRand.nextGaussian() * 0.02D;
				double d1 = itemRand.nextGaussian() * 0.02D;
				double d2 = itemRand.nextGaussian() * 0.02D;
				entityliving.worldObj.spawnParticle("flame", (entityliving.posX + (((double) (itemRand.nextFloat() * entityliving.width * 2.0F)) - entityliving.width) * 0.75F), entityliving.posY + 0.25F + (itemRand.nextFloat() * entityliving.height), (entityliving.posZ + (((double) (itemRand.nextFloat() * entityliving.width * 2.0F)) - entityliving.width) * 0.75F), d, d1, d2);
			}
			itemstack.damageItem(1, entityliving1);
			return true;
		}
		return super.hitEntity(itemstack, entityliving, entityliving1);
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
		if (entityplayer.canPlayerEdit(i, j, k, l, itemstack)) {
			if (world.isAirBlock(i, j, k)) {
				world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				world.setBlock(i, j, k, Block.fire.blockID, 0, 3);
			}
			itemstack.damageItem(1, entityplayer);
			return true;
		} else {
			return false;
		}
	}
}
