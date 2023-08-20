package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.LKMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class LKBlockOre extends LKBlock {
	@SideOnly(Side.CLIENT)
	private Icon nukaIcon;
	@SideOnly(Side.CLIENT)
	private Icon kivuliteIcon;

	public LKBlockOre(int i) {
		super(i, Material.rock);
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return blockID == LKMod.prideCoal.blockID ? (i == 0 ? Item.coal.itemID : LKMod.nukaShard.itemID) : blockID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		if (blockID == LKMod.prideCoal.blockID && j == 1) {
			return nukaIcon;
		}
		if (blockID == LKMod.oreSilver.blockID && j == 1) {
			return kivuliteIcon;
		}
		return super.getIcon(i, j);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconregister) {
		super.registerIcons(iconregister);
		if (blockID == LKMod.prideCoal.blockID) {
			nukaIcon = iconregister.registerIcon("lionking:oreNuka");
		}
		if (blockID == LKMod.oreSilver.blockID) {
			kivuliteIcon = iconregister.registerIcon("lionking:oreKivulite");
		}
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int i, int j, int k, int meta, float f, int fortune) {
		super.dropBlockAsItemWithChance(world, i, j, k, meta, f, fortune);
		int amount = 0;

		if (blockID == LKMod.prideCoal.blockID && meta == 0) {
			amount = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
		} else if (blockID == LKMod.prideCoal.blockID && meta == 1) {
			amount = MathHelper.getRandomIntegerInRange(world.rand, 0, 1);
		}

		if (amount > 0) {
			dropXpOnBlockBreak(world, i, j, k, amount);
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l) {
		if (blockID == LKMod.prideCoal.blockID && l == 1 && !EnchantmentHelper.getSilkTouchModifier(entityplayer)) {
			Random random = world.rand;
			int i1 = random.nextInt(10) == 0 ? 1 : (random.nextInt(3) == 0 ? 2 : 3);
			for (int j1 = 0; j1 < i1; j1++) {
				super.harvestBlock(world, entityplayer, i, j, k, 1);
			}
		} else {
			super.harvestBlock(world, entityplayer, i, j, k, l);
		}
	}

	@Override
	public int damageDropped(int i) {
		if (blockID == LKMod.oreSilver.blockID) {
			return i;
		}
		return 0;
	}

	@Override
	public int quantityDroppedWithBonus(int i, Random random) {
		if (i > 0 && blockID == LKMod.prideCoal.blockID) {
			int j = random.nextInt(i + 2) - 1;
			if (j < 0) {
				j = 0;
			}
			return quantityDropped(random) * (j + 1);
		} else {
			return quantityDropped(random);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int i, CreativeTabs tab, List list) {
		if (i == LKMod.prideCoal.blockID || i == LKMod.oreSilver.blockID) {
			for (int j = 0; j < 2; j++) {
				list.add(new ItemStack(i, 1, j));
			}
		} else {
			super.getSubBlocks(i, tab, list);
		}
	}

	@Override
	public int getDamageValue(World world, int i, int j, int k) {
		return world.getBlockMetadata(i, j, k);
	}
}
