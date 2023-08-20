package lionking.common.item;

import lionking.common.LKCreativeTabs;
import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LKItemTunnahDiggah extends LKItemPickaxe {
	public LKItemTunnahDiggah(int i) {
		super(i, EnumToolMaterial.IRON);
		setMaxDamage(690);
		setCreativeTab(LKCreativeTabs.tabQuest);
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		return block.blockMaterial == Material.rock || block.blockID == Block.dirt.blockID || block.blockID == Block.grass.blockID;
	}

	private boolean shouldDamageWithLevel(int level) {
		return level == 0 || itemRand.nextFloat() >= 0.18F;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, int i, int j, int k, int l, EntityLivingBase entityliving) {
		itemstack.damageItem(1, entityliving);
		if (i == Block.dirt.blockID || i == Block.grass.blockID || i == LKMod.pridestone.blockID || i == Block.stone.blockID || i == Block.netherrack.blockID || i == Block.whiteStone.blockID) {
			int level = EnchantmentHelper.getEnchantmentLevel(LKMod.diggahEnchantment.effectId, itemstack) + 1;
			for (int j1 = -level; j1 <= level; j1++) {
				for (int k1 = -level; k1 <= level; k1++) {
					for (int l1 = -level; l1 <= level; l1++) {
						int i1 = world.getBlockId(j + j1, k + k1, l + l1);
						if (i1 == Block.dirt.blockID || i1 == Block.netherrack.blockID || i1 == Block.whiteStone.blockID) {
							world.setBlockToAir(j + j1, k + k1, l + l1);
							dropBlock(itemstack, world, j + j1, k + k1, l + l1, Block.blocksList[i1]);
							if (shouldDamageWithLevel(level)) {
								itemstack.damageItem(1, entityliving);
							}
						}
						if (i1 == LKMod.pridestone.blockID) {
							int metadata = world.getBlockMetadata(j + j1, k + k1, l + l1);
							world.setBlockToAir(j + j1, k + k1, l + l1);
							dropBlock(itemstack, world, j + j1, k + k1, l + l1, LKMod.pridestone, metadata);
							if (shouldDamageWithLevel(level)) {
								itemstack.damageItem(1, entityliving);
							}
						}
						if (i1 == Block.grass.blockID) {
							world.setBlockToAir(j + j1, k + k1, l + l1);
							boolean flag = EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, itemstack) > 0;
							dropBlock(itemstack, world, j + j1, k + k1, l + l1, flag ? Block.grass : Block.dirt);
							if (shouldDamageWithLevel(level)) {
								itemstack.damageItem(1, entityliving);
							}
						}
						if (i1 == Block.stone.blockID) {
							world.setBlockToAir(j + j1, k + k1, l + l1);
							boolean flag = EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, itemstack) > 0;
							dropBlock(itemstack, world, j + j1, k + k1, l + l1, flag ? Block.stone : Block.cobblestone);
							if (shouldDamageWithLevel(level)) {
								itemstack.damageItem(1, entityliving);
							}
						}
					}
				}
			}
		}
		return true;
	}

	private void dropBlock(ItemStack itemstack, World world, int i, int j, int k, Block block) {
		dropBlock(itemstack, world, i, j, k, block, 0);
	}

	private void dropBlock(ItemStack itemstack, World world, int i, int j, int k, Block block, int metadata) {
		if (world.isRemote) {
			return;
		}

		boolean drop = itemRand.nextInt(3) == 0;
		if (EnchantmentHelper.getEnchantmentLevel(LKMod.diggahPrecision.effectId, itemstack) > 0) {
			drop = itemRand.nextInt(3) > 0;
		}

		if (drop) {
			float f = 0.7F;
			double d = (world.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
			double d1 = (world.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
			double d2 = (world.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
			EntityItem entityitem = new EntityItem(world, i + d, j + d1, k + d2, new ItemStack(block, 1, metadata));
			entityitem.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(entityitem);
		}
	}
}
