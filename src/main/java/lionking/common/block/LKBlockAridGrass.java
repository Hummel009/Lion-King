package lionking.common.block;

import lionking.common.LKCreativeTabs;
import lionking.common.LKMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLogic;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.ArrayList;
import java.util.Random;

public class LKBlockAridGrass extends BlockFlower implements IShearable {
	private static Material material = (new MaterialLogic(MapColor.sandColor)).setReplaceable();

	public LKBlockAridGrass(int i) {
		super(i, material);
		setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.5F, 0.9F);
		setCreativeTab(LKCreativeTabs.tabDeco);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		int l = world.getBlockId(i, j - 1, k);
		return ((world.getFullBlockLightValue(i, j, k) >= 8 || world.canBlockSeeTheSky(i, j, k)) && (l == Block.sand.blockID || l == LKMod.tilledSand.blockID));
	}

	@Override
	public boolean isBlockReplaceable(World world, int i, int j, int k) {
		return true;
	}

	@Override
	public int getRenderType() {
		return LKMod.proxy.getAridGrassRenderID();
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return Block.tallGrass.idDropped(i, random, j);
	}

	@Override
	public int quantityDroppedWithBonus(int i, Random random) {
		return Block.tallGrass.quantityDroppedWithBonus(i, random);
	}

	@Override
	public ArrayList getBlockDropped(World world, int i, int j, int k, int meta, int fortune) {
		return Block.tallGrass.getBlockDropped(world, i, j, k, meta, fortune);
	}

	@Override
	public boolean isShearable(ItemStack item, World world, int i, int j, int k) {
		return Block.tallGrass.isShearable(item, world, i, j, k);
	}

	@Override
	public ArrayList onSheared(ItemStack item, World world, int i, int j, int k, int fortune) {
		ArrayList list = new ArrayList();
		list.add(new ItemStack(this));
		return list;
	}

	@Override
	public int getMobilityFlag() {
		return 1;
	}
}
