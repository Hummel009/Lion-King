package lionking.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lionking.common.entity.LKEntityTermite;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class LKBlockTermite extends LKBlock {
	public LKBlockTermite(int i) {
		super(i, Material.ground);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l) {
		if (l == 0 && !world.isRemote && world.rand.nextBoolean()) {
			int i1 = world.rand.nextInt(3);
			for (int i2 = 0; i2 < i1; i2++) {
				spawnTermite(world, i, j, k);
			}
		}
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int i, int j, int k, int meta, float f, int fortune) {
		if (meta == 0 && !world.isRemote && world.rand.nextBoolean()) {
			spawnTermite(world, i, j, k);
		}
		super.dropBlockAsItemWithChance(world, i, j, k, meta, f, fortune);
	}

	private void spawnTermite(World world, int i, int j, int k) {
		LKEntityTermite termite = new LKEntityTermite(world);
		termite.setLocationAndAngles(i + 0.5D, j, k + 0.5D, 0.0F, 0.0F);
		world.spawnEntityInWorld(termite);
	}

	@Override
	public int damageDropped(int i) {
		return i == 1 ? 1 : 0;
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		return meta == 1 ? 1 : 0;
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return i == 1 ? blockID : 0;
	}

	@Override
	protected ItemStack createStackedBlock(int i) {
		return new ItemStack(this, 1, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int i, CreativeTabs tab, List list) {
		for (int j = 0; j < 2; j++) {
			list.add(new ItemStack(i, 1, j));
		}
	}
}
